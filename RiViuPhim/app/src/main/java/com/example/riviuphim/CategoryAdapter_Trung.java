package com.example.riviuphim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter_Trung extends RecyclerView.Adapter<CategoryAdapter_Trung.CategoryViewHolder>{
    private ArrayList<Category> lstCategory;
    private Context context;

    public CategoryAdapter_Trung(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<Category> list){
        this.lstCategory =list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = lstCategory.get(position);
        if(category == null){
            return;
        }

        holder.tvCategory.setText(category.getNameCategory());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.rcvPhim.setLayoutManager(linearLayoutManager);
        CustomAdapterPhim_Trung phimAdapter = new CustomAdapterPhim_Trung(context);
        phimAdapter.setData(category.getPhims());
        holder.rcvPhim.setAdapter(phimAdapter);
    }


    @Override
    public int getItemCount() {
        if(lstCategory != null){
            return lstCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{


        private TextView tvCategory;
        private RecyclerView rcvPhim;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategory = itemView.findViewById(R.id.tv_Category);
            rcvPhim = itemView.findViewById(R.id.rcv_Phim);
        }
    }
}
