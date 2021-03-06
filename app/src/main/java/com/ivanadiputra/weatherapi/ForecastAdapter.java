package com.ivanadiputra.weatherapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ivanadiputra.weatherapi.settergetter.Forecastday;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ListViewHolder> {


    ArrayList<Forecastday> listForecast = new ArrayList<>();
    private OnForecastListener mOnForecastListener;


    public ForecastAdapter(ArrayList<Forecastday> mListForecast, OnForecastListener onForecastListener) {
        this.listForecast = mListForecast;
        this.mOnForecastListener = onForecastListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item, parent,false);
        ListViewHolder listViewHolder = new ListViewHolder(view, mOnForecastListener);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ListViewHolder holder, int position) {
        holder.tv_date.setText(listForecast.get(position).getDate());
        holder.tv_avgtemp.setText(Double.toString(listForecast.get(position).getDay().getAvgtemp_c()));
        holder.tv_condition.setText(listForecast.get(position).getDay().getCondition().getText());
        Glide.with(holder.itemView.getContext()).load("https:" + listForecast.get(position).getDay().getCondition().getIcon()).into(holder.tv_icon);

    }

    @Override
    public int getItemCount() {
        return listForecast.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_date, tv_avgtemp, tv_condition;
        public ImageView tv_icon;
        OnForecastListener onForecastListener;

        public ListViewHolder(@NonNull View itemView, OnForecastListener onForecastListener){
            super(itemView);

            tv_date = itemView.findViewById(R.id.tv_forecast_date);
            tv_avgtemp = itemView.findViewById(R.id.tv_forecast_avgtemp);
            tv_condition = itemView.findViewById(R.id.tv_forecast_condition);
            tv_icon = itemView.findViewById(R.id.img_forecast);
            this.onForecastListener = onForecastListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onForecastListener.onForecastClick(getAdapterPosition());
        }


    }
    public interface OnForecastListener{
        void onForecastClick(int position);
    }
}
