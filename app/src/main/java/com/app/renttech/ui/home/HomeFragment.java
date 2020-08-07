package com.app.renttech.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.renttech.PreferenceHelper;
import com.app.renttech.R;
import com.app.renttech.slider.SliderAdapter;
import com.app.renttech.slider.SliderItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.renttech.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.app.renttech.util.Utils.StringToCurrency;

public class HomeFragment extends Fragment {
    private PreferenceHelper preferenceHelper;
    int position;
    private StringRequest stringRequest;
    private RequestQueue rQueue;
    ArrayList<HashMap<String, String>> list_data;

    TextView saldo;

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        preferenceHelper = new PreferenceHelper(getContext());
        list_data = new ArrayList<HashMap<String, String>>();
        saldo = (TextView) root.findViewById(R.id.saldo);

        viewPager2 = (ViewPager2) root.findViewById(R.id.viewPagerImageSlider);
        final List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.a));
        sliderItems.add(new SliderItem(R.drawable.b));
        sliderItems.add(new SliderItem(R.drawable.c));
        sliderItems.add(new SliderItem(R.drawable.d));
        sliderItems.add(new SliderItem(R.drawable.e));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

        getSaldo();

        return root;
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };
    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    public void getSaldo(){
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
                            saldo.setText(StringToCurrency(list_data.get(position).get("SUM(TotalSetor)")));
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
