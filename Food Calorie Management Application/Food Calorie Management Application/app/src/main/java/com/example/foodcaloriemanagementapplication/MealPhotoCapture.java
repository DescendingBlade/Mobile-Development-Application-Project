package com.example.foodcaloriemanagementapplication;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MealPhotoCapture extends Fragment {
    private static final String TAG = "MealPhotoCapture";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String FILE_PROVIDER_AUTHORITY = "com.example.foodcaloriemanagementapplication.fileprovider";

    private ImageView mealPhotoPreview;
    private Button takePhotoButton;
    private Button backButton;
    private EditText mealNameInput, caloriesInput, fatInput, proteinInput, carbsInput;
    private Button submitMealButton;
    private ProgressBar uploadProgressBar;
    private Uri photoUri;

    private MainActivityData mainActivityData;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private DatabaseManager databaseManager;
    private String uploadedPhotoUrl;
    private FirebaseAuth mAuth;

    private ActivityResultLauncher<String> requestPermissionLauncher;

    /**
     * Called when the fragment is created.
     * Initializes Firebase components, authentication, and registers permission launcher.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(requireContext());
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(requireContext(), "Camera permission is required to take photos", Toast.LENGTH_SHORT).show();
            }
        });

        signInAnonymously();
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meal_capture_photo, container, false);
        mainActivityData = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
        databaseManager = new DatabaseManager(requireContext());
        mealPhotoPreview = view.findViewById(R.id.mealPhotoPreview);
        takePhotoButton = view.findViewById(R.id.takePhotoButton);
        mealNameInput = view.findViewById(R.id.mealNameInput);
        caloriesInput = view.findViewById(R.id.caloriesInput);
        fatInput = view.findViewById(R.id.fatInput);
        proteinInput = view.findViewById(R.id.proteinInput);
        carbsInput = view.findViewById(R.id.carbsInput);
        submitMealButton = view.findViewById(R.id.submitMealButton);
        uploadProgressBar = view.findViewById(R.id.uploadProgressBar);
        backButton = view.findViewById(R.id.backButton);

        takePhotoButton.setOnClickListener(v -> checkCameraPermission());
        submitMealButton.setOnClickListener(v -> submitMeal());
        backButton.setOnClickListener(v -> mainActivityData.setClickedValue(2));
        return view;
    }

    /**
     * Checks if the app has camera permission. If granted, dispatches the take picture intent.
     * If not granted, requests the camera permission.
     */
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    /**
     * Dispatches the intent to capture a photo using the device's camera app.
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canProceed = true;
        File photoFile = null;

        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            System.out.println(TAG + "Error occurred while creating the file" + ex);
            Toast.makeText(requireContext(), "Error occurred while creating the file", Toast.LENGTH_SHORT).show();
            canProceed = false;
        }

        if (canProceed && photoFile != null) {
            photoUri = FileProvider.getUriForFile(requireContext(), FILE_PROVIDER_AUTHORITY, photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            launchCameraApp(takePictureIntent);
        }
    }

    /**
     * Launches the camera app with the provided intent.
     *
     * @param takePictureIntent The intent to launch the camera app.
     */
    private void launchCameraApp(Intent takePictureIntent) {
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "No camera app found", e);
            Toast.makeText(requireContext(), "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Creates a file to store the captured photo.
     *
     * @return The created file.
     * @throws IOException If an I/O error occurs while creating the file.
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(null);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    /**
     * Called when an activity launched by this fragment returns a result.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult().
     * @param resultCode  The integer result code returned by the child activity.
     * @param data        An Intent, which can return result data to the caller.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == android.app.Activity.RESULT_OK) {
            if (photoUri != null) {
                mealPhotoPreview.setImageURI(photoUri);
                uploadPhotoToFirebase();
            }
        }
    }

    /**
     * Uploads the captured photo to Firebase Storage.
     */
    private void uploadPhotoToFirebase() {
        boolean canProceed = true;

        if (photoUri == null) {
            canProceed = false;
        }

        if (canProceed && !isNetworkAvailable()) {
            Toast.makeText(requireContext(), "No internet connection. Please try again later.", Toast.LENGTH_SHORT).show();
            canProceed = false;
        }

        if (canProceed && mAuth.getCurrentUser() == null) {
            signInAnonymously();
            Toast.makeText(requireContext(), "Please sign in to upload photos.", Toast.LENGTH_SHORT).show();
            canProceed = false;
        }

        if (canProceed) {
            performUpload();
        }
    }

    /**
     * Performs the photo upload to Firebase Storage.
     */
    private void performUpload() {
        uploadProgressBar.setVisibility(View.VISIBLE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageName = "meal_" + timestamp + ".jpg";

        StorageReference imagesMealPhotosRef = storageRef.child("images/meal_photos/" + imageName);
        UploadTask uploadTask = imagesMealPhotosRef.putFile(photoUri);

        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            uploadProgressBar.setProgress((int) progress);
        }).addOnSuccessListener(taskSnapshot -> {
            uploadProgressBar.setVisibility(View.GONE);
            imagesMealPhotosRef.getDownloadUrl().addOnSuccessListener(uri -> {
                uploadedPhotoUrl = uri.toString();
                Toast.makeText(requireContext(), "Photo uploaded successfully", Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(this::handleUploadFailure);
    }

    /**
     * Handles the failure of the photo upload.
     *
     * @param e The exception that caused the failure.
     */
    private void handleUploadFailure(Exception e) {
        uploadProgressBar.setVisibility(View.GONE);
        if (e instanceof StorageException) {
            StorageException storageException = (StorageException) e;
            int errorCode = storageException.getErrorCode();
            String errorMessage = storageException.getMessage();
            Log.e(TAG, "Upload failed. Error code: " + errorCode + ", Message: " + errorMessage);
            Toast.makeText(requireContext(), "Upload failed. Error: " + errorMessage, Toast.LENGTH_LONG).show();
        } else {
            Log.e(TAG, "Upload failed with unexpected error", e);
            Toast.makeText(requireContext(), "Upload failed with unexpected error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if a network connection is available.
     *
     * @return true if a network connection is available, false otherwise.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    /**
     * Submits the meal data entered by the user.
     */
    private void submitMeal() {
        if (validateInputs() && uploadedPhotoUrl != null) {
            submitMealButton.setEnabled(false);
            saveMealToDatabase(uploadedPhotoUrl);
        } else {
            if (!validateInputs()) {
                // Toast message will be shown by validateInputs() method
            } else {
                Toast.makeText(requireContext(), "Please wait for the photo to upload", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Validates the user input for the meal data.
     *
     * @return true if the input is valid, false otherwise.
     */
    private boolean validateInputs() {
        String mealName = mealNameInput.getText().toString().trim();
        String caloriesStr = caloriesInput.getText().toString().trim();
        String fatStr = fatInput.getText().toString().trim();
        String proteinStr = proteinInput.getText().toString().trim();
        String carbsStr = carbsInput.getText().toString().trim();

        boolean isValid = !(mealName.isEmpty() || photoUri == null || caloriesStr.isEmpty() || fatStr.isEmpty() || proteinStr.isEmpty() || carbsStr.isEmpty());

        if (!isValid) {
            String errorMessage = "";
            if (mealName.isEmpty()) {
                errorMessage += "Please enter a meal name. ";
            }
            if (photoUri == null) {
                errorMessage += "Please take a photo. ";
            }
            if (caloriesStr.isEmpty()) {
                errorMessage += "Please enter the calories. ";
            }
            if (fatStr.isEmpty()) {
                errorMessage += "Please enter the fat content. ";
            }
            if (proteinStr.isEmpty()) {
                errorMessage += "Please enter the protein content. ";
            }
            if (carbsStr.isEmpty()) {
                errorMessage += "Please enter the carbohydrates content. ";
            }
            Toast.makeText(requireContext(), errorMessage.trim(), Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    /**
     * Saves the meal data to the database and updates the current calorie count.
     *
     * @param photoUrl The URL of the uploaded meal photo.
     */
    private void saveMealToDatabase(String photoUrl) {
        String mealName = mealNameInput.getText().toString().trim();
        double calories = Double.parseDouble(caloriesInput.getText().toString().trim());
        double fat = Double.parseDouble(fatInput.getText().toString().trim());
        double protein = Double.parseDouble(proteinInput.getText().toString().trim());
        double carbs = Double.parseDouble(carbsInput.getText().toString().trim());

        FoodEntry newMeal = new FoodEntry(
                mainActivityData.getUsername(),
                mealName,
                1, // Default serving size
                calories,
                fat,
                protein,
                carbs,
                photoUrl
        );

        databaseManager.addFoodEntry(newMeal, result -> {
            uploadProgressBar.setVisibility(View.GONE);
            submitMealButton.setEnabled(true);
            Toast.makeText(requireContext(), "Meal added successfully", Toast.LENGTH_SHORT).show();

            // Update the current calorie count in MainActivityData
            int currentCalories = mainActivityData.getCurrentCalorie();
            mainActivityData.setCurrentCalorie((int) (currentCalories + calories));

            mainActivityData.setClickedValue(4); // Navigate back to FoodLog
        });
    }

    /**
     * Signs in the user anonymously.
     */
    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInAnonymously:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        Log.w(TAG, "signInAnonymously:failure", task.getException());
                        Toast.makeText(requireContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}