package io.github.luankuhlmann.compassUoldesafio3.services;


import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.PostHistory;
import io.github.luankuhlmann.compassUoldesafio3.dto.CommentDto;
import io.github.luankuhlmann.compassUoldesafio3.dto.PostDto;

import java.util.List;

public interface PostService {


    void processPost(Long postId);

    void postFindHistory(Long postId, List<PostHistory> histories);

    void postOkHistory(Long postId, PostDto postDto, List<PostHistory> histories);

    void commentsFindHistory(Long postId, Post post, List<PostHistory> histories);

    void commentsOkHistory(Long postId, Post post, List<PostHistory> histories, List<CommentDto> findComments);

    void enabledHistory(Long postId, Post post, List<PostHistory> histories);

    void disablePost(Long postId);

    void reprocessPost(Long postId);

    List<Post> queryPosts();

}
