package io.github.luankuhlmann.compassUoldesafio3.services.impl;

import io.github.luankuhlmann.compassUoldesafio3.client.ExternalApiClient;
import io.github.luankuhlmann.compassUoldesafio3.domain.PostState;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Comment;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.PostHistory;
import io.github.luankuhlmann.compassUoldesafio3.dto.CommentDto;
import io.github.luankuhlmann.compassUoldesafio3.dto.PostDto;
import io.github.luankuhlmann.compassUoldesafio3.exceptions.InvalidPostIdValueException;
import io.github.luankuhlmann.compassUoldesafio3.exceptions.handler.PostAlreadyInProcessException;
import io.github.luankuhlmann.compassUoldesafio3.repository.PostHistoryRepository;
import io.github.luankuhlmann.compassUoldesafio3.repository.PostRepository;
import io.github.luankuhlmann.compassUoldesafio3.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
        Post post = new Post();
        post.setId(postId);

        if (postId < 0 || postId > 100) throw new InvalidPostIdValueException("Id should be between 0 and 100");

        if (postRepository.findById(postId).isPresent()) throw new PostAlreadyInProcessException("Id: %s is already in use", postId);

        return createdHistory(postId, post);
    }

    @Override
    public Post createdHistory(Long postId, Post post) {
        List<PostHistory> histories = new ArrayList<>();
        PostHistory createdHistory = new PostHistory(Instant.now(Clock.systemDefaultZone()), PostState.CREATED);
        postHistoryRepository.save(createdHistory);
        histories.add(createdHistory);

        return postFindHistory(postId, post, histories);
    }

    @Override
    public Post postFindHistory(Long postId, Post post, List<PostHistory> histories) {
        post = externalApiClient.findPostById(postId);
        PostHistory postFindHistory = new PostHistory(Instant.now(Clock.systemDefaultZone()), PostState.POST_FIND);
        postHistoryRepository.save(postFindHistory);
        histories.add(postFindHistory);

        return postOkHistory(postId, post, histories);
    }

    @Override
    public Post postOkHistory(Long postId, Post post, List<PostHistory> histories) {
        PostHistory postOkHistory = new PostHistory(Instant.now(Clock.systemDefaultZone()), PostState.POST_OK);
        postHistoryRepository.save(postOkHistory);
        histories.add(postOkHistory);

        return commentsFindHistory(postId, post, histories);
    }

    @Override
    public Post commentsFindHistory(Long postId, Post post, List<PostHistory> histories) {
        List<Comment> comments = new ArrayList<>();
        List<Comment> findComments = externalApiClient.findCommentByPostId(postId);

        for (Comment foundComment : findComments) {
            Comment comment = new Comment(foundComment.getId(), foundComment.getBody());
            comments.add(comment);
        }

        PostHistory commentFindHistory = new PostHistory(Instant.now(Clock.systemDefaultZone()), PostState.COMMENTS_FIND);
        postHistoryRepository.save(commentFindHistory);
        histories.add(commentFindHistory);
        post.setComments(comments);

        return commentsOkHistory(postId, post, histories);
    }

    @Override
    public Post commentsOkHistory(Long postId, Post post, List<PostHistory> histories) {
        PostHistory commentOkHistory = new PostHistory(Instant.now(Clock.systemDefaultZone()), PostState.COMMENTS_OK);
        postHistoryRepository.save(commentOkHistory);
        histories.add(commentOkHistory);

        post.setHistories(histories);
        postRepository.save(post);

        return post;
    }


    public void disablePost(Long postId) {

    }

    public void reprocessPost(Long postId) {

    }

    public List<Post> queryPosts() {
        return null;
    }

}
