package com.example.bufetemanolousersmanual;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Locale;

public class Menu extends AppCompatActivity {

    Button backMenu;
    ViewPager viewPager;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        backMenu = findViewById(R.id.buttonBackToMenu);
        viewPager = findViewById(R.id.viewPager);

        FirebaseUtils.initializeFirebase(this);

        Intent intent = getIntent();
        setLocaleFromIntent(intent);

        obtenerTextosDesdeFirebase();

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void obtenerTextosDesdeFirebase() {
        FirebaseUtils.readData("manual", "paginaMenu",
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Locale currentLocale = getResources().getConfiguration().locale;
                            String currentLanguage = currentLocale.getLanguage();

                            String text1, text2;

                            if ("en".equalsIgnoreCase(currentLanguage)) {
                                text1 = documentSnapshot.getString("english-text1");
                                text2 = documentSnapshot.getString("english-text2");
                                adapter = new PagerAdapter(Menu.this, new String[]{text1, text2}, new int[]{R.drawable.menu_lawyer_eng, R.drawable.menu_client_eng} );

                            } else if ("es".equalsIgnoreCase(currentLanguage)) {
                                text1 = documentSnapshot.getString("spanish-text1");
                                text2 = documentSnapshot.getString("spanish-text2");
                                adapter = new PagerAdapter(Menu.this, new String[]{text1, text2}, new int[]{R.drawable.menu_lawyer, R.drawable.menu_client} );

                            } else {
                                text1 = "not found";
                                text2 = "not found";
                            }

                            viewPager.setAdapter(adapter);
                        }
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
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
