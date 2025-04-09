package com.example.instarescue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageView alertIcon;
    private ImageView accidentImage;

    private static final String BASE_URL = "http://10.10.174.238:5001/";
    private AccidentApi apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Init views
        alertIcon = findViewById(R.id.alertIcon);
        accidentImage = findViewById(R.id.accidentImage);
        TextView alertText = findViewById(R.id.alertText);
        // Hide icons initially
        alertIcon.setVisibility(View.GONE);
        accidentImage.setVisibility(View.GONE);
        alertText.setVisibility(View.GONE);


        // Load map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Setup Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(AccidentApi.class);

        // Fetch accident data
        checkForAccidents();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void checkForAccidents() {
        apiService.checkAccident().enqueue(new Callback<AccidentResponse>() {
            @Override
            public void onResponse(Call<AccidentResponse> call, Response<AccidentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccidentResponse accident = response.body();
                    if (accident.isAccidentDetected()) {
                        showAccidentData(
                                accident.getLatitude(),
                                accident.getLongitude(),
                                accident.getImageBase64()
                        );
                    }
                } else {
                    Log.e("API_RESPONSE", "Empty or failed response");
                }
            }

            @Override
            public void onFailure(Call<AccidentResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch accident data", t);
            }
        });
    }

    private void showAccidentData(double lat, double lng, String base64Image) {
        TextView alertText = findViewById(R.id.alertText);
        alertIcon.setVisibility(View.VISIBLE);
        alertText.setVisibility(View.VISIBLE);


        // Place marker on map
        LatLng accidentLocation = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions()
                .position(accidentLocation)
                .title("Accident Detected")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(accidentLocation, 15f));

        // Decode and display image
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                accidentImage.setImageBitmap(bitmap);
                accidentImage.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Log.e("ImageDecode", "Error decoding image", e);
            }
        }
    }
}
