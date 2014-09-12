package com.example.cities.client.cloud.cloudfoundry;

import com.example.cities.client.cloud.WebServiceInfo;
import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import java.util.Map;

public class CitiesWebServiceInfoCreator extends CloudFoundryServiceInfoCreator<WebServiceInfo> {

    public static final String CITIES_TAG = "cities";

    public CitiesWebServiceInfoCreator() {
        super(new Tags(CITIES_TAG));
    }

    @Override
    public boolean accept(Map<String, Object> serviceData) {
        Map<String, Object> credentials = getCredentials(serviceData);
        String tag = (String) credentials.get("tag");
        return super.accept(serviceData) || CITIES_TAG.equals(tag);
    }

    @Override
    public WebServiceInfo createServiceInfo(Map<String, Object> serviceData) {
        String id = (String) serviceData.get("name");

        Map<String, Object> credentials = getCredentials(serviceData);
        String uri = getStringFromCredentials(credentials, "uri", "url");

        return new WebServiceInfo(id, uri);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getCredentials(Map<String, Object> serviceData) {
        return (Map<String, Object>) serviceData.get("credentials");
    }
}
