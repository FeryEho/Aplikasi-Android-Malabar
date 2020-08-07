package com.app.renttech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText txt_nama, txt_NoTelepon, txt_password;
    private Button btn_registrasi;
    private TextView tvlogin;

    private PreferenceHelper preferenceHelper;
    private String registerURL = "http://192.168.27.252/malabar/RestAPI/register.php";
    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferenceHelper = new PreferenceHelper(this);

        if(preferenceHelper.getIsLogin()){
            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }

        txt_nama = (EditText) findViewById(R.id.txt_nama);
        txt_NoTelepon = (EditText) findViewById(R.id.txt_NoTelepon);
        txt_password = (EditText) findViewById(R.id.txt_password);

        btn_registrasi = (Button) findViewById(R.id.btn_registrasi);
        tvlogin = (TextView) findViewById(R.id.tv_login);

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });

        btn_registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrasi();
            }
        });

    }

    private void registrasi(){

        final String nama = txt_nama.getText().toString();
        final String notelepon = txt_NoTelepon.getText().toString();
        final String password = txt_password.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, registerURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_LONG).show();
                        try {

                            parseData(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("Nama",      nama);
                params.put("NoTelepon", notelepon);
                params.put("Password",  password);

                return params;
            }

        };

        rQueue = Volley.newRequestQueue(RegisterActivity.this);
        rQueue.add(stringRequest);
    }

    private void parseData(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("true")){

            //saveInfo(response);

            Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //this.finish();
        }else {

            Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveInfo(String response){

        preferenceHelper.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.putNama(dataobj.getString("Nama"));
                    preferenceHelper.putNoTelepon(dataobj.getString("NoTelepon"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
