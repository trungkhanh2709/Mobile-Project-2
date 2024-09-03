package com.example.riviuphim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {
    EditText editND, editMK;
    TextView tvDK;
    Button btnLogin;
    ImageButton imgButton_SeePass;
    CheckBox checkRemember;
    SharedPreferences sharedPreferences;
    String ip = Data.getIp();
    String loginUrl = "http://" + ip + "/democuoiki/nguoidung/login.php"; // Địa chỉ API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        tvDK = findViewById(R.id.tvDK);
        editND = findViewById(R.id.editND); //edit của tài khoản
        editMK = findViewById(R.id.editMK);
        btnLogin = findViewById(R.id.btnLogin_Vy);
        imgButton_SeePass = (ImageButton) findViewById(R.id.imgButton_SeePass);
        // Ánh xạ checkBoxRemember từ layout
        checkRemember = (CheckBox) findViewById(R.id.checkRemember);


        boolean isChecked = sharedPreferences.getBoolean("isChecked", false);
        checkRemember.setChecked(isChecked);

        if (isChecked) {
            String savedTaiKhoan = sharedPreferences.getString("TaiKhoan", "");
            String savedMatKhau = sharedPreferences.getString("MatKhau", "");
            editND.setText(savedTaiKhoan);
            editMK.setText(savedMatKhau);
        }

        checkRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isChecked", isChecked);
                editor.apply();
            }
        });

        tvDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivitySignUp.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TaiKhoan = editND.getText().toString();//edit của tài khoản
                String MatKhau = editMK.getText().toString();

                JSONObject jsonParams = new JSONObject();
                try {
                    jsonParams.put("TaiKhoan", TaiKhoan);
                    jsonParams.put("MatKhau", MatKhau);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("Response", response); // In phản hồi từ máy chủ để kiểm tra
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        // Hiển thị toast khi đăng nhập thành công
                                        if (checkRemember.isChecked()) {
                                            String taikhoan = editND.getText().toString();
                                            String matkhau = editMK.getText().toString();
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("TaiKhoan", taikhoan);
                                            editor.putString("MatKhau", matkhau);
                                            editor.apply();
                                        }

                                        // Lưu tên tài khoản vào SharedPreferences để sử dụng ở các Activity khác
                                        String taikhoan = editND.getText().toString();
                                        String matkhau = editMK.getText().toString();
                                        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("TaiKhoan", taikhoan);
                                        editor.putString("MatKhau", matkhau);
                                        editor.apply();

                                        // Chuyển sang Main_WishList và truyền tên tài khoản
                                        Intent intent = new Intent(ActivityLogin.this, Menu.class);
                                        intent.putExtra("TaiKhoan", taikhoan);
                                        intent.putExtra("MatKhau", matkhau);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(ActivityLogin.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("JSONException", e.getMessage());
                                }

                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ActivityLogin.this, "Thất bại: " + error.toString(), Toast.LENGTH_LONG).show();
                                Log.d("TAG", "onErrorResponse: " + error.toString());
                                if (error.networkResponse != null) {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    Toast.makeText(ActivityLogin.this, "Status code: " + statusCode, Toast.LENGTH_LONG).show();
                                    byte[] errorData = error.networkResponse.data;
                                    if (errorData != null) {
                                        String errorString = new String(errorData);
                                        Toast.makeText(ActivityLogin.this, "Error response: " + errorString, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }) {

                    @Override
                    public byte[] getBody() {
                        return jsonParams.toString().getBytes();
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }
                };

                Volley.newRequestQueue(ActivityLogin.this).add(stringRequest);
            }
        });

        imgButton_SeePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selection = editMK.getSelectionEnd(); // Lưu vị trí con trỏ

                if (editMK.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    // Hiển thị password
                    editMK.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgButton_SeePass.setImageResource(R.drawable.baseline_visibility_off_24);
                } else {
                    // Ẩn password
                    editMK.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgButton_SeePass.setImageResource(R.drawable.baseline_visibility_24);
                }

                // Đặt vị trí con trỏ ở cuối EditText sau khi thay đổi kiểu hiển thị
                editMK.setSelection(selection);
            }
        });
    }

}

