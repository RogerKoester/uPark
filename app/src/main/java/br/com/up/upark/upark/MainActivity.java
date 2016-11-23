package br.com.up.upark.upark;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    boolean tf = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }




    String usuario;
    String senha;
    Usuario user = new Usuario();

    public void login(View v) {


        EditText edt1 = (EditText) findViewById(R.id.editText);
        usuario = edt1.getText().toString();
        EditText edt2 = (EditText) findViewById(R.id.editText2);
        senha = edt2.getText().toString();

        new JSONTask().execute("http://beta.json-generator.com/api/json/get/NJUMbRRbM");



    }

    private class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {

                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJSON = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJSON);
                JSONArray parentArray = parentObject.getJSONArray("value");
                StringBuffer finalBufferedData = new StringBuffer();
                for (int i = 0; i < parentArray.length(); i++) {

                    JSONObject finalObject = parentArray.getJSONObject(i);
                    int idJson = finalObject.getInt("UsuarioId");
                    String nomeJson = finalObject.getString("Nome");
                    String dataJson = finalObject.getString("DataNasc");
                    int cpfJson = finalObject.getInt("CPF");
                    String emailJson = finalObject.getString("Email");
                    String senhaJson = finalObject.getString("Senha");



                    if (nomeJson.equals(usuario) && senhaJson.equals(senha)) {
                        user.setNome(nomeJson);
                        user.setSenha(senhaJson);
                        user.setCPF(cpfJson);
                        user.setEmail(emailJson);
                        user.setDataNasc(dataJson);
                        user.setUsuarioId(idJson);
                        break;
                    }

                }
                return (user.Nome + user.Senha);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            EditText edt1 = (EditText) findViewById(R.id.editText);
            usuario = edt1.getText().toString();
            EditText edt2 = (EditText) findViewById(R.id.editText2);
            senha = edt2.getText().toString();
            if (senha.equals(user.Senha) && usuario.equals(user.Nome)) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getBaseContext(), "Senha ou usuario errados!", Toast.LENGTH_SHORT).show();
            }

        }
    }

}