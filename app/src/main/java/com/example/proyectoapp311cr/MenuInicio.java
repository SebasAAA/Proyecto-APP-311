package com.example.proyectoapp311cr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuInicio extends AppCompatActivity {

EditText txtusuario, txtpass, txtvalidar;
UsersData user;

SQLite database = new SQLite(this,"App311CRDB",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);

        user = new UsersData();
        txtusuario = findViewById(R.id.txtUsuario);
        txtpass = findViewById(R.id.txtPasswordLogin);
        txtvalidar= findViewById(R.id.txtValidar);


        Button btnInicar = (Button) findViewById(R.id.btnIniciar);
        btnInicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                     //Try-Catch para manejo de errores en caso de objetos nulos.
                try {
                    user = database.SelectUserByID(Integer.parseInt(txtusuario.getText().toString()));
                    if(user.getPassword().equals(txtpass.getText().toString())){
                        if (user.getEstado().equals("Inactivo")){
                            Toast message = Toast.makeText(getApplicationContext(), "Debe activar su cuenta, con la clave suministrada vía correo electrónico", Toast.LENGTH_LONG);
                            message.show();
                        }else {
                            Intent intent = new Intent(v.getContext(),MainActivity.class);
                            intent.putExtra("id", Integer.parseInt(txtusuario.getText().toString()));
                            startActivity(intent);
                        }

                    }else{
                        Toast message = Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG);
                        message.show();
                    }

                }catch (Exception e) {
                    Toast message = Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG);
                    message.show();
                }


            }
        });

        Button btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),RegistryActivity.class);
                startActivityForResult(intent,0);
            }
        });

        Button btnValidar = (Button) findViewById(R.id.btnValidar);
        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast message;

                //Por medio de este booleano se determinará si la cuenta pasa a estar en modo activada.
                boolean validado= false;
                validado= database.CodeValidator(txtvalidar.getText().toString());

               if (validado){
                   message = Toast.makeText(getApplicationContext(), "Su usuario se encuentra activo, puede iniciar sesión", Toast.LENGTH_LONG);
                   message.show();
                }else {
                   message = Toast.makeText(getApplicationContext(), "Insertó un código no válido", Toast.LENGTH_LONG);
                   message.show();
               }

            }
        });

    }
}
