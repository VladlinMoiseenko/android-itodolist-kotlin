package ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.R;
import ru.vladlin.itodolist.kotlin.android_itodolist_kotlin.models.*;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ApiHolder> {

    private List<TaskModel> tasksList;

    private Context context;

    public TasksAdapter(List<TaskModel> tasksList, Context context, Listener listener) {
        this.tasksList = tasksList;
        this.context = context;
        this.listener = listener;
    }

    public interface Listener {
        void onItemClicked(View v, TaskModel item);
    }

    private Listener listener;

    @Override
    public ApiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_main_item,parent,false);
        ApiHolder mh = new ApiHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ApiHolder holder, int position) {

        final TaskModel item = tasksList.get(position);

        holder.tTitle.setText(item.getTitle());
        holder.tContent.setText(item.getContent());

        //convert unix time
        long unixSeconds = Long.parseLong(item.getCreatedAt());;
        Date date = new java.util.Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3"));
        holder.tCreatedAt.setText(sdf.format(date));

        holder.cv.setOnClickListener(v -> listener.onItemClicked(v, item));

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class ApiHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tTitle, tContent, tCreatedAt;

        public ApiHolder(View v) {
            super(v);

            cv = (CardView)itemView.findViewById(R.id.cv);

            tTitle = (TextView) v.findViewById(R.id.tTitle);
            tContent = (TextView) v.findViewById(R.id.tContent);
            tCreatedAt = (TextView) v.findViewById(R.id.tCreatedAt);

        }
    }
}
