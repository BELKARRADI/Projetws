package com.example.projetws;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetws.EtudiantAdapter;
import com.example.projetws.R;
import com.example.projetws.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class list extends AppCompatActivity {
    private ListView liste;
    private EtudiantAdapter adapter;
    private OkHttpClient client = new OkHttpClient();
    private String serverUrl = "http://192.168.11.105/Projet/ws/loadEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        liste = findViewById(R.id.list);

        adapter = new EtudiantAdapter(this, new ArrayList<>());
        liste.setAdapter(adapter);

        // Set item click listener for the ListView
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Etudiant etudiant = adapter.getItem(position);
                showOptionsDialog(etudiant);
            }
        });

        loadEtudiantList();
    }

    private void loadEtudiantList() {
        RequestBody postRequestBody = new FormBody.Builder().build();

        Request request = new Request.Builder()
                .url(serverUrl)
                .post(postRequestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    Type type = new TypeToken<Collection<Etudiant>>() {}.getType();
                    final Collection<Etudiant> etudiants = new Gson().fromJson(responseData, type);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.clear();
                            adapter.addAll(etudiants);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ListActivity", "Error: " + e.getMessage());
                showToast("Failed to load data.");
            }
        });
    }

    private void showToast(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showOptionsDialog(final Etudiant etudiant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");
        builder.setItems(new CharSequence[]{"Update", "Delete"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Handle "Update" option
                        // Vous pouvez démarrer une activité pour mettre à jour l'élément ici
                        break;
                    case 1:
                        // Handle "Delete" option

                        // Créez un dialogue de confirmation pour confirmer la suppression
                        AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(list.this);
                        confirmationDialog.setTitle("Confirm Deletion");
                        confirmationDialog.setMessage("Are you sure you want to delete this student?");
                        confirmationDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // L'utilisateur a confirmé la suppression, envoyez la requête de suppression
                                sendDeleteRequest(etudiant);
                            }
                        });
                        confirmationDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // L'utilisateur a annulé la suppression, ne rien faire
                            }
                        });
                        confirmationDialog.show();
                        break;
                }
            }
        });
        builder.show();
    }

    private void sendDeleteRequest(final Etudiant etudiant) {
        RequestBody postRequestBody = new FormBody.Builder()
                .add("student_id", String.valueOf(etudiant.getId()))
                .build();

        Request request = new Request.Builder()
                .url("URL_de_suppression_du_service_web")
                .post(postRequestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    loadEtudiantList();
                    showToast("Student deleted successfully.");
                } else {
                    showToast("Failed to delete student.");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ListActivity", "Error: " + e.getMessage());
                showToast("Failed to delete student.");
            }
        });
    }
}
