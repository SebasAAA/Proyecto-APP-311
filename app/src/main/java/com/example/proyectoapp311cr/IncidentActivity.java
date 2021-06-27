package com.example.proyectoapp311cr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.location.Location;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import android.net.Uri;
import android.os.Environment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;


public class IncidentActivity extends AppCompatActivity implements FragmentInterface {

    //private Camera CameraStatus;
    private static final String TAG = "";

    //Variables para determinar si el archivo es una imagen o un vídeo.
    static final int REQUEST_IMAGE_CAPTURE = 1;

    //Variable para asignar la ruta de la foto capturada y obtener el String de dirección.
    String currentPhotoPath, locationString="", StringPhoto1="", StringPhoto2="", StringPhoto3="", StringPhoto4="";

    //Contador para asignar las fotos a las imgView.
    int contador = 1;

    //imgView para las fotos.
    ImageView photo1, photo2, photo3, photo4;

    EditText txtEmpresa, txtDescripcionIncidencia, txtCantonIncident, txtDistritoIncident, txtDirectionIncident ;

    //Instancia de la base de datos.
    SQLite database = new SQLite(this, "App311CRDB", null, 1);

    //Variable para obtener la localización del dispositivo.
    private FusedLocationProviderClient fusedLocationClient;

    Spinner spnProvinciaIncidente, spnEstadoIncidente, spnTipoIncidente;

    UsersData user;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);

        photo1 = findViewById(R.id.imgPhoto1);
        photo2 = findViewById(R.id.imgPhoto2);
        photo3 = findViewById(R.id.imgPhoto3);
        photo4 = findViewById(R.id.imgPhoto4);


        txtEmpresa = findViewById(R.id.txtEmpresa);

        txtDescripcionIncidencia = findViewById(R.id.txtDescripcionIncidencia);
        spnProvinciaIncidente = findViewById(R.id.spn_Incident_Provincia);
        spnEstadoIncidente = findViewById(R.id.spn_Incident_Status);
        spnTipoIncidente = findViewById(R.id.spn_Incident_Type);
        txtCantonIncident = findViewById(R.id.txtCantonIncident);
        txtDistritoIncident = findViewById(R.id.txtDistritoIncident);
        txtDirectionIncident = findViewById(R.id.txtDirectionIncident);


        Bundle extras = getIntent().getExtras();
        id =  extras.getInt("id");

        ArrayAdapter<CharSequence> adapterProvincia = ArrayAdapter.createFromResource(this,
                R.array.spn_Provincia, android.R.layout.simple_spinner_item);


        ArrayAdapter<CharSequence> adapterIncidentType = ArrayAdapter.createFromResource(this,
                R.array.Incidents_Status, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterIncidentStatus = ArrayAdapter.createFromResource(this,
                R.array.Incidents_Category, android.R.layout.simple_spinner_item);


        spnProvinciaIncidente.setAdapter(adapterProvincia);

        spnEstadoIncidente.setAdapter(adapterIncidentType);

        spnTipoIncidente.setAdapter(adapterIncidentStatus);

        //Inicia el objeto de localización
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //Obtiene la última localización.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null) {
                                locationString = location.convert(location.getLatitude(), Location.FORMAT_DEGREES) + " " + Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
                                Toast message = Toast.makeText(getApplicationContext(), locationString , Toast.LENGTH_LONG);
                                message.show();

                            }
                        }
                    });
        }

        LocationCallback locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    locationString = location.convert(location.getLatitude(), Location.FORMAT_DEGREES) + " " + Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
                    Toast message = Toast.makeText(getApplicationContext(), locationString, Toast.LENGTH_LONG);
                    message.show();
                }
            };
        };


        // Añade un listener a un botón para poder capturar una foto.
        Button btnPhoto = (Button) findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        if(contador==4){

                            Toast message = Toast.makeText(getApplicationContext(), "Ha alcanzado el máximo de fotografías por incidente", Toast.LENGTH_LONG);
                            message.show();



                        } else{

                            dispatchTakePictureIntent();
                        }

                    }

                }
        );

        // Añade un listener a un botón para poder insertar los datos.
        Button btnCrearReporte = (Button) findViewById(R.id.btnCrearReporte);
        btnCrearReporte.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        //Valida que los editText y Spinner estén completos.
                        if (spnEstadoIncidente.getSelectedItem().toString().equals("") || txtDescripcionIncidencia.getText().toString().equals("")
                                || txtEmpresa.getText().toString().equals("") || spnTipoIncidente.getSelectedItem().toString().equals("")
                                || spnProvinciaIncidente.getSelectedItem().toString().equals("") || txtCantonIncident.getText().toString().equals("")
                                || txtDistritoIncident.getText().toString().equals("") || txtDirectionIncident.getText().toString().equals("")
                                || StringPhoto1.equals("")  ){


                            Toast message = Toast.makeText(getApplicationContext(), "Complete todos los campos y tome al menos una foto", Toast.LENGTH_LONG);
                            message.show();

                        } else {
                            //Invoca la clase UsersData, que traerá la información del usuario basada en el ID obtenido en el bundle.
                            user = new UsersData();
                            user = database.SelectUserByID(id);
                            //Realiza la inserción del incidente en la base de datos.
                            database.InsertIncident(user.cedula, user.nombre, user.apellido1, user.apellido2, spnTipoIncidente.getSelectedItem().toString(), txtEmpresa.getText().toString(),
                                    spnEstadoIncidente.getSelectedItem().toString(), txtDescripcionIncidencia.getText().toString(), spnProvinciaIncidente.getSelectedItem().toString(),
                                    txtCantonIncident.getText().toString(), txtDistritoIncident.getText().toString(), txtDirectionIncident.getText().toString(),
                                    locationString, StringPhoto1, StringPhoto2, StringPhoto3, StringPhoto4 );


                            Toast message = Toast.makeText(getApplicationContext(), "Incidencia insertada con éxito", Toast.LENGTH_LONG);
                            message.show();


                        }
                        }
                    }


        );


        // Crea una instancia de la cámara.
        //CameraStatus = getCameraStatus();

        // Crea el View que será asignado a un layout para que sea visible la cámara al usuario final.
        // CameraPreview Preview = new CameraPreview(this, CameraStatus);
        //FrameLayout View = (FrameLayout) findViewById(R.id.cameraView);
        //View.addView(Preview);



    }


    @Override
    public void menu(int boton) {

      //Case para realizar distintos Intent, dependiendo el botón clickeado.
        Intent intento;
        switch(boton){
            case 0:
                break;
            case 1:
                intento = new Intent(this,UpdateIncidentActivity.class);
                intento.putExtra("id", id);
                startActivity(intento);
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

    //Crea el archivo de la imegen capturada.

    private File createImageFile() throws IOException {
        //String que genera un nombre único, para que no choque con el nombre de otro archivo.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefijo de la imagen */
                ".jpg",         /* sufijo de la imagen */
                storageDir      /* Directorio de la imagen */
        );

        // Guarda el directorio de la imagen y retorna el archivo de imagen.
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    //Variable estática, necesaria para guardar la URI y utilizarla después de tomar la foto.
    static Uri UriPhoto = null;

//Método para guardar la imegen capturada.
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {


            // Crea el archivo que contendrá la imagen capturada.
            File photoFile = null;
            //Condición Try que valida si la imagen pudo ser creada.
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }


            // Ingresa al Intent solamente si se creó un archivo de imágen.
            if (photoFile != null) {

                //Se toma el URI del archivo y se pasa como parámetro en el Intent de la cámara.
                UriPhoto = Uri.fromFile(photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, UriPhoto);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            //Si la foto se ha tomado con éxito, se genera un archivo bitmap, que será añadido al ImageView correspondiente para mostrarla al usuario final.
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UriPhoto);
                } catch (IOException e) {
                    Toast message = Toast.makeText(getApplicationContext(), "La fotografía no se ha tomado con éxito", Toast.LENGTH_LONG);
                    message.show();
                }
                //Switch que valida el número de foto tomada actualmente y asigna la URI a variables que serán insertadas en la base de datos.
                switch(contador){
                    case 1:
                        photo1.setImageBitmap(bitmap);
                        StringPhoto1 = UriPhoto.toString();
                        contador+=1;
                        break;
                    case 2:
                        photo2.setImageBitmap(bitmap);
                        contador+=1;
                        StringPhoto2 = UriPhoto.toString();
                        break;
                    case 3:
                        photo3.setImageBitmap(bitmap);
                        contador+=1;
                        StringPhoto3 = UriPhoto.toString();
                        break;
                    case 4:
                        photo4.setImageBitmap(bitmap);
                        contador=0;
                        StringPhoto4 = UriPhoto.toString();
                        break;
                    default:
                        break;
                }
                galleryAddPic();
            }
        } catch (Exception e) {


        }

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


