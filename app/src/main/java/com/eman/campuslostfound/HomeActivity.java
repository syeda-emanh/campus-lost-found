package com.eman.campuslostfound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcomeName;
    private ImageView ibProfile;
    private MaterialCardView cardReportLost, cardReportFound, cardBrowse, cardMyClaims, cardCampusMap, cardProfile;
    private UserSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new UserSessionManager(this);

        initViews();
        setupUserData();
        setupClickListeners();
    }

    private void initViews() {
        tvWelcomeName = findViewById(R.id.tv_welcome_name);
        ibProfile = findViewById(R.id.ib_profile);

        cardReportLost = findViewById(R.id.card_report_lost);
        cardReportFound = findViewById(R.id.card_report_found);
        cardBrowse = findViewById(R.id.card_browse);
        cardMyClaims = findViewById(R.id.card_my_claims);
        cardCampusMap = findViewById(R.id.card_campus_map);
        cardProfile = findViewById(R.id.card_profile);
    }

    private void setupUserData() {
        String userName = sessionManager.getUserName();
        String welcomeText = getString(R.string.home_welcome) + userName;
        tvWelcomeName.setText(welcomeText);
    }

    private void setupClickListeners() {
        ibProfile.setOnClickListener(v -> openProfileScreen());
        cardProfile.setOnClickListener(v -> openProfileScreen());

        cardReportLost.setOnClickListener(v -> showModuleToast(getString(R.string.module_report_lost)));
        cardReportFound.setOnClickListener(v -> showModuleToast(getString(R.string.module_report_found)));
        cardBrowse.setOnClickListener(v -> showModuleToast(getString(R.string.module_browse)));
        cardMyClaims.setOnClickListener(v -> showModuleToast(getString(R.string.module_my_claims)));
        cardCampusMap.setOnClickListener(v -> showModuleToast(getString(R.string.module_campus_map)));
    }

    private void openProfileScreen() {
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void showModuleToast(String moduleName) {
        Toast.makeText(this, getString(R.string.msg_module_clicked) + moduleName, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupUserData();
    }
}