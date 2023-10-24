package com.example.projetws;

import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projetws.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nom;
    private EditText prenom;
    private Spinner ville;
    private RadioButton m;
    private RadioButton f;
    private Button add;
    private Button show;

    RequestQueue requestQueue;
    private String sexe ;
    private OkHttpClient client = new OkHttpClient();

    String insertUrl = "http://192.168.11.105/Projet/ws/createEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);
        Log.d("MainActivity", "onCreate");

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        ville = findViewById(R.id.ville);
        add = findViewById(R.id.add);
        show = findViewById(R.id.show);

        m = findViewById(R.id.m);
        f = findViewById(R.id.f);
        add.setOnClickListener(this);
        show.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        Log.d("AddEtudiant", "Button clicked");
        if (v == add) {

            if (m.isChecked())
                sexe = "H";
            else
                sexe = "F";

            RequestBody body = new FormBody.Builder()
                    .add("nom", nom.getText().toString())
                    .add("prenom", prenom.getText().toString())
                    .add("ville", ville.getSelectedItem().toString())
                    .add("sexe", sexe)
                    .build();

            Request request = new Request.Builder()
                    .url(insertUrl)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        Type type = new TypeToken<Collection<Etudiant>>() {}.getType();
                        Collection<Etudiant> etudiants = new Gson().fromJson(responseData, type);
                        for (Etudiant e : etudiants) {
                            Log.d("AddEtudiant", e.toString());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                nom.getText().clear();
                                prenom.getText().clear();
                                ville.setSelection(0);
                                m.setChecked(true);
                            }
                        });

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Succès");
                                builder.setMessage("Insertion faite avec succès");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                            }
                        });

                        setResult(RESULT_OK);

                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("AddEtudiant", "Error: " + e.getMessage());
                }
            });
        }else if (v == show) {
            Intent intent = new Intent(this, list.class);
            startActivity(intent);
        }
    }



}
