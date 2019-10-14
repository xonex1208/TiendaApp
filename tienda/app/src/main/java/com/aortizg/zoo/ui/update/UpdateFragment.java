package com.aortizg.zoo.ui.update;

import android.app.DatePickerDialog;
import android.database.Cursor;
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

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aortizg.zoo.R;

import java.util.Calendar;

import SQL.SQLite;

public class UpdateFragment extends Fragment {
    EditText nombre, precio, descuento, id, cantidad;
    TextView fecha_entrada;
    Switch perecedero;
    Button btnGuardar, btnBuscar, btnLimpiar;
    private String fechaS;
    private int mYear, mMonth, mDay;
    private DatePickerDialog.OnDateSetListener fechaNacimiento;
    private String finalPerecedero = "No";
    private final String si_es = "Sí";
    private final String no_es = "No";
    private Boolean fechaB = true;
    private String ID_producto;
    public SQLite sqlite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private UpdateViewModel updateViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        updateViewModel =
                ViewModelProviders.of(this).get(UpdateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_update, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        updateViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        id = root.findViewById(R.id.buscar_mod_id);
        nombre = root.findViewById(R.id.modificar_nombre);
        precio = root.findViewById(R.id.modificar_precio);
        descuento = root.findViewById(R.id.modificar_descuento);
        cantidad = root.findViewById(R.id.modificar_cantidad);
        perecedero = root.findViewById(R.id.modificar_perecedero);
        fecha_entrada = root.findViewById(R.id.modificar_fecha);

        btnBuscar =  root.findViewById(R.id.u_btn_buscar);
        btnGuardar = root.findViewById(R.id.u_btn_modificar);
        btnLimpiar = root.findViewById(R.id.modificar_limpiar);

        nombre.setVisibility(View.INVISIBLE);
        precio.setVisibility(View.INVISIBLE);
        descuento.setVisibility(View.INVISIBLE);
        cantidad.setVisibility(View.INVISIBLE);
        perecedero.setVisibility(View.INVISIBLE);
        fecha_entrada.setVisibility(View.INVISIBLE);
        btnGuardar.setVisibility(View.INVISIBLE);

        fecha_entrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarFecha();
            }
        });
        ponerFecha();
        sqlite = new SQLite(getContext());
        sqlite.abrir();

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
                fechaB = false;
            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!id.getText().toString().isEmpty()) {
                    Boolean chkID = sqlite.verificarID(id.getText().toString());
                    if (chkID) {
                        nombre.setVisibility(View.VISIBLE);
                        precio.setVisibility(View.VISIBLE);
                        descuento.setVisibility(View.VISIBLE);
                        cantidad.setVisibility(View.VISIBLE);
                        perecedero.setVisibility(View.VISIBLE);
                        fecha_entrada.setVisibility(View.VISIBLE);

                        btnGuardar.setVisibility(View.VISIBLE);
                        int f = Integer.parseInt(id.getText().toString());
                        Cursor cursor = sqlite.getCant(f);
                        String g1 = null, g2 = null, g3 = null, g4 = null, g5 = null, g6 = null;
                        if (cursor.moveToFirst()) {
                            do {
                                //Inicia en 0
                                g1 = cursor.getString(1);
                                g2 = cursor.getString(2);
                                g3 = cursor.getString(3);
                                g4 = cursor.getString(4);
                                g5 = cursor.getString(5);
                                g6 = cursor.getString(6);
                            } while (cursor.moveToNext());
                        }
                        if (g5.equals("Sí")) {
                            perecedero.setChecked(true);
                        } else if (g5.equals("No")) {
                            perecedero.setChecked(false);
                        }
                        nombre.setText(g1.toString());
                        precio.setText(g2.toString());
                        descuento.setText(g3.toString());
                        cantidad.setText(g4.toString());
                        fecha_entrada.setText(g6.toString());
                        ID_producto = id.getText().toString();
                    }else
                        Toast.makeText(getContext(), "Error: No existe ese ID", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Error: No has puesto un ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fechaB) {
                    fechaB = false;
                    Toast.makeText(getContext(),
                            "Error: seleccione una fecha de entrada",
                            Toast.LENGTH_SHORT).show();
                } else if (!nombre.getText().toString().equals("")
                        && !precio.getText().toString().equals("")
                        && !descuento.getText().toString().equals("")
                        && !cantidad.getText().toString().equals("")) {
                    sqlite.updateProducto(Integer.parseInt(ID_producto),
                            nombre.getText().toString(),
                            precio.getText().toString(),
                            descuento.getText().toString(),
                            cantidad.getText().toString(),
                            finalPerecedero,
                            fecha_entrada.getText().toString());
                    //recuperar id del ultimo registtro y pasa como parmetro

                    Toast.makeText(getContext(), "Registro añadido", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    nombre.setText("");
                    precio.setText("");
                    descuento.setText("");
                    cantidad.setText("");
                    perecedero.setChecked(false);
                    fecha_entrada.setText("Fecha de Entrada");
                }else{
                    Toast.makeText(getContext(), "Error: no puede haber campos vacios", Toast.LENGTH_SHORT).show();
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