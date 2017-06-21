package fo.rodol.retail.manager.service;

import fo.rodol.retail.manager.domain.Shop;

public interface GeolocationService {

    void findLatLonAndUpdateShop(Shop shop);
}
