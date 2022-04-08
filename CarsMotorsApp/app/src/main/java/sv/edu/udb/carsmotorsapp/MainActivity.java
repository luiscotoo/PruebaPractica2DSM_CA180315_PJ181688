package sv.edu.udb.carsmotorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void prueba(View v){
        try{
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"CarsMotorsDB", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
        }catch (Error e){
            Toast.makeText(this, "Error al crear la base de datos",Toast.LENGTH_SHORT).show();
        }

    }
}