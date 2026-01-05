package com.grocery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.model.Order;
import com.grocery.model.Product;
import com.grocery.repository.OrderRepository;
import com.grocery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")


public class OrderController {
    @Autowired
    private ProductRepository productRepository;
    private final OrderRepository repo;
    private final ObjectMapper mapper = new ObjectMapper();

    public OrderController(OrderRepository repo) {
        this.repo = repo;
    }


    @GetMapping("/user/{userName}")
    public List<Order> getOrdersByUser(@PathVariable String userName) {
        return repo.findByUserName(userName);
    }


    @PostMapping("/place")
    public String placeOrder(@RequestBody Map<String, Object> payload) throws Exception {

        Order order = new Order();
        order.setUserName((String) payload.get("userName"));
        order.setName((String) payload.get("name"));
        order.setPhone((String) payload.get("phone"));
        order.setAddress((String) payload.get("address"));
        order.setPaymentMethod((String) payload.get("paymentMethod"));

        ObjectMapper mapper = new ObjectMapper();


        String itemsJson = mapper.writeValueAsString(payload.get("items"));
        order.setItems(itemsJson);

        order.setStatus("PLACED");

        repo.save(order);
         List<Map<String, Object>> items =
            (List<Map<String, Object>>) payload.get("items");

    for (Map<String, Object> item : items) {

        int productId = (int) item.get("id");
        int qty = (int) item.get("quantity");

        Product product = productRepository.findById(productId).orElse(null);

        if (product == null || product.getStock() < qty) {
            return "OUT_OF_STOCK";
        }

        product.setStock(product.getStock() - qty);
        productRepository.save(product);
    }
        return "ORDER_PLACED";
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable int id, @RequestParam String status) {
        Order o = repo.findById(id).orElseThrow();
        o.setStatus(status);
        repo.save(o);
    }
}
