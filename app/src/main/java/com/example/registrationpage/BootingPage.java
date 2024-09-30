package com.example.registrationpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BootingPage extends AppCompatActivity {

    ImageView bootLogoB1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booting_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bootLogoB1 = findViewById(R.id.bootLogoB1);
        Animation Rotate = AnimationUtils.loadAnimation(this, R.anim.rotation);
        bootLogoB1.startAnimation(Rotate);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent pindahIntent = new Intent(BootingPage.this, MainActivity.class);
                startActivity(pindahIntent);
                finish();
            }
        }, 2500);
    }
}