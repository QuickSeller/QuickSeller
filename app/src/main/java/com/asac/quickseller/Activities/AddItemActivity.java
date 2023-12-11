package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.CityEnum;
import com.amplifyframework.datastore.generated.model.Post;
import com.amplifyframework.datastore.generated.model.Product;
import com.amplifyframework.datastore.generated.model.ProductCategoryEnum;



import com.asac.quickseller.R;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity {

    public static final String TAG = "AddItemActivity";

    Spinner productCategorySpinner = null;
    Spinner citiesSpinner = null;
    Button addItemBtn = null;
    ImageButton back = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);

        setupSpinners();
        setupAddProductBtn();
        setupBackBtn();

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
            saveNewProduct(title, description, price);

        });

    }
    private void setupBackBtn(){
        back= (ImageButton) findViewById(R.id.backFromAddItem);
        back.setOnClickListener(b -> {

            Intent backToHome = new Intent(AddItemActivity.this,HomeActivity.class);
            startActivity(backToHome);

        });
    }

    private void saveNewProduct(String title, String description,String price){


        ArrayList<String> imageList=new ArrayList<>();

        Post newPost = Post.builder()
                .city((CityEnum) citiesSpinner.getSelectedItem())
                .title(title)
                .price(price)
                .productCategory((ProductCategoryEnum) productCategorySpinner.getSelectedItem())
                .images(imageList)
                .createdAt(new Temporal.DateTime(new Date(), 0))
                .description(description)
                .build();



        if(title == ""){
            Snackbar.make(findViewById(R.id.addItem),"Please add your product name", Snackbar.LENGTH_SHORT).show();
        }else if(description == ""){
            Snackbar.make(findViewById(R.id.addItem),"Please add description about your product", Snackbar.LENGTH_SHORT).show();
        } else if (price == "") {
            Snackbar.make(findViewById(R.id.addItem),"Please add your product price", Snackbar.LENGTH_SHORT).show();
        }else{
            Amplify.API.mutate(
                    ModelMutation.create(newPost),
                    success -> {
                        Log.i(TAG, "AddItemActivity(): Item added Successfully" + success.toString());
                        runOnUiThread(() -> {
                            Snackbar.make(findViewById(R.id.addItem),"New Post Added", Snackbar.LENGTH_SHORT).show();
                        });
                    },
                    failure -> {
                        Log.e(TAG, "AddItemActivity(): Failed to add Item" + failure.toString());

                    }
            );
        }

    }
}