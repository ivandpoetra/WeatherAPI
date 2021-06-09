package com.ivanadiputra.weatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ivanadiputra.weatherapi.settergetter.Forecastday;
import com.ivanadiputra.weatherapi.settergetter.MainModel;
import com.ivanadiputra.weatherapi.retrofit.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.OnForecastListener{


    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static MainActivity mainActivity;

    TextView tv_loc, tv_wind, tv_pressure, tv_precip, tv_humidity, tv_cloud, tv_gust, tv_condition, tv_temp, tv_lastup, tv_localtime ;
    ImageView img;

    SwipeRefreshLayout swipeLayout;
    LinearLayout linearLayout;

    ArrayList<Forecastday> listForecast = new ArrayList<>();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize swipe refresh layout
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        linearLayout = (LinearLayout) findViewById(R.id.swiperefresh);

        int color1 = getResources().getColor(R.color.Purple);
        int color2 = getResources().getColor(R.color.Paradise);

        swipeLayout.setColorSchemeColors(color1,color2);

        tv_loc = findViewById(R.id.tv_location);
        tv_wind = findViewById(R.id.tv_wind);
        tv_pressure = findViewById(R.id.tv_pressure);
        tv_precip = findViewById(R.id.tv_precip);
        tv_humidity = findViewById(R.id.tv_humidity);
        tv_cloud = findViewById(R.id.tv_cloud);
        tv_gust = findViewById(R.id.tv_gust);
        tv_condition = findViewById(R.id.tv_condition);
        tv_temp = findViewById(R.id.tv_temp);
        tv_lastup = findViewById(R.id.tv_lastup);
        tv_localtime = findViewById(R.id.tv_localtime);
        img = findViewById(R.id.img_weather);

        recyclerView = findViewById(R.id.list_forecast);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mainActivity = this;

        getCurrentDataFromApi();
        getForecastDataFromApi();

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {

                        // Stop Refresh
                        swipeLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });
    }

    private void getCurrentDataFromApi (){
        ApiService.endpoint().getCurrentData().enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {


                tv_loc.setText(response.body().getLocation().getName() );
                tv_wind.setText(Double.toString(response.body().getCurrent().getWind_kph()) + " kph");
                tv_pressure.setText(Double.toString(response.body().getCurrent().getPressure_mb() )+ " mb");
                tv_precip.setText(Double.toString(response.body().getCurrent().getPrecip_mm())+ " mm");
                tv_humidity.setText(Integer.toString(response.body().getCurrent().getHumidity()) + " %");
                tv_cloud.setText(Integer.toString(response.body().getCurrent().getCloud()) + " %");
                tv_gust.setText(Double.toString(response.body().getCurrent().getGust_kph()) + " kph");
                tv_condition.setText(response.body().getCurrent().getCondition().getText());
                tv_temp.setText(Double.toString(response.body().getCurrent().getTemp_c()) + " °C");
                tv_lastup.setText("Last Updated : " + response.body().getCurrent().getLast_updated());
                Glide.with(MainActivity.this).load("https:" + response.body().getCurrent().getCondition().getIcon()).into(img);


            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

            }
        });
    }



   private void getForecastDataFromApi() {
        ApiService.endpoint().getForecastData().enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {

                listForecast = response.body().getForecast().getForecastday();
                forecastAdapter = new ForecastAdapter(listForecast, mainActivity);
                recyclerView.setAdapter(forecastAdapter);
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onForecastClick(int position) {
        Intent intent = new Intent(this, DetailForecastActivity.class);
        intent.putExtra("ForecastDay", listForecast.get(position));
        startActivity(intent);
        Log.d(TAG, "onForecastClick: click");
    }

}