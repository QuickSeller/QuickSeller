package com.asac.quickseller.Activities;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.asac.quickseller.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private String s3ImageKey = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        Intent intent = getIntent();
        if (intent != null) {
            String email = intent.getStringExtra(SignupActivity.SIGNUP_EMAIL_TAG);
            String nickname = intent.getStringExtra("NICKNAME_TAG");

            EditText edFirstName = findViewById(R.id.edFirstName);
            EditText edEmail = findViewById(R.id.edEmail);

            edFirstName.setText(nickname);
            edEmail.setText(email);
            activityResultLauncher = getImagePickingActivityResultLauncher();
            setUpAddImageButton();
            saveTask();
        }

    }

    private ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher() {
        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                Button addImageButton = findViewById(R.id.addImageButton);
                                if (result.getResultCode() == Activity.RESULT_OK) {
                                    if (result.getData() != null) {
                                        Uri pickedImageFileUri = result.getData().getData();
                                        try {
                                            InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
                                            String pickedImageFilename = getFileNameFromUri(pickedImageFileUri);
                                            Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
                                            uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename, pickedImageFileUri);
                                        } catch (FileNotFoundException e) {
                                            Log.e(TAG, "Could not get file from file picker! " + e.getMessage(), e);
                                        }
                                    }
                                } else {
                                    Log.e(TAG, "Activity result error in ActivityResultLauncher.onActivityResult");
                                }
                            }
                        });

        return imagePickingActivityResultLauncher;
    }
    private void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename, Uri pickedImageFileUri) {
        try {
            pickedImageInputStream.reset();
        } catch (IOException e) {
            Log.e(TAG, "IOException while resetting input stream");
        }

        Amplify.Storage.uploadInputStream(
                pickedImageFilename,
                pickedImageInputStream,
                success -> {
                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
                    s3ImageKey = success.getKey();
                    saveTask(s3ImageKey);
                    ImageView taskImageView = findViewById(R.id.imageUser);
                    InputStream pickedImageInputStreamCopy = null;
                    try {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(pickedImageInputStreamCopy);
                        taskImageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.e(TAG, "Could not get file stream from URI! " + e.getMessage(), e);
                    } finally {
                        if (pickedImageInputStreamCopy != null) {
                            try {
                                pickedImageInputStreamCopy.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                failure -> {
                    Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage());
                }
        );
        String newUsername = "newUsername";
        String newEmail = "newEmail@example.com";
        String newPassword = "newPassword123";
        updateUserInformation(newUsername, newEmail, newPassword);
    }
    private void updateUserInformation(String newUsername, String newEmail, String newPassword) {
//        User updatedUser = User.builder()
//                //.username(newUsername)
//               // .email(newEmail)
//               // .password(newPassword)
//                .image(s3ImageKey)
//                .build();


//        Amplify.API.mutate(
//                ModelMutation.update(updatedUser),
//                successResponse -> {
//                    Log.i(TAG, "User information updated successfully");
//                    Snackbar.make(findViewById(R.id.imageUser), "User information updated!", Snackbar.LENGTH_SHORT).show();
//                },
//                failureResponse -> {
//                    Log.e(TAG, "Failed to update user information: " + failureResponse.getMessage());
//                    Snackbar.make(findViewById(R.id.imageUser), "Failed to update user information", Snackbar.LENGTH_SHORT).show();
//                }
//        );
    }
    private void setUpAddImageButton() {
        Button addImageButton = findViewById(R.id.addImageButton);
        addImageButton.setOnClickListener(b -> {
            launchImageSelectionIntent();
        });
    }
    private void saveTask(String imageS3Key) {
//        Button saveButton = findViewById(R.id.saveImageButton);
//        saveButton.setOnClickListener(v -> {
//            User profileToSave = User.builder()
//                    .productImageS3Key(imageS3Key)
//                    .build();
//           // String imageKey = "placeholderImageKey";
//           // updateUserInformation("newUsername", "newEmail@example.com", "newPassword123");
//            Amplify.API.mutate(
//                    ModelMutation.update(profileToSave),
//                    successResponse -> {
//                        Log.i(TAG, "EditActivity.onCreate(): edited a task successfully");
//                        Snackbar.make(findViewById(R.id.imageUser), "Task saved!", Snackbar.LENGTH_SHORT).show();
//                    },
//                    failureResponse -> Log.i(TAG, "EditActivity.onCreate(): failed with this response: " + failureResponse)
//            );
//        });
    }
    private void saveTask() {
        Button saveButton = findViewById(R.id.saveImageButton);
        saveButton.setOnClickListener(v -> {
            String imageKey = "placeholderImageKey";
            updateUserInformation("newUsername", "newEmail@example.com", "newPassword123");
        });
    }
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
                cursor.close();
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
    }}