package fo.rodol.retail.manager.service;

import fo.rodol.retail.manager.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;

@Service
public class GeolocationServiceImpl implements GeolocationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${google.maps.api.root}")
    private String googleMapsApiRoot;

    @Value("${google.maps.api.key}")
    private String apiKey;

    private UriComponents googleMapsUriComponents;

    @PostConstruct
    public void initialise() {

        googleMapsUriComponents = UriComponentsBuilder.fromHttpUrl(googleMapsApiRoot)
                .pathSegment("maps", "api", "geocode", "json")
                .queryParam("address", "{address}")
                .queryParam("key", apiKey)
                .build();
    }

    @Override
    public void findLatLonAndUpdateShop(Shop shop) {

        URI uri =  googleMapsUriComponents.expand(
                String.format("%s,+%s", shop.getShopAddress().getNumber(), shop.getShopAddress().getPostcode())).toUri();

        ResponseEntity<GoogleMapsGeocodeResponse> response = restTemplate.getForEntity(uri, GoogleMapsGeocodeResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && !response.getBody().getResults().isEmpty()) {

            Geolocation location = response.getBody().getResults().get(0).getGeometry().getLocation();
            shop.setLat(location.getLat());
            shop.setLng(location.getLng());
        }
    }

    private static class GoogleMapsGeocodeResponse {

        private List<GeocodeResult> results;

        public List<GeocodeResult> getResults() {
            return results;
        }

        public void setResults(List<GeocodeResult> results) {
            this.results = results;
        }
    }

    private static class GeocodeResult {

        private GeocodeGeometry geometry;

        public GeocodeGeometry getGeometry() {
            return geometry;
        }

        public void setGeometry(GeocodeGeometry geometry) {
            this.geometry = geometry;
        }
    }

    private static class GeocodeGeometry {

        private Geolocation location;

        public Geolocation getLocation() {
            return location;
        }

        public void setLocation(Geolocation location) {
            this.location = location;
        }
    }

    private static class Geolocation {

        private Double lat;
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }
    }
}
