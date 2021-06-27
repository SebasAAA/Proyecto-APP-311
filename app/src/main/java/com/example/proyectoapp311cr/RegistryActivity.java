package com.example.proyectoapp311cr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.UUID;
import android.util.Patterns;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class RegistryActivity extends AppCompatActivity {

    EditText txtID, txtName, txtLastName1, txtLastName2, txtCanton, txtDistrito,
            txtDirection, txtEmail, txtTelephone, txtPassword;
    Button btnRegistry;
    SQLite database = new SQLite(this, "App311CRDB", null, 1);
    private Mail m;
    String verificationCode="";
    Spinner spnProvincia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        txtID = findViewById(R.id.txtID);
        txtName = findViewById(R.id.txtName);
        txtLastName1 = findViewById(R.id.txtLastName1);
        txtLastName2 = findViewById(R.id.txtLastName2);
        spnProvincia = findViewById(R.id.spnProvincia);
        txtCanton = findViewById(R.id.txtCanton);
        txtDistrito = findViewById(R.id.txtDistrito);
        txtDirection = findViewById(R.id.txtDirection);
        txtEmail = findViewById(R.id.txtEmail);
        txtTelephone = findViewById(R.id.txtTelephone);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegistry = findViewById(R.id.btnRegistry);


        //Se crea un adapter para llenar el Spinner de Provincias.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spn_Provincia, android.R.layout.simple_spinner_item);


        spnProvincia.setAdapter(adapter);


      /* ^([+]*[(]506[)][-\s][2|4|5|6|7|8]{1}[0-9]{3}[-\s]{1}[0-9]{4})$ */

      /*  m = new Mail("app.guayabita@gmail.com", "guayabitaulacit");*/


        btnRegistry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Patrones Regex personalizdos que validan la  veracidad de los datos de cédula y teléfono ingresados por el usuario.
                Pattern telephonePattern = Pattern.compile("(2|4|5|6|7|8)\\d{3}\\d{4}");
                Matcher telephoneMatcher = telephonePattern.matcher(txtTelephone.getText().toString());

                Pattern cedulaPattern = Pattern.compile("(\\d{9})");
                Matcher cedulaMatcher = cedulaPattern.matcher(txtID.getText().toString());


                //Condición que valida, además de la cédula y el teléfono, el Email, este último por medio de un Regex predefinido en Java.

                // Condición que verifica que no existan campos vacíos.
                if (txtID.getText().toString().equals("") || txtName.getText().toString().equals("")
                        || txtLastName1.getText().toString().equals("") || txtLastName2.getText().toString().equals("")
                        || spnProvincia.getSelectedItem().toString().equals("") || txtCanton.getText().toString().equals("")
                        || txtDistrito.getText().toString().equals("") || txtDirection.getText().toString().equals("")
                        || txtEmail.getText().toString().equals("") || txtTelephone.getText().toString().equals("")
                        || txtPassword.getText().toString().equals("")) {

                    Toast message = Toast.makeText(getApplicationContext(), "Complete todos los campos", Toast.LENGTH_LONG);
                    message.show();
                } else {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches() && telephoneMatcher.matches() && cedulaMatcher.matches()) {
                        //La clase VerificationCode genera un String aleatorio que el usuario debe validar la primera vez que inicie sesión.
                        verificationCode = VerificationCode();
                        long id = database.InsertUsers(Integer.parseInt(txtID.getText().toString()), txtName.getText().toString(),
                                txtLastName1.getText().toString(), txtLastName2.getText().toString(), spnProvincia.getSelectedItem().toString(),
                                txtCanton.getText().toString(), txtDistrito.getText().toString(), txtDirection.getText().toString(),
                                txtEmail.getText().toString(), Integer.parseInt(txtTelephone.getText().toString()),
                                txtPassword.getText().toString(), "Inactivo", verificationCode);

                        if (id == -1) {
                            Toast message = Toast.makeText(getApplicationContext(), "Esta cédula ya ha sido registrada", Toast.LENGTH_LONG);
                            message.show();
                        } else {
                            Toast message = Toast.makeText(getApplicationContext(), verificationCode, Toast.LENGTH_LONG);
                            message.show();
                        }
                        /*       sendEmail(view);*/
                    } else {

                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()) {
                            Toast message = Toast.makeText(getApplicationContext(), "Email incorrecto", Toast.LENGTH_LONG);
                            message.show();
                        }
                        if (!cedulaMatcher.matches()) {
                            Toast message = Toast.makeText(getApplicationContext(), "Cédula incorrecta", Toast.LENGTH_LONG);
                            message.show();
                        }
                        if (!telephoneMatcher.matches()) {
                            Toast message = Toast.makeText(getApplicationContext(), "Teléfono incorrecto", Toast.LENGTH_LONG);
                            message.show();
                        }

                    }


                }


            }



        });


    }

    public void sendEmail(View view) {



        try {
            String[] toArr = {"app.guayabita@gmail.com"};
            m.setTo(toArr); // load array to setTo function
            m.setFrom("app.guayabita@gmail.com"); // who is sending the email
            m.setSubject("Codigo de verificacion;");
            m.setBody("Codigo de verificacion: "+verificationCode);

            if (m.send()) {
                // success
                Toast.makeText(RegistryActivity.this, "Email enviado correctamente.", Toast.LENGTH_LONG).show();
            } else {
                // failure
                Toast.makeText(RegistryActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // some other problem
            Toast.makeText(RegistryActivity.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }


    public String VerificationCode() {
        //La primera función genera un String al azar, la segunda, corta este String en la longitud insertada en el método
        //Si el String posee menos caracteres que el máximo permitido, se cierra la aplicación.

        String code = UUID.randomUUID().toString();
        String finalCode = code.substring(0, 4);
        return finalCode;
    }




}

