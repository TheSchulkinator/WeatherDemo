package theschulk.com.weatherdemo.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import theschulk.com.weatherdemo.Model.ForecastModel;
import theschulk.com.weatherdemo.R;
import theschulk.com.weatherdemo.Utility.DateUtility;

/**
 * Class to adapt the list of forecast objects to a recycler view
 */

public class FiveDayForecastAdapter extends RecyclerView.Adapter<FiveDayForecastAdapter.DailyViewHolder> {

    List<ForecastModel> forecasts;
    Context context;

    public FiveDayForecastAdapter(Context mContext){
        context = mContext;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.day_forecast, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {

        //get resource id from string
        String currentIconName = "ic_" + forecasts.get(position).getWeatherIcon();
        holder.iconImageView.setImageResource(context.getResources().
                getIdentifier(currentIconName, "drawable", context.getPackageName()));

        //get string values formatted
        String currentDate = DateUtility.formatDate(forecasts.get(position).getForecastDate());
        String high = context.getString(R.string.high) +
                forecasts.get(position).getHighTemp() + (char) 0x00B0 +
                context.getString(R.string.fahrenheit);
        String low = context.getString(R.string.low) +
                forecasts.get(position).getLowTemp() +
                (char) 0x00B0 +
                context.getString(R.string.fahrenheit);

        //set text views text
        holder.dateTextView.setText(currentDate);
        holder.highTextView.setText(high);
        holder.lowTextView.setText(low);

    }

    @Override
    public int getItemCount() {
        if (null == forecasts) return 0;
        int length = forecasts.size();
        return length;
    }

    public class DailyViewHolder extends RecyclerView.ViewHolder{
        public final TextView highTextView;
        public final TextView lowTextView;
        public final TextView dateTextView;
        public final ImageView iconImageView;

        public DailyViewHolder(View itemView){
            super(itemView);

            highTextView = itemView.findViewById(R.id.tv_temp_high);
            lowTextView = itemView.findViewById(R.id.tv_temp_low);
            dateTextView = itemView.findViewById(R.id.tv_current_date);
            iconImageView = itemView.findViewById(R.id.iv_weather_icon);
        }
    }


    public void setAdapterData(List<ForecastModel> data){
        forecasts = data;
        notifyDataSetChanged();
    }
}
