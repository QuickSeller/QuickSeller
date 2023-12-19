package com.asac.quickseller.Activities;

import static com.amplifyframework.auth.AuthUserAttributeKey.*;
import static com.asac.quickseller.Activities.VerifyActivity.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.asac.quickseller.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private String s3ImageKey = "";
    private EditText edFirstName, edPassword, edEmail;
    private Button saveImageButton;
    private ImageView editProfileImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);


        edFirstName = findViewById(R.id.edFirstName);
        edPassword = findViewById(R.id.edPassword);
        edEmail = findViewById(R.id.edEmail);
        saveImageButton = findViewById(R.id.saveImageButton);

        getUserDataAndPopulateFields();
        saveImageButton.setOnClickListener(v -> onSaveButtonClick());
//        editProfileImageView = findViewById(R.id.editImageView);

//        activityResultLauncher = getImagePickingActivityResultLauncher();
//        setUpAddImageButton();
    }


    private void getUserDataAndPopulateFields() {
        Amplify.API.query(
                ModelQuery.list(User.class, User.EMAIL.eq(Amplify.Auth.getCurrentUser().getUsername())),
                response -> {
                    if (response.hasData() && response.getData().iterator().hasNext()) {
                        User user = response.getData().iterator().next();
                        runOnUiThread(() -> {
                            edFirstName.setText(user.getUsername());
                            edPassword.setText(user.getPhoneNumber());
                            edEmail.setText(user.getEmail());

                            if (user.getImage() != null && !user.getImage().isEmpty()) {
                                // Load and display the image
                                Amplify.Storage.downloadFile(
                                        user.getImage(),
                                        new File(getApplication().getFilesDir(), user.getImage()),
                                        success ->
                                        {
                                            runOnUiThread(() -> {
                                                Bitmap bitmap = BitmapFactory.decodeFile(success.getFile().getPath());
                                                editProfileImageView.setImageBitmap(bitmap);
                                            });
                                        },
                                        failure ->
                                        {
                                            Log.e(TAG, "Unable to get image from S3 for reason: " + failure.getMessage());
                                        }
                                );
                            }


                            edFirstName.setEnabled(true);
                            edPassword.setEnabled(true);
                        });
                    } else {
                        Log.e(TAG, "User not found in the User table.");
                    }
                },
                failure -> {
                    Log.e(TAG, "Failed to query user from the User table: " + failure.getMessage());
                }
        );
    }

    private void onSaveButtonClick() {
        String updatedFirstName = edFirstName.getText().toString().trim();
        String updatedPassword = edPassword.getText().toString().trim();
        String updatedEmail = edEmail.getText().toString().trim();

        String updatedImageKey = s3ImageKey;

        updateUserDataInDynamoDB(updatedFirstName, updatedPassword, updatedEmail, updatedImageKey);
    }

    private void updateUserDataInDynamoDB(String updatedFirstName, String updatedPassword, String updatedEmail , String updatedImageKey) {
        Amplify.API.query(
                ModelQuery.list(User.class, User.EMAIL.eq(Amplify.Auth.getCurrentUser().getUsername())),
                response -> {
                    if (response.hasData() && response.getData().iterator().hasNext()) {
                        User user = response.getData().iterator().next();
                        user = user.copyOfBuilder()
                                .username(updatedFirstName)
                                .phoneNumber(updatedPassword)
                                .image(updatedImageKey)
                                .build();

                        Amplify.API.mutate(
                                ModelMutation.update(user),
                                success -> {
                                    runOnUiThread(() -> {
                                        // Optionally, hide loading indicator
                                        // ProgressBar progressBar = findViewById(R.id.progressbarAccount);
                                        // progressBar.setVisibility(View.GONE);

                                        // Optionally, show a success message or perform other actions
                                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                                        // Example: finish the activity
                                        finish();
                                    });
                                },
                                failure -> {
                                    Log.e(TAG, "Failed to update user data: " + failure.getMessage());
                                    runOnUiThread(() -> {
                                        // Optionally, hide loading indicator
                                        // ProgressBar progressBar = findViewById(R.id.progressbarAccount);
                                        // progressBar.setVisibility(View.GONE);

                                        // Optionally, show an error message or perform other actions
                                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                                    });
                                }
                        );
                    } else {
                        Log.e(TAG, "User not found in the User table.");
                    }
                },
                failure -> {
                    Log.e(TAG, "Failed to query user from the User table: " + failure.getMessage());
                }
        );
    }




