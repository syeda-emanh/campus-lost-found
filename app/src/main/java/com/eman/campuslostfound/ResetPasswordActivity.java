package com.eman.campuslostfound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ResetPasswordActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextInputLayout tilNewPassword, tilConfirmNewPassword;
    private TextInputEditText etNewPassword, etConfirmNewPassword;
    private MaterialButton btnResetPassword;
    private UserSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        sessionManager = new UserSessionManager(this);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        tilNewPassword = findViewById(R.id.til_new_password);
        tilConfirmNewPassword = findViewById(R.id.til_confirm_new_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmNewPassword = findViewById(R.id.et_confirm_new_password);
        btnResetPassword = findViewById(R.id.btn_reset_password);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnResetPassword.setOnClickListener(v -> attemptPasswordReset());
    }

    private void attemptPasswordReset() {
        tilNewPassword.setError(null);
        tilConfirmNewPassword.setError(null);

        String newPassword = etNewPassword.getText() != null ? etNewPassword.getText().toString().trim() : "";
        String confirmPassword = etConfirmNewPassword.getText() != null ? etConfirmNewPassword.getText().toString().trim() : "";

        boolean isValid = true;

        if (newPassword.isEmpty()) {
            tilNewPassword.setError(getString(R.string.err_empty_field));
            isValid = false;
        } else if (newPassword.length() < 6) {
            tilNewPassword.setError(getString(R.string.err_password_short));
            isValid = false;
        }

        if (confirmPassword.isEmpty()) {
            tilConfirmNewPassword.setError(getString(R.string.err_empty_field));
            isValid = false;
        } else if (!confirmPassword.equals(newPassword)) {
            tilConfirmNewPassword.setError(getString(R.string.err_passwords_dont_match));
            isValid = false;
        }

        if (isValid) {
            sessionManager.updatePassword(newPassword);
            Toast.makeText(this, getString(R.string.msg_password_reset_success), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}