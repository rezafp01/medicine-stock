package ms.paket.medicinestock.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ms.paket.medicinestock.R;
import ms.paket.medicinestock.model.Apotek;
import ms.paket.medicinestock.ui.ApotekActivity;

public class ApotekAdapter extends RecyclerView.Adapter<ApotekAdapter.GridViewHolder> {
    ArrayList<Apotek> list;

    public ApotekAdapter(ArrayList<Apotek> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ApotekAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_apotek,parent,false);
        return new GridViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ApotekAdapter.GridViewHolder holder, int position) {
        final Apotek apotek = list.get(position);
        holder.title.setText(apotek.getName());
        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImg())
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = apotek.getName();
                Intent intent = new Intent(holder.itemView.getContext(), ApotekActivity.class);
                intent.putExtra("apotek",c);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.categoryName);
            img = itemView.findViewById(R.id.categoryThumb);
        }
    }
}
