package com.study.BlogPlatform.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Контроллер главной страницы и страницы "О нас".
 * <p>
 * Этот контроллер обрабатывает запросы к корневому URL ("/") и к URL "/about",
 * предоставляя соответствующие представления для главной страницы и страницы
 * о компании.
 * </p>
 */
@Controller
public class MainController {

    /**
     * Обрабатывает GET-запрос к корневому URL ("/").
     * <p>
     * Метод добавляет атрибут "title" в модель с заголовком "Главная страница"
     * и возвращает имя представления "home", которое будет отображено пользователю.
     * </p>
     *
     * @param model объект {@link Model}, используемый для передачи данных в представление
     * @return имя представления, которое будет отображено (в данном случае "home")
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    /**
     * Обрабатывает GET-запрос к URL "/about".
     * <p>
     * Метод добавляет атрибут "title" в модель с заголовком "Страница про нас"
     * и возвращает имя представления "about", которое будет отображено пользователю.
     * </p>
     *
     * @param model объект {@link Model}, используемый для передачи данных в представление
     * @return имя представления, которое будет отображено (в данном случае "about")
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Страница про нас");
        return "about";
    }
}