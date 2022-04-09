package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class VistaVehiculos extends AppCompatActivity {

    ArrayList<VehiculosVo> listvehiculos;
    RecyclerView recycleVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_vehiculos);

        listvehiculos=new ArrayList<>();
        recycleVehiculo=findViewById(R.id.recycler);
        recycleVehiculo.setLayoutManager(new LinearLayoutManager(this));
        llenarvehiculos();

        AdapterDatos adapter=new AdapterDatos(listvehiculos);
        recycleVehiculo.setAdapter(adapter);
    }

    private void llenarvehiculos(){
        listvehiculos.add(new VehiculosVo("Toyota","RAV4","2022","rojo","6","40000",R.drawable.rav4));
        listvehiculos.add(new VehiculosVo("Toyota","RAV4","2022","rojo","6","40000",R.drawable.rav4));
        listvehiculos.add(new VehiculosVo("Toyota","RAV4","2022","rojo","6","40000",R.drawable.rav4));

    }
}