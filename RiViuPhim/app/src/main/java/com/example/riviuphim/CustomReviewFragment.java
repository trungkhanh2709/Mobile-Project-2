package com.example.riviuphim;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomReviewFragment extends DialogFragment {
// rồi

    // Khai báo biến DiemSo ở cấp độ class
    private String DiemSo;
    private int maN = Data.getIdUser();
        String MaNguoiDung = String.valueOf(maN);
    private int maP = Data.getIdPhim();

    private String MaPhim = String.valueOf(maP);
    private Button btnPost;
    private TextView tvIdUser, tvDateReview;
    private RatingBar ratingBar;
    private EditText edtBinhluan;
    private ImageButton btnOut;


    private Date ngay = new Date();
    private Date ngay_notime = new Date();
    private SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dformat_notime = new SimpleDateFormat("dd-MM-yyyy");
    private String ngayf = dformat.format(ngay);
    private String ngayf_notime = dformat_notime.format(ngay_notime);


    private String ip = Data.getIp();
    private String urlInsertDanhgia = "http://"+ ip +"/democuoiki/danhgia/insertDanhGia.php";

    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_review, container, false);

        ratingBar = view.findViewById(R.id.ratingBar);
        edtBinhluan = view.findViewById(R.id.edtBinhluan);
        btnPost = view.findViewById(R.id.btnPost);
        btnOut = view.findViewById(R.id.btnOut);
        tvIdUser = view.findViewById(R.id.tvIdUser);
        tvDateReview = view.findViewById(R.id.tvDateReview);
        tvDateReview.setText(ngayf_notime);

        getUserData(MaNguoiDung);


        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Đóng fragment khi button được nhấn
                dismiss();
            }
        });

        // Đặt màu nền trực tiếp cho button
        btnPost.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button_pressed_color));

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String binhLuan = edtBinhluan.getText().toString().trim();

                if(DiemSo == null && TextUtils.isEmpty(binhLuan)){
                    Toast.makeText(getContext(), "Vui lòng đánh giá và nhập bình luận trước khi đăng bài", Toast.LENGTH_SHORT).show();
                } else if (DiemSo == null ){
                    Toast.makeText(getContext(), "Vui lòng đánh giá trước khi đăng bài", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(binhLuan)){
                    Toast.makeText(getContext(), "Vui lòng nhập bình luận trước khi đăng bài", Toast.LENGTH_SHORT).show();
                } else {
                    new InsertDataAsyncTask().execute(urlInsertDanhgia, MaPhim, MaNguoiDung, DiemSo, binhLuan ,ngayf );
                    Toast.makeText(getContext(), "Đánh giá thành công " , Toast.LENGTH_SHORT).show();

                    TestFragment();
                }

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



        return view;

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
        return response;
    }


    private void getUserData(String maNguoiDung) {
        String urlGetUserName = "http://" + ip + "/democuoiki/nguoidung/dulieunguoidung.php?MaNguoiDung=" + maNguoiDung;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetUserName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    // Phân tích phản hồi từ API thứ hai để lấy tên người dùng
                    String userName = parseUserNameFromResponse(response, MaNguoiDung);
                    tvIdUser.setText(userName);

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