//Métodos no utilizados.


// Verifica si la cámara está disponible para ser accedida.

/*
        public static Camera getCameraStatus () {
            Camera camera = null;
            try {
                camera = Camera.open();
            } catch (Exception e) {

            }
            return camera;
        }
*/


  /*  //Método para realizar la caputra de una foto
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {


            //Se crea un archivo de tipo imagen.
            File Image = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (Image == null){
                //Error en caso de no tener permisos para guardar la imagen en SD.
                Log.d(TAG, "No hay permisos para escribir en SD");
                return;
            }

            try {
                //Crea un socket para guardar la imagen en la SD.
                FileOutputStream FOS = new FileOutputStream(Image);
                FOS.write(data);
                FOS.close();
                //Manejo de errores en caso de no poder guardar la imagen.
            } catch (FileNotFoundException e) {
                Log.d(TAG, "Archivo no encontrado: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error al acceder el archivo: " + e.getMessage());
            }
        }
    };



    //Crea el Uri para poder procesar el guardado de la imagen.
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }



    //Método que crea el archivo para guardarlo en la SD.
    @org.jetbrains.annotations.Nullable
    private static File getOutputMediaFile(int type){


        //Este método verifica si existe una micro SD conectada al dispositivo.
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ProyectoApp311CR");


        //Método para crear el directorio para las imégenes
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("ProyectoApp311CR", "No se puede crear el directorio");
                return null;
            }
        }

        // Crea el nombre para el archivo que se guardará en SD.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        //Condición lógica para determinar si es imagen o video.
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }
        //Retorna el archivo que se guardará en la SD.
        return mediaFile;
    }*/

}



