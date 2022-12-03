package com.example.empleados;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AgregarEmpleado extends DialogFragment {

    Button btn_ingresar;
    EditText nombre, rut, cargo, antiguedad;
    private FirebaseFirestore mFirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  V = inflater.inflate(R.layout.fragment_agregar_empleado, container, false);

        String id = getActivity().getIntent().getStringExtra("id_empleado");
        mFirestore = FirebaseFirestore.getInstance();

        nombre = V.findViewById(R.id.nombre);
        rut = V.findViewById(R.id.rut);
        cargo = V.findViewById(R.id.cargo);
        antiguedad = V.findViewById(R.id.antiguedad);
        btn_ingresar = V.findViewById(R.id.btn_inscribir);

        if (id == null || id == ""){

            btn_ingresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombreempleado = nombre.getText().toString().trim();
                    String rutempleado = rut.getText().toString().trim();
                    String cargoempleado = cargo.getText().toString().trim();
                    String antiguedade = antiguedad.getText().toString().trim();

                    if (nombreempleado.isEmpty() && rutempleado.isEmpty() && cargoempleado.isEmpty()&& antiguedade.isEmpty()){
                        Toast.makeText(getContext(),"Ingresa los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        postEmpleado(nombreempleado, rutempleado, cargoempleado, antiguedade);
                    }
                }
            });

        }else{
            btn_ingresar.setText("Actualizar");
            getEmpleado(id);
            btn_ingresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombreempleado = nombre.getText().toString().trim();
                    String rutempleado = rut.getText().toString().trim();
                    String cargoempleado = cargo.getText().toString().trim();
                    String antiguedade = antiguedad.getText().toString().trim();

                    if (nombreempleado.isEmpty() && rutempleado.isEmpty() && cargoempleado.isEmpty()&& antiguedade.isEmpty()){
                        Toast.makeText(getContext(),"Ingresa los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        updateEmpleado(nombreempleado, rutempleado, cargoempleado, antiguedade, id);
                    }
                }
            });

        }
        return V;
    }

    private void updateEmpleado(String nombreempleado, String rutempleado, String cargoempleado, String antiguedade, String id) {

        Map<String, Object> data = new HashMap<>();
        data.put("Nombre", nombreempleado);
        data.put("RUT", rutempleado);
        data.put("Cargo", cargoempleado);
        data.put("Antiguedad", antiguedade);

        mFirestore.collection("Empleados").document(id).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(),"Exito al actualizar", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postEmpleado(String nombreempleado, String rutempleado, String cargoempleado, String antiguedade) {
        Map<String, Object> data = new HashMap<>();
        data.put("Nombre", nombreempleado);
        data.put("RUT", rutempleado);
        data.put("Cargo", cargoempleado);
        data.put("Antiguedad", antiguedade);

        mFirestore.collection("Empleados").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(),"Creado exitosamente", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.print(e);
                Toast.makeText(getContext(),"Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getEmpleado(String id){
        mFirestore.collection("Empleados").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombreempleado = documentSnapshot.getString("Nombre");
                String rutempleado = documentSnapshot.getString("RUT");
                String cargoempleado = documentSnapshot.getString("Cargo");
                String antiguedadempleado = documentSnapshot.getString("Antiguedad");

                nombre.setText(nombreempleado);
                rut.setText(rutempleado);
                cargo.setText(cargoempleado);
                antiguedad.setText(antiguedadempleado);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}