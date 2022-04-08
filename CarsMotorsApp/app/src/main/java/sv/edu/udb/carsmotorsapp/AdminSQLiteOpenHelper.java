package sv.edu.udb.carsmotorsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String name,
                                 SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table marcas(idmarcas integer primary key autoincrement, nombre VARCHAR(45))");
        db.execSQL("create table colores(idcolores integer primary key autoincrement, descripcion VARCHAR(45))");
        db.execSQL("create table tipo_automovil(idtipoautomovil integer primary key autoincrement, descripcion VARCHAR(45))");
        db.execSQL("create table usuario(idusuario integer primary key autoincrement,nombres VARCHAR(45),apellidos VARCHAR(45), email VARCHAR(45), user VARCHAR(45) UNIQUE," +
                " password VARCHAR(45),tipo VARCHAR(45))");
        db.execSQL("create table automovil(idautomovil integer primary key autoincrement,modelo VARCHAR(45), numero_vin VARCHAR(45), numero_chasis VARCHAR(45), numero_motor VARCHAR(45)," +
                "numero_asientos integer, anio year,capacidad_asientos int,precio decimal(10,2), URI_IMG VARCHAR(45), descripcion VARCHAR(45),idmarcas integer,idtipoautomovil integer," +
                "idcolores integer,FOREIGN KEY(idmarcas) REFERENCES marcas(idmarcas) ON UPDATE CASCADE ON DELETE CASCADE," +
                "FOREIGN KEY(idtipoautomovil) REFERENCES tipo_automovil(idtipoautomovil) ON UPDATE CASCADE ON DELETE CASCADE," +
                "FOREIGN KEY(idcolores) REFERENCES colores(idcolores) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("create table favoritos_automovil(idfavoritosautomovil integer primary key autoincrement,idusuario integer,idfavoritoautomovil integer,fecha_agregado timestamp," +
                "FOREIGN KEY(idusuario) REFERENCES usuario(idusuario) ON UPDATE CASCADE ON DELETE CASCADE," +
                "FOREIGN KEY(idfavoritoautomovil) REFERENCES automovil(idautomovil) ON UPDATE CASCADE ON DELETE CASCADE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}