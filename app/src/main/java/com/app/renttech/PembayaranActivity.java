package com.app.renttech;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PembayaranActivity extends AppCompatActivity {

    TextView getrekening, getnamapemilik, totalbayar;
    ImageView getimagebank, imgbuktipembayaran;
    final int CODE_GALERY_REQUEST = 999;
    Bitmap bitmap;
    String notelepon, rekening, namapemilik, total;
    Button btn_upload;
    Context context;

    private PreferenceHelper preferenceHelper;
    private String insertURL = "http://192.168.27.88/malabar/RestAPI/insertbuktitransfer.php";
    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        preferenceHelper = new PreferenceHelper(PembayaranActivity.this);
        notelepon = preferenceHelper.getNoTelepon();
        System.out.println(notelepon);
        getrekening = (TextView) findViewById(R.id.getrekening);
        getnamapemilik = (TextView) findViewById(R.id.getnamapemilik);
        getimagebank = (ImageView) findViewById(R.id.getimagebank);
        totalbayar = (TextView) findViewById(R.id.totalbayar);
        imgbuktipembayaran = (ImageView) findViewById(R.id.imgbuktipembayaran);
        btn_upload = (Button) findViewById(R.id.btn_upload);

        String kode1 = getIntent().getStringExtra("Kode");
        String namabank1 = getIntent().getStringExtra("NamaBank");
        String rekening1 = getIntent().getStringExtra("NoRekening");
        String image1 = getIntent().getStringExtra("image");
        String namapemilik1 = getIntent().getStringExtra("NamaPemilikRekening");
        String jumlah1 = getIntent().getStringExtra("nominal");

        Glide.with(this)
                .asBitmap()
                .load("http://192.168.27.88/malabar/upload/imagebank/" + image1)
                .into(getimagebank);

        getrekening.setText(rekening1);
        getnamapemilik.setText(namapemilik1);
        totalbayar.setText(jumlah1);

        rekening = getrekening.getText().toString();
        namapemilik = getnamapemilik.getText().toString();
        total = totalbayar.getText().toString();
        //totalbayar.setText(jumlah);

        imgbuktipembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        PembayaranActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALERY_REQUEST
                );
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });

    }

    private void ShowDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("KONFIRMASI BUKTI TRANSFER !")
                .setMessage("Apakah Anda Sudah Mentransfer Saldo Dan Sudah Upload Bukti Transfer?")
                .setPositiveButton("Sudah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        insert();
                        //Toast.makeText(PembayaranActivity.this, "KLIK KLIK", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Belum", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        alert.show();
    }

    /*
    //keluar dialog untuk tombol back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            ShowDialog();
        }
        return true;
    }

     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CODE_GALERY_REQUEST){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALERY_REQUEST);
            }
            else {
                Toast.makeText(getApplicationContext(), "Anda tidak memiliki izin untuk mengakses galeri!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALERY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgbuktipembayaran.setImageBitmap(bitmap);
                //imgktp.setImageBitmap();
                //imgphoto.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,  100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    private void insert(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        Toast.makeText(PembayaranActivity.this,"Data Berhasil Disimpan",Toast.LENGTH_LONG).show();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PembayaranActivity.this,"Gagal Insert Data",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("NoTelepon", notelepon);
                params.put("NoRekening", rekening);
                params.put("NamaPemilikRekening",  namapemilik);
                params.put("TotalSetor",  total);

                String imageData = imageToString(bitmap);
                params.put("image", imageData);

                return params;
            }

        };

        rQueue = Volley.newRequestQueue(PembayaranActivity.this);
        rQueue.add(stringRequest);
    }
}
