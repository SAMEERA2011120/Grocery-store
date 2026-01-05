

package com.grocery.service;

import com.grocery.dto.RecipeRequest;
import com.grocery.dto.RecipeResponse;

public interface RecipeService {
    RecipeResponse getRecipeIngredients(RecipeRequest request);
}
