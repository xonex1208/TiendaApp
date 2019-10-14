

package SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.util.Log;

import java.util.ArrayList;

public class SQLite {
    private sql sql;
    private SQLiteDatabase db;

    public SQLite(Context context) {
        sql = new sql(context);
    }

    public SQLite() {

    }

    public void abrir() {
        Log.i("SQLite",
                "Se abre conexión a la base de datos" +
                        sql.getDatabaseName());
        db = sql.getWritableDatabase();
    }

    public void cerrar() {
        Log.i("SQLite",
                "Se cierra conexión a la base de datos" +
                        sql.getDatabaseName());
        sql.close();
    }

    public boolean addProducto(
            int id_producto,
            String nombre,
            String precio,
            String descuento,
            String cantidad,
            String perecedero,
            String fecha_entrada) {
        ContentValues cv = new ContentValues(); //Equivalente a putExtra
        cv.put("ID_PRODUCTO", id_producto);
        cv.put("NOMBRE", nombre);
        cv.put("PRECIO", precio);
        cv.put("DESCUENTO", descuento);
        cv.put("CANTIDAD_EXISTENCIA", cantidad);
        cv.put("PERECEDERO", perecedero);
        cv.put("FECHA_ENTRADA", fecha_entrada);
        return (db.insert(
                "PRODUCTOS",
                null, cv) != -1) ? true : false;
    }

    //Leer base de datos
    public Cursor getRegistro() {
        return db.rawQuery("SELECT * FROM PRODUCTOS", null);
    }


    public ArrayList<String> getProdcuto(Cursor cursor) {
        ArrayList<String> listData = new ArrayList<>();
        String item = "";
        if (cursor.moveToFirst()) {
            do {
                item += "ID Producto: " + cursor.getString(0) + "\r\n";
                item += "Nombre del producto: " + cursor.getString(1) + "\r\n";
                item += "Precio: " + cursor.getString(2) + "\r\n";
                item += "Descuento: " + cursor.getString(3) + "\r\n";
                item += "Cantidad en existencia: " + cursor.getString(4) + "\r\n";
                item += "Perecedero: " + cursor.getString(5) + "\r\n";
                item += "Fecha de entrada: " + cursor.getString(6) + "\r\n";
                listData.add(item); //LO AGREGAMOS AL ARRAYLIST
                item = ""; //LIMPIAMOS LA CADENA ITEM
            } while (cursor.moveToNext()); //MIENTRAS LA CONSULTA TENGA DATOS
        }
        return listData;
    }

    public ArrayList<String> getID(Cursor cursor) {
        ArrayList<String> listData = new ArrayList<>();
        String item = "";
        if (cursor.moveToFirst()) {
            do {
                item += "ID Producto: [" + cursor.getString(0) + "]\r\n";
                listData.add(item);
                item = "";
            } while (cursor.moveToNext());
        }
        return listData;
    }

    public String updateProducto(
            int id_producto,
            String nombre,
            String precio,
            String descuento,
            String cantidad,
            String perecedero,
            String fecha) {
        ContentValues cv = new ContentValues(); //Equivalente a putExtra
        cv.put("ID_PRODUCTO", id_producto);
        cv.put("NOMBRE", nombre);
        cv.put("PRECIO", precio);
        cv.put("DESCUENTO", descuento);
        cv.put("CANTIDAD_EXISTENCIA", cantidad);
        cv.put("PERECEDERO", perecedero);
        cv.put("FECHA_ENTRADA", fecha);
        int cant = db.update(
                "PRODUCTOS",
                cv,
                "ID_PRODUCTO=" + id_producto,
                null);
        if (cant == 1) {
            return "Producto modificado";
        } else {
            return "Error, no se ha podido modificar";
        }
    }

    public Cursor getCant(int id) {
        return db.rawQuery(
                "SELECT * FROM PRODUCTOS WHERE ID_PRODUCTO=" + id,
                null);
    }

    public int eliminar(Editable id) {
        return db.delete(
                "PRODUCTOS",
                "ID_PRODUCTO=" + id,
                null);
    }

    public Boolean verificarID(String id) {
        Cursor cursor = db.rawQuery("Select * from productos where id_producto = ?", new String[]{id});
        if (cursor.getCount() > 0) return true;
        else return false;
    }
}
