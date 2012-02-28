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

import java.lang.reflect.Type;
import java.util.Date;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateConverter implements JsonDeserializer<Date>, JsonSerializer<Date> {

    // 2010-12-25T12:15:00Z
    private DateTimeFormatter deserializeFormatter = 
            ISODateTimeFormat.dateTimeNoMillis().withZone(DateTimeZone.UTC); 
    
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        
        return deserializeFormatter.parseDateTime(json.getAsJsonPrimitive().getAsString()).toDate();
    }
    
    public JsonElement serialize(Date date, Type typeOfT, JsonSerializationContext context) {
        return new JsonPrimitive(deserializeFormatter.print(date.getTime()));
    }
    
}