//    private void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename, Uri pickedImageFileUri) {
//        try {
//            pickedImageInputStream.reset();
//        } catch (IOException e) {
//            Log.e(TAG, "IOException while resetting input stream");
//        }
//
//        Amplify.Storage.uploadInputStream(
//                pickedImageFilename,
//                pickedImageInputStream,
//                success -> {
//                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
//                    s3ImageKey = success.getKey();
//                    ImageView taskImageView = findViewById(R.id.editImageView);
//                    InputStream pickedImageInputStreamCopy = null;
//                    try {
//                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
//                        Bitmap bitmap = BitmapFactory.decodeStream(pickedImageInputStreamCopy);
//                        taskImageView.setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        Log.e(TAG, "Could not get file stream from URI! " + e.getMessage(), e);
//                    } finally {
//                        if (pickedImageInputStreamCopy != null) {
//                            try {
//                                pickedImageInputStreamCopy.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                },
//                failure -> {
//                    Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage());
//                }
//        );
//    }

//    private ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher() {
//        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher =
//                registerForActivityResult(
//                        new ActivityResultContracts.StartActivityForResult(),
//                        new ActivityResultCallback<ActivityResult>() {
//                            @Override
//                            public void onActivityResult(ActivityResult result) {
//                                if (result.getResultCode() == Activity.RESULT_OK) {
//                                    if (result.getData() != null) {
//                                        Uri pickedImageFileUri = result.getData().getData();
//                                        try {
//                                            InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
//                                            String pickedImageFilename = getFileNameFromUri(pickedImageFileUri);
//                                            Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
//                                            uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename, pickedImageFileUri);
//                                        } catch (FileNotFoundException e) {
//                                            Log.e(TAG, "Could not get file from file picker! " + e.getMessage(), e);
//                                        }
//                                    }
//                                } else {
//                                    Log.e(TAG, "Activity result error in ActivityResultLauncher.onActivityResult");
//                                }
//                            }
//                        });
//
//        return imagePickingActivityResultLauncher;
//    }
//    private void setUpSaveButton() {
//        Button saveButton = findViewById(R.id.saveImageButton);
//        saveButton.setOnClickListener(v -> {
//            saveTask(s3ImageKey);
//        });
//    }
//    private void saveTask(String imageS3Key) {
//        EditText edUsername = findViewById(R.id.edFirstName);
//        EditText edEmail = findViewById(R.id.edEmail);
//
//        String updatedUsername = edUsername.getText().toString();
//        String updatedEmail = edEmail.getText().toString();
//
//        AuthUser authUser = Amplify.Auth.getCurrentUser();
//
//        User taskToSave = User.builder()
//                .username(updatedUsername)
//                .email(updatedEmail)
//                .phoneNumber(authUser.getUserId())
//                .image(s3ImageKey)
//                .build();
//
//        Amplify.API.mutate(
//                ModelMutation.update(taskToSave),
//                successResponse -> {
//                    Log.i(TAG, "User data updated successfully");
//                    Snackbar.make(findViewById(R.id.editImageView), "Task saved!", Snackbar.LENGTH_SHORT).show();
//                    Intent resultIntent = new Intent();
//                    resultIntent.putExtra("updatedUsername", updatedUsername);
//                    resultIntent.putExtra("updatedEmail", updatedEmail);
//                    setResult(Activity.RESULT_OK, resultIntent);
//                    finish();
//                },
//                failureResponse -> {
//                    Log.e(TAG, "Failed to update user data: " + failureResponse.toString());
//                }
//        );
//    }

//    private void setUpAddImageButton() {
//        Button addImageButton = findViewById(R.id.addImageButton);
//        addImageButton.setOnClickListener(b -> {
//            launchImageSelectionIntent();
//        });
//    }

    private void launchImageSelectionIntent() {
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickingIntent.setType("image/*");
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});
        activityResultLauncher.launch(imageFilePickingIntent);
    }

    @SuppressLint("Range")
    public String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}