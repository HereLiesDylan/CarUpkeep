package dylantrebilcock.com.carupkeep;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HistoryActivity extends AppCompatActivity {

    private HistoryRecyclerViewAdapter mAdapter;

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
            mAdapter = new HistoryRecyclerViewAdapter(this, null);
        }
        recyclerView.setAdapter(mAdapter);
    }

}
