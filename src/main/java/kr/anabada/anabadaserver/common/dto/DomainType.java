package kr.anabada.anabadaserver.common.dto;

public enum DomainType {
    BUY_TOGETHER("같이 사요"),
    KNOW_TOGETHER("같이 알아요"),
    MY_PRODUCT("내 상품");

    private final String ko;

    DomainType(String ko) {
        this.ko = ko;
    }

    public static DomainType of(String str) {
        for (DomainType domainType : DomainType.values()) {
            if (domainType.toString().equals(str))
                return domainType;
        }
        throw new IllegalArgumentException("존재하지 않는 도메인 타입입니다.");
    }

    public String getKo() {
        return ko;
    }
}