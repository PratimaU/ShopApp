package com.example.pratima.shopapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pratima on 19-03-2016.
 */
public class Product {

    @SerializedName("productname")
    private String name;

    @SerializedName("price")
    private long price;

    @SerializedName("vendorname")
    private String vendorName;

    @SerializedName("vendoraddress")
    private String vendorAddress;

    @SerializedName("productImg")
    private String imageUrl;

    @SerializedName("phoneNumber")
    private long phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        return this.name.equalsIgnoreCase(((Product)o).name);
    }
}
