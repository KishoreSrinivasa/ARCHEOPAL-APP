package com.example.archeopal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class addPostActivity extends AppCompatActivity {

    //declaring the variables
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private String selectedDistrict, selectedState, imageUri, userId;
    private Spinner stateSpinner, districtSpinner;
    private ArrayAdapter<CharSequence> districtAdapter;
    private EditText editTextTitle, editTextArticle;
    private ImageView photoShowCase;
    private Uri uri;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        editTextTitle = findViewById(R.id.addPostTitleEditText);
        editTextArticle = findViewById(R.id.addPostArticleEditText);
        Button imageAddingButton = findViewById(R.id.addPostAddImageButton);
        Button submissionButton = findViewById(R.id.addPostSubmissionButton);
        photoShowCase = findViewById(R.id.addPostImageDisplay);
        progressBar = findViewById(R.id.addPostProgressBar);
        stateSpinner = findViewById(R.id.spinner_indian_states);

        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(addPostActivity.this,
                R.array.array_indian_states, R.layout.location_dropdown);

        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Define City Spinner but we will populate the options through the selected state
                districtSpinner = findViewById(R.id.spinner_indian_districts);

                selectedState = stateSpinner.getSelectedItem().toString();      //Obtain the selected State

                int parentID = parent.getId();
                if (parentID == R.id.spinner_indian_states){
                    switch (selectedState){
                        case "Select Your State": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_default_districts, R.layout.location_dropdown);
                            break;
                        case "Andhra Pradesh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_andhra_pradesh_districts, R.layout.location_dropdown);
                            break;
                        case "Arunachal Pradesh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_arunachal_pradesh_districts, R.layout.location_dropdown);
                            break;
                        case "Assam": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_assam_districts, R.layout.location_dropdown);
                            break;
                        case "Bihar": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_bihar_districts, R.layout.location_dropdown);
                            break;
                        case "Chhattisgarh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_chhattisgarh_districts, R.layout.location_dropdown);
                            break;
                        case "Goa": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_goa_districts, R.layout.location_dropdown);
                            break;
                        case "Gujarat": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_gujarat_districts, R.layout.location_dropdown);
                            break;
                        case "Haryana": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_haryana_districts, R.layout.location_dropdown);
                            break;
                        case "Himachal Pradesh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_himachal_pradesh_districts, R.layout.location_dropdown);
                            break;
                        case "Jharkhand": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_jharkhand_districts, R.layout.location_dropdown);
                            break;
                        case "Karnataka": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_karnataka_districts, R.layout.location_dropdown);
                            break;
                        case "Kerala": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_kerala_districts, R.layout.location_dropdown);
                            break;
                        case "Madhya Pradesh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_madhya_pradesh_districts, R.layout.location_dropdown);
                            break;
                        case "Maharashtra": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_maharashtra_districts, R.layout.location_dropdown);
                            break;
                        case "Manipur": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_manipur_districts, R.layout.location_dropdown);
                            break;
                        case "Meghalaya": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_meghalaya_districts, R.layout.location_dropdown);
                            break;
                        case "Mizoram": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_mizoram_districts, R.layout.location_dropdown);
                            break;
                        case "Nagaland": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_nagaland_districts, R.layout.location_dropdown);
                            break;
                        case "Odisha": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_odisha_districts, R.layout.location_dropdown);
                            break;
                        case "Punjab": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_punjab_districts, R.layout.location_dropdown);
                            break;
                        case "Rajasthan": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_rajasthan_districts, R.layout.location_dropdown);
                            break;
                        case "Sikkim": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_sikkim_districts, R.layout.location_dropdown);
                            break;
                        case "Tamil Nadu": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_tamil_nadu_districts, R.layout.location_dropdown);
                            break;
                        case "Telangana": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_telangana_districts, R.layout.location_dropdown);
                            break;
                        case "Tripura": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_tripura_districts, R.layout.location_dropdown);
                            break;
                        case "Uttar Pradesh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_uttar_pradesh_districts, R.layout.location_dropdown);
                            break;
                        case "Uttarakhand": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_uttarakhand_districts, R.layout.location_dropdown);
                            break;
                        case "West Bengal": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_west_bengal_districts, R.layout.location_dropdown);
                            break;
                        case "Andaman and Nicobar Islands": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_andaman_nicobar_districts, R.layout.location_dropdown);
                            break;
                        case "Chandigarh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_chandigarh_districts, R.layout.location_dropdown);
                            break;
                        case "Dadra and Nagar Haveli": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_dadra_nagar_haveli_districts, R.layout.location_dropdown);
                            break;
                        case "Daman and Diu": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_daman_diu_districts, R.layout.location_dropdown);
                            break;
                        case "Delhi": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_delhi_districts, R.layout.location_dropdown);
                            break;
                        case "Jammu and Kashmir": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_jammu_kashmir_districts, R.layout.location_dropdown);
                            break;
                        case "Lakshadweep": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_lakshadweep_districts, R.layout.location_dropdown);
                            break;
                        case "Ladakh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_ladakh_districts, R.layout.location_dropdown);
                            break;
                        case "Puducherry": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_puducherry_districts, R.layout.location_dropdown);
                            break;
                        default:  break;
                    }
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears
                    districtSpinner.setAdapter(districtAdapter);        //Populate the list of Districts in respect of the State selected

                    //To obtain the selected District from the spinner
                    districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedDistrict = districtSpinner.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        imageAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(addPostActivity.this)
                        .crop()
                        .maxResultSize(1080, 1080)
                        .start();
            }
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
                } else if(selectedState.equals("Select Your State")) {
                    Toast.makeText(addPostActivity.this, "Select the state that the post belongs to", Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(selectedDistrict) || selectedDistrict.equals("Select Your District")) {
                    Toast.makeText(addPostActivity.this, "Select the district that the post belongs to", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(addPostActivity.this, "Data's Verified successfully", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.VISIBLE);
                    storeImage();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser != null) {
            userId = currentUser.getUid();
        } else {
            Intent intent = new Intent(addPostActivity.this, loginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(addPostActivity.this, "Please, Login", Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }
    }

    private void storeImage() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Post Image").
                child(uri.getLastPathSegment());

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri storedUri = uriTask.getResult();
                imageUri = storedUri.toString();
                pushPostDate();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(addPostActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void pushPostDate() {
        //get data
        String title = editTextTitle.getText().toString(), article = editTextArticle.getText().toString();

        //getting the reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Post Data");

        //creating the data
        PostDetails postDetails = new PostDetails(title, article, imageUri, selectedState, selectedDistrict);

        //uploading the data
        databaseReference.child(userId).setValue(postDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(addPostActivity.this, "Post created successfully", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(addPostActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ImagePicker.REQUEST_CODE) {
                // Image Picker result
                if (data != null) {
                    uri = data.getData();
                    if (uri != null) {
                        // Image selected successfully
                        photoShowCase.setImageURI(uri);
                        Toast.makeText(addPostActivity.this, "Photo selected successfully", Toast.LENGTH_LONG).show();
                    } else {
                        // No image selected
                        Toast.makeText(addPostActivity.this, "No image selected", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // No data received
                    Toast.makeText(addPostActivity.this, "No data received", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}