package dylantrebilcock.com.carupkeep;

import android.content.Intent;
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

        // Launcher view
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentMainActivity()).commit();
            navigationView.setCheckedItem(R.id.nav_garage);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onEditClick(@NonNull Vehicle vehicle) {
        AddEditRequest(vehicle);
    }

    private void AddEditRequest(Vehicle vehicle) {
        FragmentAddEdit FragmentAddEdit = new FragmentAddEdit();

        Bundle arguments = new Bundle();
        arguments.putSerializable(Vehicle.class.getSimpleName(), vehicle);
        FragmentAddEdit.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, FragmentAddEdit)
                .commit();

    }

    @Override
    public void onDeleteClick(@NonNull Vehicle vehicle) {

        getContentResolver().delete(VehiclesContract.buildVehicleUri(vehicle.getId()), null, null);
    }

}