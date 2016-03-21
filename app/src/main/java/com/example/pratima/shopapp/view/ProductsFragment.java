package com.example.pratima.shopapp.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pratima.shopapp.R;
import com.example.pratima.shopapp.api.ProductsApi;
import com.example.pratima.shopapp.model.Cart;
import com.example.pratima.shopapp.model.Product;
import com.example.pratima.shopapp.model.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * ProductsFragment.java
 *
 * Fragment for displaying list of products from API.
 */
public class ProductsFragment extends Fragment implements View.OnClickListener {

    private RecyclerView productsRecyclerView = null;
    private ArrayList<Product> productsList = null;
    private ProductsListAdapter listAdapter = null;
    private ICartListener cartListener = null;

    private View errorView = null;
    private ImageButton refreshButton = null;

    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        this.productsList = new ArrayList<>();
        this.productsRecyclerView = (RecyclerView) view.findViewById(R.id.productsRecyclerView);
        this.errorView = view.findViewById(R.id.errorView);
        this.refreshButton = (ImageButton) view.findViewById(R.id.refreshBtn);
        this.refreshButton.setOnClickListener(this);

        this.productsRecyclerView.hasFixedSize();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        gridLayoutManager.offsetChildrenVertical(100);
        this.productsRecyclerView.addItemDecoration(new ProductDecoration());
        this.productsRecyclerView.setLayoutManager(gridLayoutManager);

        this.listAdapter = new ProductsListAdapter(this.productsList);
        this.productsRecyclerView.setAdapter(this.listAdapter);
        return view;
    }

    public ICartListener getCartListener() {
        return cartListener;
    }

    public void setCartListener(ICartListener cartListener) {
        this.cartListener = cartListener;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getProducts();
    }

    @Override
    public void onClick(View v) {
        if(v == this.refreshButton) {
            this.errorView.setVisibility(View.GONE);
            this.getProducts();
        }
    }

    private class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {

        ArrayList<Product> products = null;

        public ProductsListAdapter(ArrayList<Product> products) {
            this.products = products;
        }

        @Override
        public ProductsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_product_card, null);
            ProductsListAdapter.ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ProductsListAdapter.ViewHolder holder, int position) {
            final Product product = this.products.get(position);
            holder.productName.setText(product.getName());
            holder.productPrice.setText(getString(R.string.price,product.getPrice()));
            holder.vendorName.setText(product.getVendorName());
            holder.vendorAddress.setText(product.getVendorAddress());
            Picasso.with(getActivity()).load(product.getImageUrl()).into(holder.productImage);
            holder.addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart.getCart().add(product);
                    if(ProductsFragment.this.cartListener != null){
                        ProductsFragment.this.cartListener.onProductAdd(product);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.products.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView productName;
            public TextView productPrice;
            public TextView vendorName;
            public TextView vendorAddress;
            public ImageView productImage;
            public Button addToCart;

            public ViewHolder(View itemView) {
                super(itemView);
                this.productName = (TextView) itemView.findViewById(R.id.productName);
                this.productPrice = (TextView) itemView.findViewById(R.id.productPrice);
                this.vendorName = (TextView) itemView.findViewById(R.id.vendorName);
                this.vendorAddress = (TextView) itemView.findViewById(R.id.vendorAddress);
                this.productImage = (ImageView) itemView.findViewById(R.id.productImg);
                this.addToCart = (Button) itemView.findViewById(R.id.btnAddToCart);
            }
        }
    }

    private class ProductDecoration extends RecyclerView.ItemDecoration {


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int space = 10;
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
        }
    }

    private void getProducts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mobiletest-hackathon.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductsApi api = retrofit.create(ProductsApi.class);
        Call<Products> products = api.getProducts();
        products.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if(ProductsFragment.this.productsList == null) {
                    ProductsFragment.this.productsList = new ArrayList<>();
                }

                ProductsFragment.this.productsList.addAll(response.body().getProducts());
                ProductsFragment.this.listAdapter.notifyDataSetChanged();

                ProductsFragment.this.errorView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                ProductsFragment.this.errorView.setVisibility(View.VISIBLE);
            }
        });
    }

}
