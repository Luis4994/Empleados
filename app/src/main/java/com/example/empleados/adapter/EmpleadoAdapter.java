package com.example.empleados.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empleados.AgregarEmpleado;
import com.example.empleados.MainEditarEmpleado;
import com.example.empleados.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.example.empleados.model.Empleado;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EmpleadoAdapter extends FirestoreRecyclerAdapter<Empleado, EmpleadoAdapter.ViewHolder> {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EmpleadoAdapter(@NonNull FirestoreRecyclerOptions<Empleado> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Empleado Empleado) {

        DocumentSnapshot documentSanpshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSanpshot.getId();

        holder.Nombre.setText("Nombre: "+Empleado.getNombre());
        holder.RUT.setText("RUT: "+Empleado.getRUT());
        holder.Cargo.setText("Cargo: "+Empleado.getCargo());
        holder.Antiguedad.setText("Antiguedad: "+Empleado.getAntiguedad());

        holder.btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MainEditarEmpleado.class);
                i.putExtra("id_empleado", id);
                activity.startActivity(i);
            }
        });

        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarEmpleado(id);
            }
        });
    }

    private void borrarEmpleado(String id) {
        mFirestore.collection("Empleados").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Exito al eliminar", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_empleado_single, parent, false);
        return new ViewHolder(V);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Nombre, RUT, Cargo, Antiguedad;
        ImageView btn_eliminar, btn_editar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Nombre = itemView.findViewById(R.id.Nombre);
            RUT = itemView.findViewById(R.id.RUT);
            Cargo = itemView.findViewById(R.id.Cargo);
            Antiguedad = itemView.findViewById(R.id.Antiguedad);
            btn_eliminar = itemView.findViewById(R.id.btn_eliminar);
            btn_editar = itemView.findViewById(R.id.btn_inscribir);
        }
    }
}
