package com.pc.dailymile.domain.converters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.pc.dailymile.utils.Type;

public class TypeConverter implements JsonDeserializer<Type> {

    @SuppressWarnings("unchecked")
    public Type deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        Type ret;
        try {
            ret = Enum.valueOf((Class<Type>) typeOfT, json.getAsString());
        } catch (IllegalArgumentException e) {
            ret = Type.unknown;
        }
        return ret;
    }
}
