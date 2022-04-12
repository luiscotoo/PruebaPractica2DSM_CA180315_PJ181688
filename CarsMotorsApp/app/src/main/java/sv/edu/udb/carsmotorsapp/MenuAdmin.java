package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuAdmin extends AppCompatActivity {

    private TextView tvUsuarioAdmin;
    private String user,idusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        tvUsuarioAdmin = (TextView) findViewById(R.id.tvUsuarioAdmin);
        Bundle extras = getIntent().getExtras();
        tvUsuarioAdmin.setText("Bienvenido " + extras.getString("usuarioAdmin"));
        user = extras.getString("usuarioAdmin");
    }
    public void salir(View v){
        finish();
    }

    public void CrudMarcas(View v){
        Intent llamar = new Intent(this, CrudMarcas.class);
        llamar.putExtra("user",user);
        startActivity(llamar);
    }

    public void CrudUsuarios(View v){
        Intent llamar = new Intent(this, CrudUsuarios.class);
        llamar.putExtra("user",user);
        startActivity(llamar);
    }

    public void CrudTipoAuto(View v){
        Intent llamar = new Intent(this, CrudTipoAuto.class);
        llamar.putExtra("user",user);
        startActivity(llamar);
    }

    public void CrudColores(View v){
        Intent llamar = new Intent(this, CrudColores.class);
        llamar.putExtra("user",user);
        startActivity(llamar);
    }
    public void CrudAutomovil(View v){
        Intent llamar = new Intent(this, CrudAutomovil.class);
        llamar.putExtra("user",user);
        startActivity(llamar);
    }
}