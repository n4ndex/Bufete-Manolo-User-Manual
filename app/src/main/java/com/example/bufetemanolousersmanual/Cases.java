package com.example.bufetemanolousersmanual;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;

public class Cases extends AppCompatActivity {

    Button backMenu;
    private TextView descriptionCase;
    private ImageView imageCases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cases);

        backMenu = findViewById(R.id.buttonBackToMenu);
        descriptionCase = findViewById(R.id.descriptionCase);
        imageCases = findViewById(R.id.imageCases);

        FirebaseUtils.initializeFirebase(this);

        Intent intent = getIntent();
        setLocaleFromIntent(intent);

        obtenerDatosFirestore();

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cases.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void obtenerDatosFirestore() {
        FirebaseUtils.readData("manual", "paginaCases",
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String text = obtenerTextoSegunIdioma(documentSnapshot, "english-text1", "spanish-text1");
                            descriptionCase.setText(text);
                        } else {
                            Toast.makeText(Cases.this, "No existen datos en Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(Cases.this, "Error al obtener datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String obtenerTextoSegunIdioma(DocumentSnapshot documentSnapshot, String keyIngles, String keyEspanol) {
        Locale currentLocale = getResources().getConfiguration().locale;
        String currentLanguage = currentLocale.getLanguage();

        String textKey = "";

        if (currentLanguage.equalsIgnoreCase("en")) {
            textKey = keyIngles;
            imageCases.setImageResource(R.drawable.cases_eng);

        } else if(currentLanguage.equalsIgnoreCase("es")){
            textKey = keyEspanol;
            imageCases.setImageResource(R.drawable.cases);
        }

        return documentSnapshot.getString(textKey);
    }

    private void setLocaleFromIntent(Intent intent) {
        if (intent != null && intent.hasExtra("LOCALE")) {
            Locale locale = (Locale) intent.getSerializableExtra("LOCALE");
            if (locale != null) {
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = locale;
                res.updateConfiguration(conf, dm);
            }
        }
    }
}