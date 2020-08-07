package com.app.renttech.ui.profil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.renttech.DashboardActivity;
import com.app.renttech.IsiSimpananActivity;
import com.app.renttech.LoginActivity;
import com.app.renttech.PortofolioActivity;
import com.app.renttech.PreferenceHelper;
import com.app.renttech.ProfilActivity;
import com.app.renttech.ProfilActivity3;
import com.app.renttech.R;
import com.app.renttech.RegisterActivity;
import com.app.renttech.ui.history.HistoryFragment;
import com.app.renttech.ui.history.RecycleAdapterHistory;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.app.renttech.util.Utils.StringToCurrency;

public class ProfilFragment extends Fragment {

    CardView profil, portofolio;
    ImageView isisaldo, logout;
    ImageView btnprofil, imgprofil;
    private TextView tvname, tvtelepon, tvsaldo;
    private PreferenceHelper preferenceHelper;
    int position;

    final int CODE_GALERY_REQUEST = 1;
    Bitmap bitmapImage;

    private String insertURL = "http://192.168.27.252/malabar/RestAPI/insertprofil.php";

    private StringRequest stringRequest;
    private RequestQueue rQueue;
    ArrayList<HashMap<String, String>> list_data;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profil, container, false);
        //final TextView textView = root.findViewById(R.id.text_notifications);
        preferenceHelper = new PreferenceHelper(getContext());
        list_data = new ArrayList<HashMap<String, String>>();

        tvtelepon = (TextView) root.findViewById(R.id.tvtelepon);
        tvname = (TextView) root.findViewById(R.id.tvname);
        tvsaldo = (TextView) root.findViewById(R.id.tvsaldo);

        tvname.setText(preferenceHelper.getNama());
        tvtelepon.setText(preferenceHelper.getNoTelepon());

        profil = (CardView) root.findViewById(R.id.profil);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = tvname.getText().toString();
                String telepon = tvtelepon.getText().toString();
                Intent i = new Intent(getActivity().getBaseContext(), ProfilActivity.class);
                i.putExtra("nama", nama);
                i.putExtra("telepon", telepon);
                getActivity().startActivity(i);
            }
        });

        isisaldo = (ImageView) root.findViewById(R.id.isisaldo);
        isisaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), IsiSimpananActivity.class);
                startActivity(i);

            }
        });

        portofolio = (CardView) root.findViewById(R.id.portofolio);
        portofolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PortofolioActivity.class);
                startActivity(i);
            }
        });

        imgprofil = (ImageView) root.findViewById(R.id.imgprofil);
        btnprofil = (ImageView) root.findViewById(R.id.btnprofil);
        btnprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
                else {
                    startGallery();
                }
            }
        });

        Glide.with(getActivity())
                .asBitmap()
                .load("http://192.168.27.252/malabar/RestAPI/upload/imagesprofil/" + preferenceHelper.getNama()+".jpeg")
                .into(imgprofil);

        logout = (ImageView) root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferenceHelper.putIsLogin(false);
                Toast.makeText(getActivity(), "Logout Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //ProfilFragment.this.finish();
            }
        });

        getDataBuktiTransfer();

        return root;
    }

    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgprofil.setImageBitmap(bitmap);
                insertprofil(bitmap);
            }
        }
        //Uri returnUri;
        //returnUri = data.getData();
    }

    private String imageToString(Bitmap bitmapImage){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG,  100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    private void insertprofil(final Bitmap image){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        Toast.makeText(getActivity(),"Profil Berhasil Di Update",Toast.LENGTH_LONG).show();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Gagal Upload Profil",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("Nama",  preferenceHelper.getNama());

                String imageData = imageToString(image);
                params.put("image", imageData);

                return params;
            }

        };

        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(stringRequest);
    }

    public void getDataBuktiTransfer(){
        String url = "http://192.168.27.88/malabar/RestAPI/gettotalsetor.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //rQueue.getCache().clear();
                        //Toast.makeText(getActivity(),"",Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject json = jsonArray.getJSONObject(a);
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("NoTelepon", json.getString("NoTelepon"));
                                map.put("SUM(TotalSetor)", json.getString("SUM(TotalSetor)"));
                                list_data.add(map);
                            }
                            tvsaldo.setText(StringToCurrency(list_data.get(position).get("SUM(TotalSetor)")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("NoTelepon", preferenceHelper.getNoTelepon());
                //params.put("Password",  password);

                return params;
            }

        };
        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(stringRequest);
    }

}
