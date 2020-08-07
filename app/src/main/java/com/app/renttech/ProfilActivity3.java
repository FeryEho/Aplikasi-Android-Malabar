package com.app.renttech;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.renttech.ui.profil.ProfilFragment;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProfilActivity3 extends AppCompatActivity {

    String tanggal;
    TextView judul, tglsekarang;
    Button btnphoto, btnktp, btn_sebelumnya, btn_simpan;
    ImageView imgphoto, imgktp;

    final int CODE_GALERY_REQUEST = 999;
    final int CODE_GALERY_REQUEST1 = 111;
    Bitmap bitmap, bitmap1;

    ProgressDialog progressDialog;

    private String insertURL = "http://192.168.27.88/malabar/RestAPI/insertnasabah.php";
    private RequestQueue rQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil3);

        judul           = (TextView) findViewById(R.id.judul);
        tglsekarang     = (TextView) findViewById(R.id.tglsekarang);
        imgphoto        = (ImageView) findViewById(R.id.imgphoto);
        imgktp          = (ImageView) findViewById(R.id.imgktp);
        btnphoto        = (Button) findViewById(R.id.btnphoto);
        btnktp          = (Button) findViewById(R.id.btnktp);
        btn_sebelumnya  = (Button) findViewById(R.id.btn_sebelumnya);
        btn_simpan      = (Button) findViewById(R.id.btn_simpan);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        tglsekarang.setText(currentDate);
        tanggal = tglsekarang.getText().toString();
        //mengambil data dari array class profilactivity dan profilactivity1 dan profilactivity2
        //Intent intent = getIntent();
        //final String [] DataProfil = intent.getStringArrayExtra("DataProfil");
        //final String [] DataProfil1 = intent.getStringArrayExtra("DataProfil1");
        //final String [] DataProfil2 = intent.getStringArrayExtra("DataProfil2");
        /*final String [] DataProfil2 = new String[5];
        DataProfil2 [0] = getIntent().getStringExtra("pasangan");
        DataProfil2 [1] = getIntent().getStringExtra("ibu");
        DataProfil2 [2] = getIntent().getStringExtra("ahliwaris");
        DataProfil2 [3] = getIntent().getStringExtra("tempatlahirahliwaris");
        DataProfil2 [4] = getIntent().getStringExtra("tgllahirahliwaris");
*/

        btnktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ProfilActivity3.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALERY_REQUEST
                );
            }
        });

        btnphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ProfilActivity3.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALERY_REQUEST1
                );

            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jalankan method insert dulu
                insert();
                //setelah method selesai inten/pindah ke activiti awal
                Intent i = new Intent(ProfilActivity3.this, ProfilFragment.class );
                startActivity(i);
                finish();
            }
        });
    }

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
        }else if (requestCode == CODE_GALERY_REQUEST1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALERY_REQUEST1);
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
                imgktp.setImageBitmap(bitmap);
                //imgktp.setImageBitmap();
                //imgphoto.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else if (requestCode == CODE_GALERY_REQUEST1 && resultCode == RESULT_OK && data != null){
            Uri filePath1 = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath1);
                bitmap1 = BitmapFactory.decodeStream(inputStream);
                imgphoto.setImageBitmap(bitmap1);
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
    private String imageToString1(Bitmap bitmap){
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
                        Toast.makeText(ProfilActivity3.this,"Data Berhasil Disimpan",Toast.LENGTH_LONG).show();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfilActivity3.this,"Gagal Insert Data",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                final String nama = getIntent().getStringExtra("nama");
                final String telepon = getIntent().getStringExtra("telepon");
                final String kelamin = getIntent().getStringExtra("kelamin");
                final String tempatlahir = getIntent().getStringExtra("tempatlahir");
                final String tgllahir = getIntent().getStringExtra("tgllahir");
                final String agama = getIntent().getStringExtra("agama");
                final String statuskawin = getIntent().getStringExtra("statuskawin");
                final String pendidikan = getIntent().getStringExtra("pendidikan");
                final String pekerjaan = getIntent().getStringExtra("pekerjaan");
                final String penghasilan = getIntent().getStringExtra("penghasilan");

                final String alamat = getIntent().getStringExtra("alamat");
                final String rtrw = getIntent().getStringExtra("rtrw");
                final String kota = getIntent().getStringExtra("kota");
                final String kecamatan = getIntent().getStringExtra("kecamatan");
                final String kelurahan = getIntent().getStringExtra("kelurahan");
                final String ktp = getIntent().getStringExtra("ktp");
                final String npwp = getIntent().getStringExtra("npwp");
                final String email = getIntent().getStringExtra("email");

                final String pasangan = getIntent().getStringExtra("pasangan");
                final String ibu = getIntent().getStringExtra("ibu");
                final String ahliwaris = getIntent().getStringExtra("ahliwaris");
                final String tempatlahirahliwaris = getIntent().getStringExtra("tempatlahirahliwaris");
                final String tgllahirahliwaris = getIntent().getStringExtra("tgllahirahliwaris");
                final String hubungan = getIntent().getStringExtra("hubungan");

                //System.out.println(nama);
                //System.out.println(alamat);
                //System.out.println(pasangan);

                params.put("Nama",    nama);
                params.put("NoTelepon",    telepon);
                params.put("JenisKelamin",    kelamin);
                params.put("TempatLahir",    tempatlahir);
                params.put("TglLahir",    tgllahir);
                params.put("Agama",    agama);
                params.put("StatusPerkawinan",    statuskawin);
                params.put("Pendidikan",    pendidikan);
                params.put("Pekerjaan",    pekerjaan);
                params.put("Penghasilan",   penghasilan);
                params.put("Alamat", alamat);
                params.put("RtRw", rtrw);
                params.put("Kota", kota);
                params.put("Kecamatan", kecamatan);
                params.put("Kelurahan", kelurahan);
                params.put("KTP", ktp);
                params.put("NPWP", npwp);
                params.put("Email", email);
                params.put("Pasangan",  pasangan);
                params.put("IbuKandung",  ibu);
                params.put("AhliWaris",  ahliwaris);
                params.put("TempatLahirAhliWaris",  tempatlahirahliwaris);
                params.put("TglLahirAhliWaris",  tgllahirahliwaris);
                params.put("HubunganAhliWaris",  hubungan);

                String imageData = imageToString(bitmap);
                params.put("image", imageData);

                String imageData1 = imageToString1(bitmap1);
                params.put("image1", imageData1);

                params.put("TanggalPermohonan",  tanggal);

                return params;
            }

        };

        rQueue = Volley.newRequestQueue(ProfilActivity3.this);
        rQueue.add(stringRequest);
    }
}
