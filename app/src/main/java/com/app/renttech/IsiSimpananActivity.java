package com.app.renttech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.app.renttech.util.Utils.StringToCurrency;

public class IsiSimpananActivity extends AppCompatActivity {

    EditText txt_nominalsetor;
    TextView tgl, waktu, tv_jumlahsetor;
    Button btnsetor;
    LinearLayout btnmandirisyariah;
    TextView syaratketentuan;
    String setor, nominal;

    private RecyclerView rv_bank;
    CardView cv_bank;
    ProgressDialog progressDialog;
    private RequestQueue rQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap<String, String>> list_data;
    ArrayList<HashMap<String, String>> tmpList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_simpanan);

        txt_nominalsetor = (EditText) findViewById(R.id.txt_nominalsetor);
        tv_jumlahsetor = (TextView) findViewById(R.id.tv_jumlahsetor);

        /*btnmandirisyariah = (LinearLayout) findViewById(R.id.btnmandirisyariah);
        btnmandirisyariah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IsiSimpananActivity.this, PembayaranActivity.class);
                startActivity(i);
            }
        });

         */
        btnsetor = (Button) findViewById(R.id.btnsetor);
        btnsetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ednominal = txt_nominalsetor.getText().toString();
                if (ednominal.matches("")){
                    Toast.makeText(IsiSimpananActivity.this,"Anda belum mengisi 'Nominal Setor'",Toast.LENGTH_SHORT).show();
                    return;
                }
                getData();

            }
        });

        syaratketentuan = (TextView) findViewById(R.id.syaratketentuan);
        syaratketentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IsiSimpananActivity.this, SyaratKetentuanActivity.class);
                startActivity(i);
            }
        });

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
       // tgl = (TextView) findViewById(R.id.tgl);
        //tgl.setText(currentDate);
        waktu = (TextView) findViewById(R.id.waktu);
        waktu.setText(currentDate);

        rv_bank = (RecyclerView) findViewById(R.id.rv_bank);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_bank.setLayoutManager(llm);
        cv_bank = (CardView) findViewById(R.id.cv_bank);
        Bundle b = getIntent().getExtras();


        //requestQueue = Volley.newRequestQueue(IsiSimpananActivity.this);
        list_data = new ArrayList<HashMap<String, String>>();
    }


    public void getData(){
        tv_jumlahsetor.setText(StringToCurrency(txt_nominalsetor.getText().toString()));
        nominal = tv_jumlahsetor.getText().toString();
        System.out.println(nominal);
        //setor = tv_jumlahsetor.getText().toString();
        String url = "http://192.168.27.88/malabar/RestAPI/getdaftarbank.php";
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("Kode", json.getString("Kode"));
                        map.put("NamaBank", json.getString("NamaBank"));
                        map.put("NoRekening", json.getString("NoRekening"));
                        map.put("image", json.getString("image"));
                        map.put("NamaPemilikRekening", json.getString("NamaPemilikRekening"));
                        map.put("jumlah",  nominal);
                        list_data.add(map);
                        RecycleAdapterBank adapter = new RecycleAdapterBank(IsiSimpananActivity.this, list_data, R.layout.activity_recycle_adapter_bank, new String[]{"NamaBank", "NoRekening", "NamaPemilikRekening"}, new int[]{R.id.tv_item_namabank, R.id.tv_item_norekbank, R.id.tv_item_namapemilik});
                        rv_bank.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IsiSimpananActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        rQueue = Volley.newRequestQueue(IsiSimpananActivity.this);
        rQueue.add(stringRequest);
    }
}
