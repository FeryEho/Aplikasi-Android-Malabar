package com.app.renttech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.renttech.ui.history.HistoryFragment;
import com.app.renttech.ui.profil.ProfilFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText txt_NoTelepon, txt_password;
    private Button btn_login;
    private TextView tv_register;
    private PreferenceHelper preferenceHelper;

    private String URLline = "http://192.168.27.88/malabar/RestAPI/login.php";
    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceHelper = new PreferenceHelper(this);

        if(preferenceHelper.getIsLogin()){
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }

        txt_NoTelepon = (EditText) findViewById(R.id.txt_NoTelepon);
        txt_password = (EditText) findViewById(R.id.txt_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        tv_register = (TextView) findViewById(R.id.tv_register);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser(){

        final String username = txt_NoTelepon.getText().toString().trim();
        final String password = txt_password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        parseData(response);
                        /*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        *///this.finish();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("NoTelepon",username);
                params.put("Password",password);

                return params;
            }

        };

        rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(stringRequest);
    }

    private void parseData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {

                saveInfo(response);

                Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }else if(jsonObject.getString("status").equals("false")){
                Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
