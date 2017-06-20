import fo.rodol.retail.manager.web.ShopsController
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class ShopsApiSpecification extends Specification {

    def controller = new ShopsController()

    def mockMvc = standaloneSetup(controller).build()

    def 'creates a new shop when a user does a RESTful POST to /shops '() {

        when:
        def response = mockMvc.perform(post("/shops"))

        then:
        response.andExpect(status().isAccepted())
    }
}