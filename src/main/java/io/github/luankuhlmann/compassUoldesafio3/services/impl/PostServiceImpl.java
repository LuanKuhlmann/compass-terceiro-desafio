package io.github.luankuhlmann.compassUoldesafio3.services.impl;

import io.github.luankuhlmann.compassUoldesafio3.client.ExternalApiClient;
import io.github.luankuhlmann.compassUoldesafio3.domain.PostState;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Comment;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.PostHistory;
import io.github.luankuhlmann.compassUoldesafio3.dto.CommentDto;
import io.github.luankuhlmann.compassUoldesafio3.dto.PostDto;
import io.github.luankuhlmann.compassUoldesafio3.exceptions.InvalidPostIdValueException;
import io.github.luankuhlmann.compassUoldesafio3.exceptions.PostAlreadyInProcessException;
import io.github.luankuhlmann.compassUoldesafio3.exceptions.StatusNotValidException;
import io.github.luankuhlmann.compassUoldesafio3.repository.PostHistoryRepository;
import io.github.luankuhlmann.compassUoldesafio3.repository.PostRepository;
import io.github.luankuhlmann.compassUoldesafio3.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final PostHistoryRepository postHistoryRepository;

    @Autowired
    private final ExternalApiClient externalApiClient;

    @Autowired
    private final ModelMapper mapper;

    private Post mapPostToEntity(PostDto postDto) {
        return mapper.map(postDto, Post.class);
    }

    private Comment mapCommentToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }

    @Override
    @Async
    public void processPost(Long postId) {
        if (postId < 1 || postId > 100) throw new InvalidPostIdValueException("Id must be between");

        Post post = new Post();
        post.setId(postId);

        if (postRepository.findById(postId).isPresent()) throw new PostAlreadyInProcessException(String.format("Id %d is already in use", postId));

        postRepository.save(post);

        List<PostHistory> histories = new ArrayList<>();
        PostHistory createdHistory = new PostHistory(PostState.CREATED, postId);
        postHistoryRepository.save(createdHistory);
        histories.add(createdHistory);

        postFindHistory(postId, histories);
    }

    @Override
    public void postFindHistory(Long postId, List<PostHistory> histories) {
        PostDto postDto = externalApiClient.findPostById(postId);

        PostHistory postFindHistory = new PostHistory(PostState.POST_FIND, postId);
        postHistoryRepository.save(postFindHistory);
        histories.add(postFindHistory);

        postOkHistory(postId, postDto, histories);
    }

    @Override
    public void postOkHistory(Long postId, PostDto postDto, List<PostHistory> histories) {
        Post post = mapPostToEntity(postDto);

        PostHistory postOkHistory = new PostHistory(PostState.POST_OK, postId);
        postHistoryRepository.save(postOkHistory);
        histories.add(postOkHistory);


        post.setHistories(histories);
        postRepository.save(post);

        commentsFindHistory(postId, post, histories);
    }

    @Override
    public void commentsFindHistory(Long postId, Post post, List<PostHistory> histories) {
        List<CommentDto> findComments = externalApiClient.findCommentByPostId(postId);

        PostHistory commentFindHistory = new PostHistory(PostState.COMMENTS_FIND, postId);
        postHistoryRepository.save(commentFindHistory);
        histories.add(commentFindHistory);

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

        PostHistory commentOkHistory = new PostHistory(PostState.COMMENTS_OK, postId);
        postHistoryRepository.save(commentOkHistory);
        histories.add(commentOkHistory);

        enabledHistory(postId, post, histories);
    }

    @Override
    public void enabledHistory(Long postId, Post post, List<PostHistory> histories) {
        PostHistory enabledHistory = new PostHistory(PostState.ENABLED, postId);
        postHistoryRepository.save(enabledHistory);
        histories.add(enabledHistory);

        post.setHistories(histories);

        postRepository.save(post);
    }


    public void disablePost(Long postId) {
        if (postId < 0 || postId > 100) throw new InvalidPostIdValueException("Id must be between 0 and 100");
        if (postRepository.findById(postId).isEmpty()) throw new InvalidPostIdValueException(String.format("Id %d is not present on database", postId));

        Optional<Post> postOptional = postRepository.findPostById(postId);
        Post post = postOptional.get();

        List<PostHistory> histories = post.getHistories();
        PostState state = post.getHistories().get(post.getHistories().size()-1).getStatus();

        if (state.equals(PostState.ENABLED)) {
            PostHistory disabledHistory = new PostHistory(PostState.DISABLED, post.getId());
            postHistoryRepository.save(disabledHistory);
            histories.add(disabledHistory);

            post.setHistories(histories);
        }
    }

    public void reprocessPost(Long postId) {
        if (postId < 0 || postId > 100) throw new InvalidPostIdValueException("Id must be between 0 and 100");

        Optional<Post> postOptional = postRepository.findPostById(postId);
        Post post = postOptional.get();

        List<PostHistory> histories = post.getHistories();

        PostState state = post.getHistories().get(post.getHistories().size()-1).getStatus();

        if (!state.equals(PostState.ENABLED) && state.equals(PostState.DISABLED)) throw new StatusNotValidException("Post state: %s is not valid for this action", state);

        PostHistory updatingHistory = new PostHistory(PostState.UPDATING, post.getId());
        postHistoryRepository.save(updatingHistory);
        histories.add(updatingHistory);

        post.setHistories(histories);
        postRepository.save(post);

        postFindHistory(postId, histories);
    }

    public List<Post> queryPosts() {
        return postRepository.findAll();
    }

}
