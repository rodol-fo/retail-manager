package fo.rodol.retail.manager.domain;

public class Shop {

    private String shopName;

    private ShopAddress shopAddress;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public ShopAddress getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(ShopAddress shopAddress) {
        this.shopAddress = shopAddress;
    }

    static class ShopAddress {

        private int number;

        private String postcode;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }
    }
}
