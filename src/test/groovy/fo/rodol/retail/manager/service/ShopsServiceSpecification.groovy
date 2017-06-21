package fo.rodol.retail.manager.service

import fo.rodol.retail.manager.domain.Shop
import spock.lang.Specification

class ShopsServiceSpecification extends Specification {

    def shopsService = new ShopsServiceImpl()

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
}
