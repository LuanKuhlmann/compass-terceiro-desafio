package io.github.luankuhlmann.compassUoldesafio3.services;


import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;

import java.util.List;

public interface PostService {

    void processPost(Long postId);

    void disablePost(Long postId);

    void reprocessPost(Long postId);

    List<Post> queryPosts();

}
