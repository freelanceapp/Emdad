package com.creativeshare.emdad.activities_fragments.activities.home_activity.fragments.fragment_home;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.creativeshare.emdad.R;
import com.creativeshare.emdad.activities_fragments.activities.home_activity.activity.Home_Activity;
import com.creativeshare.emdad.models.ShippingOrderDetailsModel;
import com.creativeshare.emdad.models.UserModel;
import com.creativeshare.emdad.preferences.Preferences;
import com.creativeshare.emdad.remote.Api;
import com.creativeshare.emdad.share.Common;
import com.creativeshare.emdad.tags.Tags;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Order_Details_Shipping extends Fragment {
    private static final String TAG = "ORDER_ID";
    private static final String TAG2 = "PRICE";

    private ImageView image_back, image_map_arrow1, image_map_arrow2;
    private LinearLayout ll_back, ll,ll_order_state;
    private CircleImageView image;
    private TextView tv_client_name, tv_order_num, tv_truck_number, tv_truck, tv_load_type, tv_truck_size, tv_from_company_name, tv_from_company_phone, tv_from_responsible, tv_from_address, tv_from_city, tv_load_time, tv_to_company_name, tv_to_company_phone, tv_to_responsible, tv_to_address, tv_to_city, tv_shipment_number, tv_arrival_time, tv_description,tv_value,tv_weight,tv_cost;
    private ProgressBar progBar;
    private CoordinatorLayout cord_layout;
    private FrameLayout fl_view_location1, fl_view_location2;
    private AppBarLayout app_bar;
    private Button btn_done;
    private String current_language;
    private UserModel userModel;
    private Preferences preferences;
    private Home_Activity activity;
    private String price;
    ////////////////////////
    private ImageView image1,image5;
    private TextView tv1, tv5, tv_order_id;
    private View view1;


    private ShippingOrderDetailsModel shippingOrderDetailsModel;

    public static Fragment_Order_Details_Shipping newInstance(int order_id, String price) {
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, order_id);
        bundle.putString(TAG2,price);
        Fragment_Order_Details_Shipping fragment_order_details_shipping = new Fragment_Order_Details_Shipping();
        fragment_order_details_shipping.setArguments(bundle);
        return fragment_order_details_shipping;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details_shipment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        activity = (Home_Activity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        image_back = view.findViewById(R.id.image_back);
        image_map_arrow1 = view.findViewById(R.id.image_map_arrow1);
        image_map_arrow2 = view.findViewById(R.id.image_map_arrow2);

        if (current_language.equals("ar") || current_language.equals("ur")) {
            image_back.setRotation(180.0f);
            image_map_arrow1.setRotation(180.0f);
            image_map_arrow2.setRotation(180.0f);

        }
        ll_back = view.findViewById(R.id.ll_back);
        ll = view.findViewById(R.id.ll);
        image = view.findViewById(R.id.image);
        tv_client_name = view.findViewById(R.id.tv_client_name);
        tv_order_num = view.findViewById(R.id.tv_order_num);
        tv_truck_number = view.findViewById(R.id.tv_truck_number);
        tv_truck = view.findViewById(R.id.tv_truck);
        tv_load_type = view.findViewById(R.id.tv_load_type);
        tv_truck_size = view.findViewById(R.id.tv_truck_size);
        tv_from_company_name = view.findViewById(R.id.tv_from_company_name);
        tv_from_company_phone = view.findViewById(R.id.tv_from_company_phone);
        tv_from_responsible = view.findViewById(R.id.tv_from_responsible);
        tv_from_address = view.findViewById(R.id.tv_from_address);
        tv_from_city = view.findViewById(R.id.tv_from_city);
        tv_load_time = view.findViewById(R.id.tv_load_time);
        tv_to_company_name = view.findViewById(R.id.tv_to_company_name);
        tv_to_company_phone = view.findViewById(R.id.tv_to_company_phone);
        tv_to_responsible = view.findViewById(R.id.tv_to_responsible);
        tv_to_address = view.findViewById(R.id.tv_to_address);
        tv_to_city = view.findViewById(R.id.tv_to_city);
        tv_shipment_number = view.findViewById(R.id.tv_shipment_number);
        tv_arrival_time = view.findViewById(R.id.tv_arrival_time);
        tv_description = view.findViewById(R.id.tv_description);
        tv_value = view.findViewById(R.id.tv_value);
        tv_weight = view.findViewById(R.id.tv_weight);


        progBar = view.findViewById(R.id.progBar);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        cord_layout = view.findViewById(R.id.cord_layout);
        fl_view_location1 = view.findViewById(R.id.fl_view_location1);
        fl_view_location2 = view.findViewById(R.id.fl_view_location2);

        tv_cost = view.findViewById(R.id.tv_cost);
        app_bar = view.findViewById(R.id.app_bar);
        btn_done = view.findViewById(R.id.btn_done);
        ll_order_state = view.findViewById(R.id.ll_order_state);
        image1 = view.findViewById(R.id.image1);
        image5 = view.findViewById(R.id.image5);
        tv1 = view.findViewById(R.id.tv1);
        tv5 = view.findViewById(R.id.tv5);
        tv_order_id = view.findViewById(R.id.tv_order_id);
        view1 = view.findViewById(R.id.view1);

        app_bar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int total_range = appBarLayout.getTotalScrollRange();
                if ((total_range + i) > 60) {
                    image.setVisibility(View.VISIBLE);
                    tv_client_name.setVisibility(View.VISIBLE);
                } else {
                    image.setVisibility(View.GONE);
                    tv_client_name.setVisibility(View.GONE);
                }
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            int order_id = bundle.getInt(TAG);
            price = bundle.getString(TAG2);

            getOrderData(order_id);
        }

        fl_view_location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.DisplayFragmentMapLocation_Details(Double.parseDouble(shippingOrderDetailsModel.getOrder_details().getLat_from()), Double.parseDouble(shippingOrderDetailsModel.getOrder_details().getLong_from()), shippingOrderDetailsModel.getOrder_details().getAddress_from());
            }
        });

        fl_view_location2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.DisplayFragmentMapLocation_Details(Double.parseDouble(shippingOrderDetailsModel.getOrder_details().getLat_to()), Double.parseDouble(shippingOrderDetailsModel.getOrder_details().getLong_to()), shippingOrderDetailsModel.getOrder_details().getAddress_to());
            }
        });
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishOrder();
            }
        });

    }

    private void FinishOrder() {
        final ProgressDialog dialog = Common.createProgressDialog(activity,getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .companyFinishOrder(shippingOrderDetailsModel.getOrder_details().getOrder_id())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialog.dismiss();
                        if (response.isSuccessful())
                        {
                            Toast.makeText(activity, getString(R.string.suc), Toast.LENGTH_SHORT).show();
                            activity.Back();
                            activity.refreshFragmentOrder();
                        }else
                        {
                            try {
                                Log.e("code_error",response.code()+"_"+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        try {
                            Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Log.e("error",t.getMessage());
                        }catch (Exception e)
                        {

                        }


                    }
                });

    }
    private void getOrderData(int order_id) {

        Api.getService(Tags.base_url)
                .getShipmentOrderDetails(order_id, Tags.SHIPPING_ORDER)
                .enqueue(new Callback<ShippingOrderDetailsModel>() {
                    @Override
                    public void onResponse(Call<ShippingOrderDetailsModel> call, Response<ShippingOrderDetailsModel> response) {
                        if (response.isSuccessful()) {
                            progBar.setVisibility(View.GONE);
                            updateUI(response.body());
                        } else {
                            try {
                                Log.e("code_error", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ShippingOrderDetailsModel> call, Throwable t) {
                        Log.e("error", t.getMessage());
                    }
                });

    }

    private void updateUI(ShippingOrderDetailsModel shippingOrderDetailsModel) {
        this.shippingOrderDetailsModel = shippingOrderDetailsModel;
        cord_layout.setVisibility(View.VISIBLE);

        if (userModel.getUser().getCompany_information()==null)
        {
            Picasso.with(activity).load(Uri.parse(Tags.base_url+shippingOrderDetailsModel.getOrder().getTo_user_image())).placeholder(R.drawable.logo_512).fit().into(image);
            tv_client_name.setText(shippingOrderDetailsModel.getOrder().getTo_user_name());
            ll.setVisibility(View.GONE);
        }else
        {
            Picasso.with(activity).load(Uri.parse(Tags.base_url+shippingOrderDetailsModel.getOrder().getFrom_user_image())).placeholder(R.drawable.logo_512).fit().into(image);
            tv_client_name.setText(shippingOrderDetailsModel.getOrder().getFrom_user_name());
            ll.setVisibility(View.VISIBLE);

        }
        tv_order_num.setText(String.format("%s %s", "#", shippingOrderDetailsModel.getOrder_details().getOrder_id()));
        tv_truck_number.setText(shippingOrderDetailsModel.getOrder_details().getNum_of_tran());

        if (current_language.equals("ar") || current_language.equals("ur")) {
            tv_truck.setText(shippingOrderDetailsModel.getOrder_details().getAr_container_title());
            tv_load_type.setText(shippingOrderDetailsModel.getOrder_details().getAr_title_load());
            tv_truck_size.setText(shippingOrderDetailsModel.getOrder_details().getAr_title_size());
            tv_from_city.setText(shippingOrderDetailsModel.getOrder_details().getAr_city_title_from());
            tv_to_city.setText(shippingOrderDetailsModel.getOrder_details().getAr_city_title_to());

        } else {
            tv_truck.setText(shippingOrderDetailsModel.getOrder_details().getEn_container_title());
            tv_load_type.setText(shippingOrderDetailsModel.getOrder_details().getEn_title_load());
            tv_truck_size.setText(shippingOrderDetailsModel.getOrder_details().getEn_title_size());
            tv_from_city.setText(shippingOrderDetailsModel.getOrder_details().getEn_city_title_from());
            tv_to_city.setText(shippingOrderDetailsModel.getOrder_details().getEn_city_title_to());

        }
        tv_from_company_name.setText(shippingOrderDetailsModel.getOrder_details().getCompany_name_from());
        tv_from_company_phone.setText(String.format("%s %s", shippingOrderDetailsModel.getOrder_details().getPhone_code_from().replaceFirst("00", "+"), shippingOrderDetailsModel.getOrder_details().getPhone_from()));
        tv_from_responsible.setText(shippingOrderDetailsModel.getOrder_details().getResponsible_from());
        tv_from_address.setText(shippingOrderDetailsModel.getOrder_details().getAddress_from());

        tv_to_company_phone.setText(String.format("%s %s", shippingOrderDetailsModel.getOrder_details().getPhone_code_to().replaceFirst("00", "+"), shippingOrderDetailsModel.getOrder_details().getPhone_to()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa",Locale.ENGLISH);
        String date_from = dateFormat.format(new Date(Long.parseLong(shippingOrderDetailsModel.getOrder_details().getDate_from())*1000));
        tv_load_time.setText(date_from);

        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa",Locale.ENGLISH);
        String date_to = dateFormat2.format(new Date(Long.parseLong(shippingOrderDetailsModel.getOrder_details().getDate_to())*1000));
        tv_arrival_time.setText(date_to);

        tv_to_company_name.setText(shippingOrderDetailsModel.getOrder_details().getCompany_name_to());
        tv_to_responsible.setText(shippingOrderDetailsModel.getOrder_details().getResponsible_to());
        tv_to_address.setText(shippingOrderDetailsModel.getOrder_details().getAddress_to());
        tv_shipment_number.setText(shippingOrderDetailsModel.getOrder_details().getNum_of_shipping());
        tv_description.setText(shippingOrderDetailsModel.getOrder_details().getDescription());
        tv_value.setText(shippingOrderDetailsModel.getOrder_details().getValue());
        tv_weight.setText(shippingOrderDetailsModel.getOrder_details().getWeight());
        tv_cost.setText(String.format("%s %s",price,getString(R.string.sar)));

        tv_order_id.setText(String.format("%s %s","#",shippingOrderDetailsModel.getOrder_details().getOrder_id()));

        if (userModel.getUser().getCompany_information()==null)
        {
            ll_order_state.setVisibility(View.VISIBLE);
        }else
        {
            ll_order_state.setVisibility(View.GONE);

        }
        updateStepView(Integer.parseInt(shippingOrderDetailsModel.getOrder().getOrder_status()));

        if (shippingOrderDetailsModel.getOrder().getOrder_status().equals("2"))
        {
            btn_done.setVisibility(View.GONE);
        }

    }
    public void updateStepView(int completePosition) {
        switch (completePosition) {

            case 1:
                image1.setBackgroundResource(R.drawable.step_green_circle);
                image1.setImageResource(R.drawable.step_green_true);
                view1.setBackgroundColor(ContextCompat.getColor(activity, R.color.done));
                tv1.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                break;
            case 2:
                image1.setBackgroundResource(R.drawable.step_green_circle);
                image1.setImageResource(R.drawable.step_green_true);
                view1.setBackgroundColor(ContextCompat.getColor(activity, R.color.done));
                tv1.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                image5.setBackgroundResource(R.drawable.step_green_circle);
                image5.setImageResource(R.drawable.step_green_heart);
                tv5.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));


        }
    }

}
