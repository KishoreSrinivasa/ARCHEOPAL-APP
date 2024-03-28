package com.example.archeopal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.libraries.places.api.model.Autocomplete;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

public class addPostActivity extends AppCompatActivity {

    //declaring the variables
    private TextView locationTextView;
    private Button imageAddingButton, submissionButton;
    private EditText editTextTitle, editTextArticle;
    private ImageView photoShowCase;
    private String path;
    private Uri uri;
    private Place place;
    private ProgressBar progressBar;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_CODE = 101;

    private final ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                Place place = Autocomplete.getPlaceFromIntent(data);
                                locationTextView.setText(place.getName());
                            }
                        }
                    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        editTextTitle = findViewById(R.id.addPostTitleEditText);
        editTextArticle = findViewById(R.id.addPostArticleEditText);
        imageAddingButton = findViewById(R.id.addPostAddImageButton);
        submissionButton = findViewById(R.id.addPostSubmissionButton);
        photoShowCase = findViewById(R.id.addPostImageDisplay);
        ImageButton pickLocationButton = findViewById(R.id.addPostLocationButton);
        locationTextView = findViewById(R.id.addPostLocationTextView);
        progressBar = findViewById(R.id.addPostProgressBar);

        imageAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(addPostActivity.this)
                        .crop()
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

        pickLocationButton.setOnClickListener(v -> {
            Places.initialize(getApplicationContext(), "AIzaSyDFgecq8_uE2r7RCvrggdPcKhA7ucqv3yI");
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(addPostActivity.this);
            launcher.launch(intent);
        });

        submissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postTitle = editTextTitle.getText().toString();
                String postArticle = editTextArticle.getText().toString();

                if(TextUtils.isEmpty(postTitle)) {
                    Toast.makeText(addPostActivity.this, "Title is required", Toast.LENGTH_LONG).show();
                    editTextTitle.setError("Title is required");
                    editTextTitle.requestFocus();
                } else if(TextUtils.isEmpty(postArticle)) {
                    Toast.makeText(addPostActivity.this, "Article or description is required", Toast.LENGTH_LONG).show();
                    editTextArticle.setError("Article or description is required");
                    editTextArticle.requestFocus();
                } else if(uri == null) {
                    Toast.makeText(addPostActivity.this, "A picture is required",Toast.LENGTH_LONG).show();
                } else if(place == null) {
                    Toast.makeText(addPostActivity.this, "Select the location of the post refer",Toast.LENGTH_LONG).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    pushPostDate(postTitle, postArticle, uri, place);
                }
            }
        });
    }

    private void pushPostDate(String postTitle, String postArticle, Uri uri, Place place) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            photoShowCase.setImageURI(uri);
            Toast.makeText(addPostActivity.this, "Photo selected successfully",Toast.LENGTH_LONG).show();
        }

        //if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                place = Autocomplete.getPlaceFromIntent(data);
                locationTextView.setText(place.getName());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR && data != null) {
                Status status = Autocomplete.getStatusFromIntent(data);
                // Handle the error.
                Toast.makeText(addPostActivity.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Toast.makeText(addPostActivity.this, "Location is required.", Toast.LENGTH_LONG).show();
            }
        //}
    }
}