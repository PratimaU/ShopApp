package com.example.pratima.shopapp.view;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pratima.shopapp.R;
import com.example.pratima.shopapp.model.Cart;
import com.example.pratima.shopapp.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * CartFragment.java
 *
 * Fragment for displaying products in cart.
 */
public class CartFragment extends Fragment implements ICartListener{

    private RecyclerView productsRecyclerView = null;
    private ArrayList<Product> productsList = null;
    private CartListAdapter listAdapter = null;
    private TextView totalPriceText = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        this.productsList = new ArrayList<>();
        this.totalPriceText = (TextView) view.findViewById(R.id.totalPriceText);
        this.productsRecyclerView = (RecyclerView) view.findViewById(R.id.cartRecyclerView);
        this.productsRecyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        this.productsRecyclerView.addItemDecoration(new ProductDecoration());
        this.productsRecyclerView.setLayoutManager(layoutManager);

        this.listAdapter = new CartListAdapter(this.productsList);
        this.productsRecyclerView.setAdapter(this.listAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshList();
    }

    @Override
    public void onProductAdd(Product product) {
        refreshList();
    }

    @Override
    public void onProductRemoved(Product product) {

    }

    private void refreshList() {
        this.productsList.clear();
        this.productsList.addAll(Cart.getCart().getCartItems());
        this.listAdapter.notifyDataSetChanged();

        //update total price text
        long total = 0;
        for(Product product : this.productsList) {
            total = total + product.getPrice();
        }

        this.totalPriceText.setText(getString(R.string.totalPrice, total));
    }

    private class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

        ArrayList<Product> products = null;

        public CartListAdapter(ArrayList<Product> products) {
            this.products = products;
        }

        @Override
        public CartListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cart_item, null);
            CartListAdapter.ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CartListAdapter.ViewHolder holder, int position) {
            final Product product = this.products.get(position);
            holder.productName.setText(product.getName());
            holder.productPrice.setText(getString(R.string.cartItemPrice,product.getPrice()));
            holder.vendorName.setText(product.getVendorName());
            holder.vendorAddress.setText(product.getVendorAddress());
            Picasso.with(getActivity()).load(product.getImageUrl()).into(holder.productImage);

            holder.removeFromCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart.getCart().remove(product);
                    refreshList();
                }
            });

            holder.callVendorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchDialer(product.getPhoneNumber());
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
            public Button removeFromCartBtn;
            public Button callVendorBtn;

            public ViewHolder(View itemView) {
                super(itemView);
                this.productName = (TextView) itemView.findViewById(R.id.productName);
                this.productPrice = (TextView) itemView.findViewById(R.id.productPrice);
                this.vendorName = (TextView) itemView.findViewById(R.id.vendorName);
                this.vendorAddress = (TextView) itemView.findViewById(R.id.vendorAddress);
                this.productImage = (ImageView) itemView.findViewById(R.id.productImg);
                this.removeFromCartBtn = (Button) itemView.findViewById(R.id.btnRemoveFromCart);
                this.callVendorBtn = (Button) itemView.findViewById(R.id.btnCallVendor);
            }
        }

        private void launchDialer(long phoneNumber) {
            String uri = "tel:" + phoneNumber ;
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
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
}
