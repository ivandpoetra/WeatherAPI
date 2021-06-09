package com.ivanadiputra.weatherapi.settergetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Forecast {
   private Current current;
   private Condition condition;
   @SerializedName("forecastday")
   private ArrayList<Forecastday> forecastday;


   public Current getCurrent() {
      return current;
   }

   public void setCurrent(Current current) {
      this.current = current;
   }

   public Condition getCondition() {
      return condition;
   }

   public void setCondition(Condition condition) {
      this.condition = condition;
   }

   public ArrayList<Forecastday> getForecastday() {
      return forecastday;
   }

   public void setForecastday(ArrayList<Forecastday> forecastday) {
      this.forecastday = forecastday;
   }
}
