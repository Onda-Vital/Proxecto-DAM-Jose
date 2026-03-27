package com.example.proyecto_modular;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Apires {

    public static void subirCorreo(String email, String username, String password, String display_name){
        new Thread(() -> {
            HttpURLConnection con = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/xapi/rest/user/");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("username", username);
                json.put("email", email);
                json.put("password", password);
                json.put("display_name", display_name);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                }

                int code = con.getResponseCode();
                InputStream is = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();
                String resp = readAll(is);
                Log.i("APIREST", "CODE = " + code + " BODY = " + resp);
                Log.i("APIREST", "username=" + username + " len=" + (username==null ? -1 : username.length()));
                Log.i("APIREST", "JSON enviado: " + json.toString());


            } catch (Exception e) {
                Log.e("APIREST", "Erro chamando á API", e);
            } finally {
                if (con != null) con.disconnect();
            }
        }).start();
    }

    public interface LoginCallback {
        void onLoginResult(Perfil usuario);
        void onError();
    }

    public static void login(String username, String password, Activity activity, LoginCallback callback){
        new Thread(() -> {
            HttpURLConnection con = null;
            try {
                Log.i("APIREST", "Intentando login: username=" + username);

                URL url = new URL("http://10.0.2.2:8080/xapi/rest/user/login");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("username", username);
                json.put("password", password);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                }

                int code = con.getResponseCode();
                InputStream is = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();
                String resp = readAll(is);

                Log.i("APIREST", "LOGIN CODE = " + code + " BODY = " + resp);

                if (code == 200) {
                    JSONObject o = new JSONObject(resp);

                    long id = o.optLong("id", -1);
                    String user = o.optString("username", "");
                    String email = o.optString("email", "");
                    String displayName = o.optString("display_name", "");

                    Perfil usuario = new Perfil(id, user, email, displayName);
                    activity.runOnUiThread(() -> callback.onLoginResult(usuario));
                } else {
                    activity.runOnUiThread(callback::onError);
                }

            } catch (Exception e) {
                Log.e("APIREST", "Erro login", e);
                activity.runOnUiThread(callback::onError);
            } finally {
                if (con != null) con.disconnect();
            }
        }).start();
    }

    public static void subirTweet(String username, String handle, String content){
        new Thread(() -> {
            HttpURLConnection con = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/xapi/rest/tweet");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("username", username);
                json.put("handle", handle);
                json.put("content", content);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                }

                int code = con.getResponseCode();
                Log.i("APIREST", "TWEET CODE = " + code);

            } catch (Exception e) {
                Log.e("APIREST", "Erro subindo tweet", e);
            } finally {
                if (con != null) con.disconnect();
            }
        }).start();
    }

    public interface TweetsCallback {
        void onOk(ArrayList<Tweet> tweets);
        void onError(Exception e);
    }


    public static void getTimeline(TweetsCallback cb) {
        new Thread(() -> {
            HttpURLConnection con = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/xapi/rest/tweet");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Accept", "application/json");

                int code = con.getResponseCode();
                InputStream is = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

                String json = readAll(is); // función de abaixo
                JSONArray arr = new JSONArray(json);

                ArrayList<Tweet> out = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);

                    // adapta isto ao teu constructor real de Tweet
                    Tweet t = new Tweet(
                            String.valueOf(o.getLong("id")),
                            o.getString("username"),
                            o.getString("handle"),
                            o.getString("content"),
                            R.drawable.shinji, // ou unha foto por defecto
                            null,
                            0,
                            0
                    );
                    out.add(t);
                }

                cb.onOk(out);

            } catch (Exception e) {
                cb.onError(e);
            } finally {
                if (con != null) con.disconnect();
            }
        }).start();
    }

    private static String readAll(InputStream is) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        return sb.toString();
    }

    public interface LootboxCallback {
        void onOk(int opensLeft, int rewardCoins, int newBalance);
        void onError(String msg);
    }

    public static void openLootbox(long userId, Activity activity, LootboxCallback cb) {
        new Thread(() -> {
            HttpURLConnection con = null;

            try {
                // Cambia esta URL se o teu backend ten outra ruta/porto
                URL url = new URL("http://10.0.2.2:8080/xapi/rest/lootbox/open");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                con.setConnectTimeout(8000);
                con.setReadTimeout(8000);

                // JSON body
                JSONObject body = new JSONObject();
                body.put("userId", userId);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(body.toString().getBytes(StandardCharsets.UTF_8));
                }

                int code = con.getResponseCode();
                InputStream is = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();
                String resp = readAll(is);

                if (code >= 200 && code < 300) {
                    JSONObject json = new JSONObject(resp);

                    int opensLeft = json.getInt("opensLeft");
                    int newBalance = json.getInt("newBalance");

                    // reward.amount (só moedas por agora)
                    JSONObject reward = json.getJSONObject("reward");
                    int rewardCoins = reward.getInt("amount");

                    activity.runOnUiThread(() -> cb.onOk(opensLeft, rewardCoins, newBalance));
                } else {
                    String msg = "Non se puido abrir a lootbox";
                    try { msg = new JSONObject(resp).optString("error", msg); } catch (Exception ignored) {}
                    String finalMsg = msg;
                    activity.runOnUiThread(() -> cb.onError(finalMsg));
                }

            } catch (Exception e) {
                activity.runOnUiThread(() -> cb.onError("Erro de rede"));
            } finally {
                if (con != null) con.disconnect();
            }
        }).start();
    }


    public interface DeleteUserCallback {
        void onOk();
        void onError(String msg);
    }

    public static void borrarUsuario(long userId, Activity activity, DeleteUserCallback callback) {
        new Thread(() -> {
            HttpURLConnection con = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/xapi/rest/user/" + userId);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("DELETE");
                con.setRequestProperty("Accept", "application/json");
                con.setConnectTimeout(8000);
                con.setReadTimeout(8000);

                int code = con.getResponseCode();

                if (code == 200 || code == 204) {
                    activity.runOnUiThread(callback::onOk);
                } else {
                    InputStream is = con.getErrorStream();
                    String resp = (is != null) ? readAll(is) : "";
                    activity.runOnUiThread(() -> callback.onError("Erro borrando conta. Código: " + code));
                }

            } catch (Exception e) {
                activity.runOnUiThread(() -> callback.onError("Erro de rede"));
            } finally {
                if (con != null) con.disconnect();
            }
        }).start();
    }

    public interface DeleteTweetCallback {
        void onOk();
        void onError(String msg);
    }

    public static void borrarTweet(String id, String username, Activity activity, DeleteTweetCallback cb) {
        new Thread(() -> {
            HttpURLConnection con = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/xapi/rest/tweet/"
                        + id + "?username=" + username);

                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("DELETE");
                con.setRequestProperty("Accept", "application/json");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);

                int code = con.getResponseCode();

                if (code == 200 || code == 204) {
                    activity.runOnUiThread(cb::onOk);
                } else {
                    InputStream is = con.getErrorStream();
                    String resp = (is != null) ? readAll(is) : "";
                    Log.e("APIREST", "DELETE tweet code=" + code + " body=" + resp);
                    String msg = resp.isEmpty() ? "Erro " + code : resp;
                    activity.runOnUiThread(() -> cb.onError(msg));
                }

            } catch (Exception e) {
                Log.e("APIREST", "Erro borrando tweet", e);
                activity.runOnUiThread(() -> cb.onError("Erro: " + e.getMessage()));
            } finally {
                if (con != null) con.disconnect();
            }
        }).start();
    }

}
