package fo.rodol.retail.manager.web;

import fo.rodol.retail.manager.domain.Shop;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ShopsController {

    @RequestMapping(value = "/shops", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createShop(@RequestBody Shop shop) {

        System.out.println(shop);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
