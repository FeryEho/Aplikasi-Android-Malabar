package com.app.renttech;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfilActivity2 extends AppCompatActivity {

    EditText txtpasangan, txtibu, txtahliwaris, txttempatlahirahliwaris, txttgllahirahliwaris;
    String pasangan, ibu, ahliwaris, tempatlahirahliwaris, tgllahirahliwaris, hubungan;
    Spinner spnhubungan;
    Button btn_sebelumnya, btn_berikutnya;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil2);

        txtpasangan              = (EditText) findViewById(R.id.txtpasangan);
        txtibu                   = (EditText) findViewById(R.id.txtibu);
        txtahliwaris             = (EditText) findViewById(R.id.txtahliwaris);
        txttempatlahirahliwaris  = (EditText) findViewById(R.id.txttempatlahirahliwaris);
        txttgllahirahliwaris     = (EditText) findViewById(R.id.txttgllahirahliwaris);
        spnhubungan              = (Spinner) findViewById(R.id.spnhubungan);
        btn_sebelumnya           = (Button) findViewById(R.id.btn_sebelumnya);
        btn_berikutnya           = (Button) findViewById(R.id.btn_berikutnya);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        txttgllahirahliwaris.setEditableFactory(Editable.Factory.getInstance());
        txttgllahirahliwaris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        //get data di activity ini Profilaktivity2
        final String [] DataProfil2 = new String[6];
        DataProfil2 [0] = txtpasangan.getText().toString();
        DataProfil2 [1] = txtibu.getText().toString();
        DataProfil2 [2] = txtahliwaris.getText().toString();
        DataProfil2 [3] = txttempatlahirahliwaris.getText().toString();
        DataProfil2 [4] = txttgllahirahliwaris.getText().toString();
        DataProfil2 [5] = spnhubungan.getSelectedItem().toString();

        //mengambil data dari array class profilactivity dan profilactivity1
        //Intent intent = getIntent();
        //final String [] DataProfil = intent.getStringArrayExtra("DataProfil");
        //final String [] DataProfil1 = getIntent().getStringArrayExtra("DataProfil1");
        final String [] DataProfil1 = new String[8];
        DataProfil1 [0] = getIntent().getStringExtra("alamat");
        DataProfil1 [1] = getIntent().getStringExtra("rtrw");
        DataProfil1 [2] = getIntent().getStringExtra("kota");
        DataProfil1 [3] = getIntent().getStringExtra("kecamatan");
        DataProfil1 [4] = getIntent().getStringExtra("kelurahan");
        DataProfil1 [5] = getIntent().getStringExtra("ktp");
        DataProfil1 [6] = getIntent().getStringExtra("npwp");
        DataProfil1 [7] = getIntent().getStringExtra("email");


        btn_berikutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = getIntent().getStringExtra("nama");
                String telepon = getIntent().getStringExtra("telepon");
                String kelamin = getIntent().getStringExtra("kelamin");
                String tempatlahir = getIntent().getStringExtra("tempatlahir");
                String tgllahir = getIntent().getStringExtra("tgllahir");
                String agama = getIntent().getStringExtra("agama");
                String statuskawin = getIntent().getStringExtra("statuskawin");
                String pendidikan = getIntent().getStringExtra("pendidikan");
                String pekerjaan = getIntent().getStringExtra("pekerjaan");
                String penghasilan = getIntent().getStringExtra("penghasilan");

                String alamat = getIntent().getStringExtra("alamat");
                String rtrw = getIntent().getStringExtra("rtrw");
                String kota = getIntent().getStringExtra("kota");
                String kecamatan = getIntent().getStringExtra("kecamatan");
                String kelurahan = getIntent().getStringExtra("kelurahan");
                String ktp = getIntent().getStringExtra("ktp");
                String npwp = getIntent().getStringExtra("npwp");
                String email = getIntent().getStringExtra("email");

                /*String [] DataProfil1 = new String[8];
                DataProfil1 [0] = getIntent().getStringExtra("alamat");
                DataProfil1 [1] = getIntent().getStringExtra("rtrw");
                DataProfil1 [2] = getIntent().getStringExtra("kota");
                DataProfil1 [3] = getIntent().getStringExtra("kecamatan");
                DataProfil1 [4] = getIntent().getStringExtra("kelurahan");
                DataProfil1 [5] = getIntent().getStringExtra("ktp");
                DataProfil1 [6] = getIntent().getStringExtra("npwp");
                DataProfil1 [7] = getIntent().getStringExtra("email");
*/

                pasangan = txtpasangan.getText().toString();
                ibu = txtibu.getText().toString();
                ahliwaris = txtahliwaris.getText().toString();
                tempatlahirahliwaris = txttempatlahirahliwaris.getText().toString();
                tgllahirahliwaris = txttgllahirahliwaris.getText().toString();
                hubungan = spnhubungan.getSelectedItem().toString();

                if (ibu.matches("")){
                    Toast.makeText(ProfilActivity2.this,"Anda belum mengisi 'Ibu Kandung'",Toast.LENGTH_SHORT).show();
                    return;
                }if (ahliwaris.matches("")){
                    Toast.makeText(ProfilActivity2.this,"Anda belum mengisi 'Ahli Waris'",Toast.LENGTH_SHORT).show();
                    return;
                }if (tempatlahirahliwaris.matches("")){
                    Toast.makeText(ProfilActivity2.this,"Anda belum mengisi 'Tempat Lahir Ahli Waris'",Toast.LENGTH_SHORT).show();
                    return;
                }if (tgllahirahliwaris.matches("")){
                    Toast.makeText(ProfilActivity2.this,"Anda belum mengisi 'Tanggal Lahir Ahli Waris'",Toast.LENGTH_SHORT).show();
                    return;
                }if (hubungan.matches("")){
                    Toast.makeText(ProfilActivity2.this,"Anda belum mengisi 'Hubungan'",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent(ProfilActivity2.this, ProfilActivity3.class );
                i.putExtra("nama", nama);
                i.putExtra("telepon", telepon);
                i.putExtra("kelamin", kelamin);
                i.putExtra("tempatlahir", tempatlahir);
                i.putExtra("tgllahir", tgllahir);
                i.putExtra("agama", agama);
                i.putExtra("statuskawin", statuskawin);
                i.putExtra("pendidikan", pendidikan);
                i.putExtra("pekerjaan", pekerjaan);
                i.putExtra("penghasilan", penghasilan);

                i.putExtra("alamat", alamat);
                i.putExtra("rtrw", rtrw);
                i.putExtra("kota", kota);
                i.putExtra("kecamatan", kecamatan);
                i.putExtra("kelurahan", kelurahan);
                i.putExtra("ktp", ktp);
                i.putExtra("npwp", npwp);
                i.putExtra("email", email);

                i.putExtra("pasangan", pasangan);
                i.putExtra("ibu", ibu);
                i.putExtra("ahliwaris", ahliwaris);
                i.putExtra("tempatlahirahliwaris", tempatlahirahliwaris);
                i.putExtra("tgllahirahliwaris", tgllahirahliwaris);
                i.putExtra("hubungan", hubungan);
                startActivity(i);
            }
        });

    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                txttgllahirahliwaris.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
