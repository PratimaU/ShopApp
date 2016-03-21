package com.example.pratima.shopapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ViewSwitcher;

import com.example.pratima.shopapp.view.CartFragment;
import com.example.pratima.shopapp.view.ProductsFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewSwitcher viewSwitcher = null;
    private View productsTab = null;
    private View cartTab = null;
    private ProductsFragment productsFragment = null;
    private CartFragment cartFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.viewSwitcher = (ViewSwitcher) findViewById(R.id.productsCartSwitcherView);
        this.productsTab = findViewById(R.id.productsTab);
        this.cartTab = findViewById(R.id.cartTab);

        this.productsTab.setOnClickListener(this);
        this.cartTab.setOnClickListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        this.productsFragment = (ProductsFragment) fragmentManager.findFragmentById(R.id.productsFragment);
        this.cartFragment = (CartFragment) fragmentManager.findFragmentById(R.id.cartFragment);
        if(this.productsFragment != null && this.cartFragment != null) {
            this.productsFragment.setCartListener(this.cartFragment);
        }

        //Highlight products tab
        this.productsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    public void onClick(View v) {

        this.productsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        this.cartTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if(v == this.productsTab) {
            this.productsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            this.viewSwitcher.setDisplayedChild(0);
        } else if(v == this.cartTab) {
            this.cartTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            this.viewSwitcher.setDisplayedChild(1);
        }
    }
}
