package dylantrebilcock.com.carupkeep;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class FragmentAddEdit extends Fragment {

    private enum FragmentEditMode {EDIT, ADD}

    private FragmentEditMode mMode;


    private EditText mVehicleName;
    private EditText mAverageDist;
    private EditText mCurrentDist;
    private OnSaveClicked mSaveListener = null;

    interface OnSaveClicked {
        void onSaveClicked();
    }

    Spinner spinYear, spinMake, spinModel;
    ArrayAdapter ar, ar2, ar3;
    String[] spinStrYear;
    ArrayList<String> spinStrMake, spinStrModel;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_edit, container, false);

        mVehicleName = view.findViewById(R.id.vehicle_name);
        mAverageDist = view.findViewById(R.id.avg_dist);
        mCurrentDist = view.findViewById(R.id.current_dist);
        spinYear = view.findViewById(R.id.spinnerYear);
        spinMake = view.findViewById(R.id.spinnerMake);
        spinModel = view.findViewById(R.id.spinnerModel);
        Button saveButton = view.findViewById(R.id.save);

        Bundle arguments = getArguments();
        // Checks if the bundle is empty

        final Vehicle vehicle;
        if (arguments != null) {
            vehicle = (Vehicle) arguments.getSerializable(Vehicle.class.getSimpleName());

            if (vehicle != null) {
                mVehicleName.setText(vehicle.getName());
                mAverageDist.setText(Integer.toString(vehicle.getAvgDist()));
                mCurrentDist.setText(Integer.toString(vehicle.getCurrentDist()));
                mMode = FragmentEditMode.EDIT;
            } else {
                // No vehicle. So must be adding one
                mMode = FragmentEditMode.ADD;
            }
        } else {    // No arguments so we're adding a new vehicle
            vehicle = null;
            mMode = FragmentEditMode.ADD;
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Updates the database if at least one thing was changed.

                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues values = new ContentValues();

                switch (mMode) {
                    case EDIT:
                        if (vehicle == null) {
                            // removes lint warnings, will never execute
                            break;
                        }
                        if (!mVehicleName.getText().toString().equals(vehicle.getName())) {
                            values.put(VehiclesContract.Columns.VEHICLE_NAME, mVehicleName.getText().toString());
                        }
                        if (!mAverageDist.getText().toString().equals(vehicle.getAvgDist())) {
                            values.put(VehiclesContract.Columns.VEHICLE_AVERAGE, mAverageDist.getText().toString());
                        }
                        if (!mCurrentDist.getText().toString().equals(vehicle.getCurrentDist())) {
                            values.put(VehiclesContract.Columns.VEHICLE_CURRENT, mCurrentDist.getText().toString());
                        }
                        if (values.size() != 0) {
                            contentResolver.update(VehiclesContract.buildVehicleUri(vehicle.getId()), values, null, null);
                        }
                        break;
                    case ADD:
                        if (mVehicleName.length() > 0) {
                            values.put(VehiclesContract.Columns.VEHICLE_NAME, mVehicleName.getText().toString());
                            values.put(VehiclesContract.Columns.VEHICLE_AVERAGE, mAverageDist.getText().toString());
                            values.put(VehiclesContract.Columns.VEHICLE_CURRENT, mCurrentDist.getText().toString());
                            contentResolver.insert(VehiclesContract.CONTENT_URI, values);
                        }
                        break;
                }

                if (mSaveListener != null) {
                    mSaveListener.onSaveClicked();
                }


                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FragmentMainActivity())
                        .commit();

                Toast.makeText(getContext(), spinYear.getSelectedItem().toString() + " " +
                                spinMake.getSelectedItem().toString() + " " +
                                spinModel.getSelectedItem().toString() + " has been added",
                        Toast.LENGTH_LONG).show();

            }
        });


        spinStrYear = getResources().getStringArray(R.array.year);
        spinStrMake = new ArrayList<>();
        spinStrModel = new ArrayList<>();

        ar = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinStrYear);
        ar2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinStrMake);
        ar3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinStrModel);
        spinYear.setAdapter(ar);
        spinMake.setAdapter(ar2);
        spinModel.setAdapter(ar3);


            spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selectedYear = parent.getItemAtPosition(position).toString();
                    switch (selectedYear) {
                        case "2000":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2001":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2002":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2003":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2004":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2005":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2006":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2007":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2008":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2009":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2010":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2011":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2012":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2013":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2014":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2015":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2016":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2017":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2018":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;

                        case "2019":
                            spinMake.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Makes)));
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    //Dialog for missing info?
                }
            });

            spinMake.setVisibility(View.VISIBLE);

            spinMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    String selectedMake = parent.getItemAtPosition(position).toString();
                    switch (selectedMake) {
                        case "Acura":
                            spinModel.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Acura)));
                            break;

                        case "Audi":
                            spinModel.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Audi)));
                            break;

                        case "BMW":
                            spinModel.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.BMW)));
                            break;

                        case "Buick":
                            spinModel.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Buick)));
                            break;

                        case "Cadillac":
                            spinModel.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Cadillac)));
                            break;

                        case "Oldsmobile":
                            spinModel.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Oldsmobile)));
                            break;

                        case "Pontiac":
                            spinModel.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Pontiac)));
                            break;

                        case "Saturn":
                            spinModel.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Saturn)));
                            break;

                        case "Tesla":
                            spinModel.setAdapter(new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    getResources().getStringArray(R.array.Tesla)));
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    //Dialog for missing info?
                }
            });

            spinModel.setVisibility(View.VISIBLE);

            return view;
        }
    }