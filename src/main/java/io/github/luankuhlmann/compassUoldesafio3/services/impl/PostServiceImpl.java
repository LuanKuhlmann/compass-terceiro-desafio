package io.github.luankuhlmann.compassUoldesafio3.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.luankuhlmann.compassUoldesafio3.client.ExternalApiClient;
import io.github.luankuhlmann.compassUoldesafio3.client.mqueues.MessagePublisher;
import io.github.luankuhlmann.compassUoldesafio3.domain.enums.PostState;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Comment;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.PostHistory;
import io.github.luankuhlmann.compassUoldesafio3.dto.CommentDto;
import io.github.luankuhlmann.compassUoldesafio3.dto.PostDto;
import io.github.luankuhlmann.compassUoldesafio3.exceptions.NotFoundException;
import io.github.luankuhlmann.compassUoldesafio3.exceptions.InvalidPostIdValueException;
import io.github.luankuhlmann.compassUoldesafio3.exceptions.PostAlreadyInProcessException;
import io.github.luankuhlmann.compassUoldesafio3.exceptions.StatusNotValidException;
import io.github.luankuhlmann.compassUoldesafio3.repository.PostHistoryRepository;
import io.github.luankuhlmann.compassUoldesafio3.repository.PostRepository;
import io.github.luankuhlmann.compassUoldesafio3.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final PostHistoryRepository postHistoryRepository;

    @Autowired
    private final ExternalApiClient externalApiClient;

    @Autowired
    private final ModelMapper mapper;

    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private final MessagePublisher messagePublisher;

    @Override
    public void processPost(Long postId) throws JsonProcessingException {
        idSizeValidation(postId);
        log.info("Processing post");
        Post post = new Post();
        post.setId(postId);

        if (postRepository.findById(postId).isPresent()) throw new PostAlreadyInProcessException(String.format("Id %d is already in use", postId));
        postRepository.save(post);

        List<PostHistory> histories = new ArrayList<>();
        var state = createHistories(PostState.CREATED, postId);
        histories.add(state);
        post.setHistories(histories);

        PostState postStatus = post.getHistories().get(post.getHistories().size()-1).getStatus();
        if (!postStatus.equals(PostState.CREATED)) throw new StatusNotValidException("Post state: %s is not valid for this action", postStatus);
        postFindHistory(postId, histories);
    }

    @Override
    @Async
    public void postFindHistory(Long postId, List<PostHistory> histories) throws JsonProcessingException {
        PostDto postDto = externalApiClient.findPostById(postId);

        if(postDto == null) {
            failedHistory(postId, histories);
            throw new NotFoundException("Post was not found");
        }

        String messageStr = objectMapper.writeValueAsString(postDto);
        messagePublisher.sendPostMessage("process_posts", messageStr);

        var state = createHistories(PostState.POST_FIND, postId);
        histories.add(state);

        postOkHistory(postId, postDto, histories);
    }

    @Override
    public void postOkHistory(Long postId, PostDto postDto, List<PostHistory> histories) throws JsonProcessingException {
        Post post = mapPostToEntity(postDto);

        var state = createHistories(PostState.POST_OK, postId);
        histories.add(state);

        post.setHistories(histories);
        postRepository.save(post);

        PostState postStatus = post.getHistories().get(post.getHistories().size()-1).getStatus();
        if (!postStatus.equals(PostState.POST_OK)) throw new StatusNotValidException("Post state: %s is not valid for this action", postStatus);

        commentsFindHistory(postId, post, histories);
    }

    @Override
    @Async
    public void commentsFindHistory(Long postId, Post post, List<PostHistory> histories) throws JsonProcessingException {
        log.info("Post CREATED");
        List<CommentDto> findComments = externalApiClient.findCommentByPostId(postId);
        if(findComments.isEmpty()) {
            failedHistory(postId, histories);
            throw new NotFoundException(String.format("Couldn't find comments for post id: %d" , postId));
        }

        var state = createHistories(PostState.COMMENTS_FIND, postId);
        histories.add(state);

        post.setHistories(histories);
        postRepository.save(post);

        PostState postStatus = post.getHistories().get(post.getHistories().size()-1).getStatus();
        if (!postStatus.equals(PostState.COMMENTS_FIND)) throw new StatusNotValidException("Post state: %s is not valid for this action", postStatus);

        commentsOkHistory(postId, post, histories, findComments);
    }

    @Override
    public void commentsOkHistory(Long postId, Post post, List<PostHistory> histories, List<CommentDto> findComments) {
        List<Comment> comments = new ArrayList<>();

        for (CommentDto foundComment : findComments) {
            CommentDto commentDto = new CommentDto(foundComment.getId(), foundComment.getBody());
            Comment comment = mapCommentToEntity(commentDto);
            comments.add(comment);
        }

        post.setComments(comments);

        var state = createHistories(PostState.COMMENTS_OK, postId);
        histories.add(state);

        post.setHistories(histories);
        postRepository.save(post);

        PostState postStatus = post.getHistories().get(post.getHistories().size()-1).getStatus();
        if (!postStatus.equals(PostState.COMMENTS_OK)) throw new StatusNotValidException("Post state: %s is not valid for this action", postStatus);

        log.info("Comment's OK");
        enabledHistory(postId, post, histories);
    }

    @Override
    public void enabledHistory(Long postId, Post post, List<PostHistory> histories) {
        PostHistory enabledHistory = new PostHistory(PostState.ENABLED, postId);
        postHistoryRepository.save(enabledHistory);
        histories.add(enabledHistory);

        post.setHistories(histories);
        postRepository.save(post);
        log.info("Post ENABLED");
    }

    @Override
    public void failedHistory(Long postId, List<PostHistory> histories) {
        var state = createHistories(PostState.FAILED, postId);
        histories.add(state);

        var post = findPostInDatabase(postId);
        post.setHistories(histories);

        postRepository.save(post);
        log.info("Post FAILED to be processed");
        disablePost(postId);
    }

    @Override
    public void disablePost(Long postId) {
        idSizeValidation(postId);
        var post = findPostInDatabase(postId);;

        List<PostHistory> histories = post.getHistories();
        PostState postStatus = post.getHistories().get(post.getHistories().size()-1).getStatus();
        if (!postStatus.equals(PostState.ENABLED) && postStatus.equals(PostState.FAILED)) throw new StatusNotValidException("Post state: %s is not valid for this action", postStatus);

        var state = createHistories(PostState.DISABLED, postId);
        histories.add(state);

        post.setHistories(histories);
        log.info("Post DISABLED");
    }

    @Override
    public void reprocessPost(Long postId) throws JsonProcessingException {
        idSizeValidation(postId);
        var post = findPostInDatabase(postId);

        List<PostHistory> histories = post.getHistories();
        PostState postStatus = post.getHistories().get(post.getHistories().size()-1).getStatus();
        if (!postStatus.equals(PostState.ENABLED) && postStatus.equals(PostState.DISABLED)) throw new StatusNotValidException("Post state: %s is not valid for this action", postStatus);

        var state = createHistories(PostState.UPDATING, postId);
        histories.add(state);

        post.setHistories(histories);
        postRepository.save(post);

        postFindHistory(postId, histories);
        log.info("Post will be UPDATED");
    }

    @Override
    public List<Post> queryPosts() {
        if(postRepository.findAll().isEmpty()) throw new NotFoundException("Database is empty!");
        log.info("Printing all posts");
        return postRepository.findAll();
    }

    private Post mapPostToEntity(PostDto postDto) {
        return mapper.map(postDto, Post.class);
    }

    private Comment mapCommentToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }

    private void idSizeValidation(Long postId) {
        if (postId < 0 || postId > 100) throw new InvalidPostIdValueException("Id must be between 0 and 100");
    }

    private PostHistory createHistories(PostState state, Long postId) {
        PostHistory status = new PostHistory(state, postId);
        postHistoryRepository.save(status);
        return status;
    }

    private Post findPostInDatabase(Long postId) {
        Optional<Post> postOptional = postRepository.findPostById(postId);
        if(postOptional.isEmpty()) throw new InvalidPostIdValueException(String.format("ID %d is not present on database", postId));
        return postOptional.get();
    }
}