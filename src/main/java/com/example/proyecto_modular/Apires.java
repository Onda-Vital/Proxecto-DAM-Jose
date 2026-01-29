package com.example.proyecto_modular;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Apires {
    public static void subirCorreo(String email){
        new Thread(() -> {
            HttpURLConnection con = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/xapi/rest/user/");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("email", email);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                }

                int code = con.getResponseCode();
                Log.i("APIREST", "CODE = " + code);

            } catch (Exception e) {
                Log.e("APIREST", "Erro chamando á API", e);
            } finally {
                if (con != null) con.disconnect();
            }
        }).start();
    }

    private boolean emailExiste = false;  //

    public void verusuarios(String email, Activity activity){
        new Thread(() -> {
            HttpURLConnection con = null;
            boolean existe = false;

            try {
                String emailEnc = URLEncoder.encode(email, "UTF-8");
                URL url = new URL("http://10.0.2.2:8080/xapi/rest/user/" + emailEnc);


                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                int code = con.getResponseCode();
                Log.i("APIREST", "GET CODE = " + code);

                existe = (code == 200);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) con.disconnect();
            }

            boolean finalExiste = existe;
            activity.runOnUiThread(() -> {
                if (finalExiste) {
                    Toast.makeText(activity, "O correo existe", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "O correo NON existe", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

}




