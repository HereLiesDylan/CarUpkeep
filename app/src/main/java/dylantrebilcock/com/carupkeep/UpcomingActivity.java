package dylantrebilcock.com.carupkeep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class UpcomingActivity extends AppCompatActivity {

    private UpcomingRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upcoming);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.upcoming_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create an empty adapter to display the loaded data.
        if (mAdapter == null) {
            mAdapter = new UpcomingRecyclerViewAdapter( this, null);
        }
        recyclerView.setAdapter(mAdapter);
    }

}