package fo.rodol.retail.manager.service

import fo.rodol.retail.manager.domain.Shop
import spock.lang.Specification

class ShopsServiceSpecification extends Specification {

    def geolocationService = Mock(GeolocationService)

    def shopMap = new HashMap<>()

    def shopsService = new ShopsServiceImpl(geolocationService, shopMap)

    def 'saves a shop'() {

        given:
        def shop = new Shop("The Shop", 12345, "90210")

        when:
        shopsService.saveShop(shop)

        then:
        def savedShop = shopsService.findShop("The Shop").get()

        and:
        savedShop.shopName == "The Shop"
        savedShop.shopAddress.postcode == "90210"
        savedShop.shopAddress.number == 12345
    }

    def 'adds the lat and long after adding a shop'() {

        given:
        def shop = new Shop("The Shop", 12345, "90210")

        when:
        shopsService.saveShop(shop)

        then:
        1 * geolocationService.findLatLonAndUpdateShop(shop)
    }

    def 'gets the nearest shop'() {

        given:

        def shopMap = [
            "a shop that's near" : new Shop("a shop that's near", 123, "SW1 122", 51, 10.5),
            "a shop that's far" : new Shop("a shop that's far", 456, "EC1 345", 85, 33)
        ]

        def shopsServiceNearest = new ShopsServiceImpl(geolocationService, shopMap)

        when:
        def shop = shopsServiceNearest.findNearestShop(50, 10).get()

        then:
        shop.shopName == "a shop that's near"
    }
}
