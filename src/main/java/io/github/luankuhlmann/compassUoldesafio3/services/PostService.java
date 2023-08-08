package io.github.luankuhlmann.compassUoldesafio3.services;

import io.github.luankuhlmann.compassUoldesafio3.client.ExternalApiClient;
import io.github.luankuhlmann.compassUoldesafio3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final ExternalApiClient externalApiClient;


    public void save(Long postId) {
        System.out.println(externalApiClient.findPostById(postId));
    }


}
