package com.example.empleados;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.empleados.adapter.EmpleadoAdapter;
import com.example.empleados.model.Empleado;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    Button btn_agregar;
    RecyclerView mRecycler;
    EmpleadoAdapter eAdapter;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.RecyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("Empleados");

        FirestoreRecyclerOptions<Empleado> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Empleado>().setQuery(query, Empleado.class).build();

        eAdapter = new EmpleadoAdapter(firestoreRecyclerOptions, this);
        eAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(eAdapter);


        btn_agregar = findViewById(R.id.btn_agregar);

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarEmpleado fm = new AgregarEmpleado();
                fm.show(getSupportFragmentManager(), "Navegar a fragment");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        eAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        eAdapter.stopListening();
    }
}