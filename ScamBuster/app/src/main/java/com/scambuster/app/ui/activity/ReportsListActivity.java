package com.scambuster.app.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.scambuster.app.R;
import com.scambuster.app.model.ScamReport;
import com.scambuster.app.repository.ScamReportRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to display a list of all scam reports.
 */
public class ReportsListActivity extends AppCompatActivity {
    private ListView reportsListView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_list);

        initializeViews();
        loadReports();
    }

    private void initializeViews() {
        reportsListView = findViewById(R.id.reports_list_view);
    }

    private void loadReports() {
        ScamReportRepository repository = ScamReportRepository.getInstance();
        List<ScamReport> reports = repository.getAllReports();

        List<String> reportStrings = new ArrayList<>();
        for (ScamReport report : reports) {
            reportStrings.add(report.getTitle() + " - Risk: " + report.getRiskLevel() + "/5");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reportStrings);
        reportsListView.setAdapter(adapter);
    }
}
