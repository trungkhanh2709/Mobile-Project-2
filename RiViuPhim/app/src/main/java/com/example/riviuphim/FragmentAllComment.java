package com.example.riviuphim;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAllComment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAllComment extends Fragment {
    int man = Data.getIdUser();
    int MaP = Data.getIdPhim();

    String MaPhim = String.valueOf(MaP);
    String MaNguoiDung = String.valueOf(man);
    ImageButton imgbtnOut, imgbtnPost;
    ListView lvFullDanhgia;
    Button btnUpdate;
    ArrayList<DanhGia> lvDataDanhgia = new ArrayList<>();
    String ip = Data.getIp();

    String urlDanhgia = "http://" + ip + "/democuoiki/danhgia/dulieudanhgia.php";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_comment, container, false);


        addControl(view);
        addEvents();
        getAllData(urlDanhgia);

        return  view;
    }
    private void addControl(View view) {
        lvFullDanhgia = view.findViewById(R.id.lvFullDanhgia);
        imgbtnOut = view.findViewById(R.id.imgbtnOut);
        imgbtnPost = view.findViewById(R.id.imgbtnPost);
        btnUpdate = view.findViewById(R.id.btnUpdate);

        // Đặt màu nền trực tiếp cho button
        btnUpdate.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button_pressed_color));


    }
    private void addEvents(){



        if (imgbtnOut != null) {
            // Đảm bảo imageButton không null
            imgbtnOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentDetailFilm fragmentDanhgia = new FragmentDetailFilm();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragmentDanhgia); // Sử dụng hàm add() thay vì replace()
                    fragmentTransaction.commit();
                }
            });
        } else {
            Log.e("FragmentAllComment", "ImageButton is null");
        }


        // Kiểm tra xem MaPhim và MaNguoiDung có rỗng không
        if (TextUtils.isEmpty(MaPhim) || TextUtils.isEmpty(MaNguoiDung)){
            // Ẩn button
            btnUpdate.setVisibility(View.INVISIBLE);
            imgbtnPost.setVisibility(View.VISIBLE);

            if (imgbtnPost != null) {
                // Đảm bảo imageButton không null
                imgbtnPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CustomReviewFragment rFragment = new CustomReviewFragment();
                        rFragment.show(getChildFragmentManager(), "CustomReviewFragment");

                    }
                });
            } else {
                Log.e("FragmentAllComment", "ImageButton is null");
            }

        } else {
            // Khi muốn hiện Button
            imgbtnPost.setVisibility(View.INVISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnUpdate.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT; // Đặt lại chiều cao
            if (btnUpdate != null) {
                // Đảm bảo imageButton không null
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FragmentUpdate rFragment = new FragmentUpdate();
                        rFragment.show(getChildFragmentManager(), "FragmentUpdate");


                    }
                });
            } else {
                Log.e("FragmentAllComment", "ImageButton is null");
            }


        }



    }
    public void getAllData(String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    parseJsonDataToArrayList(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error Data!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

    }
    public void parseJsonDataToArrayList(String reponse) throws JSONException {
        try {
            JSONObject object = new JSONObject(reponse);
            JSONArray jsonArray = object.getJSONArray("danhgias");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.has("MaPhim")) {
                    String maPhim = jsonObject.getString("MaPhim");
                    // Tiếp tục xử lý với giá trị đã lấy
                    if (maPhim.equals(MaPhim)) {
                        String maNguoidung = jsonObject.getString("MaNguoiDung");
                        int diemso = jsonObject.getInt("DiemSo");
                        String binhluan = jsonObject.getString("BinhLuan");
                        String date = jsonObject.getString("NgayBinhLuan");
                        // Gọi API thứ hai để lấy tên người dùng
                        getUserData(maNguoidung, diemso, binhluan, date);



                    }
                } else {
                    // Xử lý trường hợp khi khóa "MaPhim" bị thiếu
                    Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu mã phim!", Toast.LENGTH_SHORT).show();

                }

            }


        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void getUserData(String maNguoiDung, int diemso, String binhluan,  String date) {
        String urlGetUserName = "http://" + ip + "/democuoiki/nguoidung/dulieunguoidung.php?MaNguoiDung=" + maNguoiDung;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetUserName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    if (maNguoiDung.equals(MaNguoiDung)){
                        // Phân tích phản hồi từ API thứ hai để lấy tên người dùng
                        String userName = parseUserNameFromResponse(response, maNguoiDung);
                        // Tạo đối tượng Chitietphim với tên người dùng
                        String name = userName;
                        lvDataDanhgia.add(new DanhGia(name +" (you) ", diemso , binhluan, date));

                    } else {
                        // Phân tích phản hồi từ API thứ hai để lấy tên người dùng
                        String userName = parseUserNameFromResponse(response, maNguoiDung);

                        // Tạo đối tượng Chitietphim với tên người dùng
                        String name = userName;
                        lvDataDanhgia.add(new DanhGia(name , diemso , binhluan, date));

                    }
                    CustomAdapterDanhgia adapter = new CustomAdapterDanhgia(getContext(), lvDataDanhgia);
                    lvFullDanhgia.setAdapter(adapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu người dùng!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    private String parseUserNameFromResponse(String response, String maNguoiDung) throws JSONException {
        // Phân tích phản hồi từ API thứ hai để lấy tên người dùng
        JSONObject object = new JSONObject(response);

        // Kiểm tra xem khóa "nguoidungs" có tồn tại trong đối tượng JSON không
        if (object.has("nguoidungs")) {
            // Lấy mảng JSON "nguoidungs" từ đối tượng JSON chính
            JSONArray nguoiDungsArray = object.getJSONArray("nguoidungs");


            // Lặp qua mảng để tìm phần tử có "MaNguoiDung" khớp
            for (int i = 0; i < nguoiDungsArray.length(); i++) {
                JSONObject nguoiDung = nguoiDungsArray.getJSONObject(i);

                // Kiểm tra xem khóa "MaNguoiDung" có tồn tại và có khớp với giá trị bạn quan tâm không
                if (nguoiDung.has("MaNguoiDung") && nguoiDung.getString("MaNguoiDung").equals(maNguoiDung)) {
                    // Kiểm tra xem khóa "TenNguoiDung" có tồn tại trong đối tượng JSON người dùng không
                    if (nguoiDung.has("TenNguoiDung")) {
                        return nguoiDung.getString("TenNguoiDung");
                    }
                }
            }
        }
        // Xử lý trường hợp khóa "TenNguoiDung" không có trong JSON
        return "Unknown User";


    }

}