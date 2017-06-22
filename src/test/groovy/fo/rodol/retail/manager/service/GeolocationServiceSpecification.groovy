package fo.rodol.retail.manager.service

import fo.rodol.retail.manager.domain.Shop
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import static org.springframework.web.util.UriUtils.encode

class GeolocationServiceSpecification extends Specification {

    def restTemplate = new RestTemplate()

    def googleMapsApiRoot = 'http://googlemapsroot'

    def apiKey = 'apikey'

    def geolocationService = new GeolocationServiceImpl(
            restTemplate: restTemplate,
            googleMapsApiRoot: googleMapsApiRoot,
            apiKey: apiKey
    )

    def mockGoogleMapsApi = MockRestServiceServer.createServer(restTemplate)

    def 'calls Google Maps Geocoding API to get the lat long of a shop'() {

        given:
        def shop = new Shop("SHop Name", 1234, "SW15 1SR")
        def url = "$googleMapsApiRoot/maps/api/geocode/json?address=${shop.shopAddress.number},+${encode(shop.shopAddress.postcode, 'UTF-8')}&key=$apiKey"

        mockGoogleMapsApi.expect(requestTo(url))
                .andRespond(withSuccess("""
				{
                  "results": [
                    {
                      "geometry": {
                        "location": {
                          "lat": 37.4224764,
                          "lng": -122.0842499
                        }
                      }
                    }
                  ],
                  "status": "OK"
                }
                """, MediaType.APPLICATION_JSON))

        geolocationService.initialise()

        when:
        geolocationService.findLatLonAndUpdateShop(shop)

        then:
        mockGoogleMapsApi.verify()

        and:
        shop.lat == 37.4224764
        shop.lng == -122.0842499
    }
}
