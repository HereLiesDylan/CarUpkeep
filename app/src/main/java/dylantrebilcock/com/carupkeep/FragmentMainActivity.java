package dylantrebilcock.com.carupkeep;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalTime;


public class FragmentMainActivity extends Fragment implements RecyclerViewAdapter.OnVehicleClickListener,
                                                                LoaderManager.LoaderCallbacks<Cursor> {


    private static final String TAG = "FragmentMainActivity";

    private RecyclerViewAdapter mAdapter;
    Button addButton;
    EditText current_dist;

    // A loader manager can handle multiple loaders, so it needs a way to identify this one
    public static final int LOADER_ID = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Activities containing this fragment must implement its callbacks.
        Activity activity = getActivity();
        if (!(activity instanceof RecyclerViewAdapter.OnVehicleClickListener)) {
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement RecyclerViewAdapter.OnVehicleClickListener interface");
        }

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.vehicle_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (mAdapter == null) {
            mAdapter = new RecyclerViewAdapter(null, this);
        }
        recyclerView.setAdapter(mAdapter);

        Button addButton = view.findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEditRequest(null);
            }
        });

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {VehiclesContract.Columns._ID, VehiclesContract.Columns.VEHICLE_NAME,
                VehiclesContract.Columns.VEHICLE_AVERAGE, VehiclesContract.Columns.VEHICLE_CURRENT};
        String currentMiles = VehiclesContract.Columns.VEHICLE_CURRENT + "," + VehiclesContract.Columns.VEHICLE_NAME + " COLLATE NOCASE";

        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(),
                        VehiclesContract.CONTENT_URI,
                        projection,
                        null,
                        null,
                        currentMiles);
            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid loader id" + id);

        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        int count = mAdapter.getItemCount();

        if(count > 1) {
            addButton.setVisibility(View.GONE);
            // Limits Vehicle storage to 1(for date checking/mileage code restrictions)
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    private void AddEditRequest(Vehicle vehicle) {  // Opens FragmentAddEdit on button click

        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FragmentAddEdit()).commit();
            }

    @Override
    public void onEditClick(@NonNull Vehicle vehicle) { // Opens FragmentAddEdit on button click of specific allowing the user to edit that certain vehicle
        RecyclerViewAdapter.OnVehicleClickListener listener = (RecyclerViewAdapter.OnVehicleClickListener) getActivity();
        if (listener != null) {
            listener.onEditClick(vehicle);
        }
    }

    @Override
    public void onDeleteClick(@NonNull Vehicle vehicle) {   // Deletes a vehicle when that certain vehicle's delete button (red x) is clicked
        RecyclerViewAdapter.OnVehicleClickListener listener = (RecyclerViewAdapter.OnVehicleClickListener) getActivity();
        if (listener != null) {
            listener.onDeleteClick(vehicle);
        }
    }

}