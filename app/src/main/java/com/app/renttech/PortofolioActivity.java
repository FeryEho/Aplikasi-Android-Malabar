package com.app.renttech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Map;

public class PortofolioActivity extends AppCompatActivity {

    private String URLline = "http://192.168.27.88/malabar/RestAPI/getdata.php";
    private RequestQueue rQueue;
    int position;

    TextView txtnama, txtibukandung, txtkelamin, tempat, tgllahir, alamat, ktp, telepon, status, pasangan, pekerjaan, pendidikan, penghasilan, ahliwaris ,tempatlahir ,tgllahirahliwaris ,hubungan;
    ArrayList<HashMap<String, String>> list_data;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portofolio);
        preferenceHelper = new PreferenceHelper(PortofolioActivity.this);

        getData();
        txtnama = (TextView) findViewById(R.id.txtnama);
        txtibukandung = (TextView) findViewById(R.id.txtibukandung);
        txtkelamin = (TextView) findViewById(R.id.txtkelamin);
        tempat = (TextView) findViewById(R.id.tempat);
        tgllahir = (TextView) findViewById(R.id.tgllahir);
        alamat = (TextView) findViewById(R.id.alamat);
        ktp = (TextView) findViewById(R.id.ktp);
        telepon = (TextView) findViewById(R.id.telepon);
        status = (TextView) findViewById(R.id.status);
        pasangan = (TextView) findViewById(R.id.pasangan);
        pekerjaan = (TextView) findViewById(R.id.pekerjaan);
        pendidikan = (TextView) findViewById(R.id.pendidikan);
        penghasilan = (TextView) findViewById(R.id.penghasilan);
        ahliwaris = (TextView) findViewById(R.id.ahliwaris);
        tempatlahir = (TextView) findViewById(R.id.tempatlahir);
        tgllahirahliwaris = (TextView) findViewById(R.id.tgllahirahliwaris);
        hubungan = (TextView) findViewById(R.id.hubungan);

        list_data = new ArrayList<HashMap<String, String>>();

    }

    public void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put("Id", json.getString("Id"));
                        map.put("Nama", json.getString("Nama"));
                        map.put("NoTelepon", json.getString("NoTelepon"));
                        map.put("JenisKelamin", json.getString("JenisKelamin"));
                        map.put("TempatLahir", json.getString("TempatLahir"));
                        map.put("TglLahir", json.getString("TglLahir"));
                        map.put("Agama", json.getString("Agama"));
                        map.put("StatusPerkawinan", json.getString("StatusPerkawinan"));
                        map.put("Pendidikan", json.getString("Pendidikan"));
                        map.put("Pekerjaan", json.getString("Pekerjaan"));
                        map.put("Penghasilan", json.getString("Penghasilan"));
                        map.put("Alamat", json.getString("Alamat"));
                        map.put("RtRw", json.getString("RtRw"));
                        map.put("Kota", json.getString("Kota"));
                        map.put("Kecamatan", json.getString("Kecamatan"));
                        map.put("Kelurahan", json.getString("Kelurahan"));
                        map.put("KTP", json.getString("KTP"));
                        map.put("NPWP", json.getString("NPWP"));
                        map.put("Email", json.getString("Email"));
                        map.put("Pasangan", json.getString("Pasangan"));
                        map.put("IbuKandung", json.getString("IbuKandung"));
                        map.put("AhliWaris", json.getString("AhliWaris"));
                        map.put("TempatLahirAhliWaris", json.getString("TempatLahirAhliWaris"));
                        map.put("TglLahirAhliWaris", json.getString("TglLahirAhliWaris"));
                        map.put("HubunganAhliWaris", json.getString("HubunganAhliWaris"));
                        map.put("PhotoKTP", json.getString("PhotoKTP"));
                        map.put("PhotoNasabah", json.getString("PhotoNasabah"));
                        map.put("TanggalPermohonan", json.getString("TanggalPermohonan"));
                        list_data.add(map);

                    }

                    Toast.makeText(PortofolioActivity.this, "Data Berhasil Di Temukan", Toast.LENGTH_SHORT).show();
                    txtnama.setText(list_data.get(position).get("Nama"));
                    txtibukandung.setText(list_data.get(position).get("IbuKandung"));
                    txtkelamin.setText(list_data.get(position).get("JenisKelamin"));
                    tempat.setText(list_data.get(position).get("TempatLahir"));
                    tgllahir.setText(list_data.get(position).get("TglLahir"));
                    alamat.setText(list_data.get(position).get("Alamat"));
                    ktp.setText(list_data.get(position).get("KTP"));
                    telepon.setText(list_data.get(position).get("NoTelepon"));
                    status.setText(list_data.get(position).get("StatusPerkawinan"));
                    pasangan.setText(list_data.get(position).get("Pasangan"));
                    pekerjaan.setText(list_data.get(position).get("Pekerjaan"));
                    pendidikan.setText(list_data.get(position).get("Pendidikan"));
                    penghasilan.setText(list_data.get(position).get("Penghasilan"));
                    ahliwaris.setText(list_data.get(position).get("AhliWaris"));
                    tempatlahir.setText(list_data.get(position).get("TempatLahirAhliWaris"));
                    tgllahirahliwaris.setText(list_data.get(position).get("TglLahirAhliWaris"));
                    hubungan.setText(list_data.get(position).get("HubunganAhliWaris"));


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PortofolioActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PortofolioActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("NoTelepon", preferenceHelper.getNoTelepon());
                //params.put("Password",  password);

                return params;
            }
        };
        rQueue = Volley.newRequestQueue(PortofolioActivity.this);
        rQueue.add(stringRequest);
    }
}
