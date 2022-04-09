package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText etUsuario,etContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etContra = (EditText) findViewById(R.id.etContra);
    }

    public void ingresar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String usuario = etUsuario.getText().toString();
        String contraseña = etContra.getText().toString();
        Cursor fila = bd.rawQuery("select user,password,tipo from usuario where user='" + usuario + "'AND password='" + contraseña+"'", null);
        if (fila.moveToFirst()) {
            if(fila.getString(2).equals("admin")){
                Intent intent = new Intent(this,MenuAdmin.class);
                intent.putExtra("usuarioAdmin",fila.getString(0));
                startActivity(intent);
            }else{
                Intent intent = new Intent(this,MenuCliente.class);
                intent.putExtra("usuarioCliente",fila.getString(0));
                startActivity(intent);
            }

        } else
            Toast.makeText(this, "Datos de ingreso erróneos. Verifique los datos.",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void IrRegistrarse(View v){
        Intent intent = new Intent(this,Registrarse.class);
        startActivity(intent);
    }

    public void salir(View v){
        finish();
    }
}