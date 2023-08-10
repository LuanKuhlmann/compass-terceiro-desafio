package io.github.luankuhlmann.compassUoldesafio3.services;


import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.PostHistory;
import io.github.luankuhlmann.compassUoldesafio3.dto.PostDto;

import java.util.List;

public interface PostService {


    Post processPost(Long postId);

    Post postFindHistory(Long postId, Post post, List<PostHistory> histories);

    Post postOkHistory(Long postId, Post post, List<PostHistory> histories);

    Post commentsFindHistory(Long postId, Post post, List<PostHistory> histories);

    Post commentsOkHistory(Long postId, Post post, List<PostHistory> histories);

    Post enabledHistory(Long postId, Post post, List<PostHistory> histories);

    void disablePost(Long postId);

    void reprocessPost(Long postId);

    List<Post> queryPosts();

}
