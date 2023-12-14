package com.asac.quickseller.Activities;

import static com.amplifyframework.core.Amplify.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asac.quickseller.R;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);



        EditText editTextOldPassword = findViewById(R.id.editTextOldPassword);
        EditText editTextNewPassword = findViewById(R.id.editTextNewPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonChangePassword = findViewById(R.id.buttonChangePassword);

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = editTextOldPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (newPassword.equals(confirmPassword)) {
                    Auth.updatePassword(
                            oldPassword,
                            newPassword,
                            () -> runOnUiThread(() -> {

                                Log.i("change", "Changed password");
                                Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                            }),
                            error -> runOnUiThread(() -> {
                                Log.i("no change", "failed Changing password");

                                Toast.makeText(ChangePasswordActivity.this, "Error changing password: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                // Handle error scenario
                            })
                    );
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}