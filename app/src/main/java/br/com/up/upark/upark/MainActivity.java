package br.com.up.upark.upark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }



    public void login(View v) {
        EditText edt1 = (EditText) findViewById(R.id.editText);
        String usuario = edt1.getText().toString();
        EditText edt2 = (EditText) findViewById(R.id.editText2);
        String senha = edt1.getText().toString();




        if(senha.equals("roger") && usuario.equals("roger")){
            Intent intent = new Intent(getBaseContext(), MapsActivity.class);
            startActivity(intent);
        }
        else{
           Toast.makeText(getBaseContext(), "Senha ou usuario errados!", Toast.LENGTH_SHORT).show();
        }
    }
}