package com.fedelabsv4.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String slug;
    private String content;
    private String type;
    private int estimatedMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
    private boolean published;
    
    public Post() {}
    
    public Post(String title, String slug, String content, String type, int estimatedMinutes) {
        this.title = title;
        this.slug = slug;
        this.content = content;
        this.type = type;
        this.estimatedMinutes = estimatedMinutes;
        this.createdAt = LocalDateTime.now();
        this.published = false;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }
    
    public void setEstimatedMinutes(int estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
    
    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
    
    public boolean isPublished() {
        return published;
    }
    
    public void setPublished(boolean published) {
        this.published = published;
        if (published && publishedAt == null) {
            this.publishedAt = LocalDateTime.now();
        }
    }
    
    public void publish() {
        this.published = true;
        this.publishedAt = LocalDateTime.now();
    }
}
