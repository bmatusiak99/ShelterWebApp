package com.example.demo.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "productname", nullable = false)
    private String product_name;

    @Column(name = "productdescription", nullable = false)
    private String product_description;

    @Column(name = "productprice", nullable = false)
    private Float product_price;

    @Column(name = "productisavailable", nullable = false)
    private Boolean product_isAvailable;

    @Column(name = "filename", nullable = false)
    private String fileName;

    public Product (){

    }

    public Product(Integer id, String product_name, String product_description, float product_price, Boolean product_isAvailable, String fileName) {
        this.id = id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_isAvailable = product_isAvailable;
        this.fileName = fileName;
    }

}
