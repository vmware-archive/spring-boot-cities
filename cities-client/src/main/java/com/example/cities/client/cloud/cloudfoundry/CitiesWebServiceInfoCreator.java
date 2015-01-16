package com.example.cities.client.cloud.cloudfoundry;

import com.example.cities.client.cloud.WebServiceInfo;
import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import java.util.Map;

public class CitiesWebServiceInfoCreator extends CloudFoundryServiceInfoCreator<WebServiceInfo> {

    public static final String CITIES_PREFIX = "cities";

    public CitiesWebServiceInfoCreator() {
        super(new Tags(), CITIES_PREFIX);
    }

    @Override
    public WebServiceInfo createServiceInfo(Map<String, Object> serviceData) {
        String id = (String) serviceData.get("name");

        Map<String, Object> credentials = getCredentials(serviceData);
        String uri = getUriFromCredentials(credentials);

        return new WebServiceInfo(id, uri);
    }
}
