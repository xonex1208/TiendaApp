package com.aortizg.zoo.ui.create;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aortizg.zoo.R;

import java.util.Calendar;

import SQL.SQLite;

public class CreateFragment extends Fragment {

    private CreateViewModel createViewModel;

    //Variables globales
    EditText nombre, precio, descuento, id, cantidad;
    Switch perecedero;
    TextView fecha_entrada;
    Button btnGuardar, btnLimpiar;
    private int mYear, mMonth, mDay;
    private String fechaS;
    private String finalPerecedero = "No";
    private final String si_es = "Sí";
    private final String no_es = "No";
    private Boolean fechaB = false;
    private DatePickerDialog.OnDateSetListener fechaNacimiento;
    public SQLite sqlite;



    //Esto ya estaba
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createViewModel =
                ViewModelProviders.of(this).get(CreateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_crear, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        createViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //Enlazamos la interfaz con el modelo
        id = root.findViewById(R.id.crear_id_producto);
        nombre = root.findViewById(R.id.crear_nombre);
        precio = root.findViewById(R.id.crear_precio);
        descuento = root.findViewById(R.id.crear_descuento);
        cantidad = root.findViewById(R.id.crear_cantidad);
        perecedero = root.findViewById(R.id.crear_perecedero);
        fecha_entrada = root.findViewById(R.id.crear_fecha);
        btnGuardar =root.findViewById(R.id.btn_crear_guardar);
        btnLimpiar =root.findViewById(R.id.btn_crear_limpiar);

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id.setText("");
                nombre.setText("");
                precio.setText("");
                descuento.setText("");
                cantidad.setText("");
                perecedero.setChecked(false);
                fecha_entrada.setText("Fecha de Entrada");
            }
        });

        perecedero.setChecked(false);
        perecedero.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estaActivado) {
                if (estaActivado) {
                    perecedero.setText(si_es);
                    finalPerecedero = si_es;
                } else {
                    perecedero.setText(no_es);
                    finalPerecedero = no_es;
                }
            }
        });

        fecha_entrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarFecha();
            }
        });
        ponerFecha();

        //conexion con base de datos
        sqlite = new SQLite(getContext());
        sqlite.abrir();

        //Guardar registro
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fechaB) {
                    fechaB = false;
                    Toast.makeText(getContext(),
                            "Error: seleccione una fecha de entrada",
                            Toast.LENGTH_SHORT).show();
                } else if( !id.getText().toString().equals("") &&
                        !nombre.getText().toString().equals("") &&
                        !precio.getText().toString().equals("") &&
                        !cantidad.getText().toString().equals("") &&
                        !descuento.getText().toString().equals("")) {
                    //dentro de if
                    Toast.makeText(getContext(), "ID" +
                                    id.getText().toString() + " " + nombre.getText().toString() + " " +
                                    precio.getText().toString() + " " +
                                    descuento.getText().toString() + " " +
                                    cantidad.getText().toString() + " " +
                                    finalPerecedero + " " +
                                    fechaS
                            , Toast.LENGTH_SHORT).show();
                    if (sqlite.addProducto(
                            Integer.parseInt(id.getText().toString()),
                            nombre.getText().toString(),
                            precio.getText().toString(),
                            descuento.getText().toString(),
                            cantidad.getText().toString(),
                            finalPerecedero,
                            fechaS
                    )){
                        //Dentro if agregar registro
                        Toast.makeText(getContext(), "REGISTRO AÑADIDO",Toast.LENGTH_SHORT).show();

                        //Limpiar campos
                        id.setText("");
                        nombre.setText("");
                        precio.setText("");
                        descuento.setText("");
                        cantidad.setText("");
                        fecha_entrada.setText("Fecha de Entrada");
                    }else{
                        Toast.makeText(getContext(),
                                "Error: compruebe que los datos sean correctos",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(),
                            "Error: no puede haber campos vacios",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    private void mostrarFecha() {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog, fechaNacimiento,
                mYear, mMonth, mDay);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void ponerFecha() {
        fechaNacimiento = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                mes = mes + 1;
                fechaS = anio + "/" + mes + "/" + dia;
                fecha_entrada.setText(fechaS);
                if (!fechaS.isEmpty()) {
                    fechaB = true;
                }

            }
        };

    }
}