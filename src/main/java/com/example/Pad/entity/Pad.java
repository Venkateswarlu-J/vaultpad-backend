package com.example.Pad.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pads")
public class Pad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pad_key", unique = true, nullable = false, length = 100)
    private String padKey;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(length = 255)
    private String password;

    @Column(name = "is_locked")
    private Boolean isLocked = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPadKey() {
        return padKey;
    }

    public void setPadKey(String padKey) {
        this.padKey = padKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Pad{" +
                "id=" + id +
                ", padKey='" + padKey + '\'' +
                ", content='" + content + '\'' +
                ", password='" + password + '\'' +
                ", isLocked=" + isLocked +
                ", createdAt=" + createdAt +
                '}';
    }

}
