package com.study.BlogPlatform.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Контроллер для работы с куками (cookies).
 * <p>
 * Этот контроллер предоставляет методы для установки и чтения куков в веб-приложении.
 * Он содержит два эндпоинта: один для установки кука и другой для его чтения.
 * </p>
 */
@Controller
public class CookieController {

    /**
     * Устанавливает куку с именем "myCookie" и значением "cookieValue".
     * <p>
     * Кука имеет срок действия 7 дней и устанавливается как HttpOnly,
     * что предотвращает доступ к ней через JavaScript. После установки куки
     * пользователю отображается сообщение об успешной установке.
     * </p>
     *
     * @param response объект {@link HttpServletResponse}, используемый для добавления куки
     * @param model объект {@link Model}, используемый для передачи данных в представление
     * @return имя представления, в котором отображается сообщение об установке куки
     */
    @GetMapping("/set-cookie")
    public String setCookie(HttpServletResponse response, Model model) {
        Cookie cookie = new Cookie("myCookie", "cookieValue");
        cookie.setMaxAge(7 * 24 * 60 * 60); // срок действия - 7 дней
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        model.addAttribute("message", "Cookie успешно установлено!");

        return "cookieSet";
    }

    /**
     * Читает значение куки с именем "myCookie".
     * <p>
     * Если кука отсутствует, возвращается значение по умолчанию "Отсутствует".
     * Значение куки передается в модель для отображения на странице.
     * </p>
     *
     * @param myCookie значение куки, полученное из запроса
     * @param model объект {@link Model}, используемый для передачи данных в представление
     * @return имя представления, в котором отображается значение куки
     */
    @GetMapping("/show-cookie")
    public String showCookie(@CookieValue(value = "myCookie", defaultValue = "Отсутствует") String myCookie, Model model) {
        model.addAttribute("cookieValue", myCookie);
        return "show-cookie";
    }
}