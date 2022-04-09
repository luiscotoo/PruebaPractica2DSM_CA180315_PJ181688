package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuAdmin extends AppCompatActivity {

    private TextView tvUsuarioAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        tvUsuarioAdmin = (TextView) findViewById(R.id.tvUsuarioAdmin);
        Bundle extras = getIntent().getExtras();
        tvUsuarioAdmin.setText("Sesión iniciada en " + extras.getString("usuarioAdmin"));
    }
    public void salir(View v){
        finish();
    }
}