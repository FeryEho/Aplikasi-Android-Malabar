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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfilActivity extends AppCompatActivity {

    EditText txtnama, txttelepon, txttempatlahir, txttgllahir, txtpendidikan, txtpekerjaan;
    String nama, telepon, tempatlahir, tgllahir, pendidikan, pekerjaan, kelamin1, statuskawin1, agama, penghasilan;
    RadioGroup radiokelamin, radiostatuskawin;
    RadioButton radioKelamin1, radioKelamin2, radiostatuskawin1, radiostatuskawin2, radiostatuskawin3;
    RadioButton kelamin, statuskawin;
    Spinner spnagama, spnpenghasilan;
    Button btn_berikutnya;
    TextView judul;

    private PreferenceHelper preferenceHelper;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        txtnama                    = (EditText) findViewById(R.id.txtnama);
        txttelepon                 = (EditText) findViewById(R.id.txttelepon);
        txttempatlahir             = (EditText) findViewById(R.id.txttempatlahir);
        txttgllahir                = (EditText) findViewById(R.id.txttgllahir);
        txtpendidikan              = (EditText) findViewById(R.id.txtpendidikan);
        txtpekerjaan               = (EditText) findViewById(R.id.txtpekerjaan);
        radiokelamin               = (RadioGroup) findViewById(R.id.radiokelamin);
        radiostatuskawin           = (RadioGroup) findViewById(R.id.radiostatuskawin);
        radioKelamin1              = (RadioButton) findViewById(R.id.radioKelamin1);
        radioKelamin2              = (RadioButton) findViewById(R.id.radioKelamin2);
        radiostatuskawin1              = (RadioButton) findViewById(R.id.radiostatuskawin1);
        radiostatuskawin2              = (RadioButton) findViewById(R.id.radiostatuskawin2);
        radiostatuskawin3              = (RadioButton) findViewById(R.id.radiostatuskawin3);
        spnagama                   = (Spinner) findViewById(R.id.spnagama);
        spnpenghasilan             = (Spinner) findViewById(R.id.spnpenghasilan);
        btn_berikutnya             = (Button) findViewById(R.id.btn_berikutnya);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        txttgllahir.setEditableFactory(Editable.Factory.getInstance());
        txttgllahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        /*int pilih = radiokelamin.getCheckedRadioButtonId();
        kelamin = (RadioButton) findViewById(pilih);
        final String jenis = kelamin.getText().toString();

        int pilih1 = radiostatuskawin.getCheckedRadioButtonId();
        statuskawin = (RadioButton) findViewById(pilih1);
        final String status = statuskawin.getText().toString();

         */

        btn_berikutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = txtnama.getText().toString();
                telepon = txttelepon.getText().toString();
                tempatlahir = txttempatlahir.getText().toString();
                tgllahir = txttgllahir.getText().toString();
                pendidikan = txtpendidikan.getText().toString();
                pekerjaan = txtpekerjaan.getText().toString();

                int selectedId = radiokelamin.getCheckedRadioButtonId();
                kelamin = (RadioButton) findViewById(selectedId);
                //kelamin.getText().toString();
                //System.out.println(kelamin);
                int selectedId1 = radiostatuskawin.getCheckedRadioButtonId();
                statuskawin = (RadioButton) findViewById(selectedId1);

                agama = spnagama.getSelectedItem().toString();
                penghasilan = spnpenghasilan.getSelectedItem().toString();

                if (nama.matches("")){
                    Toast.makeText(ProfilActivity.this,"Anda belum mengisi 'Nama'",Toast.LENGTH_SHORT).show();
                    return;
                }if (telepon.matches("")){
                    Toast.makeText(ProfilActivity.this,"Anda belum mengisi 'No. Telepon'",Toast.LENGTH_SHORT).show();
                    return;
                }if (tempatlahir.matches("")){
                    Toast.makeText(ProfilActivity.this,"Anda belum mengisi 'Tempat Lahir'",Toast.LENGTH_SHORT).show();
                    return;
                }if (tgllahir.matches("")){
                    Toast.makeText(ProfilActivity.this,"Anda belum mengisi 'Tanggal Lahir'",Toast.LENGTH_SHORT).show();
                    return;
                }if (pendidikan.matches("")){
                    Toast.makeText(ProfilActivity.this,"Anda belum mengisi 'Pendidikan'",Toast.LENGTH_SHORT).show();
                    return;
                }if (pekerjaan.matches("")){
                    Toast.makeText(ProfilActivity.this,"Anda belum mengisi 'Pekerjaan'",Toast.LENGTH_SHORT).show();
                    return;
                }if (penghasilan.matches("")){
                    Toast.makeText(ProfilActivity.this,"Anda belum mengisi 'Penghasilan'",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent(ProfilActivity.this,ProfilActivity1.class);
                i.putExtra("nama", nama);
                i.putExtra("telepon", telepon);
                i.putExtra("kelamin", kelamin.getText().toString());
                i.putExtra("tempatlahir", tempatlahir);
                i.putExtra("tgllahir", tgllahir);
                i.putExtra("agama", agama);
                i.putExtra("statuskawin", statuskawin.getText().toString());
                i.putExtra("pendidikan", pendidikan);
                i.putExtra("pekerjaan", pekerjaan);
                i.putExtra("penghasilan", penghasilan);
                startActivity(i);

            }
        });

        //mengambil data dari preferencehelper
        String nama1 = getIntent().getStringExtra("nama");
        txtnama.setText(nama1);
        String telepon1 = getIntent().getStringExtra("telepon");
        txttelepon.setText(telepon1);
        txttelepon.setEnabled(false);

    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                txttgllahir.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
