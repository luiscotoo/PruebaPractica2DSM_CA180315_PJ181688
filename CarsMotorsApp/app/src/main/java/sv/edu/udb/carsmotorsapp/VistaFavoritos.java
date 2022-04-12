package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VistaFavoritos extends AppCompatActivity {

    ArrayList<VehiculosVo> listvehiculos;
    RecyclerView recycleVehiculo;
    AdminSQLiteOpenHelper admin;
    String user,idusuario;
    TextView VehiculosFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_favoritos);

        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        VehiculosFavoritos = (TextView) findViewById(R.id.VehiculosFavoritos);
        VehiculosFavoritos.setText("Vehiculos Favoritos - "+user);
        idusuario = extras.getString("idusuario");
        admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);

        listvehiculos=new ArrayList<>();
        recycleVehiculo=findViewById(R.id.recyclerFavoritos);
        recycleVehiculo.setLayoutManager(new LinearLayoutManager(this));
        llenarvehiculos();
        AdapterDatos adapter=new AdapterDatos(listvehiculos);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase bd= admin.getWritableDatabase();
                Integer idfavoritoautomovil = listvehiculos.get(recycleVehiculo.getChildAdapterPosition(view)).getId();
                Cursor fila=bd.rawQuery("DELETE FROM favoritos_automovil WHERE idusuario ='"+idusuario+"' AND idfavoritoautomovil ='"+idfavoritoautomovil+"'", null);
                String eliminados = String.valueOf(fila.getCount());
                Toast.makeText(getApplicationContext(),"Se elimino el vehiculo de favoritos. Refresca para ver cambios.", Toast.LENGTH_SHORT).show();
                bd.close();
            }
        });
        recycleVehiculo.setAdapter(adapter);
    }

    private void llenarvehiculos(){
        SQLiteDatabase bd= admin.getWritableDatabase();
        VehiculosVo vehiculosVo = null;
        Cursor fila=bd.rawQuery("SELECT marcas.nombre,modelo,anio,colores.descripcion,capacidad_asientos,precio,URI_IMG,idautomovil from automovil INNER JOIN favoritos_automovil on favoritos_automovil.idfavoritoautomovil = automovil.idautomovil INNER JOIN usuario on usuario.idusuario = favoritos_automovil.idusuario INNER JOIN marcas on marcas.idmarcas = automovil.idmarcas INNER JOIN colores on colores.idcolores = automovil.idcolores WHERE usuario.user='"+user+"'",null);
        while(fila.moveToNext()){
            listvehiculos.add(new VehiculosVo("Marca: "+fila.getString(0),"Modelo: "+fila.getString(1),"Año: "+fila.getString(2),"Color: "+fila.getString(3),"Capacidad: "+fila.getString(4),"Precio: "+fila.getString(5), fila.getString(6),fila.getInt(7)));
        }

    }

}