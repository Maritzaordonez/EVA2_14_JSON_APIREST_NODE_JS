package edu.tectii.eva2_14_json_apirest_node_js;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView txtVwDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtVwDatos = (TextView)findViewById(R.id.txtVwDatos);
    }
    public void onClick(View v){
        //new JSONConnect().execute();
        new  JSONInsert().execute("Nueva Laptop", "10000");
    }
    class JSONConnect extends AsyncTask<Void,Void,String> {
        final String sEnlace = "http://";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtVwDatos.append(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String sResu = "";
            try {
                URL url = new URL(sEnlace);
                HttpURLConnection httpConClima = (HttpURLConnection) url.openConnection();
                if (httpConClima.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader brDatos = new BufferedReader(new InputStreamReader(httpConClima.getInputStream()));
                    sResu = brDatos.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sResu;
        }
/* @Override
        protected String doInBackground(Void... voids) {
            String sResu = "";
            try {
                URL url = new URL(sEnlace);
                HttpURLConnection httpConClima = (HttpURLConnection) url.openConnection();
                if (httpConClima.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader brDatos = new BufferedReader(new InputStreamReader(httpConClima.getInputStream()));
                    sResu = brDatos.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sResu;
        }*/

    }

        /*@Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtVwDatos.append(s);
        }*/
        class JSONInsert extends AsyncTask<String, Void, String>{
            final String sEnlace = "http://";
            @Override
            protected String doInBackground(String... strings) {
                String sResu = "";
                BufferedWriter bwInsertJson = null;
                try{
                    JSONObject jsnDatos = new JSONObject();
                    jsnDatos.put("productname",strings[0]);
                    jsnDatos.put("Unitprice",strings[1]);
                    URL url = new URL(sEnlace);
                    HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
                    httpcon.setDoOutput(true);
                    httpcon.setRequestMethod("Post");
                    httpcon.setRequestProperty("Content-Type", "application/json");
                    httpcon.connect();
                    OutputStream usConnect = httpcon.getOutputStream();
                    bwInsertJson = new BufferedWriter(new OutputStreamWriter(usConnect) {
                    });
                    bwInsertJson.write(jsnDatos.toString());
                    bwInsertJson.flush();
                    //leer la respuesta del servidor
                    InputStream inLeerRes = httpcon.getInputStream();
                    BufferedReader bfLeerRes = new BufferedReader(new InputStreamReader(inLeerRes));
                    //leer cadena de texto
                    String sLinea;
                    while ((sLinea = bfLeerRes.readLine()) != null);
                    sResu = sResu + sLinea + "\n";
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (bwInsertJson != null){
                        try {
                            bwInsertJson.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }

                    }
                }
                return sResu;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                txtVwDatos.append(s);
            }
        }
    }
