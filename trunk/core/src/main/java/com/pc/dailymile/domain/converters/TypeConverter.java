/*
   Copyright 2010 platers.code

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
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
