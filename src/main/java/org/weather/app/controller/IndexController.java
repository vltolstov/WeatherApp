package org.weather.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {

        // проверка сессию и куки
        // если данные верны то страница показывается
        // если нет, то перебрасывает на логин

        return "pages/index";
    }
}
