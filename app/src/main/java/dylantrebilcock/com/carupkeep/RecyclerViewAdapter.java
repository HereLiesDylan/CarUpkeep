package dylantrebilcock.com.carupkeep;


import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;



class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.VehicleViewHolder> {

    private Cursor mCursor;
    private OnVehicleClickListener mListener;

    interface OnVehicleClickListener {
        void onEditClick(@NonNull Vehicle vehicle);
        void onDeleteClick(@NonNull Vehicle vehicle);
    }

    public RecyclerViewAdapter(Cursor cursor, OnVehicleClickListener listener) {

        mCursor = cursor;
        mListener = listener;
    }

    @Override
    public VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_list_items, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VehicleViewHolder holder, int position) {

        if((mCursor == null) || (mCursor.getCount() == 0)) {
            holder.name.setText(R.string.noVehicleAdded);
            holder.currentMiles.setVisibility(View.GONE);
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        } else {
            if(!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position " + position);
            }

            final Vehicle vehicle = new Vehicle(mCursor.getLong(mCursor.getColumnIndex(VehiclesContract.Columns._ID)),
                    mCursor.getString(mCursor.getColumnIndex(VehiclesContract.Columns.VEHICLE_NAME)),
                    mCursor.getInt(mCursor.getColumnIndex(VehiclesContract.Columns.VEHICLE_AVERAGE)),
                    mCursor.getInt(mCursor.getColumnIndex(VehiclesContract.Columns.VEHICLE_CURRENT)));

            holder.name.setText(vehicle.getName());
            holder.currentMiles.setText(Integer.toString(vehicle.getCurrentDist()));
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);

            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch(view.getId()) {
                        case R.id.vList_edit:
                            if(mListener != null) {
                                mListener.onEditClick(vehicle);
                            }
                            break;
                        case R.id.vList_delete:
                            if(mListener != null) {
                                mListener.onDeleteClick(vehicle);
                            }
                            break;
                        default:
                    }

                }
            };

            holder.editButton.setOnClickListener(buttonListener);
            holder.deleteButton.setOnClickListener(buttonListener);
        }

    }

    @Override
    public int getItemCount() {

        if((mCursor == null) || (mCursor.getCount() == 0)) {
            return 1; // A lie, because populates a single ViewHolder with instructions
        } else {
            return mCursor.getCount();
        }
    }

    Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }

        int numItems = getItemCount();

        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if(newCursor != null) {
            // notify the observers about the new cursor
            notifyDataSetChanged();
        } else {
            // notify the observers about the lack of a data set
            notifyItemRangeRemoved(0, numItems);
        }
        return oldCursor;

    }

    static class VehicleViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView currentMiles;
        ImageButton editButton;
        ImageButton deleteButton;
        View itemView;

        public VehicleViewHolder(View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.vList_name);
            this.currentMiles = itemView.findViewById(R.id.vList_miles);
            this.editButton = itemView.findViewById(R.id.vList_edit);
            this.deleteButton = itemView.findViewById(R.id.vList_delete);
            this.itemView = itemView;
        }
    }
}