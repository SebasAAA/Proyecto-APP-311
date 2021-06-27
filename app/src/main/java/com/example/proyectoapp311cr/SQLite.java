package com.example.proyectoapp311cr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLite extends SQLiteOpenHelper{


  public static final String Create_Table_Users =
            "CREATE TABLE  Users (cedula INTEGER PRIMARY KEY , nombre String, apellido1 String, apellido2 String, provincia String, canton String, distrito String, direccion String, email String, telefono INTEGER, password String, estado String, codigo String)";
    public static final String Create_Table_Incidents =
            "CREATE TABLE  Incidents (id INTEGER PRIMARY KEY AUTOINCREMENT , cedula INTEGER, nombre String, apellido1 String, apellido2 String, categoria String, empresa String, detalle String, estado String, provincia String, canton String, distrito String, direccion String, georeferencia String, fotografia1 String, fotografia2 String, fotografia3 String, fotografia4 String," +
                    "FOREIGN KEY (cedula) REFERENCES Users(cedula),FOREIGN KEY (nombre) REFERENCES Users(nombre), FOREIGN KEY (apellido1) REFERENCES Users(apellido1), FOREIGN KEY (apellido2) REFERENCES Users(apellido2))";

    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_Users);
        db.execSQL(Create_Table_Incidents);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Incidents");
        onCreate(db);
    }

    public long InsertUsers (int cedula, String nombre, String apellido1, String apellido2, String provincia, String canton, String distrito, String direccion, String email, int telefono, String password, String estado, String codigo){

        SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("cedula",cedula);
            values.put("nombre",nombre);
            values.put("apellido1",apellido1);
            values.put("apellido2",apellido2);
            values.put("provincia",provincia);
            values.put("canton",canton);
            values.put("distrito",distrito);
            values.put("direccion",direccion);
            values.put("email",email);
            values.put("telefono",telefono);
            values.put("password",password);
            values.put("estado",estado);
            values.put("codigo", codigo);


            long id = database.insert("Users",null,values);
            database.close();
            return id;
        }

   public void UpdateUser (int cedula, String nombre, String apellido1, String apellido2, String provincia, String canton, String distrito, String direccion, String email, int telefono, String password, String estado, String codigo ){


            ContentValues values = new ContentValues();
            values.put("cedula",cedula);
            values.put("nombre",nombre);
            values.put("apellido1",apellido1);
            values.put("apellido2",apellido2);
            values.put("provincia",provincia);
            values.put("canton",canton);
            values.put("distrito",distrito);
            values.put("direccion",direccion);
            values.put("email",email);
            values.put("telefono",telefono);
            values.put("password",password);
            values.put("estado", estado);
            values.put("codigo", codigo);

            SQLiteDatabase database = this.getWritableDatabase();
            database.update("Users",values,"cedula="+cedula,null);
            database.close();
        }

    public UsersData SelectUserByID(int cedula){
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM Users WHERE cedula ="+cedula ,null);
            UsersData users = new UsersData();

            if (cursor.moveToFirst()){

                users.cedula  = cursor.getInt(0);
                users.nombre = cursor.getString(1);
                users.apellido1  = cursor.getString(2);
                users.apellido2 = cursor.getString(3);
                users.provincia = cursor.getString(4);
                users.canton = cursor.getString(5);
                users.distrito = cursor.getString(6);
                users.direccion = cursor.getString(7);
                users.email = cursor.getString(8);
                users.telefono = cursor.getInt(9);
                users.password = cursor.getString(10);
                users.estado = cursor.getString(11);
                users.codigo = cursor.getString(12);

            }

            database.close();
            return users;

        }

    public boolean CodeValidator(String codigo) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Users WHERE codigo ='"+codigo+"'", null);
        UsersData users = new UsersData();
        boolean existe= false;

            if (cursor.moveToFirst()) {

                users.cedula = cursor.getInt(0);
                users.nombre = cursor.getString(1);
                users.apellido1 = cursor.getString(2);
                users.apellido2 = cursor.getString(3);
                users.provincia = cursor.getString(4);
                users.canton = cursor.getString(5);
                users.distrito = cursor.getString(6);
                users.direccion = cursor.getString(7);
                users.email = cursor.getString(8);
                users.telefono = cursor.getInt(9);
                users.password = cursor.getString(10);
                users.estado = cursor.getString(11);
                users.codigo = cursor.getString(12);

                database.close();
                UpdateUser( users.cedula, users.nombre, users.apellido1, users.apellido2, users.provincia, users.canton, users.distrito, users.direccion,
                        users.email, users.telefono, users.password, "Activo", "");

                existe= true;

            }

        return existe;
    }



    public void InsertIncident (int cedula, String nombre, String apellido1, String apellido2, String categoria, String empresa,String estado, String detalle, String provincia, String canton, String distrito, String direccion, String georeferencia, String fotografia1, String fotografia2, String fotografia3, String fotografia4 ){

        ContentValues values = new ContentValues();
        values.put("cedula",cedula);
        values.put("nombre",nombre);
        values.put("apellido1",apellido1);
        values.put("apellido2",apellido2);
        values.put("categoria",categoria);
        values.put("empresa",empresa);
        values.put("estado",estado);
        values.put("detalle",detalle);
        values.put("provincia",provincia);
        values.put("canton",canton);
        values.put("distrito",distrito);
        values.put("direccion",direccion);
        values.put("georeferencia",georeferencia);
        values.put("fotografia1",fotografia1);
        values.put("fotografia2",fotografia2);
        values.put("fotografia3",fotografia3);
        values.put("fotografia4",fotografia4);



        SQLiteDatabase database = this.getWritableDatabase();
        database.insert("Incidents",null,values);
        database.close();

    }
    public void UpdateIncident (int id, int cedula, String nombre, String apellido1, String apellido2, String categoria,  String empresa, String estado, String detalle, String provincia, String canton, String distrito, String direccion, String georeferencia, String fotografia1, String fotografia2, String fotografia3, String fotografia4 ){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cedula",cedula);
        values.put("nombre",nombre);
        values.put("apellido1",apellido1);
        values.put("apellido2",apellido2);
        values.put("categoria",categoria);
        values.put("empresa",empresa);
        values.put("estado",estado);
        values.put("detalle",detalle);
        values.put("provincia",provincia);
        values.put("canton",canton);
        values.put("distrito",distrito);
        values.put("direccion",direccion);
        values.put("georeferencia",georeferencia);
        values.put("fotografia1",fotografia1);
        values.put("fotografia2",fotografia2);
        values.put("fotografia3",fotografia3);
        values.put("fotografia4",fotografia4);



        database.update("Incidents",values,"id="+id,null);
        database.close();
    }

    public IncidentData GetIncidentbyUser (int cedula){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Incidents WHERE cedula ="+cedula,null);
        IncidentData incident = new IncidentData();
        while (cursor.moveToNext()){
            incident.id = cursor.getInt(0);
            incident.cedula = cursor.getInt(1);
            incident.nombre = cursor.getString(2);
            incident.apellido1 = cursor.getString(3);
            incident.apellido2 = cursor.getString(4);
            incident.categoria = cursor.getString(5);
            incident.empresa = cursor.getString(6);
            incident.detalle = cursor.getString(7);
            incident.estado = cursor.getString(8);
            incident.provincia = cursor.getString(9);
            incident.canton = cursor.getString(10);
            incident.distrito = cursor.getString(1);
            incident.direccion = cursor.getString(12);
            incident.georeferencia = cursor.getString(13);
            incident.fotografia1 = cursor.getString(14);
            incident.fotografia2 = cursor.getString(15);
            incident.fotografia3 = cursor.getString(16);
            incident.fotografia4 = cursor.getString(17);


        }
        cursor.close();
        database.close();
        return incident;
    }

    public IncidentData GetIncidentbyID (int id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Incidents WHERE id ="+id,null);
        IncidentData incident = new IncidentData();
        while (cursor.moveToNext()){
            incident.id = cursor.getInt(0);
            incident.cedula = cursor.getInt(1);
            incident.nombre = cursor.getString(2);
            incident.apellido1 = cursor.getString(3);
            incident.apellido2 = cursor.getString(4);
            incident.categoria = cursor.getString(5);
            incident.empresa = cursor.getString(6);
            incident.detalle = cursor.getString(7);
            incident.estado = cursor.getString(8);
            incident.provincia = cursor.getString(9);
            incident.canton = cursor.getString(10);
            incident.distrito = cursor.getString(1);
            incident.direccion = cursor.getString(12);
            incident.georeferencia = cursor.getString(13);
            incident.fotografia1 = cursor.getString(14);
            incident.fotografia2 = cursor.getString(15);
            incident.fotografia3 = cursor.getString(16);
            incident.fotografia4 = cursor.getString(17);


        }
        cursor.close();
        database.close();
        return incident;
    }

    public ArrayList<String> SelectIncidents (int cedula){
        ArrayList<String> IncidentList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT id, categoria, detalle, estado, georeferencia FROM Incidents WHERE cedula ="+cedula,null);
            while (cursor.moveToNext()){
            IncidentList.add(
                    " ID: "+cursor.getString(cursor.getColumnIndex("id"))+
                    " Categoria: "+cursor.getString(cursor.getColumnIndex("categoria"))+
                    " Detalle: "+cursor.getString(cursor.getColumnIndex("detalle"))+
                    " Estado: "+cursor.getString(cursor.getColumnIndex("estado"))+
                    " Georefencia: "+cursor.getString(cursor.getColumnIndex("georeferencia")));
        }
        cursor.close();
        database.close();
        return IncidentList;
    }


}
