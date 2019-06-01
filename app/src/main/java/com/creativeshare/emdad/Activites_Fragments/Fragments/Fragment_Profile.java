package com.creativeshare.emdad.Activites_Fragments.Fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.creativeshare.emdad.Activites_Fragments.Activites.Home_Activity;
import com.creativeshare.emdad.R;
import com.google.android.material.appbar.AppBarLayout;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_Profile extends Fragment {
    private CircleImageView logo;
    private ImageView upgrade;
    private LinearLayout linearLayout;
    private RatingBar ratingBar;
    private AppBarLayout appBarLayout;
    private Home_Activity activity;

    public static Fragment_Profile newInstance() {

        return new Fragment_Profile();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        intitview(view);
        return view;
    }

    private void intitview(View view) {
        activity=(Home_Activity)getActivity();
        upgrade = view.findViewById(R.id.upgrade_company_toolbar_image);
        logo = view.findViewById(R.id.accout_img);
        logo.setImageResource(R.drawable.ic_photo_camera);
        linearLayout = view.findViewById(R.id.lin);
        ratingBar = view.findViewById(R.id.myrating);
        appBarLayout = view.findViewById(R.id.app_bar);
        ratingBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.yellow1), PorterDuff.Mode.SRC_ATOP);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) > 50) {

                    logo.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    ratingBar.setVisibility(View.GONE);
                } else {
                    logo.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    ratingBar.setVisibility(View.VISIBLE);
                }
            }
        });
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
activity.DisplayFragmentUpgrade();
activity.DisplayFragmentDataUpgrade();
            }
        });
    }


}
