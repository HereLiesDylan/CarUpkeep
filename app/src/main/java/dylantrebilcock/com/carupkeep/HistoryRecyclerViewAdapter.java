package dylantrebilcock.com.carupkeep;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private Cursor mCursor;


    public HistoryRecyclerViewAdapter(Context context, Cursor cursor) {
        this.mCursor = cursor;
    }

    @Override
    public HistoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_items, parent, false);
        return new HistoryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryRecyclerViewAdapter.ViewHolder holder, int position) {

        if ((mCursor != null) && (mCursor.getCount() != 0)) {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position " + position);
            }

            final Vehicle vehicle = new Vehicle(mCursor.getLong(mCursor.getColumnIndex(VehiclesContract.Columns._ID)),
                    mCursor.getString(mCursor.getColumnIndex(VehiclesContract.Columns.VEHICLE_NAME)),
                    mCursor.getInt(mCursor.getColumnIndex(VehiclesContract.Columns.VEHICLE_AVERAGE)),
                    mCursor.getInt(mCursor.getColumnIndex(VehiclesContract.Columns.VEHICLE_CURRENT)));

            String maintenance = MaintenanceSchedule.makeMaintenance(vehicle);

            holder.name.setText(vehicle.getName());
            holder.miles.setText(Integer.toString(vehicle.getCurrentDist()));
            holder.maintenance.setText(maintenance);
        }
    }


    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView miles;
        TextView maintenance;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.upcoming_list_name);
            this.miles = itemView.findViewById(R.id.upcoming_list_miles);
            this.maintenance = itemView.findViewById(R.id.upcoming_list_maintenance);
        }
    }
}
