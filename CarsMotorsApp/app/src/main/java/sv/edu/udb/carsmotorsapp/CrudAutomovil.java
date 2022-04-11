package sv.edu.udb.carsmotorsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CrudAutomovil extends AppCompatActivity {
    private Spinner comboMarcas, comboColores, comboTipos;
    private EditText etIdAutomovil,etModelo, etNumeroVin, etNumeroCha, etNumeroMotor, etNumeroAsi,etAnio,etCapacidadAsi,etPrecio,etImagen,etDescripcion;
    private ImageView imagen;
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
        imagen=findViewById(R.id.IdImagen);
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
        ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this,R.layout.spinner_item,listMarcas);
        comboMarcas.setAdapter(adapter);
        ConsultarListaTipos();
        ArrayAdapter<CharSequence> adapter1=new ArrayAdapter(this,R.layout.spinner_item,listTipo);
        comboTipos.setAdapter(adapter1);
        ConsultarListaColor();
        ArrayAdapter<CharSequence> adapter2=new ArrayAdapter(this,R.layout.spinner_item,listColor);
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

    public void Ingresar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        Uri pathImag=getIntent().getData();
        String id = etIdAutomovil.getText().toString();
        String modelo=etModelo.getText().toString();
        String numero_vin=etNumeroVin.getText().toString();
        String numero_chasis=etNumeroCha.getText().toString();
        String numero_motor=etNumeroMotor.getText().toString();
        Integer numero_asientos=Integer.parseInt(etNumeroAsi.getText().toString());
        Integer anio=Integer.parseInt(etAnio.getText().toString());
        Integer capacidad_asientos=Integer.parseInt(etCapacidadAsi.getText().toString());
        Double precio=Double.parseDouble(etPrecio.getText().toString());
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
            registro.put("URI_IMG", String.valueOf(pathImag));
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
            comboMarcas.setSelection(0);
            comboColores.setSelection(0);
            comboTipos.setSelection(0);
            imagen.setImageDrawable(null);
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
            Cursor fila=bd.rawQuery("select idautomovil, modelo, numero_vin, numero_chasis, numero_motor, numero_asientos, anio, capacidad_asientos, precio, URI_IMG, descripcion,idmarcas,idtipoautomovil,idcolores from automovil where modelo='"+modelo+"'",null);
            if (fila.moveToFirst()){

                etIdAutomovil.setText(fila.getString(0));
                etModelo.setText(fila.getString(1));
                etNumeroVin.setText(fila.getString(2));
                etNumeroCha.setText(fila.getString(3));
                etNumeroMotor.setText(fila.getString(4));
                etNumeroAsi.setText(fila.getString(5));
                etAnio.setText(fila.getString(6));
                etCapacidadAsi.setText(fila.getString(7));
                etPrecio.setText(fila.getString(8));
                imagen.setImageURI(Uri.parse(fila.getString(9)));
                etDescripcion.setText(fila.getString(10));
                comboMarcas.setSelection(Integer.valueOf(fila.getString(11)));
                comboTipos.setSelection(Integer.valueOf(fila.getString(12)));
                comboColores.setSelection(Integer.valueOf(fila.getString(13)));
                Toast.makeText(this,"Vehiculo Encontrado con exito",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"No existe vehiculo",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void Actualizar(View V){
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
        if(id.isEmpty()){
            Toast.makeText(this,"Ingrese un ID",Toast.LENGTH_SHORT).show();
        }else{
            ContentValues registro=new ContentValues();
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
            int cant=bd.update("automovil", registro,"idautomovil="+id,null);
            bd.close();
            if(cant==1){
                Toast.makeText(this,"Se actualizo el automovil",Toast.LENGTH_SHORT).show();
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
                comboMarcas.setSelection(0);
                comboColores.setSelection(0);
                comboTipos.setSelection(0);
            }
            else
                Toast.makeText(this,"No existe un automovil con ese ID",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void Borrar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String modelo=etModelo.getText().toString();
        if(modelo.isEmpty()){
            Toast.makeText(this,"Ingrese un modelo",Toast.LENGTH_SHORT).show();
        }else{
            int cant=bd.delete("automovil","modelo='"+modelo+"'",null);
            bd.close();
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
            comboMarcas.setSelection(0);
            comboColores.setSelection(0);
            comboTipos.setSelection(0);
            if(cant==1){
                Toast.makeText(this,"Se borró el automovil Correctamente",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"No existe el automovil",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }


    public void CargarImagen(View view) {
        cargarImagen();

    }

    private void cargarImagen() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Selecione la aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            imagen.setImageURI(path);

            try {
                Object bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imagen.setImageBitmap((Bitmap) bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}