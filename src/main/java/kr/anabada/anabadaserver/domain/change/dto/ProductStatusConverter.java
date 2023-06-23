package kr.anabada.anabadaserver.domain.change.dto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.StringUtils;

import static kr.anabada.anabadaserver.domain.change.dto.ProductStatus.of;

@Converter
public class ProductStatusConverter implements AttributeConverter<ProductStatus, String> {
    @Override
    public String convertToDatabaseColumn(ProductStatus attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getStatus();
    }

    @Override
    public ProductStatus convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) {
            return null;
        }
        return of(dbData);
    }
}
