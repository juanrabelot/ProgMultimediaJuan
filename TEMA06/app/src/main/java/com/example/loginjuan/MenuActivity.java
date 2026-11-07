package com.example.loginjuan;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.appbar.MaterialToolbar;
import android.widget.TextView;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    public static final String EXTRA_CORREO = "extra_correo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String correo = getIntent().getStringExtra(EXTRA_CORREO);
        TextView tvCorreo = findViewById(R.id.tvCorreo);
        Button btnCerrarSesion =
                findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            MenuActivity.this,
                            MainActivity.class
                    );
            startActivity(intent);
            finish();
        });
        tvCorreo.setText("Hola, " + correo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}