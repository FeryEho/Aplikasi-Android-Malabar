package com.app.renttech;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;

public class RecycleAdapterBank extends RecyclerView.Adapter<RecycleAdapterBank.ViewHolder> {
    private Context context;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>>list_data;

    public RecycleAdapterBank(IsiSimpananActivity context, ArrayList<HashMap<String, String>> list_data, int activity_isi_simpanan, String[] strings, int[] ints) {
        this.context = context;
        this.list_data = list_data;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //membuat link view baru dengan tampilan intent baru
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycle_adapter_bank, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final HashMap<String, String> array_kode = list_data.get(position);
        final HashMap<String, String> array_namabank = list_data.get(position);
        final HashMap<String, String> array_norekbank = list_data.get(position);
        final HashMap<String, String> array_image = list_data.get(position);
        final HashMap<String, String> array_pemilikbank = list_data.get(position);
        final HashMap<String, String> array_jumlah = list_data.get(position);

        Glide.with(context)
                .load("http://192.168.27.88/malabar/upload/imagebank/" + list_data.get(position).get("image"))
                .into(holder.img_photobank);
        holder.tv_item_namabank.setText(list_data.get(position).get("NamaBank"));
        holder.tv_item_norekbank.setText(list_data.get(position).get("NoRekening"));
        holder.tv_item_namapemilik.setText(list_data.get(position).get("NamaPemilikRekening"));

        holder.cv_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(array_jumlah);
                Intent i =new Intent(context, PembayaranActivity.class);
                i.putExtra("Kode", array_kode.get("Kode"));
                i.putExtra("NamaBank", array_namabank.get("NamaBank"));
                i.putExtra("NoRekening", array_norekbank.get("NoRekening"));
                i.putExtra("image", array_image.get("image"));
                i.putExtra("NamaPemilikRekening", array_pemilikbank.get("NamaPemilikRekening"));
                i.putExtra("nominal", array_pemilikbank.get("jumlah"));
                ((IsiSimpananActivity)context).startActivityForResult(i,2);
            }
        });

    }

    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_photobank;
        TextView tv_item_namabank, tv_item_norekbank, tv_item_namapemilik;
        CardView cv_bank;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_item_namabank = (TextView) itemView.findViewById(R.id.tv_item_namabank);
            tv_item_norekbank = (TextView) itemView.findViewById(R.id.tv_item_norekbank);
            tv_item_namapemilik = (TextView) itemView.findViewById(R.id.tv_item_namapemilik);
            img_photobank = (ImageView) itemView.findViewById(R.id.img_photobank);
            cv_bank = (CardView) itemView.findViewById(R.id.cv_bank);

        }

    }

}
