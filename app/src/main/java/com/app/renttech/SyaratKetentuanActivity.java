package com.app.renttech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SyaratKetentuanActivity extends AppCompatActivity {
    Button kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syarat_ketentuan);

        kembali = (Button) findViewById(R.id.kembali);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SyaratKetentuanActivity.this, IsiSimpananActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
