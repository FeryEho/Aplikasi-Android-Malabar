package com.app.renttech.ui.history;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.renttech.IsiSimpananActivity;
import com.app.renttech.R;
import com.app.renttech.RecycleAdapterBank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HistoryFragment extends Fragment {
    Button btn_cari;
    EditText tgl_mulai, tgl_sampai;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    String awal, akhir;

    public static final int REQUEST_CODE = 11;

    private RecyclerView rv_history;
    CardView cv_history;
    ProgressDialog progressDialog;
    private RequestQueue rQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap<String, String>> list_data;
    ArrayList<HashMap<String, String>> tmpList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);
        setHasOptionsMenu(true);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        rv_history = (RecyclerView) root.findViewById(R.id.rv_history);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_history.setLayoutManager(llm);
        cv_history = (CardView) root.findViewById(R.id.cv_history);
        btn_cari =(Button) root.findViewById(R.id.btn_cari);
        tgl_mulai =(EditText)root.findViewById(R.id.tgl_mulai);
        tgl_sampai =(EditText)root.findViewById(R.id.tgl_sampai);

        tgl_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.setTargetFragment(HistoryFragment.this,0);
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });

        tgl_sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment1();
                newFragment.setTargetFragment(HistoryFragment.this,0);
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awal = tgl_mulai.getText().toString();
                akhir = tgl_sampai.getText().toString();
                System.out.println(akhir);
                getData();
            }
        });

        list_data = new ArrayList<HashMap<String, String>>();

        return root;
    }

    public void getData(){

        String url = "http://192.168.27.88/malabar/RestAPI/getbuktitransfer.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response ", response);
                rQueue.getCache().clear();
                try {
                    //listdata.clear adalah untuk membersihkan data array perulangan
                    list_data.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("Kode", json.getString("Kode"));
                        map.put("NoTelepon", json.getString("NoTelepon"));
                        map.put("NoRekening", json.getString("NoRekening"));
                        map.put("NamaPemilikRekening", json.getString("NamaPemilikRekening"));
                        map.put("TotalSetor", json.getString("TotalSetor"));
                        map.put("PhotoBukti", json.getString("PhotoBukti"));
                        map.put("Tanggal", json.getString("Tanggal"));
                        map.put("DateTime", json.getString("DateTime"));
                        list_data.add(map);

                    }
                    RecycleAdapterHistory adapter = new RecycleAdapterHistory(HistoryFragment.this, list_data, R.layout.activity_recycle_adapter_history, new String[]{"NoTelepon", "Tanggal", "TotalSetor"}, new int[]{R.id.tv_item_notelepon, R.id.tv_item_tanggal, R.id.tv_item_totalsetor});
                    rv_history.setAdapter(adapter);
                    Toast.makeText(getActivity(), "Data Berhasil Di Temukan", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //System.out.println("TES"+awal+" "+akhir);
                params.put("TanggalAwal",  awal);
                params.put("TanggalAkhir", akhir);

                return params;
            }
        };

        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(stringRequest);
    }

}
