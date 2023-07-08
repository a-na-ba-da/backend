package kr.anabada.anabadaserver.domain.change.dto;

import io.swagger.v3.oas.annotations.Parameter;

public enum ProductStatus {
    @Parameter(description = "교환 완료 상태")
    CHANGED("CHANGED", "교환 완료"),
    @Parameter(description = "교환 요청중 상태")
    REQUESTING("REQUESTING", "교환 요청중"),
    @Parameter(description = "교환 가능 상태")
    AVAILABLE("AVAILABLE", "교환 가능");

    private final String status;
    private final String ko;

    ProductStatus(String status, String ko) {
        this.status = status;
        this.ko = ko;
    }

    public static ProductStatus of(String status) {
        for (ProductStatus productStatus : ProductStatus.values()) {
            if (productStatus.getStatus().equals(status)) {
                return productStatus;
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }
}
