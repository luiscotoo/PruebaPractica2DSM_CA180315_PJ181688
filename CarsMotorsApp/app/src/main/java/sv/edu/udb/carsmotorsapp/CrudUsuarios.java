package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CrudUsuarios extends AppCompatActivity {

    private EditText etIdUsuario, etNombre, etApellido, etEmail, etUser, etPassword, etTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_usuarios);

        etIdUsuario = (EditText) findViewById(R.id.etIdUsuario);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etTipo = (EditText) findViewById(R.id.etTipo);
    }

    public void Ingresar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String id = etIdUsuario.getText().toString();
        String nombre=etNombre.getText().toString();
        String apellido=etApellido.getText().toString();
        String email=etEmail.getText().toString();
        String user=etUser.getText().toString();
        String password=etPassword.getText().toString();
        String tipo=etTipo.getText().toString();
        ContentValues registro=new ContentValues();
        if(user.isEmpty()){
            Toast.makeText(this,"Ingrese un usuario",Toast.LENGTH_SHORT).show();
        }else{
            registro.put("nombres",nombre);
            registro.put("apellidos",apellido);
            registro.put("email",email);
            registro.put("user",user);
            registro.put("password",password);
            registro.put("tipo",tipo);
            bd.insert("usuario",null,registro);
            etNombre.setText("");
            etApellido.setText("");
            etEmail.setText("");
            etUser.setText("");
            etPassword.setText("");
            etTipo.setText("");
            Toast.makeText(this,"Se ingreso el usuario", Toast.LENGTH_SHORT).show();

        }
        bd.close();

    }
    public void Buscar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String user=etUser.getText().toString();
        if(user.isEmpty()){
            Toast.makeText(this,"Ingrese un usuario",Toast.LENGTH_SHORT).show();
        }else{
            Cursor fila=bd.rawQuery("select idusuario,nombres,apellidos,email,user,password,tipo from usuario where user='"+user+"'",null);
            if (fila.moveToFirst()){
                etIdUsuario.setText(fila.getString(0));
                etNombre.setText(fila.getString(1));
                etApellido.setText(fila.getString(2));
                etEmail.setText(fila.getString(3));
                etUser.setText(fila.getString(4));
                etPassword.setText(fila.getString(5));
                etTipo.setText(fila.getString(6));
                Toast.makeText(this,"Usuario Encontrado con exito",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"No existe dicho usuario",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void Actualizar(View V){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String id = etIdUsuario.getText().toString();
        String nombre=etNombre.getText().toString();
        String apellido=etApellido.getText().toString();
        String email=etEmail.getText().toString();
        String user=etUser.getText().toString();
        String password=etPassword.getText().toString();
        String tipo=etTipo.getText().toString();
        if(id.isEmpty()){
            Toast.makeText(this,"Ingrese un ID",Toast.LENGTH_SHORT).show();
        }else{
            ContentValues registro=new ContentValues();
            registro.put("nombres",nombre);
            registro.put("apellidos",apellido);
            registro.put("email",email);
            registro.put("user",user);
            registro.put("password",password);
            registro.put("tipo",tipo);
            int cant=bd.update("usuario", registro,"idusuario="+id,null);
            bd.close();
            if(cant==1){
                Toast.makeText(this,"Se actualizo el usuario",Toast.LENGTH_SHORT).show();
                etIdUsuario.setText("");
                etNombre.setText("");
                etApellido.setText("");
                etEmail.setText("");
                etUser.setText("");
                etPassword.setText("");
                etTipo.setText("");
            }
            else
                Toast.makeText(this,"No existe un usuario con ese ID",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void Borrar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String user=etUser.getText().toString();
        if(user.isEmpty()){
            Toast.makeText(this,"Ingrese un usuario",Toast.LENGTH_SHORT).show();
        }else{
            int cant=bd.delete("usuario","user='"+user+"'",null);
            bd.close();
            etNombre.setText("");
            etApellido.setText("");
            etEmail.setText("");
            etUser.setText("");
            etPassword.setText("");
            etTipo.setText("");
            if(cant==1){
                Toast.makeText(this,"Se borró el usuario Correctamente",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this,"No existe el usuario",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
}