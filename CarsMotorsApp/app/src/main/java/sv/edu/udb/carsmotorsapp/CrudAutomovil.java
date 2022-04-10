package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CrudAutomovil extends AppCompatActivity {
    private Spinner comboMarcas, comboColores, comboTipos;
    private EditText etIdAutomovil,etModelo, etNumeroVin, etNumeroCha, etNumeroMotor, etNumeroAsi,etAnio,etCapacidadAsi,etPrecio,etImagen,etDescripcion;
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
        etIdAutomovil=findViewById(R.id.etIdAutomovil);
        etModelo=findViewById(R.id.etModelo);
        etNumeroVin=findViewById(R.id.etNumeroVin);
        etNumeroCha=findViewById(R.id.etNumeroCha);
        etNumeroMotor=findViewById(R.id.etNumeroMotor);
        etNumeroAsi=findViewById(R.id.etNumeroAsi);
        etAnio=findViewById(R.id.etAnio);
        etCapacidadAsi=findViewById(R.id.etCapacidadAsi);
        etPrecio=findViewById(R.id.etPrecio);
        etImagen=findViewById(R.id.etImagen);
        etDescripcion=findViewById(R.id.etDescripcion);
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
        listMarcas.add("Selecione la Marca:");
        for (int i = 0; i< instanciasList.size(); i++){
        listMarcas.add(instanciasList.get(i).getId()+"- "+ instanciasList.get(i).getNombre());
        }
    }

    private void obtenerListaTipos() {
        listTipo=new ArrayList<String>();
        listTipo.add("Selecione Tipo de vehiculo:");
        for (int i = 0; i< instanciasList.size(); i++){
            listTipo.add(instanciasList.get(i).getId()+"- "+ instanciasList.get(i).getNombre());
        }
    }

    private void obtenerListaColor() {
        listColor = new ArrayList<String>();
        listColor.add("Selecione el Color:");
        for (int i = 0; i < colorlist.size(); i++) {
            listColor.add(colorlist.get(i).getId() + "- " + colorlist.get(i).getNombre());
        }
    }

    public void Ingresar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String id = etIdAutomovil.getText().toString();
        String modelo=etModelo.getText().toString();
        String numero_vin=etNumeroVin.getText().toString();
        String numero_chasis=etNumeroCha.getText().toString();
        String numero_motor=etNumeroMotor.getText().toString();
        Integer numero_asientos=Integer.parseInt(etNumeroAsi.getText().toString());
        Integer anio=Integer.parseInt(etAnio.getText().toString());
        Integer capacidad_asientos=Integer.parseInt(etCapacidadAsi.getText().toString());
        Double precio=Double.parseDouble(etPrecio.getText().toString());
        String imagen=etImagen.getText().toString();
        String descripcion=etDescripcion.getText().toString();
        Integer idmarcas=Integer.parseInt(String.valueOf(comboMarcas.getSelectedItemPosition()));
        Integer idtipoautomovil=Integer.parseInt(String.valueOf(comboTipos.getSelectedItemPosition()));
        Integer idcolores=Integer.parseInt(String.valueOf(comboColores.getSelectedItemPosition()));
        ContentValues registro=new ContentValues();
        if(modelo.isEmpty()){
            Toast.makeText(this,"Ingrese un modelo",Toast.LENGTH_SHORT).show();
        }else{
            registro.put("modelo",modelo);
            registro.put("numero_vin",numero_vin);
            registro.put("numero_chasis",numero_chasis);
            registro.put("numero_motor",numero_motor);
            registro.put("numero_asientos",numero_asientos);
            registro.put("anio",anio);
            registro.put("capacidad_asientos",capacidad_asientos);
            registro.put("precio",precio);
            registro.put("URI_IMG",imagen);
            registro.put("descripcion",descripcion);
            registro.put("idmarcas",idmarcas);
            registro.put("idtipoautomovil",idtipoautomovil);
            registro.put("idcolores",idcolores);
            bd.insert("automovil",null,registro);
            etIdAutomovil.setText("");
            etModelo.setText("");
            etNumeroVin.setText("");
            etNumeroCha.setText("");
            etNumeroMotor.setText("");
            etNumeroAsi.setText("");
            etAnio.setText("");
            etCapacidadAsi.setText("");
            etPrecio.setText("");
            etImagen.setText("");
            etDescripcion.setText("");
            comboMarcas.setId(0);
            comboColores.setId(0);
            comboTipos.setId(0);
            Toast.makeText(this,"Se ingreso el automovil", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
    public void Buscar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String modelo=etModelo.getText().toString();
        if(modelo.isEmpty()){
            Toast.makeText(this,"Ingrese Modelo de vehiculo",Toast.LENGTH_SHORT).show();
        }else{
            Cursor fila=bd.rawQuery("select Ma.idmarcas,Au.idautomovil, Au.modelo, Au.numero_vin, Au.numero_chasis, Au.numero_motor, Au.numero_asientos, Au.anio, Au.capacidad_asientos, Au.precio, Au.URI_IMG, Au.descripcion, cl.idcolores, Tp.idtipoautomovil from automovil Au INNER JOIN marcas Ma on Ma.idmarcas=Au.idmarcas INNER JOIN colores cl on cl.idcolores=Au.idcolores INNER JOIN tipo_automovil Tp on Tp.idtipoautomovil=Au.idtipoautomovil where Au.modelo='"+modelo+"'",null);
            if (fila.moveToFirst()){
                comboMarcas.setSelection(fila.getInt(0));
                etIdAutomovil.setText(fila.getString(1));
                etModelo.setText(fila.getString(2));
                etNumeroVin.setText(fila.getString(3));
                etNumeroCha.setText(fila.getString(4));
                etNumeroMotor.setText(fila.getString(5));
                etNumeroAsi.setText(fila.getString(6));
                etAnio.setText(fila.getString(7));
                etCapacidadAsi.setText(fila.getString(8));
                etPrecio.setText(fila.getString(9));
                etImagen.setText(fila.getString(10));
                etDescripcion.setText(fila.getString(11));
                comboTipos.setSelection(fila.getInt(12));
                comboColores.setSelection(fila.getInt(13));
                Toast.makeText(this,"Vehiculp Encontrado con exito",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"No existe vehiculo",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }


    }