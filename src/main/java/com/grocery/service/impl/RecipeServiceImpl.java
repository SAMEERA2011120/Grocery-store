package com.grocery.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.dto.RecipeRequest;
import com.grocery.dto.RecipeResponse;
import com.grocery.model.Product;
import com.grocery.repository.ProductRepository;
import com.grocery.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.*;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final ProductRepository productRepository;

    @Override
    public RecipeResponse getRecipeIngredients(RecipeRequest request) {

        try {

            String prompt =
                    "List the ingredients required to cook " + request.getDish() +
                            ". Return only ingredient names, one per line, no extra text.";

            // ---------- HTTP CLIENT ----------
            HttpClient client = HttpClient.newHttpClient();

            // ---------- REQUEST BODY ----------
            String jsonBody =
                    """
                    {
                      "model": "llama-3.1-8b-instant",
                      "messages": [
                        {
                          "role": "user",
                          "content": "%s"
                        }
                      ]
                    }
                    """.formatted(prompt);

            // ---------- BUILD REQUEST ----------
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.groq.com/openai/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // ---------- GET RESPONSE ----------
            HttpResponse<String> response =
                    client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            String result = response.body();

            System.out.println("========= RAW GROQ RESPONSE =========");
            System.out.println(result);
            System.out.println("=====================================");

            // ---------- PARSE JSON ----------
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(result);

            JsonNode contentNode =
                    root.path("choices")
                            .get(0)
                            .path("message")
                            .path("content");

            if (contentNode.isMissingNode()) {
                throw new RuntimeException("Invalid Groq response: " + result);
            }

            String output = contentNode.asText();

            // ---------- INGREDIENT SPLIT ----------
            List<String> ingredients =
                    Arrays.stream(output.split("\n"))
                            .map(String::trim)
                            .filter(s -> !s.isBlank())
                            .collect(Collectors.toList());

            // ---------- MATCH PRODUCTS WITH DB ----------
            List<String> cleanedIngredients =
                    ingredients.stream()
                            .map(i -> i.toLowerCase().replaceAll("[^a-z ]",""))
                            .collect(Collectors.toList());

            List<String> matched =
                    productRepository.findAll().stream()
                            .map(Product::getName)
                            .filter(name ->
                                    cleanedIngredients.stream()
                                            .anyMatch(ing ->
                                                    Arrays.stream(ing.split(" "))
                                                            .anyMatch(word ->
                                                                    word.equals(name.toLowerCase())
                                                            )
                                            )
                            )
                            .collect(Collectors.toList());


            // ---------- SEND RESPONSE ----------
            RecipeResponse res = new RecipeResponse();
            res.setIngredients(ingredients);
            res.setMatchedProducts(matched);

            return res;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Groq error: " + e.getMessage());
        }
    }
}
