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

public class CrudMarcas extends AppCompatActivity {

    private EditText etMarca, etIdMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_marcas);

        etMarca = (EditText) findViewById(R.id.etMarca);
        etIdMarca = (EditText) findViewById(R.id.etIdMarca);
    }
    public void Ingresar(View v){
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String id = etIdMarca.getText().toString();
        String Nombre=etMarca.getText().toString();
        ContentValues registro=new ContentValues();
        if(Nombre.isEmpty()){
            Toast.makeText(this,"Ingrese un nombre",Toast.LENGTH_SHORT).show();
        }else{
            registro.put("nombre",Nombre);
            bd.insert("marcas",null,registro);
            etMarca.setText("");
            etIdMarca.setText("");
            Toast.makeText(this,"Se ingreso la Marca", Toast.LENGTH_SHORT).show();

        }
        bd.close();

    }
    public void Buscar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String Nombre=etMarca.getText().toString();
        if(Nombre.isEmpty()){
            Toast.makeText(this,"Ingrese un nombre",Toast.LENGTH_SHORT).show();
        }else{
            Cursor fila=bd.rawQuery("select idmarcas,nombre from marcas where nombre='"+Nombre+"'",null);
            if (fila.moveToFirst()){
                etIdMarca.setText(fila.getString(0));
                etMarca.setText(fila.getString(1));
                Toast.makeText(this,"Marca Encontrada con exito",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"No existe dicha marca",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void Actualizar(View V){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String id = etIdMarca.getText().toString();
        String Nombre=etMarca.getText().toString();
        if(id.isEmpty()){
            Toast.makeText(this,"Ingrese un ID",Toast.LENGTH_SHORT).show();
        }else{
            ContentValues registro=new ContentValues();
            registro.put("idmarcas",id);
            registro.put("nombre",Nombre);
            int cant=bd.update("marcas", registro,"idmarcas="+id,null);
            bd.close();
            if(cant==1){
                Toast.makeText(this,"Se actualizo Marca",Toast.LENGTH_SHORT).show();
                etMarca.setText("");
                etIdMarca.setText("");
            }
            else
                Toast.makeText(this,"No existe una marca con ese ID",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void Borrar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String Nombre=etMarca.getText().toString();
        if(Nombre.isEmpty()){
            Toast.makeText(this,"Ingrese un nombre",Toast.LENGTH_SHORT).show();
        }else{
            int cant=bd.delete("marcas","nombre='"+Nombre+"'",null);
            bd.close();
            etMarca.setText("");
            etIdMarca.setText("");
            if(cant==1){
                Toast.makeText(this,"Se borra la marca Correctamente",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"No existe la marca",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
}