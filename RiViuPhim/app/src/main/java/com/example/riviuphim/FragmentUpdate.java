package com.example.riviuphim;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
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

public class FragmentUpdate extends DialogFragment {

    //rồi
    int idU=Data.getIdUser();
    int idP=Data.getIdPhim();
    private String MaNguoiDung = String.valueOf(idU);
    private String MaPhim = String.valueOf(idP);
    private Button btnPost;
    private TextView tvIdUser, tvDateReview;
    private RatingBar ratingBar;
    private EditText edtBinhluan;
    private ImageButton btnOut;

    private String DiemSo;

    ArrayList<DanhGia> lsDataUpdate = new ArrayList<>();


    String ip = Data.getIp();

    String urlDanhgia = "http://" + ip + "/democuoiki/danhgia/dulieudanhgia.php";
    private String urlUpdateDanhgia = "http://"+ ip +"/democuoiki/danhgia/updateDanhgia.php";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        addControl(view);
        getAllData(urlDanhgia);
        addEvents();
        return view;
    }
    private void addControl(View view) {
        ratingBar = view.findViewById(R.id.ratingBar);
        edtBinhluan = view.findViewById(R.id.edtBinhluan);
        btnPost = view.findViewById(R.id.btnPost);
        btnOut = view.findViewById(R.id.btnOut);
        tvIdUser = view.findViewById(R.id.tvIdUser);
        tvDateReview = view.findViewById(R.id.tvDateReview);

    }

    private void addEvents() {
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Đóng fragment khi button được nhấn
                dismiss();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Xử lý khi giá trị thay đổi
                Toast.makeText(getContext(), "Đánh giá: " + rating + " sao", Toast.LENGTH_SHORT).show();
                // Cập nhật giá trị DiemSo theo rating
                int DiemSoint = (int) rating;
                DiemSo = String.valueOf(DiemSoint);

            }
        });

        // Đặt màu nền trực tiếp cho button
        btnPost.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button_pressed_color));

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String binhLuan = edtBinhluan.getText().toString().trim();
                String date = tvDateReview.getText().toString().trim();


                if(DiemSo == null && TextUtils.isEmpty(binhLuan)){
                    Toast.makeText(getContext(), "Vui lòng đánh giá lại trước khi cập nhật", Toast.LENGTH_SHORT).show();
                } else if (DiemSo == null ){
                    Toast.makeText(getContext(), "Vui lòng update số sao", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binhLuan)){
                    Toast.makeText(getContext(), "Vui lòng không để trống bình luận", Toast.LENGTH_SHORT).show();
                } else {
                    new InsertDataAsyncTask().execute(urlUpdateDanhgia, MaPhim, MaNguoiDung, DiemSo, binhLuan , date);
                    Toast.makeText(getContext(), "Cập nhật thành công ", Toast.LENGTH_SHORT).show();

                    TestFragment();
                }

            }
        });



    }

    private void TestFragment(){
        // Kiểm tra xem trước đó là FragmentAllComment hay là FragmentDanhgia
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        FragmentAllComment fragmentAllComment = new FragmentAllComment();
        FragmentDetailFilm fragmentDanhgia = new FragmentDetailFilm();
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (currentFragment instanceof FragmentAllComment) {
            // Cập nhật lại FragmentAllComment
            fragmentTransaction.replace(R.id.frameLayout, fragmentAllComment);
        } else if (currentFragment instanceof FragmentDetailFilm) {
            // Cập nhật lại FragmentDanhgia
            fragmentTransaction.replace(R.id.frameLayout, fragmentDanhgia);
        } else {
            // Nếu không tìm thấy fragment nào (có thể do lần đầu chạy), thay thế bằng FragmentDanhgia
            fragmentTransaction.replace(R.id.frameLayout, fragmentDanhgia);
        }

        fragmentTransaction.commit();
    }








    private void getAllData(String url) {
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


                if (jsonObject.has("MaPhim") && jsonObject.has("MaNguoiDung") ) {
                    String maPhim = jsonObject.getString("MaPhim");
                    String maNguoiDung = jsonObject.getString("MaNguoiDung");
                    // Tiếp tục xử lý với giá trị đã lấy
                    if (maPhim.equals(MaPhim) && maNguoiDung.equals(MaNguoiDung)) {
                        String maNguoidung = jsonObject.getString("MaNguoiDung");
                        int diemso = jsonObject.getInt("DiemSo");
                        String binhluan = jsonObject.getString("BinhLuan");
                        String date = jsonObject.getString("NgayBinhLuan");

                        // Gọi API thứ hai để lấy tên người dùng
                        getUserData(maNguoidung, diemso, binhluan, date);

                        // Chuyển đổi DiemSo thành float
                        float DiemSo = (float) diemso;
                        ratingBar.setRating(DiemSo);


                    }
                } else {
                    // Xử lý trường hợp khi khóa "MaPhim" bị thiếu
                    Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu!", Toast.LENGTH_SHORT).show();

                }

            }


        } catch (JSONException e){
            e.printStackTrace();
        }

    }


    public void getUserData(String maNguoiDung, int diemso, String binhluan, String date) {
        String urlGetUserName = "http://" + ip + "/democuoiki/nguoidung/dulieunguoidung.php?MaNguoiDung=" + maNguoiDung;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetUserName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    // Phân tích phản hồi từ API thứ hai để lấy tên người dùng
                    String userName = parseUserNameFromResponse(response, maNguoiDung);

                    // Tạo đối tượng Chitietphim với tên người dùng
                    String name = userName;
                    lsDataUpdate.add(new DanhGia(name, diemso, binhluan, date));

                    tvIdUser.setText(name);
                    tvDateReview.setText(date);
                    edtBinhluan.setText(binhluan);


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


            // Lặp qua mảng để tìm phần tử có "MaDanhGia" khớp
            for (int i = 0; i < nguoiDungsArray.length(); i++) {
                JSONObject nguoiDung = nguoiDungsArray.getJSONObject(i);

                // Kiểm tra xem khóa "MaDanhGia" có tồn tại và có khớp với giá trị bạn quan tâm không
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


    @Override
    public void onResume() {
        super.onResume();

        if (getDialog() != null && getDialog().getWindow() != null) {
            // Đảm bảo Dialog và Window không null
            // Tiếp theo, thực hiện các hoạt động cần thiết

            // Lấy cửa sổ của DialogFragment
            Window window = getDialog().getWindow();

            // Đặt kích thước của cửa sổ
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT; // Đặt chiều rộng là MATCH_PARENT hoặc kích thước bạn muốn
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT; // Đặt chiều cao là WRAP_CONTENT hoặc kích thước bạn muốn
            window.setAttributes(params);

            // Đặt background
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // Nếu bạn muốn giữa màn hình, bạn có thể sử dụng Gravity
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);

        } else {
            Log.e("CustomReviewFragment", "Dialog or Window is null");
        }

    }



}