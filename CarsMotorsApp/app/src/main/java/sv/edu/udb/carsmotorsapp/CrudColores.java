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

public class CrudColores extends AppCompatActivity {

    private EditText etColor, etIdColor;
    String user;
    private TextView Colores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_colores);
        Bundle extras = getIntent().getExtras();
        user = extras.getString("user");
        Colores= (TextView) findViewById(R.id.Colores);
        Colores.setText("CRUD Colores - "+user);

        etColor = (EditText) findViewById(R.id.etColor);
        etIdColor = (EditText) findViewById(R.id.etIdColor);
    }

    public void Ingresar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String id = etIdColor.getText().toString();
        String descripcion=etColor.getText().toString();
        if (descripcion.isEmpty()) {
            etColor.setError("Ingrese un Color");
        } else {
        ContentValues registro=new ContentValues();
        if(descripcion.isEmpty()){
            Toast.makeText(this,"Ingrese un color",Toast.LENGTH_SHORT).show();
        }else{
            registro.put("descripcion",descripcion);
            bd.insert("colores",null,registro);
            etColor.setText("");
            etIdColor.setText("");
            Toast.makeText(this,"Se ingreso el color", Toast.LENGTH_SHORT).show();

        }
        bd.close();
    }
    }
    public void Buscar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "CarsMotorsDB", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String descripcion = etColor.getText().toString();
        if (descripcion.isEmpty()) {
            etColor.setError("Ingrese Un color");
        } else {
            if (descripcion.isEmpty()) {
                Toast.makeText(this, "Ingrese un color", Toast.LENGTH_SHORT).show();
            } else {
                Cursor fila = bd.rawQuery("select idcolores,descripcion from colores where descripcion='" + descripcion + "'", null);
                if (fila.moveToFirst()) {
                    etIdColor.setText(fila.getString(0));
                    etColor.setText(fila.getString(1));
                    Toast.makeText(this, "Color Encontrado con exito", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "No existe dicho color", Toast.LENGTH_SHORT).show();
            }
            bd.close();
        }
    }

    public void Actualizar(View V) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "CarsMotorsDB", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String id = etIdColor.getText().toString();
        String descripcion = etColor.getText().toString();
        if (id.isEmpty()) {
            etIdColor.setError("Ingrese ID");
        } else {
            if (id.isEmpty()) {
                Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show();
            } else {
                ContentValues registro = new ContentValues();
                registro.put("idcolores", id);
                registro.put("descripcion", descripcion);
                int cant = bd.update("colores", registro, "idcolores=" + id, null);
                bd.close();
                if (cant == 1) {
                    Toast.makeText(this, "Se actualizo el color", Toast.LENGTH_SHORT).show();
                    etColor.setText("");
                    etIdColor.setText("");
                } else
                    Toast.makeText(this, "No existe un color con ese ID", Toast.LENGTH_SHORT).show();
            }
            bd.close();
        }
    }

    public void Borrar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "CarsMotorsDB", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String descripcion = etColor.getText().toString();
        if (descripcion.isEmpty()) {
            etColor.setError("Ingrese Un color");
        } else {
            if (descripcion.isEmpty()) {
                Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show();
            } else {
                int cant = bd.delete("colores", "descripcion='" + descripcion + "'", null);
                bd.close();
                etColor.setText("");
                etIdColor.setText("");
                if (cant == 1) {
                    Toast.makeText(this, "Se borr?? el color Correctamente", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "No existe el color", Toast.LENGTH_SHORT).show();
            }
            bd.close();
        }
    }
}