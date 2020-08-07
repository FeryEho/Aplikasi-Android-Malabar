package com.app.renttech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfilActivity1 extends AppCompatActivity {

    EditText txtalamat, txtrtrw, txtkota, txtkecamatan, txtkelurahan, txtktp, txtnpwp, txtemail;
    String txtalamat1, txtrtrw1, txtkota1, txtkecamatan1, txtkelurahan1, txtktp1, txtnpwp1, txtemail1;
    Button btn_sebelumnya, btn_berikutnya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil1);

        txtalamat                    = (EditText) findViewById(R.id.txtalamat);
        txtrtrw                      = (EditText) findViewById(R.id.txtrtrw);
        txtkota                      = (EditText) findViewById(R.id.txtkota);
        txtkecamatan                 = (EditText) findViewById(R.id.txtkecamatan);
        txtkelurahan                 = (EditText) findViewById(R.id.txtkelurahan);
        txtktp                       = (EditText) findViewById(R.id.txtktp);
        txtnpwp                      = (EditText) findViewById(R.id.txtnpwp);
        txtemail                     = (EditText) findViewById(R.id.txtemail);
        btn_sebelumnya               = (Button) findViewById(R.id.btn_sebelumnya);
        btn_berikutnya               = (Button) findViewById(R.id.btn_berikutnya);

        //mengambil data dari activity profil sebelumnya dengan array
        /*final String [] DataProfil = new String[10];
        DataProfil [0] = getIntent().getStringExtra("nama");
        DataProfil [1] = getIntent().getStringExtra("telepon");
        DataProfil [2] = getIntent().getStringExtra("kelamin");
        DataProfil [3] = getIntent().getStringExtra("tempatlahir");
        DataProfil [4] = getIntent().getStringExtra("tgllahir");
        DataProfil [5] = getIntent().getStringExtra("agama");
        DataProfil [6] = getIntent().getStringExtra("statuskawin");
        DataProfil [7] = getIntent().getStringExtra("pendidikan");
        DataProfil [8] = getIntent().getStringExtra("pekerjaan");
        DataProfil [9] = getIntent().getStringExtra("penghasilan");

         */

        //txtalamat.setText(DataProfil[1]);


        //mengambil data dari activity profil sebelumnya


        //get Data dari activity1 dengan array
        /*final String DataProfil1 [] = new String[8];
        DataProfil1 [0] = txtalamat.getText().toString();
        DataProfil1 [1] = txtrtrw.getText().toString();
        DataProfil1 [2] = txtkota.getText().toString();
        DataProfil1 [3] = txtkecamatan.getText().toString();
        DataProfil1 [4] = txtkelurahan.getText().toString();
        DataProfil1 [5] = txtktp.getText().toString();
        DataProfil1 [6] = txtnpwp.getText().toString();
        DataProfil1 [7] = txtemail.getText().toString();
*/
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

                txtalamat1 = txtalamat.getText().toString();
                txtrtrw1 = txtrtrw.getText().toString();
                txtkota1 = txtkota.getText().toString();
                txtkecamatan1 = txtkecamatan.getText().toString();
                txtkelurahan1 = txtkelurahan.getText().toString();
                txtktp1 = txtktp.getText().toString();
                txtnpwp1 = txtnpwp.getText().toString();
                txtemail1 = txtemail.getText().toString();

                if (txtalamat1.matches("")){
                    Toast.makeText(ProfilActivity1.this,"Anda belum mengisi 'Alamat'",Toast.LENGTH_SHORT).show();
                    return;
                }if (txtrtrw1.matches("")){
                    Toast.makeText(ProfilActivity1.this,"Anda belum mengisi 'Rt/Rw'",Toast.LENGTH_SHORT).show();
                    return;
                }if (txtkota1.matches("")){
                    Toast.makeText(ProfilActivity1.this,"Anda belum mengisi 'Kota/Kabupaten'",Toast.LENGTH_SHORT).show();
                    return;
                }if (txtkecamatan1.matches("")){
                    Toast.makeText(ProfilActivity1.this,"Anda belum mengisi 'Kecamatan'",Toast.LENGTH_SHORT).show();
                    return;
                }if (txtkelurahan1.matches("")){
                    Toast.makeText(ProfilActivity1.this,"Anda belum mengisi 'Kelurahan'",Toast.LENGTH_SHORT).show();
                    return;
                }if (txtktp1.matches("")){
                    Toast.makeText(ProfilActivity1.this,"Anda belum mengisi 'KTP'",Toast.LENGTH_SHORT).show();
                    return;
                }if (txtnpwp1.matches("")){
                    Toast.makeText(ProfilActivity1.this,"Anda belum mengisi 'NPWP'",Toast.LENGTH_SHORT).show();
                    return;
                }if (txtemail1.matches("")){
                    Toast.makeText(ProfilActivity1.this,"Anda belum mengisi 'E-Mail'",Toast.LENGTH_SHORT).show();
                    return;
                }

                //System.out.println(DataProfil1[0]);
                Intent i = new Intent(ProfilActivity1.this, ProfilActivity2.class );
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
                i.putExtra("alamat", txtalamat1);
                i.putExtra("rtrw", txtrtrw1);
                i.putExtra("kota", txtkota1);
                i.putExtra("kecamatan", txtkecamatan1);
                i.putExtra("kelurahan", txtkelurahan1);
                i.putExtra("ktp", txtktp1);
                i.putExtra("npwp", txtnpwp1);
                i.putExtra("email", txtemail1);
                //i.putExtra("DataProfil1", DataProfil1);
                startActivity(i);
            }
        });
        btn_sebelumnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
