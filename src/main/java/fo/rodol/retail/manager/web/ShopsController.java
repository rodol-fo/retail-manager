package fo.rodol.retail.manager.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import fo.rodol.retail.manager.domain.Shop;
import fo.rodol.retail.manager.service.ShopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
class ShopsController {

    private final ShopsService shopsService;

    @Autowired
    public ShopsController(ShopsService shopsService) {
        this.shopsService = shopsService;
    }

    @RequestMapping(value = "/shops", method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ShopResponse> createShop(@RequestBody Shop newShop) {

        return shopsService.findShop(newShop.getShopName())
                .map(shop -> replaceExistingShop(shop, newShop))
                .orElseGet(() -> saveNewShop(newShop));
    }

    private ResponseEntity<ShopResponse> replaceExistingShop(Shop existingShop, Shop newShop) {

        shopsService.saveShop(newShop);
        return new ResponseEntity<>(new ShopResponse(newShop, existingShop), CREATED);
    }

    private ResponseEntity<ShopResponse> saveNewShop(Shop newShop) {

        shopsService.saveShop(newShop);
        return new ResponseEntity<>(new ShopResponse(newShop), CREATED);
    }

    @JsonInclude(NON_NULL)
    static class ShopResponse {

        String message;
        Shop newShop;
        Shop replacedShop;

        ShopResponse(Shop newShop) {
            this.message = "A new shop has been created";
            this.newShop = newShop;
        }

        ShopResponse(Shop newShop, Shop replacedShop) {
            this.message = "An existing shop has been replaced";
            this.newShop = newShop;
            this.replacedShop = replacedShop;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Shop getNewShop() {
            return newShop;
        }

        public void setNewShop(Shop newShop) {
            this.newShop = newShop;
        }

        public Shop getReplacedShop() {
            return replacedShop;
        }

        public void setReplacedShop(Shop replacedShop) {
            this.replacedShop = replacedShop;
        }
    }
}
