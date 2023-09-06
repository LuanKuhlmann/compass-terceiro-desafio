package io.github.luankuhlmann.compassUoldesafio3.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.PostHistory;

import java.util.List;

public interface PostService {


    void processPost(Long postId) throws JsonProcessingException;

    void processingPost(Long postId, List<PostHistory> histories) throws JsonProcessingException;

    void processingComments(Long postId, Post post, List<PostHistory> histories) throws JsonProcessingException;

    void failedHistory(Long postId, List<PostHistory> histories);

    void enabledHistory(Long postId, Post post, List<PostHistory> histories);

    void disablePost(Long postId);

    void reprocessPost(Long postId) throws JsonProcessingException;

    List<Post> queryPosts();

}
