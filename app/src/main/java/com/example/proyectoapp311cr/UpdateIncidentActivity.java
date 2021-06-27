package com.example.proyectoapp311cr;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateIncidentActivity extends AppCompatActivity  implements FragmentInterface {

    SQLite database = new SQLite(this, "App311CRDB", null, 1);
    /*    TableRow row;*/
    String option;
    TableLayout table ;
    Spinner spinner ;
    IncidentData incident;
    EditText txt_IDIncidencia;
    int id, IDIncidencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_incident);
        table =  findViewById(R.id.tableLayout);
        spinner =  findViewById(R.id.spnEstado);
        txt_IDIncidencia = findViewById(R.id.txtID_Incident);

        Bundle extras = getIntent().getExtras();
        id =  extras.getInt("id");


        //Se crea un adapter para llenar el Spinner de Estados de las incidencias.
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.Incidents_Status, android.R.layout.simple_spinner_item);


            spinner.setAdapter(adapter);



//Método que carga los datos en la tabla dinámica al momento de crear la Activity.
        fillTable();

        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {


                        try {
                            //Se toma el ID de la incidencia digitado por el usuario y la opción elegida en el Spinner y se procede a la validación de la incidencia.
                            option =  spinner.getSelectedItem().toString();
                            IDIncidencia = Integer.parseInt(txt_IDIncidencia.getText().toString());
                            incident = new IncidentData();
                            incident = database.GetIncidentbyID(IDIncidencia);
                                //Si la incidencia ya finalizó, no se puede editar.
                            if (incident.estado.equals("Finalizado") || option.equals(incident.estado)){
                                Toast message = Toast.makeText(getApplicationContext(), "La incidencia ya ha finalizado o ha ingresado el mismo estado que ya posee", Toast.LENGTH_LONG);
                                message.show();
                            }else{
                                //Se procede a actualizar el estado de la incidencia.
                           database.UpdateIncident(incident.id, incident.cedula, incident.nombre, incident.apellido1, incident.apellido2, incident.categoria,
                                        incident.empresa, option, incident.detalle, incident.provincia, incident.canton, incident.distrito, incident.direccion,
                                        incident.georeferencia, incident.fotografia1, incident.fotografia2, incident.fotografia3, incident.fotografia4);

                                Toast message = Toast.makeText(getApplicationContext(), "Se ha actualizado su incidencia", Toast.LENGTH_LONG);
                                message.show();
                                //Se actualiza la tabla con los cambios realizados.
                                fillTable();
                            }



                        } catch (Exception e) {
                            Toast message = Toast.makeText(getApplicationContext(), "No ha ingresado una incidencia válida", Toast.LENGTH_LONG);
                            message.show();
                        }


                    }
                }


        );



    }



    public void fillTable( ) {

        //Borra los datos ya existentes, para no duplicarlos cada vez que se llame el método.
        table.removeAllViewsInLayout();
        //Variable para generar las filas del Table Layout.
        TableRow row;
        String incident;



        //Try-Catch para manejo de errores en caso de objetos nulos.
        try {
            //Se genera un array con los incidentes registrados por el usuario.
            ArrayList<String> incidents = database.SelectIncidents(id);

            //Se generan nuevas filas por cada línea del array y se añaden a la tabla.
            for(int i=0;i<incidents.size();i++){

                row = new TableRow(this);
                incident = incidents.get(i);
                TextView userRow = new TextView(this);
                userRow.setText(incident);
                row.addView(userRow);
                table.addView(row);



            }
        } catch (Exception e) {
            Toast message = Toast.makeText(getApplicationContext(), "Aún no ha registrado incidencias", Toast.LENGTH_LONG);
            message.show();
        }
    }

    @Override
    public void menu(int boton) {

        //Switch para realizar distintos Intent, dependiendo el botón clickeado.
        Intent intento;
        switch(boton){
            case 0:
                intento = new Intent(this,IncidentActivity.class);
                intento.putExtra("id", id);
                startActivity(intento);
                break;
            case 1:
                break;
            case 2:
                intento = new Intent(this,GraphicsActivity.class);
                intento.putExtra("id", id);
                startActivity(intento);
                break;
            case 3:
                intento = new Intent(this,MenuInicio.class);
                intento.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intento);
                break;
            default:
                break;
        }

    }

    /*
        row = new TableRow(this);
        row.setClickable(true);  //allows you to select a specific row

        row.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                TableRow tablerow = (TableRow) v;
                TextView sample = (TextView) tablerow.getChildAt(1);
                String result = sample.getText().toString();
            }
        });
*/



}
