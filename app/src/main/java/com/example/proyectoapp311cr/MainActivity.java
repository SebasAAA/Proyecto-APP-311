package com.example.proyectoapp311cr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle extras = getIntent().getExtras();
        final int id =  extras.getInt("id");

        //Bundle creado específicamente para el fragment, para que pueda pasas el ID cuando sea utilizado por el usuario.
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(bundle);

       //Se pasa el bundle al presionar los botones, para que el fragment no sufra un crash por objeto nulo al no recibir el bundle.
        Button btnIncidencias = (Button) findViewById(R.id.btnIncidencias);
        btnIncidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),IncidentActivity.class);
                intent.putExtra("id", id);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                MenuFragment fragment = new MenuFragment();
                fragment.setArguments(bundle);
                startActivity(intent);
            }
        });

        Button btnActualizacion = (Button) findViewById(R.id.btnActualizacion);
        btnActualizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),UpdateIncidentActivity.class);
                intent.putExtra("id", id);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                MenuFragment fragment = new MenuFragment();
                fragment.setArguments(bundle);
                startActivity(intent);
            }
        });

        Button btnGraficos = (Button) findViewById(R.id.btnGraficos);
        btnGraficos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),GraphicsActivity.class);
                intent.putExtra("id", id);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                MenuFragment fragment = new MenuFragment();
                fragment.setArguments(bundle);
                startActivity(intent);
            }
        });

        //Los métodos de Flag Activity permiten eliminar las activities anteriores en la pila de activities,
        // impidiendo al usuario volver a los activities anteriores una vez cierre la sesión.

        Button btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MenuInicio.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(intent,0);
            }
        });

    }
}
