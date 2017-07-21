package fr.wcs.geolocalisationbestproject;


import java.io.IOException;
import roboguice.util.temp.Ln;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Key;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
 /*

public class ForecastWeatherRequest extends GoogleHttpClientSpiceRequest< CurrentWeatherModel > {

   @Key private String baseUrl;

    public ForecastWeatherRequest( ) {
        super( CurrentWeatherModel.class );
        this.baseUrl = String.format( "http://www.myweather2.com/developer/forecast.ashx?uac=AQmS68n6Ku&query=%s&output=json" );
    }

    @Override
    public CurrentWeatherModel loadDataFromNetwork() throws IOException {
        Ln.d( "Call web service " + baseUrl );
        HttpRequest request = getHttpRequestFactory()//
                .buildGetRequest( new GenericUrl( baseUrl ) );
        request.setParser( new GsonFactory().createJsonObjectParser() );
        return request.execute().parseAs( getResultType() );
    }

}
 */