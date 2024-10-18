package com.study.BlogPlatform.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title, anons, full_text;
    private int views;

    public Post(String title, String anons, String fullText) {
        this.anons = anons;
        this.title = title;
        this.full_text = fullText;
    }

    public Post() {

    }
}
