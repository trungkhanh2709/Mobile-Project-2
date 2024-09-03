package com.example.riviuphim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapterUserCmt extends ArrayAdapter<DanhGia> {
    Context context;
    int resource;
    ArrayList<DanhGia> lsDanhgia;

    public CustomAdapterUserCmt(@NonNull Context context, int resource, ArrayList<DanhGia> lsDanhgia) {
        super(context, resource, lsDanhgia);
        this.context = context;
        this.resource = resource;
        this.lsDanhgia = lsDanhgia;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DanhGia DanhGia = lsDanhgia.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tvNameUser = convertView.findViewById(R.id.tvNameUser);
        tvNameUser.setText(DanhGia.getTenNguoiDung());

        TextView tvBinhLuan = convertView.findViewById(R.id.tvBinhluan);
        tvBinhLuan.setText(DanhGia.getBinhLuan());

        TextView tvDiemso = convertView.findViewById(R.id.tvDiemso);
        tvDiemso.setText(String.valueOf(DanhGia.getDiemSo()));

        TextView tvDate = convertView.findViewById(R.id.tvDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String ngayBinhLuan = DanhGia.getNgayBinhLuan();
        if (ngayBinhLuan != null) {
            String dateString = dateFormat.format(ngayBinhLuan);
            tvDate.setText(dateString);
        } else {
            // Xử lý khi giá trị ngày bình luận là null
            tvDate.setText("Ngày bình luận không khả dụng");
        }




        return convertView;
    }
}
