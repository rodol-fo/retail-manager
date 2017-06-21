package fo.rodol.retail.manager.domain;

public class Shop {

    public Shop() {
    }

    public Shop(String shopName, int addressNumber, String postcode) {
        this.shopName = shopName;
        this.shopAddress = new ShopAddress(addressNumber, postcode);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (shopName != null ? !shopName.equals(shop.shopName) : shop.shopName != null) return false;
        return shopAddress != null ? shopAddress.equals(shop.shopAddress) : shop.shopAddress == null;
    }

    @Override
    public int hashCode() {
        int result = shopName != null ? shopName.hashCode() : 0;
        result = 31 * result + (shopAddress != null ? shopAddress.hashCode() : 0);
        return result;
    }

    static class ShopAddress {

        public ShopAddress() {
        }

        public ShopAddress(int number, String postcode) {
            this.number = number;
            this.postcode = postcode;
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ShopAddress that = (ShopAddress) o;

            if (number != that.number) return false;
            return postcode != null ? postcode.equals(that.postcode) : that.postcode == null;
        }

        @Override
        public int hashCode() {
            int result = number;
            result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
            return result;
        }
    }
}
