package sv.edu.udb.carsmotorsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CrudAutomovil extends AppCompatActivity {
    private Spinner comboMarcas, comboColores, comboTipos;
    private EditText etIdAutomovil,etModelo, etNumeroVin, etNumeroCha, etNumeroMotor, etNumeroAsi,etAnio,etCapacidadAsi,etPrecio,etDescripcion;
    private ImageView imagen;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    private String[] cameraPermissions;
    private String[] storagePermissions;
    private Uri imageUri;



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

        cameraPermissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        imagen.setOnClickListener((v) ->{
            imagePickDialog();
        });
    }

    private void imagePickDialog(){
        String[] options = {"Camara","Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione imagen");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(which ==0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        PickFromCamera();
                    }
                }
                else if(which ==1){
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        PickFromGallery();
                    }
                }
            }
        });

        builder.create().show();
    }

    private void PickFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private  void PickFromCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Titulo de la imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Descripcion de la imagen");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermissions,CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        PickFromCamera();
                    }
                    else{
                        Toast.makeText(this, "Se requieren permisos de camara y almacenamiento",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted){
                        PickFromGallery();
                    }
                    else{
                        Toast.makeText(this, "Se requieren permisos de almacenamiento",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
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
        String id = etIdAutomovil.getText().toString();
        String modelo=etModelo.getText().toString();
        String numero_vin=etNumeroVin.getText().toString();
        String numero_chasis=etNumeroCha.getText().toString();
        String numero_motor=etNumeroMotor.getText().toString();
        Integer numero_asientos=Integer.parseInt(etNumeroAsi.getText().toString());
        Integer anio=Integer.parseInt(etAnio.getText().toString());
        Integer capacidad_asientos=Integer.parseInt(etCapacidadAsi.getText().toString());
        Double precio=Double.parseDouble(etPrecio.getText().toString());
        String URI_IMG = imageUri.toString();
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
            registro.put("URI_IMG", URI_IMG);
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
            etDescripcion.setText("");
            comboMarcas.setSelection(0);
            comboColores.setSelection(0);
            comboTipos.setSelection(0);
            imagen.setImageResource(R.drawable.subir);
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
        String URI_IMG = imageUri.toString();
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
            registro.put("URI_IMG",URI_IMG);
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
                etDescripcion.setText("");
                comboMarcas.setSelection(0);
                comboColores.setSelection(0);
                comboTipos.setSelection(0);
                imagen.setImageResource(R.drawable.subir);
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
            etDescripcion.setText("");
            comboMarcas.setSelection(0);
            comboColores.setSelection(0);
            comboTipos.setSelection(0);
            imagen.setImageResource(R.drawable.subir);

            if(cant==1){
                Toast.makeText(this,"Se borró el automovil Correctamente",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"No existe el automovil",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK){
            if (requestCode == IMAGE_PICK_GALLERY_CODE){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if(resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    imagen.setImageURI(resultUri);
                }
                else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}