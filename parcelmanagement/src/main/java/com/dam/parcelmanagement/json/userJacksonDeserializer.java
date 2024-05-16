package com.dam.parcelmanagement.json;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.dam.parcelmanagement.model.Admin;
import com.dam.parcelmanagement.model.Customer;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.model.UserRole;

@JsonComponent
public class userJacksonDeserializer extends JsonDeserializer<User> {

    public userJacksonDeserializer() {
        super();
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(jsonParser);
        
        if (jsonNode.get("role").asText().equals(UserRole.ADMIN.name())) {
            return objectCodec.treeToValue(jsonNode, Admin.class);
        } else {
            return objectCodec.treeToValue(jsonNode, Customer.class);
        }
    }
}
