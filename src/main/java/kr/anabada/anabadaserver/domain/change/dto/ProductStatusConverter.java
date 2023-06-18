package kr.anabada.anabadaserver.domain.change.dto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.StringUtils;

@Converter
public class ProductStatusConverter implements AttributeConverter<ProductStatus, String> {
    @Override
    public String convertToDatabaseColumn(ProductStatus attribute) {
        return attribute.name();
    }

    @Override
    public ProductStatus convertToEntityAttribute(String dbData) {
        if (StringUtils.hasText(dbData)) {
            return null;
        }
        return ProductStatus.valueOf(dbData);
    }
}
