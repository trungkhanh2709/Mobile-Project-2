package com.example.riviuphim;

import static com.example.riviuphim.Data.ip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_AccountUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_AccountUser extends Fragment {

    String ip = Data.getIp();
    EditText editDisplayName, editPassword, editConfirmPass;
    Button btnChange, btnUpdatePass, btnExit, btnXS, btnDG;
    ImageView imgViewPass, imgView_ConfirmPass;
    String urlND = "http://"+ ip +"/democuoiki/nguoidung/dulieunguoidung.php";
    String maND= "";
    ArrayList<Nguoidung> lsNSDs = new ArrayList<>();
    String URL_FOR_SV_UPDATE =  "http://" + ip + "/democuoiki/nguoidung/update.php";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext; // Lưu trữ context


    public Fragment_AccountUser() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context.getApplicationContext();
    }

    public static Fragment_AccountUser newInstance(String param1, String param2) {
        Fragment_AccountUser fragment = new Fragment_AccountUser();
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
        View view = inflater.inflate(R.layout.fragment__account_user, container, false);
        editDisplayName = view.findViewById(R.id.editDisplayName);
        editPassword = view.findViewById(R.id.editPassword);
        editConfirmPass = view.findViewById(R.id.editConfirmPass);
        btnUpdatePass = view.findViewById(R.id.btnUpdatePass);
        btnExit = view.findViewById(R.id.btnExit);
        btnXS = view.findViewById(R.id.btnXS);
        btnDG = view.findViewById(R.id.btnDG);
        imgViewPass = view.findViewById(R.id.imgViewPass);
        imgView_ConfirmPass = view.findViewById(R.id.imgView_ConfirmPass);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            String taiKhoan = intent.getStringExtra("TaiKhoan");
            String matKhau = intent.getStringExtra("MatKhau");

            if (taiKhoan != null && !taiKhoan.isEmpty() && matKhau != null && !matKhau.isEmpty()) {

                loadNguoiDung(taiKhoan, matKhau);
            }
            final String finalMatKhau = matKhau; // Capture matKhau as final for onClick listener
            btnUpdatePass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currentPassword = editPassword.getText().toString().trim(); // Lấy mật khẩu hiện tại từ EditText
                    String newPassword = editConfirmPass.getText().toString().trim(); // Lấy mật khẩu mới từ EditText


                    // So sánh mật khẩu hiện tại nhập vào với mật khẩu từ Intent
                    if (currentPassword.equals(finalMatKhau)) {
                        // Kiểm tra xem người dùng đã nhập mật khẩu mới hay chưa
                        if (!newPassword.isEmpty()) {
                            // Gọi hàm cập nhật mật khẩu
                            updatePassword(newPassword);
                        } else {
                            Toast.makeText(getActivity(), "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ActivityLogin.class);
                    startActivity(intent);
                }
            });

            imgViewPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selection = editPassword.getSelectionEnd(); // Lưu vị trí con trỏ
                    // Đổi kiểu hiển thị của edtMK từ password sang text và ngược lại
                    if (editPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                        // Hiển thị password
                        editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        imgViewPass.setImageResource(R.drawable.baseline_visibility_off_24);
                    } else {
                        // Ẩn password
                        editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        imgViewPass.setImageResource(R.drawable.baseline_visibility_24); // Drawable1 là drawable khi ẩn password
                    }
                    // Đặt vị trí con trỏ ở cuối EditText sau khi thay đổi kiểu hiển thị
                    editPassword.setSelection(selection);
                }
            });

            imgView_ConfirmPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selection = editConfirmPass.getSelectionEnd(); // Lưu vị trí con trỏ
                    // Đổi kiểu hiển thị của edtMKcheck từ password sang text và ngược lại
                    if (editConfirmPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                        // Hiển thị password
                        editConfirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        imgView_ConfirmPass.setImageResource(R.drawable.baseline_visibility_off_24);
                    } else {
                        // Ẩn password
                        editConfirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        imgView_ConfirmPass.setImageResource(R.drawable.baseline_visibility_24); // Drawable1 là drawable khi ẩn password
                    }
                    editConfirmPass.setSelection(selection);
                }
            });

            btnDG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Tạo instance của FragmentAllComment
                    Fragment_CmtUserr fragment_userCmt = new Fragment_CmtUserr();

                    // Thay đổi Fragment từ Fragment_AccountUser sang FragmentAllComment
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, fragment_userCmt)
                            .addToBackStack(null) // Nếu bạn muốn thêm vào BackStack
                            .commit();
                }
            });


        }

        return view;
    }
    private void updatePassword(String newPassword) {
        // Tạo một đối tượng JSON chứa dữ liệu cần cập nhật
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("MaNguoiDung", maND); // Sử dụng mã người dùng đã lấy được từ trước
            jsonBody.put("MatKhau", newPassword); // Mật khẩu mới để cập nhật
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Tạo một RequestQueue để thực hiện yêu cầu
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // Tạo một yêu cầu JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_FOR_SV_UPDATE, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SERVER_RESPONSE", "Response: " + response.toString());
                        // Xử lý phản hồi từ server khi cập nhật thành công
                        Toast.makeText(getContext(), "Lỗi khi cập nhật", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi khi gửi yêu cầu cập nhật
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    String errorData = new String(error.networkResponse.data);
                    Toast.makeText(getContext(), "Error: " + errorData, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Thêm yêu cầu vào hàng đợi
        queue.add(jsonObjectRequest);
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

                    // Gọi hàm để lấy thông tin chi tiết người dùng dựa trên mã người dùng
                    loadNguoiDung(maNguoiDung);
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //done

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
    }
    //done


    void parseJSONDataNguoiDung(String jsonString, int manguoidung){
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray nguoiDungArray = object.getJSONArray("nguoidungs");
            for (int i = 0; i < nguoiDungArray.length(); ++i) {
                JSONObject obj = nguoiDungArray.getJSONObject(i);
                int maNguoiDung = obj.getInt("MaNguoiDung");
                String tenNguoiDung = obj.getString("TenNguoiDung");

                // So sánh thông tin đăng nhập với dữ liệu trong JSON
                if (maNguoiDung == manguoidung) {
                    lsNSDs.add(new Nguoidung(tenNguoiDung));
                    loadNguoiDung(maNguoiDung);
                    editDisplayName.setText(tenNguoiDung);
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void loadNguoiDung(int manguoidung) {
        StringRequest request = new StringRequest(urlND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONDataNguoiDung(response, manguoidung);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        requestQueue.add(request);
    }

}