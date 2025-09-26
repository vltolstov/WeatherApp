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
import org.weather.app.service.SearchService;

@Controller
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public String searchResult(Model model) {
        model.addAttribute("searchRequest", new SearchRequest());
        return "pages/search";
    }

    @PostMapping("/search")
    public String searchProcess(@Valid @ModelAttribute("searchRequest") SearchRequest searchRequest,
                                BindingResult result,
                                Model model,
                                HttpServletResponse response) {

        if (result.hasErrors()) {
            return "pages/search";
        }

        if (searchService.isLocationExist(searchRequest.getQuery())) {
            model.addAttribute("cities", searchService.getLocations(searchRequest.getQuery()));
            return "pages/search";
        }

        model.addAttribute("cityNotFound", "Город с таким названием не найден");
        return "pages/search";
    }
}
