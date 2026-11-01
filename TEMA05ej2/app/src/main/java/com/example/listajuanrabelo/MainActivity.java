package com.example.listajuanrabelo;
import androidx.recyclerview.widget.DividerItemDecoration;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewCoches;
    ArrayList<Coche> listaCoches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerViewCoches = findViewById(R.id.recyclerViewCoches);

        listaCoches = new ArrayList<>();

        listaCoches.add(new Coche(
                "Mustang GT",
                "Ford",
                R.drawable.mustang));

        listaCoches.add(new Coche(
                "911 Turbo",
                "Porsche",
                R.drawable.porsche911));

        listaCoches.add(new Coche(
                "Aventador",
                "Lamborghini",
                R.drawable.aventador));

        listaCoches.add(new Coche(
                "Chiron",
                "Bugatti",
                R.drawable.chiron));

        listaCoches.add(new Coche(
                "Skyline GT-R R34",
                "Nissan",
                R.drawable.skyline));

        listaCoches.add(new Coche(
                "Supra MK4",
                "Toyota",
                R.drawable.supra));

        listaCoches.add(new Coche(
                "M3 E46",
                "BMW",
                R.drawable.m3e46));

        listaCoches.add(new Coche(
                "F40",
                "Ferrari",
                R.drawable.ferrarif40));

        recyclerViewCoches.setLayoutManager(
                new LinearLayoutManager(this));
        DividerItemDecoration separador =
                new DividerItemDecoration(
                        this,
                        DividerItemDecoration.VERTICAL);

        recyclerViewCoches.addItemDecoration(separador);

        AdaptadorCoches adaptador =
                new AdaptadorCoches(listaCoches);

        recyclerViewCoches.setAdapter(adaptador);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}