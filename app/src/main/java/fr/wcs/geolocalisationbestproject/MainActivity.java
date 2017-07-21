package fr.wcs.geolocalisationbestproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.GsonGoogleHttpClientSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import static android.R.attr.apiKey;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private LocationManager mlocationManager = null;
    private LocationListener mlocationListener;
    private double longitude;
    private double latitude;
    private String API_KEY;
    private ListView listviewMeteo;
    private TextView textViewVille;
    private TextView textViewDate;
    private ImageView imageViewMeteo;
    private TextView textViewTempMatin;
    private TextView textViewTempAprem;
    protected SpiceManager spiceManager = new SpiceManager(GsonGoogleHttpClientSpiceService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API_KEY = getString(R.string.API_kEY);
        listviewMeteo = (ListView) findViewById(R.id.listviewMeteo);
        textViewVille = (TextView) findViewById(R.id.textViewVille);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewTempMatin = (TextView) findViewById(R.id.textViewTempMatin);
        imageViewMeteo = (ImageView) findViewById(R.id.imageViewMeteo);
        textViewTempAprem = (TextView)findViewById(R.id.textViewTempAprem);
        mlocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mlocationListener = new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {

                location.getLongitude();
                location.getLatitude();
                permissionRequest();

                Toast.makeText(getApplicationContext(), "Your Location is  \nLat: " + location.getLatitude() + "\nLong: " + location.getLongitude(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {


            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {


            }

        };
    }


    @Override
    protected void onPause() {
        super.onPause();
        mlocationManager.removeUpdates(mlocationListener);

    }
        @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mlocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocationListener);
            mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocationListener);
        }


    @Override
    protected void onStart() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);


            //    ActivityCompat#requestPermissions

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocationListener);
        mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocationListener);
        spiceManager.start(this);
        super.onStart();


        /*adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listviewMeteo.setSelection(adapter.getCount() - 1);
            }
        });*/
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        mlocationManager.removeUpdates(mlocationListener);
        super.onStop();
    }
    private void permissionRequest() {
        MainActivity.this.setProgressBarIndeterminateVisibility(true);
        /*ForecastWeatherRequest forecastRequest = new ForecastWeatherRequest(latitude, longitude, apiKey);
        spiceManager.execute(forecastRequest, new ForecastRequestListener());*/

        CurrentWeatherRequest currentRequest = new CurrentWeatherRequest(latitude, longitude, API_KEY);
        spiceManager.execute(currentRequest, new CurrentRequestListener());
    }



    private class CurrentRequestListener implements RequestListener<CurrentWeatherModel> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            //Update UI
        }

        @Override
        public void onRequestSuccess(CurrentWeatherModel currentWeatherModel) {
            int temp = (int)(currentWeatherModel.getMain().getTemp()- 273.16);
            if (currentWeatherModel.getName().length()>0){

                textViewVille.setText("Température:" +temp);

            }
    }
    }
}

