package dylantrebilcock.com.carupkeep;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                                RecyclerViewAdapter.OnVehicleClickListener {

    private DrawerLayout drawer;
    public java.util.Date storedDay;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Sets view seen on launch
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentMainActivity()).commit();
            navigationView.setCheckedItem(R.id.nav_garage);
        }
        checkDate();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {   // The navigation drawer and what fragments/activity gets opened on click
        switch (item.getItemId()) {
            case R.id.nav_garage:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentMainActivity()).commit();
                break;
            case R.id.nav_history:
                Intent h = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(h);
                break;
            case R.id.nav_upcoming:
                Intent u = new Intent(MainActivity.this, UpcomingActivity.class);
                startActivity(u);
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentAbout()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {   // Closes the navigation drawer if the back button is pressed when the drawer is open
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onEditClick(@NonNull Vehicle vehicle) {   // Opens FragmentAddEdit when the user clicks the vList_edit(pencil) button on a specific vehicle.
        AddEditRequest(vehicle);                          // Passes that vehicle specific vehicle to ensure the right vehicle is edited
    }

    private void AddEditRequest(Vehicle vehicle) {  // Opens FragmentAddEdit when 'Add a vehicle' button is clicked to allow the user to add a vehicle
        FragmentAddEdit fragmentAddEdit = new FragmentAddEdit();

        Bundle arguments = new Bundle();
        arguments.putSerializable(Vehicle.class.getSimpleName(), vehicle);
        fragmentAddEdit.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentAddEdit)
                .commit();
    }

    @Override
    public void onDeleteClick(@NonNull Vehicle vehicle) {   // Deletes a vehicle if delete is pressed

        getContentResolver().delete(VehiclesContract.buildVehicleUri(vehicle.getId()), null, null);
    }


    public void checkDate() {

        if (mCursor == null) {
            java.util.Date currentDay = new java.util.Date();
            ContentValues values = new ContentValues();
            int mileage = Integer.parseInt(VehiclesContract.Columns.VEHICLE_CURRENT);
            int dailyDistance = Integer.parseInt(VehiclesContract.Columns.VEHICLE_AVERAGE);

            if (!storedDay.equals(currentDay)) {
                storedDay = currentDay;
                mileage += dailyDistance;
                values.put(VehiclesContract.Columns.VEHICLE_CURRENT, mileage);
            }
        }
    }


}