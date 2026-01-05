package com.grocery.dto;

import lombok.Data;
import java.util.List;

@Data
public class RecipeResponse {
    private List<String> ingredients;
    private List<String> matchedProducts;
}
