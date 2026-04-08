package br.com.christian.contacts.database.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Roles {
    USER("user"),
    ADMIN("admin");

    private final String role;

    Roles(String role){
        this.role = role;
    }

    @JsonValue
    public String getRole(){
        return role;
    }

    @JsonCreator
    public static Roles from (String value){
        return Roles.valueOf(value.toUpperCase());
    }

}
