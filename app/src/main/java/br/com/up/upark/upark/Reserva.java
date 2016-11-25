package br.com.up.upark.upark;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.text.NumberFormat;

public class Reserva extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        Estacionamento estacionamento = new Estacionamento();
        estacionamento = getIntent().getParcelableExtra("estacionamentoReserva");

        TextView textViewPreco = (TextView) findViewById(R.id.textViewPreco);
        TextView textViewNome = (TextView) findViewById(R.id.textView9);
        TextView textViewEndereco = (TextView) findViewById(R.id.textView10);
        TextView textViewFuncio = (TextView) findViewById(R.id.textView11);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        NumberFormat format = NumberFormat.getCurrencyInstance();

        textViewNome.setText(estacionamento.getNome().toString());
        textViewFuncio.setText(estacionamento.getHorarioFuncio().toString());
        textViewEndereco.setText(estacionamento.getEndereco().toString());
        textViewPreco.setText(format.format(estacionamento.getPreco()));
        ratingBar.setRating(estacionamento.getRating());







    }
    public void reservar (View v){
        // Envia solicitação para o estacionamento e aguarda resposta
        Toast.makeText(getBaseContext(), "Reserva realizada", Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(getBaseContext(), MapsActivity.class);
        //startActivity(intent);
        finish();



    }


}
