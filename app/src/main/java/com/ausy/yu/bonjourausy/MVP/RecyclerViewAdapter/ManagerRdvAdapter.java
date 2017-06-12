package com.ausy.yu.bonjourausy.MVP.RecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ausy.yu.bonjourausy.R;
import com.ausy.yu.bonjourausy.models.ManagerRdvItem;

import java.util.List;

/**
 * Here we need to create the adapter which will actually populate the data into the RecyclerView.
 * The adapter's role is to convert an object at a position into a list row item to be inserted.
 *
 * With a RecyclerView, the adapter requires the existence of a "ViewHolder" object
 * which describes and provides access to all the views within each item row.
 *
 * Every adapter has three primary methods:
 * - onCreateViewHolder : to inflate the item layout and create the holder
 * - onBindViewHolder : to set the view attributes based on the data
 * - getItemCount : to determine the number of items.
 *
 * Created by yukaiwen on 19/04/2017.
 */
// Note that we specify the custom ViewHolder which gives us access to our views
public class ManagerRdvAdapter extends RecyclerView.Adapter<ManagerRdvAdapter.ViewHolder> {

    public static final int  PULLUP_LOAD_MORE = 0;
    public static final int  LOADING_MORE = 1;
    private int load_more_default_status = 0;

    private static final int TYPE_HEADER = 0;    // recyclerview item in one position is type of header
    private static final int TYPE_FOOTER = 1;    // recyclerview item in one position is type of footer
    private static final int TYPE_NORMAL = 2;    // recyclerview item in one position is type of item of data list

    private List<ManagerRdvItem> ManagerRdvItemList;
    private View headerView = null;
    private View footerView = null;

    public ManagerRdvAdapter(List<ManagerRdvItem> ManagerRdvItemList) {
        this.ManagerRdvItemList = ManagerRdvItemList;
    }

    public void setHeaderView(View header) {
        headerView = header;
        notifyItemInserted(0);
    }

    public void setFooterView(View footer) {
        footerView = footer;
        notifyItemInserted(getItemCount()-1);
    }

    /***** Methods for SwipeRefreshLayout Pull Up Load More *****/
    public void loadMoreItem(List<ManagerRdvItem> newDatas) {
        ManagerRdvItemList.addAll(newDatas);
        notifyDataSetChanged();
    }

    /***** Methods for SwipeRefreshLayout Change Status *****/
    public void changeMoreStatus(int status) {
        load_more_default_status = status;
        notifyDataSetChanged();
    }


    /***** The 3 default methods of RecyclerView.Adapter *****/

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ManagerRdvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;

        switch(viewType)
        {
            case TYPE_HEADER:
                viewHolder = new ViewHolder(headerView);
                break;
            case TYPE_FOOTER:
                viewHolder =  new ViewHolder(footerView);
                break;
            case TYPE_NORMAL:
                viewHolder =  new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_manager_rdv_item, parent, false));
                break;
        }
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ManagerRdvAdapter.ViewHolder holder, int position) {
        int rowViewType = getItemViewType(position);

        switch (rowViewType)
        {
            case TYPE_HEADER:
                break;
            case TYPE_FOOTER:
                switch (load_more_default_status){
                    case PULLUP_LOAD_MORE:
                        holder.txt_footer.setText("Remonter pour charger plus...");
                        break;
                    case LOADING_MORE:
                        holder.txt_footer.setText("loading ...");
                        break;
                }
                break;
            default:
                ManagerRdvItem managerRdvItem = ManagerRdvItemList.get(position-1);
                holder.txt_candidateName.setText(managerRdvItem.getCandidateName());
                holder.txt_managerName.setText(managerRdvItem.getManagerName());
                holder.txt_heurePrevu.setText(managerRdvItem.getHeurePrevu());
                break;
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        if(headerView == null && footerView == null){
            return ManagerRdvItemList.size();
        }else if(headerView == null){
            return ManagerRdvItemList.size() + 1;
        }else if(footerView == null){
            return ManagerRdvItemList.size() + 1;
        }else {
            return ManagerRdvItemList.size() + 2;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return TYPE_HEADER;
        }
        if(position == getItemCount()-1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    /***** Creating OnItemClickListener *****/

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /***** Creating ViewHolder *****/

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView txt_candidateName;
        TextView txt_managerName;
        TextView txt_heurePrevu;
        TextView txt_footer;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        ViewHolder(View itemView) {
            // Stores the v in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            if(itemView != headerView && itemView != footerView){
                txt_candidateName = (TextView) itemView.findViewById(R.id.candidateName);
                txt_managerName = (TextView) itemView.findViewById(R.id.managerName);
                txt_heurePrevu = (TextView) itemView.findViewById(R.id.heurePrevu);
                // Attach a click listener to the entire row view
                itemView.setOnClickListener(this);
            }
            if(itemView == footerView){
                txt_footer = (TextView) itemView.findViewById(R.id.manager_rdv_footer);
            }
        }

        /***** Callback OnItemClick *****/

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            // Triggers click upwards to the adapter on click
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Log.d("position", position+"");
                    listener.onItemClick(itemView, position);
                }
            }
        }
    }
}
