package com.ats.bse_tv;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.bse_tv.bean.Item;
import com.ats.bse_tv.bean.ItemData;
import com.ats.bse_tv.bean.LastOrderListData;
import com.ats.bse_tv.bean.News;
import com.ats.bse_tv.bean.NewsData;
import com.ats.bse_tv.utils.MarqueeTextView;
import com.ats.bse_tv.utils.MarqueeView;
import com.ats.bse_tv.utils.ScrollTextView;
import com.ats.bse_tv.utils.ScrollTextViewFooter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private ListView lvList;
    private TextView tvHigh, tvLow, tvTrend;
    private Button ivSettings;
    private ScrollTextView tvMarquee_Offer;
    private ScrollTextViewFooter tvMarquee;

    public static int lastIndex;
    ArrayList<Item> itemArray = new ArrayList<>();

    private ArrayList<News> newsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lastIndex = 0;

        lvList = findViewById(R.id.listView);
        ivSettings = findViewById(R.id.ivSettings);
        tvMarquee = findViewById(R.id.tvMarquee);
        tvMarquee_Offer = findViewById(R.id.tvMarquee_Offers);

        tvHigh = findViewById(R.id.tvHigh);
        tvLow = findViewById(R.id.tvLow);
        tvTrend = findViewById(R.id.tvTrend);

        ivSettings.requestFocus();

        lvList.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true; // Indicates that this has been handled by you and will not be forwarded further.
                }
                return false;
            }
        });

        lvList.setScrollContainer(false);
        lvList.setClickable(false);


        String text = "<font color=#ffffff>Welcome To SHAIL</font>";
        tvMarquee.setText(Html.fromHtml(text));
        tvMarquee.setSelected(true);
        tvMarquee.startScroll();

        tvMarquee_Offer.setText(Html.fromHtml(text));
        tvMarquee_Offer.setSelected(true);
        tvMarquee_Offer.startScroll();

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
            }
        });

        SharedPreferences pref = getSharedPreferences(InterfaceApi.MY_PREF, MODE_PRIVATE);
        int rCount = pref.getInt("RowCount", 0);


        //Log.e("Row Count : ", "-------------------> " + rCount);
        if (rCount == 0) {
            rCount = 5;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("RowCount", 5);
            editor.apply();
        }


        getAllItems(lastIndex, rCount);

        ivSettings.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {

                    Toast toast = Toast.makeText(HomeActivity.this, "Settings", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 0);
                    toast.show();


                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        ivSettings.requestFocus();

        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            public void run() {

                getLastOrders();
                getNewsData();
                handler.postDelayed(this, 180000);

            }
        };
        handler.post(runnable);
    }


    public void getAllItems(int start, final int size) {
        //Log.e("Parameters : ", " start : " + start + "   size : " + size);
        if (isOnline(this)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(InterfaceApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<ItemData> itemDataCall = api.getAllItems(start, size);

            itemDataCall.enqueue(new Callback<ItemData>() {
                @Override
                public void onResponse(Call<ItemData> call, retrofit2.Response<ItemData> response) {
                    if (response.body() != null) {
                        ItemData data = response.body();
                        itemArray = new ArrayList<>();
                        if (!data.getErrorMessage().getError()) {
                            //Log.e("DATA : ", " --- " + data);

                            if (data.getErrorMessage().getMessage().equalsIgnoreCase("Game")) {
                                tvHigh.setVisibility(View.VISIBLE);
                                tvLow.setVisibility(View.VISIBLE);
                                tvTrend.setVisibility(View.VISIBLE);
                            } else {
                                tvHigh.setVisibility(View.GONE);
                                tvLow.setVisibility(View.GONE);
                                tvTrend.setVisibility(View.GONE);
                            }

                            for (int i = 0; i < data.getItem().size(); i++) {
                                itemArray.add(data.getItem().get(i));
                            }

                            if (itemArray.size() != 0) {
                                //Log.e("Item Array Size : ", "-------" + itemArray.size());

                                ItemListAdapter adapter = new ItemListAdapter(HomeActivity.this, itemArray, data.getErrorMessage().getMessage());
                                lvList.setAdapter(adapter);
                                ivSettings.requestFocus();

                            }

                            lastIndex = lastIndex + size;
                            //Log.e(" New LastIndex : ", " ------ " + lastIndex);

                            if (itemArray.size() == 0) {
                                lastIndex = 0;
                                //getAllItems(lastIndex, size);

                                //Log.e("Size 0 ", "--------");
                            } else {


                                if (itemArray.size() < size) {
                                    lastIndex = 0;

                                    //Log.e("Size Less", " -------");
                                }
                                final Handler handler = new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        SharedPreferences pref = getSharedPreferences(InterfaceApi.MY_PREF, MODE_PRIVATE);
                                        int rCount = pref.getInt("RowCount", 0);

                                        //Log.e("onResume : ", "-------------------> " + rCount);

                                        getAllItems(lastIndex, rCount);
                                        //Log.e("Row Count : " + size, " Size : " + itemArray.size() + "    LastIndex : " + lastIndex);


                                    }
                                }, 10000);


                            }
                            //Log.e("updated Row Count : " + size, "updated Size : " + itemArray.size() + " updated LastIndex : " + lastIndex);


                        } else {
                            if (itemArray.size() == 0) {
                                lastIndex = 0;
                                SharedPreferences pref = getSharedPreferences(InterfaceApi.MY_PREF, MODE_PRIVATE);
                                int rCount = pref.getInt("RowCount", 0);

                                //Log.e("onResume : ", "-------------------> " + rCount);

                                getAllItems(lastIndex, rCount);
                            }
                        }
                    } else {
                        if (itemArray.size() == 0) {
                            lastIndex = 0;
                            SharedPreferences pref = getSharedPreferences(InterfaceApi.MY_PREF, MODE_PRIVATE);
                            int rCount = pref.getInt("RowCount", 0);

                            //Log.e("onResume : ", "-------------------> " + rCount);

                            getAllItems(lastIndex, rCount);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ItemData> call, Throwable t) {
                    t.printStackTrace();
                    //Log.e("Error : ", " ------ " + t.getMessage());
                }
            });
        } else {
            //Log.e("Internet : ", " No Connection");
        }
    }


    public void getLastOrders() {

        if (isOnline(this)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(InterfaceApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<LastOrderListData> orderListDataCall = api.getLastOrderItems();

            orderListDataCall.enqueue(new Callback<LastOrderListData>() {
                @Override
                public void onResponse(Call<LastOrderListData> call, retrofit2.Response<LastOrderListData> response) {
                    if (response.body() != null) {
                        LastOrderListData data = response.body();
                        if (!data.getErrorMessage().getError()) {
                            //Log.e("Response : ", " ---------------------------- " + " DATA : " + data);
                            String text = "";
                            for (int i = 0; i < data.getLastOrderList().size(); i++) {
                                float min = data.getLastOrderList().get(i).getMinRate();
                                float max = data.getLastOrderList().get(i).getMaxRate();
                                float rate = data.getLastOrderList().get(i).getRate();
                                float mean = (max + min) / 2;
                                String itemName = data.getLastOrderList().get(i).getItemName().toUpperCase();

                                int rateRoundUp = Math.round(rate);

                                String smallSpace = "&nbsp;&nbsp;&nbsp;&nbsp;";
                                String longSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";


                                if ((rate >= mean)) {
                                    text = text + "<font color=#ffffff>" + itemName + "</font>" + smallSpace + "<font color=#96EA19>" + " &#x20b9; " + rateRoundUp + "  &#xf062;" + "</font>" + longSpace + "|" + longSpace + "";
                                } else {
                                    text = text + "<font color=#ffffff>" + itemName + "</font>" + smallSpace + "<font color=#ea4a19>" + " &#x20b9; " + rateRoundUp + "  &#xf063;" + "</font>" + longSpace + "|" + longSpace + "";
                                }

                            }
                            tvMarquee.setText(Html.fromHtml(text));
                            tvMarquee.setSelected(true);
                            tvMarquee.startScroll();

                        } else {
                            //Log.e("Response : ", " ---- " + " NULL : ");
                            tvMarquee.setText("Welcome To SHAIL");
                            tvMarquee.setSelected(true);
                            tvMarquee.startScroll();

                        }
                    } else {
                        //Log.e("Response : ", " ---- " + " ERROR : ");
                        tvMarquee.setText("Welcome To SHAIL");
                        tvMarquee.setSelected(true);
                        tvMarquee.startScroll();

                    }
                }

                @Override
                public void onFailure(Call<LastOrderListData> call, Throwable t) {
                    //Log.e("Failure : ", " ---- " + t.getMessage());
                    t.printStackTrace();
                    tvMarquee.setText("Welcome To SHAIL");
                    tvMarquee.setSelected(true);
                    tvMarquee.startScroll();

                }
            });


        } else {
            //Log.e("Internet : ", " No Connection");
        }


    }


    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            // Toast.makeText(context, "No Internet Connection ! ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void getNewsData() {
        if (isOnline(this)) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(InterfaceApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            InterfaceApi api = retrofit.create(InterfaceApi.class);

            Call<NewsData> newsDataCall = api.getAllNews();

            newsDataCall.enqueue(new Callback<NewsData>() {
                @Override
                public void onResponse(Call<NewsData> call, retrofit2.Response<NewsData> response) {
                    try {
                        if (response.body() != null) {
                            NewsData data = response.body();
                            if (data.getErrorMessage().getError()) {
                                Log.e("ON RESPONSE : ", " ERROR : " + data.getErrorMessage().getMessage());
                                tvMarquee_Offer.setText("Welcome To SHAIL");
                                tvMarquee_Offer.setSelected(true);
                                tvMarquee_Offer.startScroll();

                            } else {
                                newsArrayList.clear();
                                String text = "";
                                for (int i = 0; i < data.getNews().size(); i++) {
                                    newsArrayList.add(i, data.getNews().get(i));

                                    String itemName = data.getNews().get(i).getNewsTitle();
                                    String itemDesc = data.getNews().get(i).getNewsDesc();

                                    String smallSpace = "&nbsp;&nbsp;&nbsp;&nbsp;";
                                    String longSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

                                    text = text + "<font color=#ffffff>" + itemName + "</font>" + smallSpace + "<font color=#ffbc00>" + itemDesc + "</font>" + smallSpace + "|" + longSpace + "";
                                }
                                Log.e("RESPONSE : ", " DATA : " + newsArrayList);


                                tvMarquee_Offer.setText(Html.fromHtml(text));
                                tvMarquee_Offer.setSelected(true);
                                tvMarquee_Offer.startScroll();


                            }
                        } else {
                            Log.e("RESPONSE : ", " NO DATA");
                            tvMarquee_Offer.setText("Welcome To SHAIL");
                            tvMarquee_Offer.setSelected(true);
                            tvMarquee_Offer.startScroll();

                        }
                    } catch (Exception e) {
                        Log.e("Exception : ", "" + e.getMessage());
                        tvMarquee_Offer.setText("Welcome To SHAIL");
                        tvMarquee_Offer.setSelected(true);
                        tvMarquee_Offer.startScroll();

                    }
                }

                @Override
                public void onFailure(Call<NewsData> call, Throwable t) {
                    Log.e("ON FAILURE : ", " ERROR : " + t.getMessage());
                    tvMarquee_Offer.setText("Welcome To SHAIL");
                    tvMarquee_Offer.setSelected(true);
                    tvMarquee_Offer.startScroll();

                }
            });


        }


    }

}
