package fo.rodol.retail.manager.service;

import fo.rodol.retail.manager.domain.Shop;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShopsServiceImpl implements ShopsService {

    private Map<String, Shop> shopMap = new HashMap<>();

    @Override
    public synchronized void saveShop(Shop shop) {

        shopMap.put(shop.getShopName(), shop);
    }

    @Override
    public synchronized Optional<Shop> findShop(String shopName) {

        Shop shop = shopMap.get(shopName);
        return shop != null ? Optional.of(shop) : Optional.empty();
    }
}
