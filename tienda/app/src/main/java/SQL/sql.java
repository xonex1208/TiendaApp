

package SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sql extends SQLiteOpenHelper {
    private static final String database = "tienda";
    private static final int VERSION = 1;

    private final String tProducto = "CREATE TABLE PRODUCTOS (" +
            "ID_PRODUCTO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "NOMBRE TEXT NOT NULL," +
            "PRECIO TEXT NOT NULL," +
            "DESCUENTO TEXT NOT NULL," +
            "CANTIDAD_EXISTENCIA TEXT NOT NULL," +
            "PERECEDERO TEXT NOT NULL," +
            "FECHA_ENTRADA TEXT NOT NULL)";

    //Constructor
    public sql(Context context){
        super(context, database, null, VERSION);
    }

    //metodos heredados
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tProducto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        if (newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS PRODUCTO");
            db.execSQL(tProducto);
        }
    }
}
