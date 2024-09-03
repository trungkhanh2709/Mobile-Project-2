package com.example.riviuphim;

import android.os.AsyncTask;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class InsertDataAsyncTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0]; // URL của file PHP trên server
        String maPhim = params[1];
        String maNguoiDung = params[2];
        String diemSo = params[3];
        String binhLuan = params[4];
        String ngayBinhLuan = params[5];

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            // Gửi dữ liệu
            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("MaPhim", maPhim);
            postDataParams.put("MaNguoiDung", maNguoiDung);
            postDataParams.put("DiemSo", diemSo);
            postDataParams.put("BinhLuan", binhLuan);
            postDataParams.put("NgayBinhLuan", ngayBinhLuan);

            OutputStream os = urlConnection.getOutputStream();
            os.write(getPostDataString(postDataParams).getBytes("UTF-8"));
            os.flush();
            os.close();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Đọc response từ server nếu cần
            } else {
                // Xử lý lỗi nếu có
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

}
