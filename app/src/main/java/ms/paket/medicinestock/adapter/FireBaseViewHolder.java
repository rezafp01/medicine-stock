package ms.paket.medicinestock.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ms.paket.medicinestock.R;

public class FireBaseViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle;
    public ImageView img;


    public FireBaseViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitle= itemView.findViewById(R.id.name_item);
        img = itemView.findViewById(R.id.img_item);


    }
}
