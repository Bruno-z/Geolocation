package fr.wcs.geolocalisationbestproject;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.gson.GsonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import roboguice.util.temp.Ln;

/**
 * Created by apprenti on 06/06/17.
 */

public class CurrentWeatherRequest extends GoogleHttpClientSpiceRequest<CurrentWeatherModel> {
    private final String baseUrl;

    public CurrentWeatherRequest( double latitude, double longitude, String apiKey ) {
        super( CurrentWeatherModel.class );
        this.baseUrl = String.format( "http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid="+apiKey+"&lang=fr");
    }

    @Override
    public CurrentWeatherModel loadDataFromNetwork() throws Exception {
        Ln.d( "Call web service " + baseUrl );
        CurrentWeatherModel request = getHttpRequestFactory()
                .buildGetRequest(new GenericUrl(baseUrl))
                .setParser(new GsonFactory().createJsonObjectParser())
                .execute()
                .parseAs(getResultType());
        return request;
    }

}
