package kr.anabada.anabadaserver.common.dto;

public enum DomainType {
    BUY_TOGETHER("같이 사요"),
    KNOW_TOGETHER("같이 알아요");

    private final String ko;

    DomainType(String ko) {
        this.ko = ko;
    }

    public static DomainType of(String ko) {
        for (DomainType imageType : values()) {
            if (imageType.ko.equals(ko)) {
                return imageType;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 이미지 타입입니다.");
    }
}