package fo.rodol.retail.manager.service;

import fo.rodol.retail.manager.domain.Shop;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopsServiceImpl implements ShopsService {

    @Override
    public void saveShop(Shop shop) {

    }

    @Override
    public Optional<Shop> findShop(String shopName) {

        return Optional.empty();
    }
}
