package fo.rodol.retail.manager.service;

import fo.rodol.retail.manager.domain.Shop;

import java.util.Optional;

public interface ShopsService {

    void saveShop(Shop shop);

    Optional<Shop> findShop(String shopName);

    Optional<Shop> findNearestShop(Double lat, Double lng);
}
