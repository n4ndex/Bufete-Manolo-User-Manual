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

public class FTPManual extends AppCompatActivity {

    Button backMenu;
    private TextView tv1;
    private TextView tv2;
    private ImageView imgLawyer;
    private ImageView imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ftp);
        backMenu = findViewById(R.id.buttonBackToMenu);
        tv1 = findViewById(R.id.textContent1);
        tv2 = findViewById(R.id.textContent2);
        imgLawyer = findViewById(R.id.imageLawyer);
        imgUser = findViewById(R.id.imageUser);

        imgLawyer.setImageResource(R.drawable.ftplawyer);
        imgLawyer.setVisibility(View.VISIBLE);

        imgUser.setImageResource(R.drawable.ftpuser);
        imgUser.setVisibility(View.VISIBLE);

        FirebaseUtils.initializeFirebase(this);

        Intent intent = getIntent();
        setLocaleFromIntent(intent);

        obtenerDatosFirestore();

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FTPManual.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void obtenerDatosFirestore() {
        FirebaseUtils.readData("manual", "paginaFTP",
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Locale currentLocale = getResources().getConfiguration().locale;
                            String currentLanguage = currentLocale.getLanguage();

                            if ("en".equalsIgnoreCase(currentLanguage)) {
                                String textEng1 = documentSnapshot.getString("english-text1");
                                String textEng2 = documentSnapshot.getString("english-text2");
                                tv1.setText(textEng1);
                                tv2.setText(textEng2);
                            } else if ("es".equalsIgnoreCase(currentLanguage)) {
                                String textEsp1 = documentSnapshot.getString("spanish-text1");
                                String textEsp2 = documentSnapshot.getString("spanish-text2");
                                tv1.setText(textEsp1);
                                tv2.setText(textEsp2);
                            } else {
                                tv1.setText("");
                                tv2.setText("");
                                Toast.makeText(FTPManual.this, "Idioma no reconocido", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(FTPManual.this, "No existen datos en Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(FTPManual.this, "Error al obtener datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

