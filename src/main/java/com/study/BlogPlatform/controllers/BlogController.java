package com.study.BlogPlatform.controllers;

import com.study.BlogPlatform.models.Post;
import com.study.BlogPlatform.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 *  Контроллер для управления постами блога.
 * Предоставляет методы для отображения, добавления, редактирования и удаления публикаций.
 */
@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    /**
     *  Отображает все посты блога.
     *
     * @param model объект Model для передачи данных на страницу
     * @return имя шаблона страницы блога
     */
    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("title1", "анонс");
        return "blog-main";
    }

    /**
     * Возвращает текст первой публикации.
     *
     * @return текст первой публикации или сообщение "Статья не найдена", если постов нет
     */
    @GetMapping("/blog/first")
    @ResponseBody
    public String getFirstPost() {
        Optional<Post> firstPost = StreamSupport.stream(postRepository.findAll().spliterator(), false).findFirst();
        return firstPost.map(Post::getFull_text).orElse("Статья не найдена");
    }

    /**
     * Возвращает страницу для добавления новой публикации.
     *
     * @param model объект Model для передачи данных на страницу
     * @return имя шаблона страницы добавления публикации
     */
    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    /**
     * Обрабатывает запрос на добавление новой публикации.
     *
     * @param title заголовок публикации
     * @param anons краткое описание публикации
     * @param full_text полный текст публикации
     * @param model объект Model для передачи данных на страницу
     * @return перенаправление на страницу блога после успешного добавления
     */
    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    /**
     * Возвращает страницу с деталями конкретной публикации по ID.
     *
     * @param id идентификатор публикации
     * @param model объект Model для передачи данных на страницу
     * @return имя шаблона страницы с деталями публикации
     */
    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model){
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }


    /**
     * Возвращает страницу для редактирования конкретной публикации по ID.
     *
     * @param id идентификатор публикации
     * @param model объект Model для передачи данных на страницу
     * @return имя шаблона страницы редактирования публикации
     */
    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model){
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    /**
     *  Обрабатывает запрос на обновление публикации по ID.
     * @param id идентификатор публикации
     * @param title новый заголовок публикации
     * @param anons новое краткое описание публикации
     * @param full_text новый полный текст публикации
     * @param model объект Model для передачи данных на страницу
     * @return перенаправление на страницу блога после успешного обновления
     */
    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    /**
     * Обрабатывает запрос на удаление публикации по ID.
     *
     * @param id идентификатор публикации
     * @param model объект Model для передачи данных на страницу
     * @return перенаправление на страницу блога после успешного удаления
     */
    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }
}

