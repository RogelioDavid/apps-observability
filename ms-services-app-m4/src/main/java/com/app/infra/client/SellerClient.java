package com.app.infra.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.model.transfer.Post;

@FeignClient(name = "jsonPlaceholderClient", url = "https://jsonplaceholder.typicode.com")
public interface SellerClient {

    @GetMapping("/posts")
    List<Post> getAllPosts();

    @GetMapping("/posts/{id}")
    Post getPostById(@PathVariable("id") Long id);
}
