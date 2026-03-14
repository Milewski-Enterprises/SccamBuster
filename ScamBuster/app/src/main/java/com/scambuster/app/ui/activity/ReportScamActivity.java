package com.scambuster.app.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scambuster.app.R;
import com.scambuster.app.model.ScamReport;
import com.scambuster.app.repository.ScamReportRepository;
import com.scambuster.app.util.ScamDetectionUtil;
import com.scambuster.app.database.entity.ScammerRecord;

/**
 * Activity for reporting a scam.
 * Includes real-time verification against known scammer database.
 */
public class ReportScamActivity extends AppCompatActivity {
    private EditText titleInput;
    private EditText descriptionInput;
    private EditText phoneInput;
    private EditText emailInput;
    private Spinner categorySpinner;
    private RatingBar riskRatingBar;
    private Button submitButton;
    private TextView warningText;
    private ScamReportRepository repository;
    private ScammerRecord matchedScammer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_scam);

        repository = ScamReportRepository.getInstance(this);
        initializeViews();
        setupCategorySpinner();
        setupSubmitButton();
        setupVerificationListeners();
    }

    private void initializeViews() {
        titleInput = findViewById(R.id.title_input);
        descriptionInput = findViewById(R.id.description_input);
        phoneInput = findViewById(R.id.phone_input);
        emailInput = findViewById(R.id.email_input);
        categorySpinner = findViewById(R.id.category_spinner);
        riskRatingBar = findViewById(R.id.risk_rating);
        submitButton = findViewById(R.id.submit_button);
        warningText = findViewById(R.id.warning_text);
    }

    private void setupCategorySpinner() {
        String[] categories = {
                "Phishing",
                "Lottery Scam",
                "Tech Support",
                "Romance Scam",
                "Identity Theft",
                "Other"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
                android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(adapter);
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> submitReport());
    }

    /**
     * Setup real-time verification when phone/email fields change.
     */
    private void setupVerificationListeners() {
        phoneInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                verifyPhoneNumber();
            }
        });

        emailInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                verifyEmailAddress();
            }
        });
    }

    /**
     * Verify phone number against known scammers database.
     */
    private void verifyPhoneNumber() {
        String phone = sanitizeInput(phoneInput.getText().toString());
        if (phone.isEmpty()) {
            clearWarning();
            return;
        }

        // Run verification in background thread
        new Thread(() -> {
            ScammerRecord scammer = ScamDetectionUtil.isKnownScammer(phone, null);
            runOnUiThread(() -> {
                if (scammer != null) {
                    displayScammerWarning(scammer);
                    matchedScammer = scammer;
                } else {
                    clearWarning();
                }
            });
        }).start();
    }

    /**
     * Verify email address against known scammers database.
     */
    private void verifyEmailAddress() {
        String email = sanitizeInput(emailInput.getText().toString());
        if (email.isEmpty()) {
            clearWarning();
            return;
        }

        // Run verification in background thread
        new Thread(() -> {
            ScammerRecord scammer = ScamDetectionUtil.isKnownScammer(null, email);
            runOnUiThread(() -> {
                if (scammer != null) {
                    displayScammerWarning(scammer);
                    matchedScammer = scammer;
                } else {
                    clearWarning();
                }
            });
        }).start();
    }

    /**
     * Display warning when contact info matches known scammer.
     */
    private void displayScammerWarning(ScammerRecord scammer) {
        if (warningText == null) return;
        
        String warningMessage = String.format(
                "⚠️ WARNING: This contact is associated with %d reported scams!\n" +
                "Name: %s | Risk Score: %.0f%%",
                scammer.getReportCount(),
                scammer.getName(),
                scammer.getRiskScore() * 100
        );
        
        warningText.setText(warningMessage);
        warningText.setVisibility(TextView.VISIBLE);
        riskRatingBar.setRating(5); // Auto-set to maximum risk
    }

    /**
     * Clear warning message and reset risk rating.
     */
    private void clearWarning() {
        if (warningText != null) {
            warningText.setVisibility(TextView.GONE);
        }
        matchedScammer = null;
    }

    private void submitReport() {
        String title = sanitizeInput(titleInput.getText().toString());
        String description = sanitizeInput(descriptionInput.getText().toString());
        String phone = sanitizeInput(phoneInput.getText().toString());
        String email = sanitizeInput(emailInput.getText().toString());
        String category = categorySpinner.getSelectedItem().toString();
        int riskLevel = (int) riskRatingBar.getRating();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!phone.isEmpty() && !ScamDetectionUtil.isValidPhoneNumber(phone)) {
            phoneInput.setError("Invalid phone number format");
            return;
        }

        if (!email.isEmpty() && !ScamDetectionUtil.isValidEmail(email)) {
            emailInput.setError("Invalid email format");
            return;
        }

        ScamReport report = new ScamReport(title, description, category, riskLevel);
        report.setPhoneNumber(phone);
        report.setEmail(email);

        repository.addReport(report);

        String message = matchedScammer != null 
                ? "Report submitted! Known scammer flagged for investigation."
                : "Report submitted successfully";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearForm();
    }

    private String sanitizeInput(String input) {
        if (input == null) return "";
        return input.trim()
                   .replaceAll("[<>&\"]", "")
                   .substring(0, Math.min(input.length(), 1000));
    }

    private void clearForm() {
        titleInput.setText("");
        descriptionInput.setText("");
        phoneInput.setText("");
        emailInput.setText("");
        riskRatingBar.setRating(0);
        clearWarning();
    }
}
