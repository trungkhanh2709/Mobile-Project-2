package com.example.riviuphim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapterDanhgia extends ArrayAdapter {

    Context context;

    ArrayList<DanhGia> lsDataDanhgia = new ArrayList<>();



    public CustomAdapterDanhgia(@NonNull Context context, ArrayList<DanhGia> lsDataDanhgia) {
        super(context, 0, lsDataDanhgia);
        this.context = context;
        this.lsDataDanhgia = lsDataDanhgia;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DanhGia danhgia = lsDataDanhgia.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_danhgia, null);
        TextView tvNameUser = (TextView) convertView.findViewById(R.id.tvNameUser);
        TextView tvDiemso = (TextView) convertView.findViewById(R.id.tvDiemso);
        TextView tvBinhluan = (TextView) convertView.findViewById(R.id.tvBinhluan);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);


        tvNameUser.setText(danhgia.getTenNguoiDung());
        tvDiemso.setText(Integer.toString(danhgia.getDiemSo()));
        tvBinhluan.setText(danhgia.getBinhLuan());
        tvDate.setText(danhgia.getNgayBinhLuan());


        return convertView;
    }
}
