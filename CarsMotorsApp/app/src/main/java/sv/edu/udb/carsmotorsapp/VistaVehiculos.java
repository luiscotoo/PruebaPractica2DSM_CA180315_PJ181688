package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VistaVehiculos extends AppCompatActivity {

    ArrayList<VehiculosVo> listvehiculos;
    RecyclerView recycleVehiculo;
    AdminSQLiteOpenHelper admin;
    String user,idusuario;
    TextView Vehiculos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_vehiculos);

        admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        Vehiculos = (TextView) findViewById(R.id.Vehiculos);
        Vehiculos.setText("Vehiculos - "+user);
        idusuario = extras.getString("idusuario");
        listvehiculos=new ArrayList<>();
        recycleVehiculo=findViewById(R.id.recycler);
        recycleVehiculo.setLayoutManager(new LinearLayoutManager(this));
        llenarvehiculos();

        AdapterDatos adapter=new AdapterDatos(listvehiculos);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase bd= admin.getWritableDatabase();
                Integer idfavoritoautomovil = listvehiculos.get(recycleVehiculo.getChildAdapterPosition(view)).getId();
                String fecha_agregado = String.valueOf(android.text.format.DateFormat.format("dd-MM-yyyy", new java.util.Date()));;
                Cursor fila=bd.rawQuery("SELECT marcas.nombre,modelo,anio,colores.descripcion,capacidad_asientos,precio,URI_IMG,idautomovil from automovil INNER JOIN favoritos_automovil on favoritos_automovil.idfavoritoautomovil = automovil.idautomovil INNER JOIN usuario on usuario.idusuario = favoritos_automovil.idusuario INNER JOIN marcas on marcas.idmarcas = automovil.idmarcas INNER JOIN colores on colores.idcolores = automovil.idcolores WHERE usuario.user='"+user+"' AND favoritos_automovil.idfavoritoautomovil='"+idfavoritoautomovil+"'",null);
                if(fila.moveToFirst()){
                    Toast.makeText(getApplicationContext(),"Este vehiculo ya está en favoritos", Toast.LENGTH_SHORT).show();
                }else{
                    ContentValues registro=new ContentValues();
                    registro.put("idusuario",idusuario);
                    registro.put("idfavoritoautomovil",idfavoritoautomovil);
                    registro.put("fecha_agregado",fecha_agregado);
                    bd.insert("favoritos_automovil",null,registro);
                    Toast.makeText(getApplicationContext(),"Se agrego a favoritos", Toast.LENGTH_SHORT).show();
                }

                bd.close();
            }
        });
        recycleVehiculo.setAdapter(adapter);
    }

    private void llenarvehiculos(){
        SQLiteDatabase bd= admin.getWritableDatabase();
        VehiculosVo vehiculosVo = null;
        Cursor fila=bd.rawQuery("SELECT marcas.nombre,modelo,anio,colores.descripcion,capacidad_asientos,precio,URI_IMG,idautomovil from automovil INNER JOIN marcas on marcas.idmarcas = automovil.idmarcas INNER JOIN colores on colores.idcolores = automovil.idcolores",null);
        while(fila.moveToNext()){
            listvehiculos.add(new VehiculosVo("Marca: "+fila.getString(0),"Modelo: "+fila.getString(1),"Año: "+fila.getString(2),"Color: "+fila.getString(3),"Capacidad: "+fila.getString(4),"Precio: "+fila.getString(5), fila.getString(6),+fila.getInt(7)));
        }
    }

    public void QuitarFavorito(View v){

    }
}