package kr.anabada.anabadaserver.common.dto;

public enum ImageType {
    BUY_TOGETHER("buy-together", "같이 사요"),
    KNOW_TOGETHER("know-together", "같이알아요");

    private final String typeStr;
    private final String name;

    ImageType(String domain, String name) {
        this.typeStr = domain;
        this.name = name;
    }

    public static ImageType of(String domain) {
        for (ImageType imageType : values()) {
            if (imageType.typeStr.equals(domain)) {
                return imageType;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 이미지 타입입니다.");
    }

    public String getTypeStr() {
        return typeStr;
    }
}