package dylantrebilcock.com.carupkeep;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


import static dylantrebilcock.com.carupkeep.VehiclesContentProvider.CONTENT_AUTHORITY;
import static dylantrebilcock.com.carupkeep.VehiclesContentProvider.CONTENT_AUTHORITY_URI;


public class VehiclesContract {

    static final String TABLE_NAME = "Vehicles";


    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String VEHICLE_NAME = "VehicleName";
        public static final String VEHICLE_AVERAGE = "AverageWeeklyMiles";
        public static final String VEHICLE_CURRENT = "CurrentMiles";

        private Columns() {
            // private constructor to prevent instantiation
        }
    }

            // URI that accesses database tables

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildVehicleUri(long vehicleId) {
        return ContentUris.withAppendedId(CONTENT_URI, vehicleId);
    }

    public static long getVehicleId(Uri uri) {
        return ContentUris.parseId(uri);
    }

}