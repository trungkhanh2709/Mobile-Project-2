package com.example.riviuphim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivitySignUp extends AppCompatActivity {
    EditText edtTK, edtND, edtMK, edtMKcheck;
    Button btnSigUp;
    TextView tvSignIn;
    String ip =  Data.getIp();
    ImageView imgshow, imgshow2;
    String URL_FOR_ND_INSERT = "http://" + ip + "/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        URL_FOR_ND_INSERT = "http://" + ip + "/democuoiki/nguoidung/insert.php";
        edtTK = findViewById(R.id.edtTK);
        edtND = findViewById(R.id.edtND);
        edtMK = findViewById(R.id.edtMK);
        edtMKcheck = findViewById(R.id.edtMKcheck);
        imgshow = (ImageView)findViewById(R.id.img_show);
        imgshow2 = (ImageView) findViewById(R.id.img_show2);
        btnSigUp = findViewById(R.id.btnSigUp);
        tvSignIn = findViewById(R.id.tvSignIn);

        btnSigUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        imgshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selection = edtMK.getSelectionEnd(); // Lưu vị trí con trỏ
                // Đổi kiểu hiển thị của edtMK từ password sang text và ngược lại
                if (edtMK.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    // Hiển thị password
                    edtMK.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgshow.setImageResource(R.drawable.baseline_visibility_off_24);
                } else {
                    // Ẩn password
                    edtMK.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgshow.setImageResource(R.drawable.baseline_visibility_24); // Drawable1 là drawable khi ẩn password
                }
                // Đặt vị trí con trỏ ở cuối EditText sau khi thay đổi kiểu hiển thị
                edtMK.setSelection(selection);
            }
        });

        imgshow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selection = edtMKcheck.getSelectionEnd(); // Lưu vị trí con trỏ
                // Đổi kiểu hiển thị của edtMKcheck từ password sang text và ngược lại
                if (edtMKcheck.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    // Hiển thị password
                    edtMKcheck.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgshow2.setImageResource(R.drawable.baseline_visibility_off_24);
                } else {
                    // Ẩn password
                    edtMKcheck.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgshow2.setImageResource(R.drawable.baseline_visibility_24); // Drawable1 là drawable khi ẩn password
                }
                edtMKcheck.setSelection(selection);
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySignUp.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
    }

    private void submitForm() {
        String taikhoan = edtTK.getText().toString(); //đúng
        String tennguoidung = edtND.getText().toString(); //đúng
        String matkhau = edtMK.getText().toString();
        String matkhauCheck = edtMKcheck.getText().toString();
        // Kiểm tra xem các EditText có trống không
        if (taikhoan.isEmpty() || tennguoidung.isEmpty() || matkhau.isEmpty() || matkhauCheck.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
            return;
        }

        // Kiểm tra định dạng của tài khoản email
        if (!taikhoan.contains("@gmail")) {
            Toast.makeText(getApplicationContext(), "Tài khoản phải có định dạng @gmail", Toast.LENGTH_LONG).show();
            return;
        }
        if (tennguoidung.length() < 5) {
            Toast.makeText(getApplicationContext(), "Tên người dùng phải có ít nhất 5 ký tự", Toast.LENGTH_LONG).show();
            return;
        }

        // Kiểm tra độ dài của mật khẩu
        if (matkhau.length() < 5 || matkhau.length() > 10 || matkhauCheck.length() < 5 || matkhauCheck.length() > 10) {
            Toast.makeText(getApplicationContext(), "Mật khẩu phải từ 5 đến 10 kí tự", Toast.LENGTH_LONG).show();
            return;
        }

        // Kiểm tra mật khẩu và mật khẩu xác nhận
        if (!matkhau.equals(matkhauCheck)) {
            Toast.makeText(getApplicationContext(), "Mật khẩu không khớp", Toast.LENGTH_LONG).show();
            return;
        }

        JSONObject json = new JSONObject();
        try {
            json.put("TaiKhoan", taikhoan);
            json.put("TenNguoiDung", tennguoidung);
            json.put("MatKhau", matkhau);

            them(json);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Lỗi tạo JSON", Toast.LENGTH_LONG).show();
        }
    }

    private void them(JSONObject jsonSV) {
        RequestQueue queue = Volley.newRequestQueue(ActivitySignUp.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL_FOR_ND_INSERT,
                jsonSV,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(ActivitySignUp.this, "Tạo tài khoản thành công", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivitySignUp.this, "Tạo tài khoản thành công", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ActivitySignUp.this, ActivityLogin.class);
                        startActivity(intent);
                        Log.d("TAG", "onErrorResponse: " + error.toString());
                        if (error.networkResponse != null) {
                            String statusCode = String.valueOf(error.networkResponse.statusCode);
                            Toast.makeText(ActivitySignUp.this, "Status code: " + statusCode, Toast.LENGTH_LONG).show();
                            byte[] errorData = error.networkResponse.data;
                            if (errorData != null) {
                                String errorString = new String(errorData);
                                Toast.makeText(ActivitySignUp.this, "Error response: " + errorString, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
        );

        queue.add(jsonObjectRequest);
    }
}