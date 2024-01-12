package com.example.bufetemanolousersmanual;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Spinner languageSpinner;
    String currentLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_manual);

        languageSpinner = findViewById(R.id.languageSpinner);

        LanguageItem[] languageItems = {
                new LanguageItem(R.drawable.icoselect, "Ninguno"),
                new LanguageItem(R.drawable.icoesp, "Español"),
                new LanguageItem(R.drawable.icoeng, "English")
        };

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, Arrays.asList(languageItems));
        languageSpinner.setAdapter(adapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LanguageItem selectedLanguage = (LanguageItem) parent.getItemAtPosition(position);
                if (selectedLanguage.getLanguageId().equalsIgnoreCase("Español") || selectedLanguage.getLanguageId().equalsIgnoreCase("Spanish")) {
                    setLocale("es");
                } else if (selectedLanguage.getLanguageId().equalsIgnoreCase("Inglés") || selectedLanguage.getLanguageId().equalsIgnoreCase("English")) {
                    setLocale("en");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        createButtons();
    }

    private void createButtons() {
        String[] buttonTitles = getResources().getStringArray(R.array.menu_buttons);
        final Class<?>[] targetActivities = {FTPManual.class, Email.class, Cases.class, AboutUs.class, RegisterYLogin.class, Menu.class};

        LinearLayout buttonContainer = findViewById(R.id.buttonsLayout);

        for (int i = 0; i < buttonTitles.length; i++) {
            final String title = buttonTitles[i];
            Button button = new Button(this);
            button.setText(title);
            button.setTextColor(Color.WHITE);
            button.setBackgroundResource(R.color.purple_500);
            button.setWidth(550);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 100, 0, 0);
            button.setLayoutParams(params);

            button.setGravity(Gravity.CENTER);

            final int finalIndex = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentLanguage = languageSpinner.getSelectedItem().toString();
                    redirectButtons(targetActivities[finalIndex]);
                }
            });

            buttonContainer.addView(button);
        }
    }

    private void redirectButtons(final Class<?> targetActivity) {
        Locale currentLocale = getResources().getConfiguration().locale;
        Intent intent = new Intent(MainActivity.this, targetActivity);
        intent.putExtra("LOCALE", currentLocale);
        startActivity(intent);
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);
    }
}