package org.weather.app.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.weather.app.dto.SearchRequest;

@Controller
public class SearchResultController {

    @GetMapping("/search-result")
    public String searchResult(Model model) {
        model.addAttribute("searchRequest", new SearchRequest());
        return "pages/search-result";
    }

    @PostMapping("/search-result")
    public String searchProcess(@Valid @ModelAttribute("searchRequest") SearchRequest searchRequest, BindingResult result, HttpServletResponse response) {

        if (result.hasErrors()) {
            return "pages/search-result";
        }

        // если ошибок нет... то

        return "pages/search-result";
    }
}
