package com.dam.parcelmanagement.json;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.dam.parcelmanagement.model.Address;
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
        
        JsonNode roleNode = jsonNode.get("role");
        String role = (roleNode != null && !(roleNode instanceof NullNode)) ? roleNode.asText() : null;

        if (UserRole.ADMIN.name().equals(role)) {
            return handleMissingFields(objectCodec.treeToValue(jsonNode, Admin.class));
        } else {
            return handleMissingFields(objectCodec.treeToValue(jsonNode, Customer.class));
        }
    }

    private <T extends User> T handleMissingFields(T user) {

        if (user.getUsername() == null) {
            user.setUsername("");
        }
        
        if (user.getPassword() == null) {
            user.setPassword("");
        }
        
        if (user.getAddress() == null) {
            user.setAddress(new Address());
        }

        if (user.getRole() == null) {
            user.setRole(UserRole.CUSTOMER);
        }

        return user;
    }
}
