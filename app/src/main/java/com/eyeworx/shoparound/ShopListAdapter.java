package com.eyeworx.shoparound;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eyeworx.shoparound.ui.StoreDetailsActivity;

import java.util.List;

/**
 * Created by Shreerama on 11/5/2017.
 */

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    private List<ShopListDataModel> shopList;
    private Context context;

    public ShopListAdapter( Context context, List<ShopListDataModel> shopList) {
        this.shopList = shopList;
        this.context = context;
    }

    //Instantiating layout & view holders
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_store_list,parent,false);
    return new ViewHolder(view);
    }

    //Binding views to the recycler
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShopListDataModel store = shopList.get(position);
        holder.Phone.setText(store.getPhone());
        holder.Address.setText(store.getAddress());
        Glide.with(context).load(store.getLogoUrl()).into(holder.LogoUri);
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        private TextView Phone;
        private TextView Address;
        private ImageView LogoUri;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            Phone = (TextView) itemView.findViewById(R.id.storePhone);
            Address = (TextView)itemView.findViewById(R.id.storeAddress);
            LogoUri = (ImageView) itemView.findViewById(R.id.logoImageView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(this);
        }

        // start StoreDetailsActivity
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context,StoreDetailsActivity.class);
            intent.putExtra("position", position);
            context.startActivity(intent);
        }
    }
}
