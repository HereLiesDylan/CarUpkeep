package dylantrebilcock.com.carupkeep;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


public class VehiclesContentProvider extends android.content.ContentProvider {
    private static final String TAG = "VehiclesContentProvider";

    private AppDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final String CONTENT_AUTHORITY = "dylantrebilcock.com.carupkeep.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int VEHICLES = 100;
    private static final int VEHICLES_ID = 101;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(CONTENT_AUTHORITY, VehiclesContract.TABLE_NAME, VEHICLES);
        matcher.addURI(CONTENT_AUTHORITY, VehiclesContract.TABLE_NAME + "/#", VEHICLES_ID);

        return matcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = AppDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "query: match is " + match);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch(match) {
            case VEHICLES:
                queryBuilder.setTables(VehiclesContract.TABLE_NAME);
                break;

            case VEHICLES_ID:
                queryBuilder.setTables(VehiclesContract.TABLE_NAME);
                long vehicleId = VehiclesContract.getVehicleId(uri);
                queryBuilder.appendWhere(VehiclesContract.Columns._ID + " = " + vehicleId);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        Log.d(TAG, "query: rows in returned cursor = " + cursor.getCount()); // TODO remove this line and adjust the one above (sortOrder)

        //noinspection ConstantConditions
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VEHICLES:
                return VehiclesContract.CONTENT_TYPE;

            case VEHICLES_ID:
                return VehiclesContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Log.d(TAG, "Entering insert, called with uri:" + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase db;

        Uri returnUri;
        long recordId;

        switch(match) {
            case VEHICLES:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(VehiclesContract.TABLE_NAME, null, values);
                if(recordId >=0) {
                    returnUri = VehiclesContract.buildVehicleUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if (recordId >= 0) {
            // something was inserted
            Log.d(TAG, "insert: Setting notifyChanged with " + uri);
            //noinspection ConstantConditions
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "insert: nothing inserted");
        }

        Log.d(TAG, "Exiting insert, returning " + returnUri);
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);


        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch(match) {
            case VEHICLES:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(VehiclesContract.TABLE_NAME, selection, selectionArgs);
                break;

            case VEHICLES_ID:
                db = mOpenHelper.getWritableDatabase();
                long vehicleId = VehiclesContract.getVehicleId(uri);
                selectionCriteria = VehiclesContract.Columns._ID + " = " + vehicleId;

                if((selection != null) && (selection.length()>0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(VehiclesContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if(count > 0) {
            // something was deleted
            Log.d(TAG, "delete: Setting notifyChange with " + uri);
            //noinspection ConstantConditions
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "delete: nothing deleted");
        }

        Log.d(TAG, "Exiting update, returning " + count);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch(match) {
            case VEHICLES:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(VehiclesContract.TABLE_NAME, values, selection, selectionArgs);
                break;

            case VEHICLES_ID:
                db = mOpenHelper.getWritableDatabase();
                long vehicleId = VehiclesContract.getVehicleId(uri);
                selectionCriteria = VehiclesContract.Columns._ID + " = " + vehicleId;

                if((selection != null) && (selection.length()>0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(VehiclesContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if(count > 0) {
            // something was deleted

            //noinspection ConstantConditions
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
        }

        return count;
    }
}