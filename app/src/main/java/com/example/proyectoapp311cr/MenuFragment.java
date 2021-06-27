package com.example.proyectoapp311cr;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private final  int [] arrayBotones = {R.id.imgIncident, R.id.imgUpdate, R.id.imgGraphics, R.id.imgExit};

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        ImageButton botonMenu;



   //For que permite determinar cuál botón ha sido presionado y en cuál actividad.
        for (int i=0; i<arrayBotones.length; i++){

            botonMenu= (ImageButton)v.findViewById(arrayBotones[i]);
            final int cualboton = i;


            botonMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity cualActividad = getActivity();

                    ((FragmentInterface) cualActividad).menu(cualboton);

                }
            });

        }

        return v;
    }


}
