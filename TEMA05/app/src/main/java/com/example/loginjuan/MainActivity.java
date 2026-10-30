package com.example.loginjuan;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editTextCorreo, editTextContraseña;
    Button botonContinuar;
    TextView textViewMensajes;
    Switch switchRecordar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        botonContinuar = findViewById(R.id.botonContinuar);
        textViewMensajes = findViewById(R.id.textViewMensajes);
        switchRecordar = findViewById(R.id.switchRecordar);
        botonContinuar.setOnClickListener(v -> {

            String correo = editTextCorreo.getText().toString();
            String contrasena = editTextContraseña.getText().toString();

            if (correo.isEmpty()) {
                editTextCorreo.setError("Debe introducir un correo");
                return;
            }

            if (contrasena.isEmpty()) {
                editTextContraseña.setError("Debe introducir una contraseña");
                return;
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}