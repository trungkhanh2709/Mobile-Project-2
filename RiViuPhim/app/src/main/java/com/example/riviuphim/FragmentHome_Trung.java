package com.example.riviuphim;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class FragmentHome_Trung extends Fragment {
    RecyclerView rcv_Menu;
    CategoryAdapter_Trung categoryAdapter;
    ArrayList<Phim> lstPhim = new ArrayList<>();
    ArrayList<Phim> lstPhim2 = new ArrayList<>();
    ArrayList<Phim> lstPhim3 = new ArrayList<>();
    ArrayList<Phim> lstPhim4 = new ArrayList<>();

    ArrayList<Phim> lstPhim5 = new ArrayList<>();

    int idUser;

    String ip = Data.getIp();
    String urlphim = "http://" + ip + "/democuoiki/phim/dulieuphim.php";
    String urlGetPhimByMaDM = "http://" + ip + "/democuoiki/danhmuc/getphimbydanhmuc.php";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHome_Trung() {
    }


    public static FragmentHome_Trung newInstance(String param1, String param2) {
        FragmentHome_Trung fragment = new FragmentHome_Trung();
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
        View view = inflater.inflate(R.layout.fragment_home__trung, container, false);
        LoadPhimMenu();
        LoadPhimMenuHanhDong();
        LoadPhimMenuNolan();
        LoadPhimMenuMCU();
        LoadPhimMenuHoatHinh();
        rcv_Menu = view.findViewById(R.id.rcv_Menu);
        idUser = Data.getIdUser();
        return view;


    }

    void LoadPhimMenu() {
        StringRequest request = new StringRequest(urlphim, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJSONData(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Some error occurred!!"
                        + volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);
    }

    void parseJSONData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phimArray = object.getJSONArray("phims");
            //ArrayList<Phim> lstPhim = new ArrayList<>();
            for (int i = 0; i < phimArray.length(); ++i) {
                JSONObject obj = phimArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");
                ;
                String tenPhim = obj.getString("TenPhim");
                int NamRaMat = obj.getInt("NamRaMat");
                ;
                String quocGia = obj.getString("QuocGia");
                String moTaPhim = obj.getString("MoTaPhim");
                String urlTrailer = obj.getString("URLTrailer");
                String PosterPhim = obj.getString("PosterPhim");
                lstPhim.add(new Phim(maPhim, tenPhim, NamRaMat, quocGia, moTaPhim, urlTrailer, PosterPhim));

            }

            categoryAdapter = new CategoryAdapter_Trung(getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            rcv_Menu.setLayoutManager(linearLayoutManager);
            categoryAdapter.setData(getListCatehory());
            rcv_Menu.setAdapter(categoryAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void LoadPhimMenuHanhDong() {
        StringRequest request = new StringRequest(urlGetPhimByMaDM, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJSONDataHanhDong(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Some error occurred!!"
                        + volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);
    }

    void parseJSONDataHanhDong(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phimArray = object.getJSONArray("1");

            for (int i = 0; i < phimArray.length(); ++i) {
                JSONObject obj = phimArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");
                ;
                String tenPhim = obj.getString("TenPhim");
                int NamRaMat = obj.getInt("NamRaMat");
                ;
                String quocGia = obj.getString("QuocGia");
                String moTaPhim = obj.getString("MoTaPhim");
                String urlTrailer = obj.getString("URLTrailer");
                String PosterPhim = obj.getString("PosterPhim");
                lstPhim2.add(new Phim(maPhim, tenPhim, NamRaMat, quocGia, moTaPhim, urlTrailer, PosterPhim));

            }
            categoryAdapter = new CategoryAdapter_Trung(getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            rcv_Menu.setLayoutManager(linearLayoutManager);
            categoryAdapter.setData(getListCatehory());
            rcv_Menu.setAdapter(categoryAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void LoadPhimMenuNolan() {
        StringRequest request = new StringRequest(urlGetPhimByMaDM, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJSONDataNoLan(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Some error occurred!!"
                        + volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);
    }

    void parseJSONDataNoLan(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phimArray = object.getJSONArray("2");

            for (int i = 0; i < phimArray.length(); ++i) {
                JSONObject obj = phimArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");
                ;
                String tenPhim = obj.getString("TenPhim");
                int NamRaMat = obj.getInt("NamRaMat");
                ;
                String quocGia = obj.getString("QuocGia");
                String moTaPhim = obj.getString("MoTaPhim");
                String urlTrailer = obj.getString("URLTrailer");
                String PosterPhim = obj.getString("PosterPhim");
                lstPhim3.add(new Phim(maPhim, tenPhim, NamRaMat, quocGia, moTaPhim, urlTrailer, PosterPhim));

            }
            categoryAdapter = new CategoryAdapter_Trung(getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            rcv_Menu.setLayoutManager(linearLayoutManager);
            categoryAdapter.setData(getListCatehory());
            rcv_Menu.setAdapter(categoryAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void LoadPhimMenuMCU() {
        StringRequest request = new StringRequest(urlGetPhimByMaDM, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJSONDataMCU(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Some error occurred!!"
                        + volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);
    }

    public void parseJSONDataMCU(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phimArray = object.getJSONArray("3");

            for (int i = 0; i < phimArray.length(); ++i) {
                JSONObject obj = phimArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");
                ;
                String tenPhim = obj.getString("TenPhim");
                int NamRaMat = obj.getInt("NamRaMat");
                ;
                String quocGia = obj.getString("QuocGia");
                String moTaPhim = obj.getString("MoTaPhim");
                String urlTrailer = obj.getString("URLTrailer");
                String PosterPhim = obj.getString("PosterPhim");
                lstPhim4.add(new Phim(maPhim, tenPhim, NamRaMat, quocGia, moTaPhim, urlTrailer, PosterPhim));

            }
            categoryAdapter = new CategoryAdapter_Trung(getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            rcv_Menu.setLayoutManager(linearLayoutManager);
            categoryAdapter.setData(getListCatehory());
            rcv_Menu.setAdapter(categoryAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    void LoadPhimMenuHoatHinh() {
        StringRequest request = new StringRequest(urlGetPhimByMaDM, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJSONDataHoatHinh(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Some error occurred!!"
                        + volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);
    }

    public void parseJSONDataHoatHinh(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phimArray = object.getJSONArray("4");

            for (int i = 0; i < phimArray.length(); ++i) {
                JSONObject obj = phimArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");
                ;
                String tenPhim = obj.getString("TenPhim");
                int NamRaMat = obj.getInt("NamRaMat");
                ;
                String quocGia = obj.getString("QuocGia");
                String moTaPhim = obj.getString("MoTaPhim");
                String urlTrailer = obj.getString("URLTrailer");
                String PosterPhim = obj.getString("PosterPhim");
                lstPhim5.add(new Phim(maPhim, tenPhim, NamRaMat, quocGia, moTaPhim, urlTrailer, PosterPhim));

            }

            categoryAdapter = new CategoryAdapter_Trung(getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            rcv_Menu.setLayoutManager(linearLayoutManager);
            categoryAdapter.setData(getListCatehory());
            rcv_Menu.setAdapter(categoryAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Category> getListCatehory() {
        ArrayList<Category> list = new ArrayList<>();
        list.add(new Category("Toàn Bộ Phim", lstPhim));
        list.add(new Category("Top Phim Hành Động", lstPhim2));
        list.add(new Category("Đến từ Christopher Nolan", lstPhim3));
        list.add(new Category("Vũ Trụ MCU", lstPhim4));
        list.add(new Category("Top Phim Hoat Hinh", lstPhim5));
        return list;


    }


    public void onItemClicked(Phim phim) {
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_phim", (Serializable) phim);
        fragment.setArguments(bundle);

        // Thay thế FragmentDetail trong Activity của bạn
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }


}