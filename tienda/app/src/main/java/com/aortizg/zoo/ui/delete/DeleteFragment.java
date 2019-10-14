package com.aortizg.zoo.ui.delete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aortizg.zoo.R;

import SQL.SQLite;

public class DeleteFragment extends Fragment {

    Button eliminar;
    private SQLite sqlite;
    EditText id;
    private DeleteViewModel deleteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        deleteViewModel =
                ViewModelProviders.of(this).get(DeleteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_delete, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        deleteViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        eliminar = root.findViewById(R.id.d_btn_eliminar);
        id = root.findViewById(R.id.id_producto_eliminar);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sqlite=new SQLite(getContext());
                sqlite.abrir();
                System.out.print("Entraste");
                if (!id.getText().toString().isEmpty()) {
                    Boolean chkID = sqlite.verificarID(id.getText().toString());
                    if (chkID) {
                        sqlite.eliminar(id.getText());
                        Toast.makeText(getContext(), "Registro Eliminado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "El ID es incorrecto", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Ingrese un ID", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return root;
    }
}