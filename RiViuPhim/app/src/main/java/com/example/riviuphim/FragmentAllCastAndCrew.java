package com.example.riviuphim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAllCastAndCrew#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAllCastAndCrew extends Fragment {

    int idPhim;
    String ip = Data.getIp();
    ArrayList<Phim_DienVien> lsPhim_DienVien = new ArrayList<>();
    ArrayList<DienVien> lsDienVien = new ArrayList<>();
    ArrayList<Integer> idPhimDienVien = new ArrayList<>();
    ListView listViewDienVien;

    String urlPhim_DienVien = "http://" + ip + "/democuoiki/Phim_DienVien/dulieuPhim_DienVien.php";
    String urlDienVien = "http://" + ip + "/democuoiki/DienVien/dulieuDienVien.php";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragmentAllCastAndCrew() {
        // Required empty public constructor
    }
    public interface OnDienVienSelectedListener {
        void onDienVienSelected(int dienVienId);
    }

    private OnDienVienSelectedListener mListener;

    public void setOnDienVienSelectedListener(OnDienVienSelectedListener listener) {
        this.mListener = listener;
    }

    public static FragmentAllCastAndCrew newInstance(String param1, String param2) {
        FragmentAllCastAndCrew fragment = new FragmentAllCastAndCrew();
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
            idPhim = getArguments().getInt("maPhim", -1);
            Log.d("onCreate: ", String.valueOf(idPhim));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_all_cast_and_crew, container, false);
        listViewDienVien = view.findViewById(R.id.listViewDienVien);
        loadDataPhim_DienVien();
        listViewDienVien.setOnItemClickListener((adapterView, view1, position, id) -> {
            DienVien dienVien = lsDienVien.get(position);

            FragmentCrew fragment = new FragmentCrew();
            Bundle bundle = new Bundle();
            bundle.putInt("idDienVien", dienVien.getMaDienVien());
            fragment.setArguments(bundle);

            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameCrew, fragment)
                        .addToBackStack(null) // Nếu muốn thêm fragment vào back stack
                        .commit();
            }
        });
        return view;
    }

    private void loadDataPhim_DienVien() {
        StringRequest requestPhim_DienVien = new StringRequest(urlPhim_DienVien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONPhim_DienVien(response);

                if (!lsPhim_DienVien.isEmpty()) {
                    for (int i = 0; i < lsPhim_DienVien.size(); i++) {
                        idPhimDienVien.add(lsPhim_DienVien.get(i).getMaDienVien());
                    }
                    Log.d("TAGfsfsdf", "onResponse: "+idPhimDienVien);
                    loadDataDienVien(idPhimDienVien); // Gọi loadDataDienVien khi lsPhim_DienVien đã được cập nhật
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error PhimDienVien", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueuePhim_DienVien = Volley.newRequestQueue(getContext());
        requestQueuePhim_DienVien.add(requestPhim_DienVien);
    }

    void parseJSONPhim_DienVien(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phim_DienVienArray = object.getJSONArray("phim_dienviens");

            for (int i = 0; i < phim_DienVienArray.length(); ++i) {
                JSONObject obj = phim_DienVienArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");
                if (maPhim == idPhim) {

                    int maDienVien = obj.getInt("MaDienVien");

                    lsPhim_DienVien.add(new Phim_DienVien(maPhim, maDienVien));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void loadDataDienVien(ArrayList<Integer> idPhimDienVien) {
        StringRequest requestDienVien = new StringRequest(urlDienVien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONDienVien(response,idPhimDienVien);
                if (!lsDienVien.isEmpty()) {
                    CustomAdapterDienVien adapterDienVien = new CustomAdapterDienVien(getContext(), lsDienVien);
                    listViewDienVien.setAdapter(adapterDienVien);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error requestDienVien", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueueDienVien = Volley.newRequestQueue(getContext());
        requestQueueDienVien.add(requestDienVien);
    }
    void parseJSONDienVien(String jsonString, ArrayList<Integer> idPhimDienVien) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray dienvienArray = object.getJSONArray("dienviens");

            for (int i = 0; i < dienvienArray.length(); ++i) {
                JSONObject obj = dienvienArray.getJSONObject(i);
                int maDienVien = obj.getInt("MaDienVien");

                for (int j = 0; j < idPhimDienVien.size(); j++) {
                    if (maDienVien == idPhimDienVien.get(j)) {
                        String tenDienVien = obj.getString("TenDienVien");
                        String quocTich = obj.getString("QuocTich");
                        String ngayThangNamSinhStr = obj.getString("NgayThangNamSinh");
                        String urlHinhAnh = obj.getString("UrlHinhAnh");

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date ngayThangNamSinh = null;
                        try {
                            ngayThangNamSinh = dateFormat.parse(ngayThangNamSinhStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        lsDienVien.add(new DienVien(maDienVien, tenDienVien, quocTich,urlHinhAnh,ngayThangNamSinh));
                    }
                }
            }

        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        if (lsDienVien.size()==0){
            Toast.makeText(getContext(), "Chuỗi rỗng", Toast.LENGTH_SHORT).show();
        }








//
//    void parseJSONPhim_DienVien(String jsonString) {
//        try {
//            JSONObject object = new JSONObject(jsonString);
//            JSONArray phim_DienVienArray = object.getJSONArray("phim_dienviens");
//
//            for (int i = 0; i < phim_DienVienArray.length(); ++i) {
//                JSONObject obj = phim_DienVienArray.getJSONObject(i);
//                int maPhim = obj.getInt("MaPhim");
//                if (maPhim == idPhim) {
//
//                    int maDienVien = obj.getInt("MaDienVien");
//
//                    lsPhim_DienVien.add(new Phim_DienVien(maPhim, maDienVien));
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//        private void switchToDetailFragment() {
//            // Create an instance of the fragment you want to switch to
//            FragmentCrew detailFragment = new FragmentCrew();
//
//            // Replace the current content with the fragment
//            if (getActivity() != null) {
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frameLayout, detailFragment)
//                        .addToBackStack(null)  // Tùy chọn: Thêm vào ngăn xếp quay lại cho việc điều hướng
//                        .commit();
//            }
//        }
//    private void loadDataPhim_DienVien() {
//        StringRequest requestPhim_DienVien = new StringRequest(urlPhim_DienVien, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                parseJSONPhim_DienVien(response);
//
//                if (!lsPhim_DienVien.isEmpty()) {
//                    for (int i = 0; i < lsPhim_DienVien.size(); i++) {
//                        idPhimDienVien.add(lsPhim_DienVien.get(i).getMaDienVien());
//                    }
//                    loadDataDienVien(idPhimDienVien);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "Error PhimDienVien", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        RequestQueue requestQueuePhim_DienVien = Volley.newRequestQueue(getContext());
//        requestQueuePhim_DienVien.add(requestPhim_DienVien);
//    }
//    void loadDataDienVien(ArrayList<Integer> idPhimDienVien) {
//        StringRequest requestDienVien = new StringRequest(urlDienVien, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                parseJSONDienVien(response,idPhimDienVien);
//                if (!lsDienVien.isEmpty()) {
//                    CustomAdapterDienVien adapterDienVien = new CustomAdapterDienVien(getContext(), lsDienVien);
//                    listViewDienVien.setAdapter(adapterDienVien);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "Error requestDienVien", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        RequestQueue requestQueueDienVien = Volley.newRequestQueue(getContext());
//        requestQueueDienVien.add(requestDienVien);
//    }
//    void parseJSONDienVien(String jsonString, ArrayList<Integer> idPhimDienVien) {
//        try {
//            JSONObject object = new JSONObject(jsonString);
//            JSONArray dienvienArray = object.getJSONArray("dienviens");
//
//            for (int i = 0; i < dienvienArray.length(); ++i) {
//                JSONObject obj = dienvienArray.getJSONObject(i);
//                int maDienVien = obj.getInt("MaDienVien");
//
//                for (int j = 0; j < idPhimDienVien.size(); j++) {
//                    if (maDienVien == idPhimDienVien.get(j)) {
//                        String tenDienVien = obj.getString("TenDienVien");
//                        String quocTich = obj.getString("QuocTich");
//                        String ngayThangNamSinhStr = obj.getString("NgayThangNamSinh");
//                        String urlHinhAnh = obj.getString("UrlHinhAnh");
//
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                        Date ngayThangNamSinh = null;
//                        try {
//                            ngayThangNamSinh = dateFormat.parse(ngayThangNamSinhStr);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        lsDienVien.add(new DienVien(maDienVien, tenDienVien, quocTich,urlHinhAnh,ngayThangNamSinh));
//                    }
//                }
//            }
//
//        } catch (JSONException ex) {
//            throw new RuntimeException(ex);
//        }
//        if (lsDienVien.size()==0){
//            Toast.makeText(getContext(), "Chuỗi rỗng", Toast.LENGTH_SHORT).show();
//        }
//        // Tạo adapter sau khi đã thêm tất cả diễn viên vào danh sách
    }
}