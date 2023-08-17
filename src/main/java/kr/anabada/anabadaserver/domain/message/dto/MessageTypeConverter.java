package kr.anabada.anabadaserver.domain.message.dto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MessageTypeConverter implements AttributeConverter<MessageType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MessageType attribute) {
        return attribute.getValue();
    }

    @Override
    public MessageType convertToEntityAttribute(Integer dbData) {
        return MessageType.of(dbData);
    }
}
