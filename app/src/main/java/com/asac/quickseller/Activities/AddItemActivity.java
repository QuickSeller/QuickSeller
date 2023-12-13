package com.asac.quickseller.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.CityEnum;
import com.amplifyframework.datastore.generated.model.Post;
import com.amplifyframework.datastore.generated.model.ProductCategoryEnum;


import com.amplifyframework.datastore.generated.model.User;
import com.asac.quickseller.R;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    public static final String TAG = "AddItemActivity";
    Spinner productCategorySpinner = null;
    Spinner citiesSpinner = null;
    Button addItemBtn = null;
    ImageButton back = null;
    Button addProductImageBtn = null;
    ActivityResultLauncher<Intent> activityResultLauncher ;

    String[] s3ImageKey= {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);

        activityResultLauncher = getImagePickerActivityLauncher();

        setupSpinners();
        setupAddProductBtn();
        setupBackBtn();
        setupAddProductImageBtn();
        setUpDeleteImageButton();

    }

    @Override
    protected void onResume(){
        super.onResume();
        setUpDeleteImageButton();


        Intent callingIntent = getIntent();

        if (callingIntent != null && callingIntent.getType() != null && callingIntent.getType().equals("text/plain")) {
            String callingText = callingIntent.getStringExtra(Intent.EXTRA_TEXT);
            String cleanText = cleanText(callingText);

            ((EditText) findViewById(R.id.productNameAddItemEditText)).setText(cleanText);
        }

        if (callingIntent != null && callingIntent.getType() != null && callingIntent.getType().startsWith("image")) {
            Uri incomingImageFileUri = callingIntent.getParcelableExtra(Intent.EXTRA_STREAM);

            if (incomingImageFileUri != null) {
                InputStream incomingImageFileInputStream = null;

                try {
                    incomingImageFileInputStream = getContentResolver().openInputStream(incomingImageFileUri);

                    ImageView productImage = findViewById(R.id.AddItemimageView);

                    if (productImage != null) {

                        productImage.setImageBitmap(BitmapFactory.decodeStream(incomingImageFileInputStream));
                    } else {
                        Log.e(TAG, "ImageView is null");
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    Log.e(TAG, " Could not get file stream from the URI " + fileNotFoundException.getMessage(), fileNotFoundException);
                }
            }
        }

    }

   private void setupAddProductImageBtn(){
    addProductImageBtn = (Button) findViewById(R.id.addItemAddImageBtn);
    addProductImageBtn.setOnClickListener(b -> {
        launchImageSelectionIntent();
    });
}
    private void launchImageSelectionIntent(){
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickingIntent.setType("*/*");
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});
        activityResultLauncher.launch(imageFilePickingIntent);
    }
    private ActivityResultLauncher<Intent> getImagePickerActivityLauncher(){
        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>()
                        {
                            @Override
                            public void onActivityResult(ActivityResult result)
                            {
                                Button addImageButton = findViewById(R.id.addItemAddImageBtn);
                                if (result.getResultCode() == Activity.RESULT_OK)
                                {
                                    if (result.getData() != null)
                                    {
                                        Uri pickedImageFileUri = result.getData().getData();
                                        try
                                        {
                                            InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
                                            String pickedImageFilename = getFileNameFromUri(pickedImageFileUri);
                                            Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
                                            switchFromAddButtonToDeleteButton(addImageButton);
                                            uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename,pickedImageFileUri);

                                        } catch (FileNotFoundException fnfe)
                                        {
                                            Log.e(TAG, "Could not get file from file picker! " + fnfe.getMessage(), fnfe);
                                        }
                                    }
                                }
                                else
                                {
                                    Log.e(TAG, "Activity result error in ActivityResultLauncher.onActivityResult");
                                }
                            }
                        }
                );

        return imagePickingActivityResultLauncher;
    }

    private void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename,Uri pickedImageFileUri){
        Amplify.Storage.uploadInputStream(
                pickedImageFilename,
                pickedImageInputStream,
                success ->
                {
                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
                    s3ImageKey = new String[]{success.getKey()};
//                    saveNewProduct(success.getKey());
                    updateImageButtons();
                    ImageView productImageView = findViewById(R.id.AddItemimageView);
                    InputStream pickedImageInputStreamCopy = null;
                    try
                    {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
                    }
                    catch (FileNotFoundException fnfe)
                    {
                        Log.e(TAG, "Could not get file stream from URI! " + fnfe.getMessage(), fnfe);
                    }
                    productImageView.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy));

                },
                failure ->
                {
                    Log.e(TAG, "Failed to upload file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage());
                }
        );

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
    private void switchFromDeleteButtonToAddButton(Button deleteImageButton) {
        Button addImageButton = (Button) findViewById(R.id.addItemAddImageBtn);
        deleteImageButton.setVisibility(View.INVISIBLE);
        addImageButton.setVisibility(View.VISIBLE);
    }
    private void switchFromAddButtonToDeleteButton(Button addImageButton) {
        Button deleteImageButton = (Button) findViewById(R.id.AddItemDeleteImageBtn);
        deleteImageButton.setVisibility(View.VISIBLE);
        addImageButton.setVisibility(View.INVISIBLE);
    }

    private void setUpDeleteImageButton() {
        Button deleteImageButton = (Button) findViewById(R.id.AddItemDeleteImageBtn);
//        String s3ImageKey = this.s3ImageKey;
        deleteImageButton.setOnClickListener(v ->
        {
            Amplify.Storage.remove(
                    Arrays.toString(s3ImageKey),
                    success ->
                    {
                        Log.i(TAG, "Succeeded in deleting file on S3! Key is: " + success.getKey());

                    },
                    failure ->
                    {
                        Log.e(TAG, "Failure in deleting file on S3 with key: " +  s3ImageKey  + " with error: " + failure.getMessage());
                    }
            );
            ImageView productImageView = findViewById(R.id.AddItemimageView);
            productImageView.setImageResource(android.R.color.transparent);
            saveNewProduct("","","", new String[]{""});
            switchFromDeleteButtonToAddButton(deleteImageButton);
        });
    }

    private String cleanText(String text) {
        text = text.replaceAll("\\b(?:https?|ftp):\\/\\/\\S+\\b", "");
        text = text.replaceAll("\"", "");
        return text;
    }
    private void setupSpinners() {
        productCategorySpinner = (Spinner) findViewById(R.id.AddItemCategoryspinner);
        productCategorySpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                ProductCategoryEnum.values()
        ));

        citiesSpinner = (Spinner) findViewById(R.id.AddItemCitiesSpinner);
        citiesSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                CityEnum.values()
        ));
    }
    private void setupAddProductBtn() {
        addItemBtn = (Button) findViewById(R.id.addItemButton);
        addItemBtn.setOnClickListener(b -> {

            String title = ((EditText) findViewById(R.id.productNameAddItemEditText)).getText().toString();
            String description = ((EditText) findViewById(R.id.descriptionAddItemEditText)).getText().toString();
            String dateCreated = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());
            String price = ((EditText) findViewById(R.id.priceAddItemEditText)).getText().toString();
            saveNewProduct(title, description, price,s3ImageKey);

        });

    }
    private void setupBackBtn(){
        back= (ImageButton) findViewById(R.id.backFromAddItem);
        back.setOnClickListener(b -> {

            Intent backToHome = new Intent(AddItemActivity.this,HomeActivity.class);
            startActivity(backToHome);

        });
    }
    private void saveNewProduct(String title, String description, String price, String[] imageList) {
        Amplify.API.query(
                ModelQuery.list(User.class, User.EMAIL.eq(Amplify.Auth.getCurrentUser().getUsername())),
                response -> {
                    if (response.hasData() && response.getData().iterator().hasNext()) {
                        User user = response.getData().iterator().next();
                        Post newPost = Post.builder()
                                .user(user)
                                .city(String.valueOf((CityEnum) citiesSpinner.getSelectedItem()))
                                .title(title)
                                .price(price)
                                .productCategory((ProductCategoryEnum) productCategorySpinner.getSelectedItem())
                                .createdAt(new Temporal.DateTime(new Date(), 0))
                                .description(description)
                                .images(List.of(imageList))
                                .build();

                        Amplify.API.mutate(
                                ModelMutation.create(newPost),
                                success -> {
                                    Log.i(TAG, "AddItemActivity(): Item added Successfully" + success.toString());
                                    runOnUiThread(() -> {
                                        Snackbar.make(findViewById(R.id.addItem), "New Post Added", Snackbar.LENGTH_SHORT).show();
                                    });
                                },
                                failure -> {
                                    Log.e(TAG, "AddItemActivity(): Failed to add Item" + failure.toString());
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
    }

}