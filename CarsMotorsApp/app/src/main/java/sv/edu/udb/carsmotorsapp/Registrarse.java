package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registrarse extends AppCompatActivity {

    private EditText etNombreRegister, etApellidoRegister, etEmailRegister,etUsuarioRegister,etContraRegister, etContraConfRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        etNombreRegister = (EditText) findViewById(R.id.etNombreRegister);
        etApellidoRegister = (EditText) findViewById(R.id.etApellidoRegister);
        etEmailRegister = (EditText) findViewById(R.id.etEmailRegister);
        etUsuarioRegister = (EditText) findViewById(R.id.etUsuarioRegister);
        etContraRegister = (EditText) findViewById(R.id.etContraRegister);
        etContraConfRegister = (EditText) findViewById(R.id.etContraConfRegister);
    }
    public void registrarse(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nombre = etNombreRegister.getText().toString();
        String apellido = etApellidoRegister.getText().toString();
        String email = etEmailRegister.getText().toString();
        String usuario = etUsuarioRegister.getText().toString();
        String contraseña = etContraRegister.getText().toString();
        String contraseñaconf = etContraConfRegister.getText().toString();
        String tipo = "cliente";

        if(nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || usuario.isEmpty() || contraseña.isEmpty() || contraseñaconf.isEmpty()){
            if(nombre.isEmpty()){
                etNombreRegister.setError("Ingrese un nombre");
            }
            if(apellido.isEmpty()){
                etApellidoRegister.setError("Ingrese un apellido");
            }
            if(email.isEmpty()){
                etEmailRegister.setError("Ingrese un email");
            }
            if(usuario.isEmpty()){
                etUsuarioRegister.setError("Ingrese un usuario");
            }
            if(contraseña.isEmpty()){
                etContraRegister.setError("Ingrese una contraseña");
            }
            if(contraseñaconf.isEmpty()){
                etContraConfRegister.setError("Confirme la contraseña");
            }
        }else{

            if(!contraseña.equals(contraseñaconf)){
                etContraConfRegister.setError("Contraseña no coincide");
            }else{
                try{
                    Cursor fila=bd.rawQuery("select user from usuario where user='"+usuario+"'",null);
                    if(fila.moveToFirst()){
                        Toast.makeText(this,"Ese usuario ya existe, intente con otro.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ContentValues registro=new ContentValues();
                        registro.put("nombres",nombre);
                        registro.put("apellidos",apellido);
                        registro.put("email",email);
                        registro.put("user",usuario);
                        registro.put("password",contraseña);
                        registro.put("tipo",tipo);
                        bd.insert("usuario",null,registro);
                        etNombreRegister.setText("");
                        etApellidoRegister.setText("");
                        etEmailRegister.setText("");
                        etUsuarioRegister.setText("");
                        etContraRegister.setText("");
                        etContraConfRegister.setText("");
                        Toast.makeText(this,"Se ha registrado exitosamente.", Toast.LENGTH_SHORT).show();
                        (new Handler()).postDelayed(this::IrLogin, 3000);
                    }
                }catch (Error e){
                    Toast.makeText(this,"Error al inciar sesión.", Toast.LENGTH_SHORT).show();
                }
            }

        }
        bd.close();
    }
    public void IrLogin(){
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    public void salir(View v){
        finish();
    }
}