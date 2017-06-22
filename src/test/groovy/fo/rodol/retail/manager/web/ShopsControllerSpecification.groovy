package fo.rodol.retail.manager.web

import fo.rodol.retail.manager.domain.Shop
import fo.rodol.retail.manager.service.ShopsService
import org.springframework.http.MediaType
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class ShopsControllerSpecification extends Specification {

    def shopsService = Mock(ShopsService)

    def controller = new ShopsController(shopsService)

    def mockMvc = standaloneSetup(controller).build()

    def 'creates a new shop when a user does a RESTful POST to /shops '() {

        given:
        def requestBody = """
            {
                "shopName": "shopName",
                "shopAddress": {
                    "number": 123,
                    "postcode": "SW6 1RS"
                }
            }
        """

        when:
        def response = mockMvc.perform(
                post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))

        then:
        1 * shopsService.findShop('shopName') >> Optional.empty()

        and:
        response.andExpect(status().isCreated())

        and:
        1 * shopsService.saveShop(new Shop('shopName', 123, 'SW6 1RS'))

        and:
        response.andExpect(content().json("""
            {
                "message": "A new shop has been created",
                "newShop": {
                    "shopName": "shopName",
                    "shopAddress": {
                        "number": 123,
                        "postcode": "SW6 1RS"
                    }
                }
            }
        """))
    }

    def 'replaces an existing shop if a shop with same shopName already exists'() {

        given:
        def requestBody = """
            {
                "shopName": "The Shop's Name",
                "shopAddress": {
                    "number": 123,
                    "postcode": "EC2A 2BB"
                }
            }
        """

        def anExistingShop = new Shop("The Shop's Name", 456, 'SW15 1SR')

        when:
        def response = mockMvc.perform(
                post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))

        then: 'an existing shop with shopName exists'
        1 * shopsService.findShop("The Shop's Name") >> Optional.of(anExistingShop)

        and: 'the new shop is saved'
        1 * shopsService.saveShop(new Shop("The Shop's Name", 123, 'EC2A 2BB'))

        and:
        response.andExpect(status().isCreated())

        and:
        response.andExpect(content().json("""
            {
                "message": "An existing shop has been replaced",
                "newShop": {
                    "shopName": "The Shop's Name",
                    "shopAddress": {
                        "number": 123,
                        "postcode": "EC2A 2BB"
                    }
                },
                "replacedShop": {
                    "shopName": "The Shop's Name",
                    "shopAddress": {
                        "number": 456,
                        "postcode": "SW15 1SR"
                    }
                }
            }
        """))
    }

    def 'returns the nearest shop when a user does a GET on /shops with their lat and long'() {

        given:
        shopsService

        when:
        def response = mockMvc.perform(
                get("/shops")
                        .param('lat', '50')
                        .param('lng', '10'))

        then:
        response.andExpect(status().isOk())

        and:
        1 * shopsService.findNearestShop(50, 10) >> Optional.of(new Shop("Near shop", 234, "S3456"))
    }
}