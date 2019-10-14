package com.aortizg.zoo.ui.read;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aortizg.zoo.R;

import java.util.ArrayList;

import SQL.SQLite;

public class ReadFragment extends Fragment {

    private ReadViewModel readViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        readViewModel =
                ViewModelProviders.of(this).get(ReadViewModel.class);
        View root = inflater.inflate(R.layout.fragment_read, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        readViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        ListView l= root.findViewById(R.id.lista) ;
        SQLite sqlite;

        //base de datos
        sqlite = new SQLite(getContext());
        sqlite.abrir();
        Cursor cursor = sqlite.getRegistro();
        ArrayList<String> reg = sqlite.getProdcuto(cursor);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,reg);
        l.setAdapter(adaptador);
        return root;
    }
}