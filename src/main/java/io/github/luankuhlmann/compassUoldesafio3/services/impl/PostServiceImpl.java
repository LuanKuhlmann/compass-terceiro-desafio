package io.github.luankuhlmann.compassUoldesafio3.services.impl;

import io.github.luankuhlmann.compassUoldesafio3.client.ExternalApiClient;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import io.github.luankuhlmann.compassUoldesafio3.repository.PostRepository;
import io.github.luankuhlmann.compassUoldesafio3.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final ExternalApiClient externalApiClient;

    @Override
    public void processPost(Long postId) {

    }

    @Override
    public void disablePost(Long postId) {

    }

    @Override
    public void reprocessPost(Long postId) {

    }

    @Override
    public List<Post> queryPosts() {
        return null;
    }
}
