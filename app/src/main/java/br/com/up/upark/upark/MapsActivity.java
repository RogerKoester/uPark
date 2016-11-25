package br.com.up.upark.upark;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private GoogleApiClient client;
    public List<Estacionamento> estacionamentoListMap = new ArrayList<Estacionamento>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }



        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String horario = marker.getSnippet();
                String nome = marker.getTitle();


                return false;
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String nome = marker.getTitle();
                Estacionamento estacionamentoReserva = new Estacionamento();
                for(Estacionamento estacionamento : estacionamentoListMap){
                    if((estacionamento.getNome().equals(nome))){
                        estacionamentoReserva = estacionamento;
                        break;
                    }
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle(estacionamentoReserva.getNome());
                builder.setMessage("Você deseja realizar uma reserva em " + estacionamentoReserva.getNome() + "?");
                final Estacionamento finalEstacionamentoReserva = estacionamentoReserva;
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(getBaseContext(), Reserva.class);
                        Location location = mMap.getMyLocation();
                        intent.putExtra("estacionamentoReserva", (Parcelable) finalEstacionamentoReserva);
                        startActivity(intent);
                        //Toast.makeText(getBaseContext(), "Reserva realizada", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("Cancelar",null);
                AlertDialog reserva = builder.create();
                reserva.show();
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getLayoutInflater().inflate(R.layout.info_window,null);
                ImageView img = (ImageView) v.findViewById(R.id.imageView2);
                TextView nome = (TextView) v.findViewById(R.id.textView6);
                TextView horario = (TextView) v.findViewById(R.id.textView7);
                TextView obs = (TextView) v.findViewById(R.id.textView8);
                nome.setText(marker.getTitle());
                horario.setText(marker.getSnippet());
                obs.setText("Clique no balão para reservar");
                return v;
            }
        });

        mMap.setMyLocationEnabled(true);

    }
    private class JSONTask extends AsyncTask<String, String, List<Estacionamento>> {

        @Override
        protected List<Estacionamento> doInBackground(String... params) {
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
                JSONArray parentArray = parentObject.getJSONArray("estacionamentos");
                Conversor conversor = new Conversor();
                List<Estacionamento> estacionamentoList = new ArrayList<>(parentArray.length());
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    Estacionamento estacionamento = new Estacionamento();
                    estacionamento.setHorarioFuncio(finalObject.getString("HorarioFuncio"));
                    estacionamento.setPreco(finalObject.getDouble("Preco"));
                    estacionamento.setCNPJ(finalObject.getString("CNPJ"));
                    estacionamento.setNumeroVagas(finalObject.getInt("NumeroVagas"));
                    estacionamento.setEndereco(finalObject.getString("Endereco"));
                    estacionamento.setSenha(finalObject.getString("Senha"));
                    estacionamento.setEmail(finalObject.getString("Email"));
                    estacionamento.setNome(finalObject.getString("Nome"));
                    estacionamento.setEstacionamentoId(finalObject.getInt("EstacionamentoId"));
                    estacionamento.setLatLng(conversor.getLocationFromAddress(MapsActivity.this, estacionamento.getEndereco()));
                    estacionamento.setRating(4);
                    estacionamentoList.add(estacionamento);


                }
                return estacionamentoList;


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
        protected void onPostExecute(List<Estacionamento> estacionamentos) {
            super.onPostExecute(estacionamentos);
            MarkerOptions options = new MarkerOptions();
            for(Estacionamento estacionamento : estacionamentos){
                DecimalFormat preco = new DecimalFormat();
                options.position(estacionamento.getLatLng());
                options.title(estacionamento.getNome());
                options.snippet("Horário de funcionamento: "+estacionamento.getHorarioFuncio()+"\n Primeira hora: " +String.valueOf(estacionamento.getPreco())+"R$");
                mMap.addMarker(options);


            }
            estacionamentoListMap = estacionamentos;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.up.upark.upark/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
        new JSONTask().execute("http://beta.json-generator.com/api/json/get/4yT9rbkzz");
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.up.upark.upark/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void LocalizarEstacionamento(LatLng CoordEstacio,String NomeEstacio, String EndereçoEstacionamento){

    }


}
