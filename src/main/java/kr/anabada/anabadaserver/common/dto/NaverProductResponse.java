package kr.anabada.anabadaserver.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class NaverProductResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<ProductItem> items;

    @Data
    public static class ProductItem {
        private String title;
        private String link;
        private String image;
        private String lprice;
        private String hprice;
        private String mallName;
        private String productId;
        private String productType;
        private String brand;
        private String maker;
        private String category1;
        private String category2;
        private String category3;
        private String category4;
    }

}
