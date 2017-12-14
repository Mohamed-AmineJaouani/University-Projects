package com.jaouani.weather;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jaouani.weather.utilities.OpenWeatherJsonUtils;
import com.jaouani.weather.utilities.WeatherUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by M-A J on 13/12/2017.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {
    private Bundle[] mWeatherData;
    private final Context mContext;
    /*
     * An on-click handler that I have defined to make it easy for an Activity to interface with
     * the RecyclerView
     */
    private final ForecastAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface ForecastAdapterOnClickHandler {
        void onClick(Bundle weatherForDay);
    }

    /**
     * Creates a ForecastAdapter.
     *
     * @param context Android context
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     */
    ForecastAdapter(Context context, ForecastAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mWeatherTextView;
        final ImageView mWeatherImageView;
        ForecastAdapterViewHolder(View view) {
            super(view);
            mWeatherTextView = (TextView) view.findViewById(R.id.tv_weather_data);
            mWeatherImageView = (ImageView) view.findViewById(R.id.weather_icon);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mWeatherData[adapterPosition]);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  Type of the view
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutIdForListItem = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ForecastAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param forecastAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
        String weatherForThisDay = mWeatherData[position].getString(OpenWeatherJsonUtils.DATE) + " - "
                + mWeatherData[position].getString(OpenWeatherJsonUtils.DESCRIPTION) + " - "
                + mWeatherData[position].getString(OpenWeatherJsonUtils.HIGH_LOW);

        int weatherId = mWeatherData[position].getInt(OpenWeatherJsonUtils.WEATHER_ID);

        String uriString = WeatherUtils.getIconResourceForWeatherCondition(weatherId);
        Uri uri = Uri.parse(uriString);
        Picasso.with(mContext).load(uri).resize(200,200).centerCrop().into(forecastAdapterViewHolder.mWeatherImageView);

        forecastAdapterViewHolder.mWeatherTextView.setText(weatherForThisDay);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if (null == mWeatherData) return 0;
        return mWeatherData.length;
    }

    /**
     * This method is used to set the weather forecast on a ForecastAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new ForecastAdapter to display it.
     *
     * @param weatherData The new weather data to be displayed.
     */
    void setWeatherData(Bundle[] weatherData) {
        mWeatherData = weatherData;
        notifyDataSetChanged();
    }
}
