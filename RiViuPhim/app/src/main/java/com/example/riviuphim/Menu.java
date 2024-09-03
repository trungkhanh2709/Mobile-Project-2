package com.example.riviuphim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Menu extends AppCompatActivity {
    String taiKhoan;
    String matKhau;
    String ip = Data.getIp();
    int maND;
    String urlND = "http://"+ ip +"/democuoiki/nguoidung/dulieunguoidung.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menubar);

        Intent intent = getIntent();
        if (intent != null) {
            taiKhoan = intent.getStringExtra("TaiKhoan");
            matKhau = intent.getStringExtra("MatKhau");

            if (taiKhoan != null && !taiKhoan.isEmpty() && matKhau != null && !matKhau.isEmpty()) {

                loadNguoiDung(taiKhoan, matKhau);
                loadHomeFragment();
            }
        }



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
         bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FloatingActionButton button = findViewById(R.id.fabMain);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                int id = item.getItemId();
                if (id == R.id.menuHome) {
                    fragment = new FragmentHome_Trung();
                } else if (id == R.id.menuUser) {
                    fragment = new Fragment_AccountUser();
                }

                else {
                    Toast.makeText(Menu.this, "1", Toast.LENGTH_SHORT).show();
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                    return true;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentSearch fragment = new FragmentSearch();
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                }
            }
        });


    }
        void loadNguoiDung(String tenNguoiDung, String matKhau) {
            StringRequest request = new StringRequest(urlND, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    parseJSONData(response, tenNguoiDung, matKhau);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Menu.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(Menu.this);
            requestQueue.add(request);
        } //done

        void parseJSONData(String jsonString, String tentaikhoan, String matKhau) {
            try {
                JSONObject object = new JSONObject(jsonString);
                JSONArray nguoiDungArray = object.getJSONArray("nguoidungs");

                for (int i = 0; i < nguoiDungArray.length(); ++i) {
                    JSONObject obj = nguoiDungArray.getJSONObject(i);

                    String taiKhoan = obj.getString("TaiKhoan");
                    String mk = obj.getString("MatKhau");
                    int maNguoiDung = obj.getInt("MaNguoiDung");

                    if (taiKhoan.equals(tentaikhoan) && mk.equals(matKhau)) {
                        // Lưu lại mã người dùng vào biến maND
                        maND = Integer.parseInt(String.valueOf(maNguoiDung));
                        Data.setIdUser(maND);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    void loadHomeFragment() {
        FragmentHome_Trung fragment = new FragmentHome_Trung();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}