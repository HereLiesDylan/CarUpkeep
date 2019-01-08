package dylantrebilcock.com.carupkeep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class AppDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CarUpkeep.db";
    public static final int DATABASE_VERSION = 3;

    // Implement AppDatabase as a singleton (guarantee there is only one instance)
    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new AppDatabase(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL;    // Using a string variable to help with logging

        SQL = "CREATE TABLE " + VehiclesContract.TABLE_NAME + " ("
                + VehiclesContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + VehiclesContract.Columns.VEHICLE_NAME + " TEXT NOT NULL, "
                + VehiclesContract.Columns.VEHICLE_AVERAGE + " INTEGER NOT NULL, "
                + VehiclesContract.Columns.VEHICLE_CURRENT + " INTEGER NOT NULL);";
        db.execSQL(SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Here to satisfy the angry class implementations
        // Can also be used later to upgrade logic from previous versions to new ones

    }
}