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
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
    EditText editTextCorreo, editTextContraseña;
    Button botonContinuar;
    TextView textViewMensajes;
    Switch switchRecordar;
    private int intentos = 0;
    private static final String PREFS = "sesion";
    private static final String CLAVE_CORREO = "correo";

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
        SharedPreferences preferencias =
                getSharedPreferences(PREFS, MODE_PRIVATE);

        String correoGuardado =
                preferencias.getString(CLAVE_CORREO, "");
        if (!correoGuardado.isEmpty()) {

            editTextCorreo.setText(correoGuardado);

            switchRecordar.setChecked(true);

            editTextContraseña.requestFocus();

        }
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

                SharedPreferences.Editor editor =
                        preferencias.edit();
                if (switchRecordar.isChecked()) {





                    editor.putString(CLAVE_CORREO, correo);

                } else {

                    editor.remove(CLAVE_CORREO);

                }

                editor.apply();

                Intent intent = new Intent(
                        MainActivity.this,
                        MenuActivity.class
                );

                intent.putExtra(
                        MenuActivity.EXTRA_CORREO,
                        correo
                );

                startActivity(intent);

                finish();

            }
            else {

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