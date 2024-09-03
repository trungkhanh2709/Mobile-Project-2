package com.example.riviuphim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterDienVien extends ArrayAdapter<DienVien> {
    private Context context;
    private ArrayList<DienVien> dienVienList;

    public CustomAdapterDienVien(Context context, ArrayList<DienVien> dienVienList) {
        super(context, 0, dienVienList);
        this.context = context;
        this.dienVienList = dienVienList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.layout_item_dienvien, parent, false);
        }

        DienVien currentDienVien = dienVienList.get(position);

        ImageView imgListDienVien = listItemView.findViewById(R.id.imgListDienVien);
        TextView txtListDienVien = listItemView.findViewById(R.id.txtListDienVien);

        txtListDienVien.setText(currentDienVien.getTenDienVien());
        Picasso.get().load(currentDienVien.getUrlHinhAnh()).resize(200, 200) .centerCrop().into(imgListDienVien);


        return listItemView;
    }
}
