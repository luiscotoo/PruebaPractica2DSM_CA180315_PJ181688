package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuCliente extends AppCompatActivity {

    private TextView tvUsuarioCliente;
    private String user,idusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        tvUsuarioCliente = (TextView) findViewById(R.id.tvUsuarioCliente);
        Bundle extras = getIntent().getExtras();
        tvUsuarioCliente.setText("Sesión iniciada en " + extras.getString("usuarioCliente"));
        user = extras.getString("usuarioCliente");
        idusuario = extras.getString("idusuario");

    }
    public void salir(View v){
        finish();
    }

    public void Vehiculos(View v){
        Intent llamar = new Intent(this, VistaVehiculos.class);
        llamar.putExtra("idusuario",idusuario);
        startActivity(llamar);
    }

    public void Favoritos(View v){
        Intent llamar = new Intent(this, VistaFavoritos.class);
        llamar.putExtra("user",user);
        startActivity(llamar);
    }


}