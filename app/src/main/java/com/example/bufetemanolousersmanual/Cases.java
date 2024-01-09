package com.example.bufetemanolousersmanual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Cases extends AppCompatActivity {

    private Button backMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cases);

        backMenu = findViewById(R.id.buttonBackToMenu);

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cases.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
