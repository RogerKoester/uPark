package br.com.up.upark.upark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Reserva extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        Estacionamento estacionamento = new Estacionamento();
        estacionamento = getIntent().getParcelableExtra("estacionamentoReserva");
        TextView textView = (TextView) findViewById(R.id.textView3);
        TextView textView2 = (TextView) findViewById(R.id.textView5);
        TextView textView3 = (TextView) findViewById(R.id.textView4);
        TextView textView4 = (TextView) findViewById(R.id.textView9);
        ImageView foto = (ImageView)findViewById(R.id.imageView3) ;
        if(foto.getDrawable() == null){
            foto.setBackgroundResource(R.drawable.logo);
        }
        textView.setText(estacionamento.getNome().toString());
        textView2.setText(estacionamento.getEndereco().toString());
        textView3.setText(estacionamento.getHorarioFuncio());
        textView4.setText(String.valueOf(estacionamento.getPreco()));


    }
}
