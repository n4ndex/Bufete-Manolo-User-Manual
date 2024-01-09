package com.example.bufetemanolousersmanual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class RegisterYLogin extends AppCompatActivity {

    Button backMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_y_register);

        backMenu = findViewById(R.id.buttonBackToMenu);

        ViewPager viewPager = findViewById(R.id.viewPager);
        RegisterYLoginPagerAdapter adapter = new RegisterYLoginPagerAdapter(this);
        viewPager.setAdapter(adapter);

        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterYLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}