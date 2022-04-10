package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class CrudAutomovil extends AppCompatActivity {
    private Spinner comboMarcas, comboColores, comboTipos;
    private EditText etModelo, etNumeroVin, etNumeroCha, etNumeroMotor, etNumeroAsi,etAnio,etCapacidadAsi,etPrecio,etImagen,etDescripcion;
    ArrayList<String>listMarcas;
    ArrayList<Instancias> instanciasList;

    ArrayList<String>listColor;
    ArrayList<Instancias>colorlist;

    ArrayList<String>listTipo;
    ArrayList<Instancias>tipolist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_automovil);
        comboMarcas=findViewById(R.id.LisMarcas);
        comboColores=findViewById(R.id.lisColor);
        comboTipos=findViewById(R.id.lisTipo);
        ConsultarListaMarcas();
        ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,listMarcas);
        comboMarcas.setAdapter(adapter);
        ConsultarListaTipos();
        ArrayAdapter<CharSequence> adapter1=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,listTipo);
        comboTipos.setAdapter(adapter1);
        ConsultarListaColor();
        ArrayAdapter<CharSequence> adapter2=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,listColor);
        comboColores.setAdapter(adapter2);
    }
    private void ConsultarListaMarcas() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        Instancias marca=null;
        instanciasList =new ArrayList<>();
        Cursor cursor=bd.rawQuery("select * from marcas",null);
        while (cursor.moveToNext()){
            marca=new Instancias();
            marca.setId(cursor.getInt(0));
            marca.setNombre(cursor.getString(1));
            instanciasList.add(marca);
        }
        obtenerListaMarcas();
        }

    private void ConsultarListaTipos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        Instancias tipo=null;
        instanciasList =new ArrayList<>();
        Cursor cursor=bd.rawQuery("select * from tipo_automovil",null);
        while (cursor.moveToNext()){
            tipo=new Instancias();
            tipo.setId(cursor.getInt(0));
            tipo.setNombre(cursor.getString(1));
            instanciasList.add(tipo);
        }
        obtenerListaTipos();
    }



    private void ConsultarListaColor() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        Instancias color=null;
        colorlist=new ArrayList<>();
        Cursor cursor=bd.rawQuery("select * from colores",null);
        while (cursor.moveToNext()){
            color=new Instancias();
            color.setId(cursor.getInt(0));
            color.setNombre(cursor.getString(1));
            colorlist.add(color);
        }
        obtenerListaColor();
    }

    private void obtenerListaMarcas() {
        listMarcas=new ArrayList<String>();
        listMarcas.add("Selecione la Marca");
        for (int i = 0; i< instanciasList.size(); i++){
        listMarcas.add(instanciasList.get(i).getId()+"- "+ instanciasList.get(i).getNombre());
        }
    }

    private void obtenerListaTipos() {
        listTipo=new ArrayList<String>();
        listTipo.add("Selecione Tipo de vehiculo");
        for (int i = 0; i< instanciasList.size(); i++){
            listTipo.add(instanciasList.get(i).getId()+"- "+ instanciasList.get(i).getNombre());
        }
    }

    private void obtenerListaColor() {
        listColor = new ArrayList<String>();
        listColor.add("Selecione el Color");
        for (int i = 0; i < colorlist.size(); i++) {
            listColor.add(colorlist.get(i).getId() + "- " + colorlist.get(i).getNombre());
        }
    }
    }