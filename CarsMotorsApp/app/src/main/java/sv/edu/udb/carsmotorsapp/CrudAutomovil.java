package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class CrudAutomovil extends AppCompatActivity {

    private EditText etModelo, etNumeroVin, etNumeroCha, etNumeroMotor, etNumeroAsi,etAnio,etCapacidadAsi,etPrecio,etImagen,etDescripcion,etIdMarcaAuto, etIdTipoAuto, etColor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_automovil);
    }
}