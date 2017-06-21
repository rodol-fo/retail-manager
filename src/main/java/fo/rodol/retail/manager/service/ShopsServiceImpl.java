package fo.rodol.retail.manager.service;

import fo.rodol.retail.manager.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShopsServiceImpl implements ShopsService {

    private final GeolocationService geolocationService;

    private Map<String, Shop> shopMap = new HashMap<>();

    @Autowired
    public ShopsServiceImpl(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @Override
    public synchronized void saveShop(Shop shop) {

        synchronized (this) {

            shopMap.put(shop.getShopName(), shop);
        }
        geolocationService.findLatLonAndUpdateShop(shop);
    }

    @Override
    public synchronized Optional<Shop> findShop(String shopName) {

        Shop shop = shopMap.get(shopName);
        return shop != null ? Optional.of(shop) : Optional.empty();
    }
}
