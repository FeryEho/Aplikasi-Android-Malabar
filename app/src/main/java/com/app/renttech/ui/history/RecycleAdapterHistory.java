package com.app.renttech.ui.history;

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

import com.app.renttech.R;
import com.bumptech.glide.Glide;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;

import static com.app.renttech.util.Utils.StringToCurrency;

public class RecycleAdapterHistory extends RecyclerView.Adapter<RecycleAdapterHistory.ViewHolder> {
    private Context context;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>>list_data;

    public RecycleAdapterHistory(HistoryFragment context, ArrayList<HashMap<String, String>> list_data, int activity_isi_simpanan, String[] strings, int[] ints) {
        this.context = context.getActivity();
        this.list_data = list_data;
        //progressDialog = new ProgressDialog(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //membuat link view baru dengan tampilan intent baru
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycle_adapter_history, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /*final HashMap<String, String> array_kode = list_data.get(position);
        final HashMap<String, String> array_namabank = list_data.get(position);
        final HashMap<String, String> array_norekbank = list_data.get(position);
        final HashMap<String, String> array_image = list_data.get(position);
        final HashMap<String, String> array_pemilikbank = list_data.get(position);
        final HashMap<String, String> array_jumlah = list_data.get(position);

         */

        Glide.with(context)
                .load("http://192.168.27.88/malabar/RestAPI/upload/imagesbuktitransfer/" + list_data.get(position).get("PhotoBukti"))
                .into(holder.img_photobukti);
        holder.tv_item_notelepon.setText(list_data.get(position).get("NoTelepon"));
        holder.tv_item_totalsetor.setText(StringToCurrency(list_data.get(position).get("TotalSetor")));
        holder.tv_item_tanggal.setText(list_data.get(position).get("Tanggal"));

        /*holder.cv_bank.setOnClickListener(new View.OnClickListener() {
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

         */

    }

    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_photobukti;
        TextView tv_item_notelepon, tv_item_tanggal, tv_item_totalsetor;
        CardView cv_bank;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_item_notelepon = (TextView) itemView.findViewById(R.id.tv_item_notelepon);
            tv_item_tanggal = (TextView) itemView.findViewById(R.id.tv_item_tanggal);
            tv_item_totalsetor = (TextView) itemView.findViewById(R.id.tv_item_totalsetor);
            img_photobukti = (ImageView) itemView.findViewById(R.id.img_photobukti);
            cv_bank = (CardView) itemView.findViewById(R.id.cv_bank);

        }

    }

}
