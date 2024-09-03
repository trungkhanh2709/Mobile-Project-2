package com.example.riviuphim;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDirect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDirect extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<DaoDien> lsDaoDiens = new ArrayList<>();
    String ip = Data.getIp();
    String urlDaodien = "http://" + ip + "/democuoiki/daodien/dulieudaodien.php";
    TextView txtTenDaoDienActi,txtQuocGiaDaoDienActi,txtNSDaoDienActi;
    ImageView imgDaoDienActi;
    private String mParam1;
    private String mParam2;
    String tenDD;

    public FragmentDirect() {
        // Required empty public constructor
    }


    public static FragmentDirect newInstance(String param1, String param2) {
        FragmentDirect fragment = new FragmentDirect();
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
            tenDD = getArguments().getString("tenDD");
            Log.d("onCreate: ", String.valueOf(tenDD));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_direct, container, false);
        txtTenDaoDienActi = (TextView) view.findViewById(R.id.txtTenDaoDienActi);
        txtQuocGiaDaoDienActi = (TextView) view.findViewById(R.id.txtQuocGiaDaoDienActi);
        txtNSDaoDienActi = (TextView) view.findViewById(R.id.txtNSDaoDienActi);
        imgDaoDienActi = (ImageView) view.findViewById(R.id.imgDaoDienActi);
        loadDirectFromName(tenDD);
        return view;
    }
    void parseJSONData(String jsonString,String NameDirect) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phimArray = object.getJSONArray("daodiens");

            for (int i = 0; i < phimArray.length(); ++i) {
                JSONObject obj = phimArray.getJSONObject(i);

                String TenDaoDien = obj.getString("TenDaoDien");
                int MaDaoDien = obj.getInt("MaDaoDien");
                if (TenDaoDien.equals(NameDirect)) {
                    String DaoDien = obj.getString("TenDaoDien");
                    String UrlHinhAnh = obj.getString("UrlHinhAnh");
                    String QuocTich = obj.getString("QuocTich");
                    String ngayThangNamSinhStr = obj.getString("NgayThangNamSinh");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date ngayThangNamSinh = null;
                    try {
                        ngayThangNamSinh = dateFormat.parse(ngayThangNamSinhStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    lsDaoDiens.add(new DaoDien(MaDaoDien, DaoDien, QuocTich,ngayThangNamSinh,UrlHinhAnh));
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadDirectFromName(String tenDD) {
        StringRequest request = new StringRequest(urlDaodien, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                parseJSONData(response,tenDD);
                if (!lsDaoDiens.isEmpty()) {
                    String TenDaoDien = lsDaoDiens.get(0).getTenDaoDien();
                    String QuocTich = lsDaoDiens.get(0).getQuocTich();
                    Date ngayThangNamSinh = lsDaoDiens.get(0).getNgayThangNamSinh();
                    String UrlHinhAnh = lsDaoDiens.get(0).getUrlHinhAnh();
                    txtQuocGiaDaoDienActi.setText(" - "+QuocTich);
                    SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String ngayThangNamFormatted = displayFormat.format(ngayThangNamSinh);

                    txtNSDaoDienActi.setText(ngayThangNamFormatted);

                    txtTenDaoDienActi.setText(TenDaoDien);
                    Picasso.get().load(UrlHinhAnh).into(imgDaoDienActi);



                } else {
                    Toast.makeText(getContext(), "không c", Toast.LENGTH_SHORT).show();
                }
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
}