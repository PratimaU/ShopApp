package com.example.pratima.shopapp.api;

import com.example.pratima.shopapp.model.Products;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * ProductsApi.java
 *
 * Class containing api calls for products
 */
public interface ProductsApi {

    @GET("getdata/")
    public Call<Products> getProducts();
}
