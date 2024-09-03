package com.example.riviuphim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomAdapterPhim_Trung extends RecyclerView.Adapter<CustomAdapterPhim_Trung.PhimViewHolder> {

    private ArrayList<Phim> lstPhim;
    private Context context;
    String ip = Data.getIp();
    String apiUrl = "http://"+ip+"/democuoiki/phim/dulieuphim.php"; // Your API endpoint URL


    public CustomAdapterPhim_Trung() {
    }

    public CustomAdapterPhim_Trung(Context context) {
        this.context = context;
    }

    public CustomAdapterPhim_Trung(ArrayList<Phim> lstPhim, Context context) {
        this.lstPhim = lstPhim;
        this.context = context;
    }



    public void setData(ArrayList<Phim> list){
        this.lstPhim =list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_item_trung,parent,false);
        return new PhimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhimViewHolder holder, int position) {
        final Phim phim = lstPhim.get(position);
        if(phim == null){
            return;
        }
        Picasso.get().load(phim.getPosterPhim()).fit().into(holder.posterPhim);
        holder.tv_TenPhim.setText(phim.getTenPhim());

        holder.layoutItem.setOnClickListener(view -> onClickGoToDetail(phim));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idphim = phim.getMaPhim();
                int idNguoiDung = Data.getIdUser();
                String ip = Data.getIp();
                String url = "http://" + ip + "/democuoiki/dsxemsau/insertwishlist.php"; // Thay thế bằng địa chỉ của file PHP trên server của bạn
                new InsertDSXS().execute(url, String.valueOf(idphim), String.valueOf(idNguoiDung));

            }
        });
    }

    public void onClickGoToDetail(Phim phim) {

        int idPhim = phim.getMaPhim();
        Data.setIdPhim(idPhim);

        FragmentDetailFilm fragment = new FragmentDetailFilm();

        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment) // Replace fragment_container with your container ID
                        .addToBackStack(null) // If you want to add to back stack, otherwise omit this line
                        .commit();



    }


    @Override
    public int getItemCount() {
        if(lstPhim != null){
            return lstPhim.size();
        }
        return 0;
    }

    public class PhimViewHolder extends RecyclerView.ViewHolder{
        CardView layoutItem;
        Button button;
        ImageView posterPhim;
        TextView tv_TenPhim;
        public PhimViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = (CardView) itemView.findViewById(R.id.layoutItem);
            posterPhim = (ImageView) itemView.findViewById(R.id.posterPhim);
            tv_TenPhim =  (TextView) itemView.findViewById(R.id.tv_TenPhim);
            button = (Button) itemView.findViewById(R.id.btnAddToWishList);

        }
    }

}

