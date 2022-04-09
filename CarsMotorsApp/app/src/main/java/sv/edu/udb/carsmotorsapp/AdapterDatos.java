package sv.edu.udb.carsmotorsapp;

import android.icu.text.RelativeDateTimeFormatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    ArrayList<VehiculosVo> listvehiculos;

    public AdapterDatos(ArrayList<VehiculosVo> listvehiculos) {
        this.listvehiculos = listvehiculos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.tvMarca.setText(listvehiculos.get(position).getMarca());
        holder.tvModelo.setText(listvehiculos.get(position).getModelo());
        holder.tvAnio.setText(listvehiculos.get(position).getAnio());
        holder.tvColor.setText(listvehiculos.get(position).getColor());
        holder.tvCapacidad.setText(listvehiculos.get(position).getCapacidad());
        holder.tvPrecio.setText(listvehiculos.get(position).getPrecio());
        holder.Imagen.setImageResource(listvehiculos.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return listvehiculos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvMarca, tvModelo, tvAnio, tvColor, tvCapacidad, tvPrecio;
        ImageView Imagen;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvMarca=itemView.findViewById(R.id.tvMarca);
            tvModelo=itemView.findViewById(R.id.tvModelo);
            tvAnio=itemView.findViewById(R.id.tvanio);
            tvColor=itemView.findViewById(R.id.tvcolor);
            tvCapacidad=itemView.findViewById(R.id.tvcapacidad);
            tvPrecio=itemView.findViewById(R.id.tvPrecio);
            Imagen=itemView.findViewById(R.id.IdImag);
        }
    }
}
