package com.example.riviuphim;

import static com.example.riviuphim.Data.ip;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.w3c.dom.Comment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_UserCmt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_UserCmt extends Fragment {
    ListView lvUsercmt;
    String maND = "";
    int idphim = 12;
    ArrayList<DanhGia> lsDanhgia = new ArrayList<>();

    ArrayList<UserCmt> lsDGs = new ArrayList<>();
    ArrayList<Nguoidung> lsNDcmt = new ArrayList<>();
    ArrayList<Comment1> lsCmts = new ArrayList<>();

    String urldanhgia = "http://" + ip + "/democuoiki/danhgia/dulieudanhgia.php";
    String urlND = "http://"+ ip +"/democuoiki/nguoidung/dulieunguoidung.php";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_UserCmt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_UserCmt.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_UserCmt newInstance(String param1, String param2) {
        Fragment_UserCmt fragment = new Fragment_UserCmt();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__user_cmt, container);
                lvUsercmt = view.findViewById(R.id.lvUsercmt);
        // Lấy dữ liệu từ Intent được gửi từ Main_Login
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            String taiKhoan = intent.getStringExtra("TaiKhoan");
            String matKhau = intent.getStringExtra("MatKhau");

            if (taiKhoan != null && !taiKhoan.isEmpty() && matKhau != null && !matKhau.isEmpty()) {
                Toast.makeText(getContext(), "Tên tài khoản và mật khẩu: " + taiKhoan + " " + matKhau, Toast.LENGTH_SHORT).show();
                // Tìm mã người dùng dựa trên thông tin tên người dùng và mật khẩu
                loadNguoiDung(taiKhoan, matKhau);
                loadDataDanhGia();
            }
        }
        return view;
    }
    void parseJSONData(String jsonString, String tentaikhoan, String matKhau) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray nguoiDungArray = object.getJSONArray("nguoidungs");

            for (int i = 0; i < nguoiDungArray.length(); ++i) {
                JSONObject obj = nguoiDungArray.getJSONObject(i);

                String taiKhoan = obj.getString("TaiKhoan");
                String mk = obj.getString("MatKhau");
                int maNguoiDung = obj.getInt("MaNguoiDung");

                // So sánh thông tin đăng nhập với dữ liệu trong JSON
                if (taiKhoan.equals(tentaikhoan) && mk.equals(matKhau)) {
                    // Lưu lại mã người dùng vào biến maND
                    maND = String.valueOf(maNguoiDung);
                    Toast.makeText(getContext(), "Mã người dùng: " + maNguoiDung, Toast.LENGTH_SHORT).show();
                    loadDanhGia(maNguoiDung);
                    loadND(maNguoiDung);
                    loadNDComment(maNguoiDung);
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    } //done

    void parseJSONDataND(String jsonString, int manguoidung){
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray nguoiDungArray = object.getJSONArray("nguoidungs");
            for (int i = 0; i < nguoiDungArray.length(); ++i) {
                JSONObject obj = nguoiDungArray.getJSONObject(i);
                int maNguoiDung = obj.getInt("MaNguoiDung");
                String tenNguoiDung = obj.getString("TenNguoiDung");

                // So sánh thông tin đăng nhập với dữ liệu trong JSON
                if (maNguoiDung == manguoidung) {
                    Toast.makeText(getContext(), "Tên người dùng: " + tenNguoiDung, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void loadND(int manguoidung) {
        StringRequest request = new StringRequest(urlND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONDataND(response, manguoidung);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    } //done


    void parseJSONDataMaDanhGia(String jsonString, int manguoidung){
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray nguoiDungArray = object.getJSONArray("danhgias");
            for (int i = 0; i < nguoiDungArray.length(); ++i) {
                JSONObject obj = nguoiDungArray.getJSONObject(i);
                int maNguoiDung = obj.getInt("MaNguoiDung");
                int maDanhGia = obj.getInt("MaDanhGia");

                // So sánh thông tin đăng nhập với dữ liệu trong JSON
                if (maNguoiDung == manguoidung) {
                    Toast.makeText(getContext(), "Mã đánh giá: " + maDanhGia, Toast.LENGTH_SHORT).show();
                    loadComment(maDanhGia);
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void loadDanhGia(int manguoidung) {
        StringRequest request = new StringRequest(urldanhgia, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONDataMaDanhGia(response, manguoidung);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    } //done

    private void loadDataDanhGia() {
        StringRequest requestDanhGia = new StringRequest(urldanhgia, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONDanhGia(response);
                if (!lsDGs.isEmpty()) {
                    float tong = 0;
                    for (int i = 0; i < lsDGs.size(); i++) {
                        tong += lsDGs.get(i).getDiemSo();
                    }// Chuyển đổi float thành chuỗi String
                    float diemTrungBinh = tong / lsDGs.size();
                    DecimalFormat df = new DecimalFormat("#.#"); // Định dạng số có 1 chữ số sau dấu phẩy

                    String diemSoString = df.format(diemTrungBinh);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueueDanhGia = Volley.newRequestQueue(getContext());
        requestQueueDanhGia.add(requestDanhGia);
    }

    void parseJSONDanhGia(String JSScore) {
        try {
            JSONObject jsonObject = new JSONObject(JSScore);
            JSONArray imageArray = jsonObject.getJSONArray("danhgias");
            for (int i = 0; i < imageArray.length(); i++) {
                JSONObject obj = imageArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");
                if (maPhim == idphim) {
                    int MaDanhGia = obj.getInt("MaDanhGia");
                    int MaNguoiDung = obj.getInt("MaNguoiDung");
                    int DiemSo = (int) obj.getDouble("DiemSo");
                    String BinhLuan = obj.getString("BinhLuan");
                    // Chuỗi ngày tháng từ JSON
                    String ngayBinhLuanString = obj.getString("NgayBinhLuan");
                    // Định dạng cho ngày tháng từ chuỗi
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    Date ngayBinhLuan = null;
                    try {
                        // Chuyển đổi chuỗi thành kiểu Date
                        ngayBinhLuan = format.parse(ngayBinhLuanString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    lsDGs.add(new UserCmt(MaDanhGia, maPhim, MaNguoiDung, DiemSo, BinhLuan, ngayBinhLuan));
                }
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    void parseJSONDataComment(String jsonString, int madanhgia){
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray nguoiDungArray = object.getJSONArray("danhgias");
            for (int i = 0; i < nguoiDungArray.length(); ++i) {
                JSONObject obj = nguoiDungArray.getJSONObject(i);
                int Madanhgia = obj.getInt("MaDanhGia");
                String binhluan = obj.getString("BinhLuan");
                int diemso = obj.getInt("DiemSo");
                String ngayBinhLuanString = obj.getString("NgayBinhLuan");

                // Chuyển đổi chuỗi ngày thành đối tượng Date (nếu cần)
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date ngaybinhluan = null;
                try {
                    ngaybinhluan = format.parse(ngayBinhLuanString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                getUserData(maND, binhluan, diemso, ngayBinhLuanString);
                // So sánh thông tin đăng nhập với dữ liệu trong JSON
                if (madanhgia == Madanhgia) {
                    // Assuming you have a constructor in the Comment class that accepts Comment1 attributes
                    lsCmts.add(new Comment1(binhluan, diemso, ngaybinhluan));
                    // Notify adapter that data set has changed
//                    if (!lsCmts.isEmpty()) {
//                        CustomAdapterComment customAdapter = new CustomAdapterComment(Main_UserComment.this, R.layout.layout_item_usercmt, lsCmts);
//                        lvUsercmt.setAdapter(customAdapter);
//                    }
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void loadComment(int madanhgia) {
        StringRequest request = new StringRequest(urldanhgia, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONDataComment(response, madanhgia);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    void parseJSONDataNDComment(String jsonString, int manguoidung){
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray nguoiDungArray = object.getJSONArray("nguoidungs");
            for (int i = 0; i < nguoiDungArray.length(); ++i) {
                JSONObject obj = nguoiDungArray.getJSONObject(i);
                int Madanhgia = obj.getInt("MaNguoiDung");
                String tennguoidung = obj.getString("TenNguoiDung");
                // So sánh thông tin đăng nhập với dữ liệu trong JSON
                if (manguoidung == Madanhgia) {
                    lsNDcmt.add(new Nguoidung(tennguoidung));
                    // Notify adapter that data set has changed
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void loadNDComment(int manguoidung) {
        StringRequest request = new StringRequest(urlND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONDataNDComment(response, manguoidung);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }


    private void getUserData(String maNguoiDung, String binhluan, int diemso, String ngaybinhluan) {
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

                    lsDanhgia.add(new DanhGia(name, binhluan, diemso));

                    CustomAdapterUserCmt adapter = new CustomAdapterUserCmt(getContext(), R.layout.layout_itemt_cmt, lsDanhgia);
                    lvUsercmt.setAdapter(adapter);
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
}

