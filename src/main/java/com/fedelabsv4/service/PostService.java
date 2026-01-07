package com.fedelabsv4.service;

import com.fedelabsv4.dominio.Post;
import com.fedelabsv4.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    public List<Post> getLatestPublishedPosts(int limit) {
        List<Post> posts = postRepository.findByPublishedTrueOrderByPublishedAtDesc();
        return posts.size() > limit ? posts.subList(0, limit) : posts;
    }
    
    public List<Post> getAllPublishedPosts() {
        return postRepository.findByPublishedTrueOrderByPublishedAtDesc();
    }
    
    public Optional<Post> getPostBySlug(String slug) {
        return postRepository.findBySlugAndPublishedTrue(slug);
    }
    
    public Post savePost(Post post) {
        return postRepository.save(post);
    }
    
    public Post publishPost(Long postId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            post.publish();
            return postRepository.save(post);
        }
        return null;
    }
}
