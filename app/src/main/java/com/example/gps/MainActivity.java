package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
        //pega localizacao do GPS...(FusedLocationProviderClient)
    private FusedLocationProviderClient fusedLocation;
    private  static final int CODIGO_GPS = 1000;
    private TextView textView;
    private Location minhaLoc = new Location("Minha Localizacão");
    private Location catedralLoc = new Location("Localização da Catedral");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewDistancia);

    }
    public  void mostrarDistancia(View v){

        //Verifica se a permissão não foi concedida
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED);

        //Solicita o uso do gps para o usuario
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION},CODIGO_GPS);

        fusedLocation = LocationServices.getFusedLocationProviderClient(this);
        //volta a ultima localização que algum app requisito.
        fusedLocation.getLastLocation().addOnSuccessListener(this, new
                OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
               //Setando minha latitude e longitudo...
                minhaLoc.setLatitude(location.getLatitude());
                minhaLoc.setLongitude(location.getLongitude());
                calculaDistancia();
            }
        });

    }

    public void calculaDistancia(){
        catedralLoc.setLatitude(-22.018229);
        catedralLoc.setLongitude(-47.8934227);

        //Retorna o valor da distancia.
        Float distancia = minhaLoc.distanceTo(catedralLoc)/1000;


        textView.setText(String.format("%.2f",distancia)+ "km");
    }

}
