package com.example.riviuphim;

import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class FragmentDetailFilm extends Fragment {

    WebView webview_Youtube;
    ImageView impPoster;
    TextView txtNameFilm, txtRating, txtNamRaMat, txtDecription,txtTenDaoDien,txtQuocGiaFilm;
    LinearLayout layoutButtons, layoutDirector,layoutAllCast;
    GridLayout gridLayoutImagePosterDetail;
    RecyclerView rcv_Menu;
    Button btnReview, btnUpdate;
    ImageButton imgFullcmt;
    ListView lvReviewLimit;


    String ip = Data.getIp();
    int idphim= Data.getIdPhim();
    int idUseru =  Data.getIdUser();
    String MaNguoiDung = String.valueOf(idUseru);
    String MaPhim = String.valueOf(idphim);
    String TAG ="TEST GO LOI";

    ArrayList<Phim> lstDetaFilm = new ArrayList<>();
    ArrayList<ImageFilm> lstImageFilm = new ArrayList<>();
    ArrayList<DanhGia> lsDanhGia = new ArrayList<>();
    ArrayList<DanhGia> lvDataDanhgia = new ArrayList<>();
    ArrayList<phim_theloai> lsPhimTheLoai = new ArrayList<>();
    ArrayList<TheLoai> lsTheLoai = new ArrayList<>();
    ArrayList<Phim_DaoDien> lsPhimDaoDien = new ArrayList<>();
    ArrayList<DaoDien> lsDaoDien = new ArrayList<>();


    ArrayList<Integer> idPhimTheLoai = new ArrayList<>();
    ArrayList<Integer> idPhimDaoDien = new ArrayList<>();
    String urlphim = "http://" + ip + "/democuoiki/phim/dulieuphim.php";
    String urlImange = "http://" + ip + "/democuoiki/hinhanhphim/dulieuhinhanhphim.php";
    String urlDanhGia = "http://" + ip + "/democuoiki/danhgia/dulieudanhgia.php";
    String urlTheLoai = "http://" + ip + "/democuoiki/theloai/dulieutheloai.php";
    String urlPhim_TheLoai = "http://" + ip + "/democuoiki/phim_theloai/dulieuphim_theloai.php";
    String urlPhim_Daodien = "http://" + ip + "/democuoiki/phim_daodien/dulieuphim_daodien.php";
    String urlDaodien = "http://" + ip + "/democuoiki/daodien/dulieudaodien.php";
    String urlGetPhimLienQuan = "http://" + ip + "/democuoiki/theloai/dulieuphimlienquan.php";

    CategoryAdapter_Trung categoryAdapter;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentDetailFilm() {
        // Required empty public constructor
    }

    public static FragmentDetailFilm newInstance(String param1, String param2) {
        FragmentDetailFilm fragment = new FragmentDetailFilm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            idphim = bundle.getInt("key");
    }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_film, container, false);



        addControls(view);


        loadDataPoster();
        loadDataDetail();
        loadDataDanhGia();
        loadDataPhim_TheLoai();
        loadDataTheLoai();
        loadDataPhim_DaoDien();
        LoadPhimLienQuan();
        addEvent();
        getAllData(urlDanhGia);
        getDataNguoidung(urlDanhGia);
        return view;
    }



    private void addEvent() {
        imgFullcmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentAllComment fragmentAllComment = new FragmentAllComment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragmentAllComment); // Sử dụng hàm add() thay vì replace()
                fragmentTransaction.commit();
            }
        });
        layoutAllCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentAllCastAndCrew fragment = new FragmentAllCastAndCrew();
                Bundle bundle = new Bundle();
                bundle.putInt("maPhim", idphim);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null); // Nếu muốn thêm fragment vào back stack
                transaction.commit();

            }
        });

        layoutDirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDD = (String) txtTenDaoDien.getText();

                FragmentDirect fragment = new FragmentDirect();
                Bundle bundle = new Bundle();
                bundle.putString("tenDD", tenDD);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null); // Nếu muốn thêm fragment vào back stack
                transaction.commit();
            }
        });

    }

    public void parseJsonDataNguoidung(String reponse) throws JSONException {
        try {
            JSONObject object = new JSONObject(reponse);
            JSONArray jsonArray = object.getJSONArray("danhgias");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.has("MaPhim")) {
                    String maPhim = jsonObject.getString("MaPhim");
                    String maNguoiDung = jsonObject.getString("MaNguoiDung");
                    // Nếu có cả 2 thì hiện update
                    if (maPhim.equals(MaPhim) && maNguoiDung.equals(MaNguoiDung)) {
                        btnReview.setVisibility(View.INVISIBLE);
                        // Khi muốn hiện Button
                        btnUpdate.setVisibility(View.VISIBLE);
                        btnUpdate.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT; // Đặt lại chiều cao
                        if (btnUpdate != null) {
                            // Đảm bảo imageButton không null
                            btnUpdate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    FragmentUpdate rFragment = new FragmentUpdate();
                                    rFragment.show(getChildFragmentManager(), "FragmentUpdate");


                                }
                            });
                        } else {
                            Log.e("FragmentAllComment", "ImageButton is null");
                        }

                        break;

                        // Nếu chỉ có mã phim thì hiện review
                    } else if (maPhim.equals(MaPhim)) {
                        // Ẩn button
                        btnUpdate.setVisibility(View.INVISIBLE);
                        btnReview.setVisibility(View.VISIBLE);
                        btnReview.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT; // Đặt lại chiều cao

                        if (btnReview != null) {
                            // Đảm bảo imageButton không null
                            btnReview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    CustomReviewFragment rFragment = new CustomReviewFragment();
                                    rFragment.show(getChildFragmentManager(), "CustomReviewFragment");

                                }
                            });
                        } else {
                            Log.e("FragmentAllComment", "ImageButton is null");
                        }

                    } else {
                        Log.e("FragmentDanhgia", "Chưa đăng nhập");
                    }
                } else {
                    // Xử lý trường hợp khi khóa "MaPhim" bị thiếu
                    Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu!", Toast.LENGTH_SHORT).show();

                }

            }



        } catch (JSONException e){
            e.printStackTrace();
        }



    }

    private void getDataNguoidung(String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    parseJsonDataNguoidung(response);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error Data!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

    }

    private void getAllData(String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    parseJsonDataToArrayList(response);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error Data!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

    }



    public void parseJsonDataToArrayList(String reponse) throws JSONException {
        try {
            JSONObject object = new JSONObject(reponse);
            JSONArray jsonArray = object.getJSONArray("danhgias");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.has("MaPhim")) {
                    String maPhim = jsonObject.getString("MaPhim");
                    // Tiếp tục xử lý với giá trị đã lấy
                    if (maPhim.equals(MaPhim)) {
                        String maNguoidung = jsonObject.getString("MaNguoiDung");
                        int diemso = jsonObject.getInt("DiemSo");
                        String binhluan = jsonObject.getString("BinhLuan");
                        String date = jsonObject.getString("NgayBinhLuan");

                        // Gọi API thứ hai để lấy tên người dùng
                        getUserData(maNguoidung, diemso, binhluan, date);

                    }
                } else {
                    // Xử lý trường hợp khi khóa "MaPhim" bị thiếu
                    Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu mã phim!", Toast.LENGTH_SHORT).show();

                }

            }


        } catch (JSONException e){
            e.printStackTrace();
        }

    }


    private void getUserData(String maNguoiDung, int diemso, String binhluan, String date) {
        String urlGetUserName = "http://" + ip + "/democuoiki/nguoidung/dulieunguoidung.php?MaNguoiDung=" + maNguoiDung;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetUserName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    int numberOfItemsToShow = 2;
                    if (maNguoiDung.equals(MaNguoiDung)){
                        // Phân tích phản hồi từ API thứ hai để lấy tên người dùng
                        String userName = parseUserNameFromResponse(response, maNguoiDung);
                        // Tạo đối tượng Chitietphim với tên người dùng
                        String name = userName;
                        lvDataDanhgia.add(new DanhGia(name +" (you) ", diemso , binhluan, date));

                    } else {
                        // Phân tích phản hồi từ API thứ hai để lấy tên người dùng
                        String userName = parseUserNameFromResponse(response, maNguoiDung);

                        // Tạo đối tượng Chitietphim với tên người dùng
                        String name = userName;
                        lvDataDanhgia.add(new DanhGia(name, diemso, binhluan, date));

                    }
                    CustomAdapterDanhgia adapter = new CustomAdapterDanhgia(getContext(), new ArrayList<>(lvDataDanhgia.subList(0, Math.min(numberOfItemsToShow, lvDataDanhgia.size()))));
                    lvReviewLimit.setAdapter(adapter);

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

    private void loadDataPoster() {

        StringRequest requestImg = new StringRequest(urlImange, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONImage(response);


                int imageSizeInPixels = getResources().getDimensionPixelSize(R.dimen.image_size); // Kích thước ảnh cố định

                for (int i = 0; i < lstImageFilm.size(); i++) {
                    ImageView imageView = new ImageView(getContext());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        imageView.setBackground(getResources().getDrawable(R.drawable.bo_goc_anh));
                    } else {
                        imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bo_goc_anh));
                    }


                    String imageUrl = lstImageFilm.get(i).getUrlHinhAnh();
                    Picasso.get().load(imageUrl)
                            .resize(imageSizeInPixels, imageSizeInPixels)
                            .centerCrop()
                            .into(imageView);

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = imageSizeInPixels;
                    params.height = imageSizeInPixels;
                    params.setMargins(8, 8, 8, 8);

                    // Xác định hàng và cột cho mỗi ảnh
                    params.rowSpec = GridLayout.spec(i / 2); // Phần nguyên của i/2 sẽ định vị hàng
                    params.columnSpec = GridLayout.spec(i % 2); // Phần dư của i/2 sẽ định vị cột

                    imageView.setLayoutParams(params);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); // Thiết lập scaleType
                    gridLayoutImagePosterDetail.addView(imageView);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestImage = Volley.newRequestQueue(getContext());
        requestImage.add(requestImg);
    }

    private void loadDataDetail() {
        StringRequest request = new StringRequest(urlphim, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONData(response);
                if (!lstDetaFilm.isEmpty()) {
                    String urlTrailer = lstDetaFilm.get(0).getUrlTrailer();
                    String videoId = GetIdFromYoutubeLink(urlTrailer);
                    String embeddedVideoUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=1&controls=1&rel=0";
                    String html = "<iframe width=\"100%\" height=\"100%\" src=\"" + embeddedVideoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>";
                    WebSettings webSettings = webview_Youtube.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webview_Youtube.loadData(html, "text/html", "utf-8");


                    String Name = lstDetaFilm.get(0).getTenPhim();
                    txtNameFilm.setText(Name.toString());
                    int namRaMat = lstDetaFilm.get(0).getNamRaMat();
                    txtNamRaMat.setText(String.valueOf(namRaMat));
                    String mota = lstDetaFilm.get(0).getMoTaPhim();
                    txtDecription.setText(mota.toString());
                    String QuocGia = lstDetaFilm.get(0).getQuocGia();
                    txtQuocGiaFilm.setText(QuocGia.toString());
                    String PosterPhim = lstDetaFilm.get(0).getPosterPhim();
                    Picasso.get().load(PosterPhim).into(impPoster);

                } else {
                    Toast.makeText(getContext(), "không có dữ liệu", Toast.LENGTH_SHORT).show();
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

    private void loadDataDanhGia() {
        StringRequest requestDanhGia = new StringRequest(urlDanhGia, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONScore(response);
                if (!lsDanhGia.isEmpty()) {
                    float tong = 0;
                    for (int i = 0; i < lsDanhGia.size(); i++) {
                        tong += lsDanhGia.get(i).getDiemSo();
                    }// Chuyển đổi float thành chuỗi String
                    float diemTrungBinh = tong / lsDanhGia.size();
                    DecimalFormat df = new DecimalFormat("#.#"); // Định dạng số có 1 chữ số sau dấu phẩy

                    String diemSoString = df.format(diemTrungBinh);

                    txtRating.setText(diemSoString);
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
    void parseJSONData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phimArray = object.getJSONArray("phims");

            for (int i = 0; i < phimArray.length(); ++i) {
                JSONObject obj = phimArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");
                if (maPhim == idphim) {
                    String tenPhim = obj.getString("TenPhim");
                    int NamRaMat = obj.getInt("NamRaMat");
                    String quocGia = obj.getString("QuocGia");
                    String moTaPhim = obj.getString("MoTaPhim");
                    String urlTrailer = obj.getString("URLTrailer");
                    String PosterPhim = obj.getString("PosterPhim");
                    lstDetaFilm.add(new Phim(maPhim, tenPhim, NamRaMat, quocGia, moTaPhim, urlTrailer,PosterPhim));
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void parseJSONImage(String JSImage) {
        try {
            JSONObject jsonObject = new JSONObject(JSImage);
            JSONArray imageArray = jsonObject.getJSONArray("hinhanhphims");
            for (int i = 0; i < imageArray.length(); i++) {
                JSONObject obj = imageArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");
                if (maPhim == idphim) {
                    int MaHinhAnh = obj.getInt("MaHinhAnh");
                    String UrlHinhAnh = obj.getString("UrlHinhAnh");
                    lstImageFilm.add(new ImageFilm(MaHinhAnh, UrlHinhAnh, maPhim));
                }
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    void parseJSONScore(String JSScore) {
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
                    lsDanhGia.add(new DanhGia(MaDanhGia, maPhim, MaNguoiDung, DiemSo, BinhLuan));
                }

            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    void parseJSONTheLoai(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray theloaiArray = object.getJSONArray("theloais");

            for (int i = 0; i < theloaiArray.length(); ++i) {
                JSONObject obj = theloaiArray.getJSONObject(i);
                int MaTheLoai = obj.getInt("MaTheLoai");
                for (int j = 0; j < idPhimTheLoai.size(); j++) {
                    if (MaTheLoai == idPhimTheLoai.get(j)) {
                        String TenTheLoai = obj.getString("TenTheLoai");

                        lsTheLoai.add(new TheLoai(MaTheLoai, TenTheLoai));
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void parseJSONDaoDien(String jsonString, ArrayList<Integer> idPhimDaoDien) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray daoDienArray = object.getJSONArray("daodiens");
            for (int i = 0; i < daoDienArray.length(); ++i) {
                JSONObject obj = daoDienArray.getJSONObject(i);
                int maDaoDien = obj.getInt("MaDaoDien");

                for (int j = 0; j < idPhimDaoDien.size(); j++) {
                    if (maDaoDien == idPhimDaoDien.get(j)) {
                        String tenDaoDien = obj.getString("TenDaoDien");
                        String quocTich = obj.getString("QuocTich");
                        lsDaoDien.add(new DaoDien(maDaoDien, tenDaoDien, quocTich));
                    }
                }
            }


        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
        Log.d(TAG, "parseJSONDaobnbnmbDien: " + lsDaoDien.toString());
    }
    void loadDataTheLoai() {
        StringRequest requestPhim_TheLoai = new StringRequest(urlTheLoai, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONTheLoai(response);

                if (!lsTheLoai.isEmpty()) {

                    for (int i = 0; i < lsTheLoai.size(); i++) {
                        Button button = new Button(getContext());
                        button.setText(lsTheLoai.get(i).getTenTheLoai());
                        button.setBackgroundResource(R.drawable.rounded_button); // Sử dụng drawable đã tạo
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(12, 8, 12, 8); // Điều chỉnh giá trị margin theo ý muốn
                        button.setLayoutParams(params);

                        // Thiết lập padding
                        button.setPadding(25, 8, 25, 8); // Điều chỉnh giá trị padding theo ý muốn

                        layoutButtons.addView(button);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error The_Loai", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueuePhim_TheLoai = Volley.newRequestQueue(getContext());
        requestQueuePhim_TheLoai.add(requestPhim_TheLoai);
    }
    void loadDataDaoDien(ArrayList<Integer> idPhimDaoDien) {
        StringRequest requestDaoDien = new StringRequest(urlDaodien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONDaoDien(response,idPhimDaoDien);

                if (!lsDaoDien.isEmpty()) {

                    txtTenDaoDien.setText(lsDaoDien.get(0).getTenDaoDien()); // Khi xác nhận dữ liệu, hiển thị lên TextView
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error PhimThe_Loai", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueueDaoDien = Volley.newRequestQueue(getContext());
        requestQueueDaoDien.add(requestDaoDien);
    }
    private void loadDataPhim_TheLoai() {
        StringRequest requestPhim_TheLoai = new StringRequest(urlPhim_TheLoai, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONPhim_TheLoai(response);

                if (!lsPhimTheLoai.isEmpty()) {

                    for (int i = 0; i < lsPhimTheLoai.size(); i++) {
                        idPhimTheLoai.add(lsPhimTheLoai.get(i).getMaTheLoai());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error PhimThe_Loai", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueuePhim_TheLoai = Volley.newRequestQueue(getContext());
        requestQueuePhim_TheLoai.add(requestPhim_TheLoai);
    }
    private void loadDataPhim_DaoDien() {
        StringRequest requestPhim_DaoDien = new StringRequest(urlPhim_Daodien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSONPhim_DaoDien(response);

                if (!lsPhimDaoDien.isEmpty()) {

                    for (int i = 0; i < lsPhimDaoDien.size(); i++) {
                        idPhimDaoDien.add(lsPhimDaoDien.get(i).getMaDaoDien());
                    }
                    loadDataDaoDien(idPhimDaoDien);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error PhimThe_Loai", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueuePhim_DaoDien = Volley.newRequestQueue(getContext());
        requestQueuePhim_DaoDien.add(requestPhim_DaoDien);
    }
    void parseJSONPhim_TheLoai(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phim_theloaiArray = object.getJSONArray("phim_theloais");

            for (int i = 0; i < phim_theloaiArray.length(); ++i) {
                JSONObject obj = phim_theloaiArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");
                if (maPhim == idphim) {

                    int MaTheLoai = obj.getInt("MaTheLoai");

                    lsPhimTheLoai.add(new phim_theloai(maPhim, MaTheLoai));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void parseJSONPhim_DaoDien(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phim_DaoDienArray = object.getJSONArray("phim_daodiens");

            for (int i = 0; i < phim_DaoDienArray.length(); ++i) {
                JSONObject obj = phim_DaoDienArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");
                if (maPhim == idphim) {

                    int MaDaoDien = obj.getInt("MaDaoDien");

                    lsPhimDaoDien.add(new Phim_DaoDien(maPhim, MaDaoDien));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void LoadPhimLienQuan(){
        StringRequest request = new StringRequest(urlGetPhimLienQuan, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJSONDataLienQuan(string);
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

    public void parseJSONDataLienQuan(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray phimArray = object.getJSONArray(String.valueOf(idphim));//de cai ma phim moi get duoc quang vao day phải la kieu String

            for (int i = 0; i < phimArray.length(); ++i) {
                JSONObject obj = phimArray.getJSONObject(i);
                int maPhim = obj.getInt("MaPhim");;
                String tenPhim = obj.getString("TenPhim");
                int NamRaMat = obj.getInt("NamRaMat");;
                String quocGia = obj.getString("QuocGia");
                String moTaPhim = obj.getString("MoTaPhim");
                String urlTrailer = obj.getString("URLTrailer");
                String PosterPhim = obj.getString("PosterPhim");
                lstDetaFilm.add(new Phim(maPhim, tenPhim, NamRaMat, quocGia, moTaPhim,urlTrailer,PosterPhim));

            }

            categoryAdapter = new CategoryAdapter_Trung(getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
            rcv_Menu.setLayoutManager(linearLayoutManager);
            categoryAdapter.setData(getListCatehory());
            rcv_Menu.setAdapter(categoryAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Category> getListCatehory(){
        ArrayList<Category> list= new ArrayList<>();
        list.add(new Category("Có Thể Bạn Cũng Thích",lstDetaFilm));
        return list;


    }
    private void addControls(View view) {
        webview_Youtube = view.findViewById(R.id.webview_Youtube);
        impPoster = view.findViewById(R.id.impPoster);
        txtNameFilm = view.findViewById(R.id.txtNameFilm);
        txtRating = view.findViewById(R.id.txtRating);
        txtNamRaMat = view.findViewById(R.id.txtNamRaMat);
        layoutButtons = view.findViewById(R.id.layoutButtons);
        gridLayoutImagePosterDetail = view.findViewById(R.id.gridLayoutImagePosterDetail);
        layoutAllCast = view.findViewById(R.id.layoutAllCast);
        txtDecription = view.findViewById(R.id.txtDecription);
        txtTenDaoDien = view.findViewById(R.id.txtTenDaoDien);
        txtQuocGiaFilm = view.findViewById(R.id.txtQuocGiaFilm);
        layoutDirector = view.findViewById(R.id.layoutDirector);
        rcv_Menu = view.findViewById(R.id.rcv_Menu);
        lvReviewLimit = view.findViewById(R.id.lvReviewLimit);
        imgFullcmt = view.findViewById(R.id.imgFullcmt);
        btnReview = view.findViewById(R.id.btnReview);
        btnUpdate = view.findViewById(R.id.btnUpdateDanhgia);
        // Đặt màu nền trực tiếp cho button
        btnReview.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button_pressed_color));

        // Đặt màu nền trực tiếp cho button
        btnUpdate.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.button_pressed_color)); }
    private String GetIdFromYoutubeLink(String Link) {
        String videoId = Link.substring(Link.lastIndexOf('/') + 1, Link.indexOf('?'));
        System.out.println("Video ID: " + videoId);
        return videoId;
    }
}