package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class VistaFavoritos extends AppCompatActivity {

    ArrayList<VehiculosVo> listvehiculos;
    RecyclerView recycleVehiculo;
    AdminSQLiteOpenHelper admin;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_favoritos);

        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);

        listvehiculos=new ArrayList<>();
        recycleVehiculo=findViewById(R.id.recyclerFavoritos);
        recycleVehiculo.setLayoutManager(new LinearLayoutManager(this));
        llenarvehiculos();

        AdapterDatos adapter=new AdapterDatos(listvehiculos);
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