package com.example.pratima.shopapp.view;

import com.example.pratima.shopapp.model.Product;

/**
 * Created by Pratima on 20-03-2016.
 */
public interface ICartListener {
    void onProductAdd(Product product);
    void onProductRemoved(Product product);
}
