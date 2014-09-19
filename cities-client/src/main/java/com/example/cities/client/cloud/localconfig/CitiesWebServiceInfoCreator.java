package com.example.cities.client.cloud.localconfig;

import com.example.cities.client.cloud.WebServiceInfo;
import org.springframework.cloud.localconfig.LocalConfigServiceInfoCreator;
import org.springframework.cloud.service.UriBasedServiceData;

public class CitiesWebServiceInfoCreator extends LocalConfigServiceInfoCreator<WebServiceInfo> {
    public static final String CITIES_TAG = "cities";

    public CitiesWebServiceInfoCreator() {
        super("http");
    }

    @Override
    public boolean accept(UriBasedServiceData serviceData) {
        return super.accept(serviceData) && CITIES_TAG.equals(serviceData.getKey());
    }

    @Override
    public WebServiceInfo createServiceInfo(String id, String uri) {
        return new WebServiceInfo(id, uri);
    }
}
