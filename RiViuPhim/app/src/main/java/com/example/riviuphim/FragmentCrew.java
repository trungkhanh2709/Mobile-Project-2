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


public class FragmentCrew extends Fragment {
    TextView txtTenDV, txtNSDV, txtQTDV, txtTest;

    ImageView imageDV;
    int idDienVien;
    String ip = Data.getIp();
    String urlDienVien = "http://" + ip + "/democuoiki/dienvien/dulieudienvien.php";

    ArrayList<DienVien> lsDienVien = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragmentCrew() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCrew.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCrew newInstance(String param1, String param2) {
        FragmentCrew fragment = new FragmentCrew();
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
            idDienVien = getArguments().getInt("idDienVien", -1);
            Log.d("onCreate: ", String.valueOf(idDienVien));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crew, container, false);

        txtTenDV = view.findViewById(R.id.txtTenDV);
        imageDV = view.findViewById(R.id.imageDV);
        txtQTDV = view.findViewById(R.id.txtQTDV);
        txtNSDV = view.findViewById(R.id.txtNSDV);
        loadDirectFromID(idDienVien);



        return view;
    }
    void parseJSONData(String jsonString,int ma) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phimArray = object.getJSONArray("dienviens");

            for (int i = 0; i < phimArray.length(); ++i) {
                JSONObject obj = phimArray.getJSONObject(i);


                int MaDienVien  = obj.getInt("MaDienVien");
                if (MaDienVien == ma ) {
                    String TenDienVien = obj.getString("TenDienVien");
                    String QuocTich = obj.getString("QuocTich");
                    String UrlHinhAnh = obj.getString("UrlHinhAnh");
                    String ngayThangNamSinhStr = obj.getString("NgayThangNamSinh");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date ngayThangNamSinh = null;
                    try {
                        ngayThangNamSinh = dateFormat.parse(ngayThangNamSinhStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    lsDienVien.add(new DienVien(MaDienVien, TenDienVien, QuocTich,UrlHinhAnh,ngayThangNamSinh));
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadDirectFromID(int ma) {
        StringRequest request = new StringRequest(urlDienVien, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                parseJSONData(response,ma);
                if (!lsDienVien.isEmpty()) {
                    String TenDienVien = lsDienVien.get(0).getTenDienVien();
                    String QuocTich = lsDienVien.get(0).getQuocTich();
                    Date ngayThangNamSinh = lsDienVien.get(0).getNgayThangNamSinh();
                    String UrlHinhAnh = lsDienVien.get(0).getUrlHinhAnh();
                    txtQTDV.setText(" - "+QuocTich);
                    SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String ngayThangNamFormatted = displayFormat.format(ngayThangNamSinh);

                    txtNSDV.setText(ngayThangNamFormatted);

                    txtTenDV.setText(TenDienVien);
                    Picasso.get().load(UrlHinhAnh).into(imageDV);



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