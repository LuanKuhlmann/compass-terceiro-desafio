package io.github.luankuhlmann.compassUoldesafio3.client;

import io.github.luankuhlmann.compassUoldesafio3.dto.PostDtoRequest;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "ExternalApiClient", url = "https://jsonplaceholder.typicode.com/posts")
public interface ExternalApiClient {

    @GetMapping("/{postId}")
    PostDtoRequest findById(@PathVariable("postId") long postId);

}
