package com.example.proyectoapp311cr;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;


public class GraphicsActivity extends AppCompatActivity implements FragmentInterface{

    SQLite database = new SQLite(this, "App311CRDB", null, 1);
    double  puentes, carreteras, servicios_publicos, servicios_privados, san_jose, alajuela, cartago, heredia, guanacaste, puntarenas, limon;
    int contador, id, option;
    TextView txtLeyenda;

    Button btnProvincia, btnCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);

        //Obtiene el ID necesario para consultar las incidencias del usuario.
        Bundle extras = getIntent().getExtras();
        id =  extras.getInt("id");

        //Genera el objeto del gráfico.
        GraphView graph = findViewById(R.id.graph);

        btnProvincia = findViewById(R.id.btn_provincia);
        btnCategoria = findViewById(R.id.btn_categoria);
        txtLeyenda = findViewById(R.id.txt_leyenda);


   //Listeners para cada gráfico, obtienen las incidencias del usuario y generan un gráfico de barras con los datos.

        btnCategoria.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

        try {
                    txtLeyenda.setText(R.string.leyenda_categoria);
                    GraphView graph = findViewById(R.id.graph);
                    graph.removeAllSeries();
                    option = 1;
                    GetIncidentbyUser(id,1);
                    BarGraphSeries<DataPoint> series = new BarGraphSeries <> ( CreateGraph());
                    graph.addSeries(series);


                } catch (Exception e) {
                    Toast message = Toast.makeText(getApplicationContext(), "No ha registrado incidencias", Toast.LENGTH_LONG);
                    message.show();
                }
            }
        });

        btnProvincia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

        try {
                    txtLeyenda.setText(R.string.leyenda_provincia);
                    GraphView graph = findViewById(R.id.graph);
                    graph.removeAllSeries();
                    option = 2;
                    GetIncidentbyUser(id,2);
                    BarGraphSeries<DataPoint> series = new BarGraphSeries <> ( CreateGraph());
                    graph.addSeries(series);



                } catch (Exception e) {
                    Toast message = Toast.makeText(getApplicationContext(), "No ha registrado incidencias", Toast.LENGTH_LONG);
                    message.show();
                }

            }
        });




        /*                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(0, 1),
                            new DataPoint(1, 5),
                            new DataPoint(2, 3)
                    });
                    graph.addSeries(series);*/



    }

    //Método que consulta la base de datos por medio de un cursor y suma en las variables cada vez que encuentra un String igual a los validados por el switch.
    public void GetIncidentbyUser (int cedula, int opcion) {
        SQLiteDatabase base = database.getReadableDatabase();
        Cursor cursor = base.rawQuery("SELECT * FROM Incidents WHERE cedula =" + cedula, null);
        String categoria = "", provincia = "";

        puentes = 0;
        carreteras = 0;
        servicios_privados = 0;
        servicios_publicos = 0;
        san_jose = 0;
        alajuela = 0;
        cartago = 0;
        heredia = 0;
        guanacaste = 0;
        puntarenas = 0;
        limon = 0;


        if (opcion == 1) {
            while (cursor.moveToNext()) {


                categoria = cursor.getString(5);


                switch (categoria) {
                    case "Puente":

                        puentes += 1;
                        break;

                    case "Carretera":

                        carreteras += 1;
                        break;

                    case "Servicio Público":

                        servicios_publicos += 1;
                        break;

                    case "Servicio Privado":

                        servicios_privados += 1;
                        break;

                    default:
                        break;
                }


            }


        } else {

            while (cursor.moveToNext()) {
                provincia = cursor.getString(9);


                switch (provincia) {
                    case "San José":

                        san_jose += 1;
                        break;

                    case "Alajuela":

                        alajuela += 1;
                        break;

                    case "Cartago":

                        cartago += 1;
                        break;

                    case "Heredia":

                        heredia += 1;
                        break;

                    case "Guanacaste":

                        guanacaste += 1;
                        break;


                    case "Puntarenas":

                        puntarenas += 1;
                        break;


                    case "Limón":

                        limon += 1;
                        break;


                    default:
                        break;
                }



            }
        }

        cursor.close();
        database.close();

    }

//Método propio del API de gráficos que genera un array donde se almacenan los datos del eje "x" y "y" del gráfico, por medio de una estructura for.
//Retorna el array para que pueda ser implementado por el gráfico.
    private DataPoint[] CreateGraph() {


        DataPoint[]  values;

        if (option==1){
            contador=5;
            values = new DataPoint[contador];
            for (int i=0; i<contador; i++) {
                double x =0;
                double y =0;


                switch (i) {

                    case 1:
                        x = i;
                        y = puentes;
                        break;
                    case 2:
                        x = i;
                        y = carreteras;
                        break;
                    case 3:
                        x = i;
                        y = servicios_publicos;
                        break;
                    case 4:
                        x = i;
                        y = servicios_privados;
                        break;
                    default:
                        break;
                }


                DataPoint dataPoint = new DataPoint(x, y);
                values[i] = dataPoint;
            }

        }else{
            contador=8;

            values = new DataPoint[contador];
            for (int i=0; i<contador; i++) {
                double x =0;
                double y =0;


                switch (i){

                    case 1:
                        x = i;
                        y = san_jose;
                        break;
                    case 2:
                        x = i;
                        y = alajuela;
                        break;
                    case 3:
                        x = i;
                        y = cartago;
                        break;
                    case 4:
                        x = i;
                        y = heredia;
                        break;
                    case 5:
                        x = i;
                        y = guanacaste;
                        break;
                    case 6:
                        x = i;
                        y = puntarenas;
                        break;
                    case 7:
                        x = i;
                        y = limon;
                        break;
                    default:
                        break;
                }

                DataPoint dataPoint = new DataPoint(x, y);
                values[i] = dataPoint;
            }
        }


        return values;
    }





    @Override
    public void menu(int boton) {

        //Case para realizar distintos Intent, dependiendo el botón clickeado.
        Intent intento;
        switch(boton){
            case 0:
                intento = new Intent(this,IncidentActivity.class);
                intento.putExtra("id", id);
                startActivity(intento);
                break;
            case 1:
                intento = new Intent(this,UpdateIncidentActivity.class);
                intento.putExtra("id", id);
                startActivity(intento);
                break;
            case 2:
                break;
            case 3:
                intento = new Intent(this,MenuInicio.class);
                intento.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intento);
                finish();

                break;
            default:
                break;
        }

    }
}
