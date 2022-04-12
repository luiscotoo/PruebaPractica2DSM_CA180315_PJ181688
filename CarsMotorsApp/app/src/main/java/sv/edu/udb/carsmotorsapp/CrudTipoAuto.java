package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CrudTipoAuto extends AppCompatActivity {

    private EditText etTipoAuto, etIdTipo;
    String user;
    private TextView TipoA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_tipo_auto);
        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        TipoA = (TextView) findViewById(R.id.tipoA);
        TipoA.setText("CRUD Tipo de Auto - "+user);
        etIdTipo = (EditText) findViewById(R.id.etIdTipo);
        etTipoAuto = (EditText) findViewById(R.id.etTipoAuto);
    }

    public void Ingresar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String id = etIdTipo.getText().toString();
        String descripcion=etTipoAuto.getText().toString();
        ContentValues registro=new ContentValues();
        if(descripcion.isEmpty()){
            Toast.makeText(this,"Ingrese un nombre",Toast.LENGTH_SHORT).show();
        }else{
            registro.put("descripcion",descripcion);
            bd.insert("tipo_automovil",null,registro);
            etTipoAuto.setText("");
            etIdTipo.setText("");
            Toast.makeText(this,"Se ingreso el tipo de auto", Toast.LENGTH_SHORT).show();

        }
        bd.close();

    }
    public void Buscar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String descripcion=etTipoAuto.getText().toString();
        if(descripcion.isEmpty()){
            Toast.makeText(this,"Ingrese un nombre",Toast.LENGTH_SHORT).show();
        }else{
            Cursor fila=bd.rawQuery("select idtipoautomovil,descripcion from tipo_automovil where descripcion='"+descripcion+"'",null);
            if (fila.moveToFirst()){
                etIdTipo.setText(fila.getString(0));
                etTipoAuto.setText(fila.getString(1));
                Toast.makeText(this,"Tipo Encontrado con éxito",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"No existe dicho tipo",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void Actualizar(View V){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String id = etIdTipo.getText().toString();
        String descripcion=etTipoAuto.getText().toString();
        if(id.isEmpty()){
            Toast.makeText(this,"Ingrese un ID",Toast.LENGTH_SHORT).show();
        }else{
            ContentValues registro=new ContentValues();
            registro.put("idtipoautomovil",id);
            registro.put("descripcion",descripcion);
            int cant=bd.update("tipo_automovil", registro,"idtipoautomovil="+id,null);
            bd.close();
            if(cant==1){
                Toast.makeText(this,"Se actualizo el tipo",Toast.LENGTH_SHORT).show();
                etTipoAuto.setText("");
                etIdTipo.setText("");
            }
            else
                Toast.makeText(this,"No existe un tipo con ese ID",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void Borrar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "CarsMotorsDB", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String descripcion = etTipoAuto.getText().toString();
        if (descripcion.isEmpty()) {
            Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show();
        } else {
            int cant = bd.delete("tipo_automovil", "descripcion='" + descripcion + "'", null);
            bd.close();
            etTipoAuto.setText("");
            etIdTipo.setText("");
            if (cant == 1) {
                Toast.makeText(this, "Se borró el tipo Correctamente", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "No existe el tipo", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
}