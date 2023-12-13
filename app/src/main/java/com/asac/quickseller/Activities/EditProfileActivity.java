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
import android.view.View;
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
import java.util.Date;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private String s3ImageKey = "";
    EditText edFirstName ;
    EditText edEmail ;
    EditText edPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

            activityResultLauncher = getImagePickingActivityResultLauncher();
            setUpAddImageButton();
            setUpSaveButton();
            updateImageButtons();
            setUpDeleteImageButton();
        }


    private void setUpAddImageButton() {
        Button addImageButton = (Button) findViewById(R.id.addImageButton);
        addImageButton.setOnClickListener(b -> {
            launchImageSelectionIntent();
        });
    }

    private void setUpDeleteImageButton()
    {
        Button deleteImageButton = (Button)findViewById(R.id.deleteImageButton);
        String s3ImageKey = this.s3ImageKey;
        deleteImageButton.setOnClickListener(v ->
        {
            Amplify.Storage.remove(
                    s3ImageKey,
                    success ->
                    {
                        Log.i(TAG, "Succeeded in deleting file on S3! Key is: " + success.getKey());

                    },
                    failure ->
                    {
                        Log.e(TAG, "Failure in deleting file on S3 with key: " + s3ImageKey + " with error: " + failure.getMessage());
                    }
            );
            ImageView productImageView = findViewById(R.id.imageUser);
            productImageView.setImageResource(android.R.color.transparent);

            saveProfile("");
            switchFromDeleteButtonToAddButton(deleteImageButton);
        });
    }

    private void switchFromDeleteButtonToAddButton(Button deleteImageButton) {
        Button addImageButton = findViewById(R.id.addImageButton);
        deleteImageButton.setVisibility(View.INVISIBLE);
        addImageButton.setVisibility(View.VISIBLE);
    }

    private void switchFromAddButtonToDeleteButton(Button addImageButton) {
        Button deleteImageButton = findViewById(R.id.deleteImageButton);
        deleteImageButton.setVisibility(View.VISIBLE);
        addImageButton.setVisibility(View.INVISIBLE);
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
                                            switchFromAddButtonToDeleteButton(addImageButton);
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
                    saveProfile(s3ImageKey);
                    ImageView profileImageView = findViewById(R.id.imageUser);
                    InputStream pickedImageInputStreamCopy = null;
                    try {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(pickedImageInputStreamCopy);
                        profileImageView.setImageBitmap(bitmap);
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

    }



    private void setUpSaveButton()
    {
        Button saveButton = (Button)findViewById(R.id.saveImageButton);
        saveButton.setOnClickListener(v ->
        {
            saveProfile(s3ImageKey);
        });
    }
    private void updateImageButtons() {
        Button addImageButton = findViewById(R.id.addItemAddImageBtn);
        Button deleteImageButton = (Button) findViewById(R.id.AddItemDeleteImageBtn);
        runOnUiThread(() -> {
            if (s3ImageKey == null) {
                deleteImageButton.setVisibility(View.INVISIBLE);
                addImageButton.setVisibility(View.VISIBLE);
            } else {
                deleteImageButton.setVisibility(View.VISIBLE);
                addImageButton.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void saveProfile(String imageS3Key)
    {

        User profileToSave = User.builder()
                .username(edFirstName.getText().toString())
                .email(edEmail.getText().toString())
                .phoneNumber(edPhoneNumber.getText().toString())
                .image(imageS3Key)
                .build();
        Amplify.API.mutate(
                ModelMutation.update(profileToSave),
                successResponse ->
                {
                    Log.i(TAG, "EditProfileActivity.onCreate(): edited a profile successfully");
                    Snackbar.make(findViewById(R.id.saveImageButton), "profile saved!", Snackbar.LENGTH_SHORT).show();
                },
                failureResponse -> Log.i(TAG, "EditProfileActivity.onCreate(): failed with this response: " + failureResponse)
        );
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