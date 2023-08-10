package io.github.luankuhlmann.compassUoldesafio3.services.impl;

import io.github.luankuhlmann.compassUoldesafio3.client.ExternalApiClient;
import io.github.luankuhlmann.compassUoldesafio3.domain.PostState;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Comment;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.PostHistory;
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

    private ModelMapper mapper;

    private PostDto mapToDTO(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        return post;
    }

    @Override
    public Post processPost(Long postId) {

        if (postId < 0 || postId > 100) throw new InvalidPostIdValueException("Id should be between 0 and 100");

        Post post = new Post();
        post.setId(postId);

        if (postRepository.findById(postId).isPresent()) throw new PostAlreadyInProcessException("Id: %s is already in use", postId);

        postRepository.save(post);

        List<PostHistory> histories = new ArrayList<>();
        PostHistory createdHistory = new PostHistory(PostState.CREATED, postId);
        postHistoryRepository.save(createdHistory);
        histories.add(createdHistory);

        return postFindHistory(postId, post, histories);

    }

    @Override
    public Post postFindHistory(Long postId, Post post, List<PostHistory> histories) {

        post = externalApiClient.findPostById(postId);

        PostHistory postFindHistory = new PostHistory(PostState.POST_FIND, postId);
        postHistoryRepository.save(postFindHistory);
        histories.add(postFindHistory);

        return postOkHistory(postId, post, histories);
    }

    @Override
    public Post postOkHistory(Long postId, Post post, List<PostHistory> histories) {

        PostHistory postOkHistory = new PostHistory(PostState.POST_OK, postId);
        postHistoryRepository.save(postOkHistory);
        histories.add(postOkHistory);

        post.setHistories(histories);

        postRepository.save(post);

        return commentsFindHistory(postId, post, histories);
    }

    @Override
    public Post commentsFindHistory(Long postId, Post post, List<PostHistory> histories) {

        List<Comment> comments = new ArrayList<>();

        List<Comment> findComments = externalApiClient.findCommentByPostId(postId);

        for (Comment foundComment : findComments) {
            Comment comment = new Comment(foundComment.getId(), foundComment.getBody(), postId);
            comments.add(comment);
        }

        PostHistory commentFindHistory = new PostHistory(PostState.COMMENTS_FIND, postId);
        postHistoryRepository.save(commentFindHistory);
        histories.add(commentFindHistory);

        post.setComments(comments);

        return commentsOkHistory(postId, post, histories);
    }

    @Override
    public Post commentsOkHistory(Long postId, Post post, List<PostHistory> histories) {

        PostHistory commentOkHistory = new PostHistory(PostState.COMMENTS_OK, postId);
        postHistoryRepository.save(commentOkHistory);
        histories.add(commentOkHistory);

        return enabledHistory(postId, post, histories);
    }

    @Override
    public Post enabledHistory(Long postId, Post post, List<PostHistory> histories) {

        PostHistory enabledHistory = new PostHistory(PostState.ENABLED, postId);
        postHistoryRepository.save(enabledHistory);
        histories.add(enabledHistory);

        post.setHistories(histories);

        postRepository.save(post);

        return post;
    }


    public void disablePost(Long postId) {

        if (postId < 0 || postId > 100) throw new InvalidPostIdValueException("Id should be between 0 and 100");

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

        if (postId < 0 || postId > 100) throw new InvalidPostIdValueException("Id should be between 0 and 100");

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

        postFindHistory(postId, post, histories);

    }

    public List<Post> queryPosts() {
        var response = postRepository.findAll();

        return response;
    }

}
