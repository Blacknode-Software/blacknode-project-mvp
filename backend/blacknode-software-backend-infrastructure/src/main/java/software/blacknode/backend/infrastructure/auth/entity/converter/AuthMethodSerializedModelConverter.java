package software.blacknode.backend.infrastructure.auth.entity.converter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.domain.auth.method.converter.model.AuthMethodSerializedModel;
import software.blacknode.backend.domain.exception.BlacknodeException;

@Component
@Converter
@RequiredArgsConstructor
public class AuthMethodSerializedModelConverter implements AttributeConverter<AuthMethodSerializedModel, String> {
    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(AuthMethodSerializedModel attribute) {
        try { return objectMapper.writeValueAsString(attribute); } 
        catch (Exception e) { throw new BlacknodeException("Error converting AuthMethodSerializedModel to String", e); }
    }

    @Override
    public AuthMethodSerializedModel convertToEntityAttribute(String dbData) {
        try { return objectMapper.readValue(dbData, AuthMethodSerializedModel.class); } 
        catch (Exception e) { throw new BlacknodeException("Error converting String to AuthMethodSerializedModel", e); }
    }
}
