package com.example.riviuphim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
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
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearch extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSearch() {
        // Required empty public constructor
    }
    String savedEditTextValue = "";
    EditText edtSearch;
    String ip = Data.getIp();
    ListView lvResult;
    RadioButton btnPhim, btnDaoDien, btnDienVien;
    CustomAdapter_Thu adapter;
    List<Phim> phimList;
    List<DaoDien> ListDaoDien;
    String urlPhim = "http://" + ip + "/democuoiki/phim/dulieuphim.php";
    String urlDanhGia = "http://" + ip + "/democuoiki/danhgia/dulieudanhgia.php";
    String urlgetphimbydaodien = "http://" + ip + "/democuoiki/daodien/getphimbydaodien.php";

    String urlgetphimbydienvien = "http://" + ip + "/democuoiki/dienvien/getphimbydienvien.php";


    public static FragmentSearch newInstance(String param1, String param2) {
        FragmentSearch fragment = new FragmentSearch();
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
       View view =  inflater.inflate(R.layout.fragment_search, container, false);
       addControls(view);
        addEvent();

        phimList = new ArrayList<>();
        adapter = new CustomAdapter_Thu(getContext(), phimList);
        lvResult.setAdapter(adapter);

        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Phim phim = phimList.get(position);
                int maphim = phim.getMaPhim();
                Data.setIdPhim(maphim);

                FragmentDetailFilm fragment = new FragmentDetailFilm();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

       return view;
    }
    private void addEvent() {
        btnPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearch.getText().clear();
                edtSearch.setText(savedEditTextValue);
                phimList.clear();
                // Cập nhật ListView
                adapter.notifyDataSetChanged();

                // Hiển thị danh sách phim tương ứng với tên đạo diễn được nhập
                String phimKeyword = edtSearch.getText().toString();
                if (!phimKeyword.isEmpty()) {
                    searchPhimByDaoDien(phimKeyword);
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập tên phim", Toast.LENGTH_SHORT).show();
                }

                btnDienVien.setChecked(false);
                btnDaoDien.setChecked(false);
            }
        });
        btnDaoDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearch.getText().clear();
                edtSearch.setText(savedEditTextValue);
                phimList.clear();
                // Cập nhật ListView
                adapter.notifyDataSetChanged();

                // Hiển thị danh sách phim tương ứng với tên đạo diễn được nhập
                String daoDienKeyword = edtSearch.getText().toString();
                if (!daoDienKeyword.isEmpty()) {
                    searchPhimByDaoDien(daoDienKeyword);
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập tên đạo diễn", Toast.LENGTH_SHORT).show();
                }
                btnDienVien.setChecked(false);
                btnPhim.setChecked(false);
            }
        });

        btnDienVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearch.getText().clear();
                edtSearch.setText(savedEditTextValue);
                phimList.clear();
                // Cập nhật ListView
                adapter.notifyDataSetChanged();

                // Hiển thị danh sách phim tương ứng với tên đạo diễn được nhập
                String dienVienKeyword = edtSearch.getText().toString();
                if (!dienVienKeyword.isEmpty()) {
                    searchPhimByDienVien(dienVienKeyword);
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập tên diễn viên", Toast.LENGTH_SHORT).show();
                }
                btnPhim.setChecked(false);
                btnDaoDien.setChecked(false);
            }
        });


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("TextWatcher", "BeforeTextChanged: " + charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("TextWatcher", "OnTextChanged: " + charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TextWatcher", "AfterTextChanged: " + editable.toString());
                // Khi nội dung của EditText thay đổi, kiểm tra RadioButton được chọn và thực hiện tìm kiếm tương ứng
                if (btnPhim.isChecked()) {
                    searchPhim(editable.toString());
                } else if (btnDaoDien.isChecked()) {
                    searchPhimByDaoDien(editable.toString());
                } else if (btnDienVien.isChecked()) {
                    searchPhimByDienVien(editable.toString());
                }
            }
        });
    }
    private void searchPhimByDaoDien(String tenDaoDien) {
        final boolean[] daoDienFound = {false};
        if (tenDaoDien.isEmpty()) {
            searchPhim("");
        } else{
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, urlgetphimbydaodien, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Xử lý JSON response từ server
                    try {
                        Log.d("JSON Respone", response);
                        JSONObject jsonObject = new JSONObject(response);

                        // Lấy danh sách phim từ JSON
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONArray phimsArray = jsonObject.getJSONArray(tenDaoDien);

                            // Log các giá trị để kiểm tra
                            Log.d("TenDaoDien", tenDaoDien);
                            Log.d("PhimsArray", phimsArray.toString());

                            // So sánh tên đạo diễn từ EditText với tên đạo diễn từ cơ sở dữ liệu
                            if (key.equalsIgnoreCase(tenDaoDien)) {
                                // Xử lý danh sách phim của đạo diễn
                                List<Phim> danhSachPhim = new ArrayList<>();
                                for (int i = 0; i < phimsArray.length(); i++) {
                                    JSONObject phimObj = phimsArray.getJSONObject(i);

                                    int maPhim = phimObj.getInt("MaPhim");
                                    String tenPhim = phimObj.getString("TenPhim");
                                    String posterPhimURL = phimObj.getString("PosterPhim");

                                    Phim phim = new Phim(tenPhim, posterPhimURL, 0, maPhim);
                                    danhSachPhim.add(phim);
                                }
                                // Hiển thị danh sách phim
                                displayPhimList(danhSachPhim);
                                updateDiemSo();
                                daoDienFound[0] = true;
                            }

                        }
                        if (!daoDienFound[0]) {
                            Toast.makeText(getContext(), "Không tìm thấy đạo diễn", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error", error.toString());
                    Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);
        }
    }
    private void displayPhimList(List<Phim> danhSachPhim) {
        // Xóa danh sách cũ
        phimList.clear();

        // Thêm danh sách phim mới vào danh sách hiển thị
        phimList.addAll(danhSachPhim);

        // Cập nhật ListView
        adapter.notifyDataSetChanged();
    }
    public void addControls(View view) {
        edtSearch = (EditText) view.findViewById(R.id.edtSearch_Thu);
        lvResult = (ListView) view.findViewById(R.id.lvResult_Thu);
        btnPhim = (RadioButton) view.findViewById(R.id.btnPhim_Thu);
        btnDienVien = (RadioButton) view.findViewById(R.id.btnDienVien_Thu);
        btnDaoDien = (RadioButton) view.findViewById(R.id.btnDaoDien_Thu);
    }
    private void searchPhim(final String keyword) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlPhim, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Xử lý JSON response từ server
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray phims = jsonObject.getJSONArray("phims");

                    //xóa danh sách cũ
                    phimList.clear();

                    //lặp qua danh sách phim từ JSON và thêm vào danh sách hiển thị
                    for (int i = 0; i < phims.length(); i++) {
                        JSONObject phimObject = phims.getJSONObject(i);

                        String tenPhim = phimObject.getString("TenPhim");
                        String posterPhimURL = phimObject.getString("PosterPhim");
                        int maPhim = phimObject.getInt("MaPhim");

                        Phim phim = new Phim(tenPhim, posterPhimURL, 0, maPhim);

                        // Nếu tên phim chứa từ khóa tìm kiếm, thêm vào danh sách hiển thị
                        if (phim.searchPhim(keyword)) {
                            phimList.add(phim);
                        }
                    }

                    // Sau khi cập nhật danh sách phim, gọi hàm để tìm điểm đánh giá
                    updateDiemSo();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

        //thêm resquest vào hàng đợi
        requestQueue.add(stringRequest);
    }
    private void updateDiemSo() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequestDanhGia = new StringRequest(Request.Method.GET, urlDanhGia, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Xử lý JSON response từ server
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray danhGias = jsonObject.getJSONArray("danhgias");

                    //lặp qua danh sách phim từ JSON và thêm vào danh sách hiển thị
                    for (int i = 0; i < danhGias.length(); i++) {
                        JSONObject danhGiaObject = danhGias.getJSONObject(i);

                        int maPhim = danhGiaObject.getInt("MaPhim");
                        int diemSo = danhGiaObject.getInt("DiemSo");

                        // Tìm phim trong danh sách
                        for (Phim phim : phimList) {
                            if (phim.getMaPhim() == maPhim) {
                                phim.setDiemSo(diemSo);
                                break;
                            }
                        }
                    }

                    //cập nhật ListView
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

        //thêm request vào hàng đợi
        requestQueue.add(stringRequestDanhGia);
    }

    private void searchPhimByDienVien(String tenDienVien) {
        if (tenDienVien.isEmpty()) {
            searchPhim("");
        } else {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, urlgetphimbydienvien, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Xử lý JSON response từ server
                    try {
                        Log.d("JSON Respone", response);
                        JSONObject jsonObject = new JSONObject(response);

                        // Lấy danh sách phim từ JSON
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONArray phimsArray = jsonObject.getJSONArray(tenDienVien);

                            // Log các giá trị để kiểm tra
                            Log.d("TenDienVien", tenDienVien);
                            Log.d("PhimsArray", phimsArray.toString());

                            Log.d("Search", "Searching for: " + tenDienVien);

                            // So sánh tên đạo diễn từ EditText với tên đạo diễn từ cơ sở dữ liệu
                            if (key.equalsIgnoreCase(tenDienVien)) {
                                // Xử lý danh sách phim của đạo diễn
                                List<Phim> danhSachPhim = new ArrayList<>();
                                for (int i = 0; i < phimsArray.length(); i++) {
                                    JSONObject phimObj = phimsArray.getJSONObject(i);

                                    int maPhim = phimObj.getInt("MaPhim");
                                    String tenPhim = phimObj.getString("TenPhim");
                                    String posterPhimURL = phimObj.getString("PosterPhim");

                                    Phim phim = new Phim(tenPhim, posterPhimURL, 0, maPhim);
                                    danhSachPhim.add(phim);
                                }
                                updateDiemSo();
                                // Hiển thị danh sách phim
                                displayPhimList(danhSachPhim);
                                //return; // Kết thúc vòng lặp khi đã tìm thấy đạo diễn
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error", error.toString());
                    Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);
        }
    }
}