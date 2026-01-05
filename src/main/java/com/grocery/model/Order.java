//package com.grocery.model;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "orders")
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    private String name;
//    private String phone;
//    private String address;
//
//    @Column(name = "payment_method")
//    private String paymentMethod;
//
//    @Column(columnDefinition = "TEXT")
//    private String items;
//
//    private String status; // ✅ NEW
//
//    // ===== Getters & Setters =====
//
//    public int getId() { return id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getPhone() { return phone; }
//    public void setPhone(String phone) { this.phone = phone; }
//
//    public String getAddress() { return address; }
//    public void setAddress(String address) { this.address = address; }
//
//    public String getPaymentMethod() { return paymentMethod; }
//    public void setPaymentMethod(String paymentMethod) {
//        this.paymentMethod = paymentMethod;
//    }
//
//    public String getItems() { return items; }
//    public void setItems(String items) { this.items = items; }
//
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//}



package com.grocery.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String phone;
    private String address;

    @Column(name = "user_name")
    private String userName;


    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(columnDefinition = "TEXT")
    private String items;

    private String status = "PLACED";

    /* GETTERS & SETTERS */

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUserName() {
    return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
