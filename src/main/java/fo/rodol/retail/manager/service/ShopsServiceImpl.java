package fo.rodol.retail.manager.service;

import fo.rodol.retail.manager.domain.Shop;
import fo.rodol.retail.manager.util.GeoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShopsServiceImpl implements ShopsService {

    private final GeolocationService geolocationService;

    private Map<String, Shop> shopMap;

    @Autowired
    public ShopsServiceImpl(GeolocationService geolocationService, Map<String, Shop> shopMap) {
        this.geolocationService = geolocationService;
        this.shopMap = shopMap;
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

    @Override
    public Optional<Shop> findNearestShop(Double lat, Double lng) {

        Shop nearestShop = null;
        Double nearestDistance = Double.MAX_VALUE;

        for (Shop shop : shopMap.values()) {

            double distance = GeoUtil.distance(shop.getLat(), shop.getLng(), lat, lng, 'K');
            if (distance < nearestDistance) {

                nearestShop = shop;
                nearestDistance = distance;
            }
        }
        return nearestShop != null ? Optional.of(nearestShop) : Optional.empty();
    }


}
