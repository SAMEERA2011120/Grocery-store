package com.grocery.controller;

import com.grocery.dto.RecipeRequest;
import com.grocery.dto.RecipeResponse;
import com.grocery.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
//@RequiredArgsConstructor
@CrossOrigin("*")
public class RecipeController {

    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public RecipeResponse search(@RequestBody RecipeRequest request){
        return recipeService.getRecipeIngredients(request);
    }
}
