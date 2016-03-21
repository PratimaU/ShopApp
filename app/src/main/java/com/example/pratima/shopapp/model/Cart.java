package com.example.pratima.shopapp.model;

import java.util.ArrayList;

/**
 * Created by Pratima on 19-03-2016.
 */
public class Cart {

    private static Cart sharedInstance = null;
    private ArrayList<Product> products;

    private Cart() {
        this.products = new ArrayList<>();
    }

    public  static Cart getCart() {
        if (sharedInstance == null) {
            sharedInstance = new Cart();
        }

        return sharedInstance;
    }

    public void add(Product product) {
        if(!this.products.contains(product)) {
            this.products.add(product);
        }
    }

    public void remove(Product product) {
        this.products.remove(product);
    }

    public ArrayList<Product> getCartItems() {
        return this.products;
    }
}
