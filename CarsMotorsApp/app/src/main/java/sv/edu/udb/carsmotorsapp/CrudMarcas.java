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

    private EditText etMarca;
    private TextView tvCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_marcas);

        etMarca = (EditText) findViewById(R.id.etMarca);
        tvCodigo=(TextView) findViewById(R.id.tvCodigo);
    }
    public void Ingresar(View v){
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String Nombre=etMarca.getText().toString();
        ContentValues registro=new ContentValues();
        registro.put("nombre",Nombre);
        bd.insert("marcas",null,registro);
        bd.close();
        etMarca.setText("");
        tvCodigo.setText("");
        Toast.makeText(this,"Se ingreso la Marca", Toast.LENGTH_SHORT).show();
    }
    public void Buscar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String Nombre=etMarca.getText().toString();
        Cursor fila=bd.rawQuery("select idmarcas,nombre from marcas where nombre='"+Nombre+"'",null);
        if (fila.moveToFirst()){
            tvCodigo.setText("ID: "+fila.getString(0));
            etMarca.setText(fila.getString(1));
            Toast.makeText(this,"Marca Encontrada con exito",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"No existe dicha marca",Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void Actualizar(View V){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String Nombre=etMarca.getText().toString();
        ContentValues registro=new ContentValues();
        registro.put("nombre",Nombre);
        int cant=bd.update("marcas", registro,"nombre="+Nombre,null);
        bd.close();
        if(cant==1){
            Toast.makeText(this,"Se actualizo Marca",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"No existe la marca ingresada",Toast.LENGTH_SHORT).show();
    }

    public void Borrar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String Nombre=etMarca.getText().toString();
        int cant=bd.delete("marcas","nombre='"+Nombre+"'",null);
        bd.close();
        etMarca.setText("");
        tvCodigo.setText("");
        if(cant==1){
            Toast.makeText(this,"Se borra la marca Correctamente",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"No existe la marca",Toast.LENGTH_SHORT).show();
    }
}