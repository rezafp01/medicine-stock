package ms.paket.medicinestock.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import ms.paket.medicinestock.R;
import ms.paket.medicinestock.ui.DetailObatActivity;
import ms.paket.medicinestock.model.Obat;


public class GridObatAdapter extends RecyclerView.Adapter<GridObatAdapter.GridViewHolder>  {
    private ArrayList<Obat> listObat;
    private Context context;





    public GridObatAdapter(ArrayList<Obat> listObat, Context context) {
        this.listObat = listObat;
        this.context = context;
    }

    public GridObatAdapter(ArrayList<Obat> listObat) {
        this.listObat = listObat;
    }


    @NonNull
    @Override
    public GridObatAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_obat, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridViewHolder holder, final int position) {

        Glide.with(holder.itemView.getContext())
                .load(listObat.get(position).getImgObat())
                .apply(new RequestOptions().fitCenter())
                .into(holder.imgPhoto);
        holder.tvName.setText(listObat.get(position).getNamaObat());
        holder.Stok.setText(listObat.get(position).getStok());
        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailObat = new Intent(context, DetailObatActivity.class);
                detailObat.putExtra("img" +"", listObat.get(holder.getAdapterPosition()).getImgObat());
                detailObat.putExtra("nama",listObat.get(holder.getAdapterPosition()).getNamaObat());
                detailObat.putExtra("des",listObat.get(holder.getAdapterPosition()).getDesObat());
                detailObat.putExtra("cara",listObat.get(holder.getAdapterPosition()).getCaraPakai());
                detailObat.putExtra("atur",listObat.get(holder.getAdapterPosition()).getAturPakai());
                context.startActivity(detailObat);
            }
        });

    }



    @Override
    public int getItemCount() {
        return listObat.size();
    }




    public class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, Stok;


        GridViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item);
            tvName = itemView.findViewById(R.id.name_item);
            Stok = itemView.findViewById(R.id.stok_item);

        }
    }
}
