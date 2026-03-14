package com.scambuster.app;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.scambuster.app.ui.activity.ReportScamActivity;
import com.scambuster.app.ui.activity.ReportsListActivity;

/**
 * Main activity for ScamBuster application.
 * Serves as the entry point and navigation hub.
 */
public class MainActivity extends AppCompatActivity {
    private Button reportScamButton;
    private Button viewReportsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        reportScamButton = findViewById(R.id.report_scam_button);
        viewReportsButton = findViewById(R.id.view_reports_button);
    }

    private void setupClickListeners() {
        reportScamButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReportScamActivity.class);
            startActivity(intent);
        });

        viewReportsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReportsListActivity.class);
            startActivity(intent);
        });
    }
}
