package com.study.BlogPlatform.controllers;

import com.study.BlogPlatform.models.Post;
import com.study.BlogPlatform.repo.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BlogController.class)
class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @Test
    void blogMain_ShouldReturnBlogMainView() throws Exception {
        Mockito.when(postRepository.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/blog"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog-main"))
                .andExpect(model().attributeExists("posts", "title1"))
                .andExpect(model().attribute("title1", "анонс"));
    }

    @Test
    void blogAdd_ShouldReturnBlogAddView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/blog/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog-add"));
    }

    @Test
    void blogPostAdd_ShouldRedirectToBlog() throws Exception {
        Mockito.when(postRepository.save(any(Post.class))).thenReturn(new Post());

        mockMvc.perform(MockMvcRequestBuilders.post("/blog/add")
                        .param("title", "Test Title")
                        .param("anons", "Test Anons")
                        .param("full_text", "Test Full Text"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog"));
    }

    @Test
    void blogDetails_ShouldReturnBlogDetailsView_WhenPostExists() throws Exception {
        Post post = new Post("Title", "Anons", "Full text");
        Mockito.when(postRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        mockMvc.perform(MockMvcRequestBuilders.get("/blog/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("blog-details"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    void blogDetails_ShouldRedirectToBlog_WhenPostDoesNotExist() throws Exception {
        Mockito.when(postRepository.existsById(anyLong())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/blog/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog"));
    }

    @Test
    void blogPostDelete_ShouldRedirectToBlog() throws Exception {
        Post post = new Post("Title", "Anons", "Full text");
        Mockito.when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        mockMvc.perform(MockMvcRequestBuilders.post("/blog/1/remove"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog"));

        Mockito.verify(postRepository).delete(post);
    }
}
