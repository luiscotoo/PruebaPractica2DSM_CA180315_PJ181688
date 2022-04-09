package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuCliente extends AppCompatActivity {

    private TextView tvUsuarioCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        tvUsuarioCliente = (TextView) findViewById(R.id.tvUsuarioCliente);
        Bundle extras = getIntent().getExtras();
        tvUsuarioCliente.setText("Sesión iniciada en " + extras.getString("usuarioCliente"));
    }
    public void salir(View v){
        finish();
    }

    public void Vehiculos(View v){
        Intent llamar = new Intent(this, VistaVehiculos.class);
        startActivity(llamar);
    }


}