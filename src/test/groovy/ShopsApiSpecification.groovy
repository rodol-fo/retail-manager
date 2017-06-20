import fo.rodol.retail.manager.web.ShopsController
import org.springframework.http.MediaType
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class ShopsApiSpecification extends Specification {

    def controller = new ShopsController()

    def mockMvc = standaloneSetup(controller).build()

    def 'creates a new shop when a user does a RESTful POST to /shops '() {

        given:
        def requestBody = """
            {
                "shopName": "shopName",
                "shopAddress": {
                    "number": 123,
                    "postcode": "SW15 1RS"
                }
            }
        """
        when:
        def response = mockMvc.perform(
                post("/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))

        then:
        response.andExpect(status().isAccepted())
    }
}