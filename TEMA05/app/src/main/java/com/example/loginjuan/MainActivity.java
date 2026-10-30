package com.example.loginjuan;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
    private int intentos = 0;

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
            if (correo.equals("juan@correo.com")
                    && contrasena.equals("1234")) {

                if (switchRecordar.isChecked()) {

                    textViewMensajes.setText("Usuario y contraseña correctos.\nAlmacenados para siguientes accesos");

                } else {

                    textViewMensajes.setText("Usuario y contraseña correctos");

                }

                textViewMensajes.setTextColor(Color.GREEN);

            } else {

                intentos++;

                textViewMensajes.setText("Usuario o contraseña incorrectos");
                textViewMensajes.setTextColor(Color.RED);

                if (intentos >= 3) {

                    botonContinuar.setEnabled(false);

                    textViewMensajes.setText("Demasiados intentos");
                    textViewMensajes.setTextColor(Color.RED);

                    Toast.makeText(
                            MainActivity.this,
                            "Botón bloqueado",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}