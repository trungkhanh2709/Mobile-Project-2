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

import java.util.List;

public class CustomAdapter_Thu extends ArrayAdapter<Phim> {
    Context context;
    List<Phim> phimList;

    public CustomAdapter_Thu(@NonNull Context context, List<Phim> phimList) {
        super(context, 0, phimList);
        this.context = context;
        this.phimList = phimList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_find, parent, false);
        }

        ImageView imgPoster = convertView.findViewById(R.id.imgPoster_Thu);
        TextView tvTenPhim = convertView.findViewById(R.id.tvTenPhim_Thu);
        TextView tvDiemSo = convertView.findViewById(R.id.tvDiemSo_Thu);

        Phim phim = getItem(position);

        //hiển thị tên phim và poster
        if(phim != null){

            tvTenPhim.setText(phim.tenPhim);
            // Kiểm tra xem phim.posterPhimURL có giá trị hay không
            if (phim.PosterPhim != null && !phim.PosterPhim.isEmpty()) {
                Picasso.get().load(phim.PosterPhim).into(imgPoster);
            }else {
                // Xử lý trường hợp URL không hợp lệ, có thể hiển thị một ảnh mặc định hoặc ẩn ImageView
                imgPoster.setImageResource(R.drawable.default_image); // Thay thế bằng ảnh mặc định của bạn
            }

            //hiển thị điểm đánh giá
            int diemSo = phim.getDiemSo();
            String formattedDiemSo = String.valueOf(diemSo);
            tvDiemSo.setText(formattedDiemSo);
        }
        return convertView;
    }
}
