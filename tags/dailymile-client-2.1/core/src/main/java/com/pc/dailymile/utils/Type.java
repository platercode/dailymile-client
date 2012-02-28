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
package com.pc.dailymile.utils;

/**
 * Enum that provides all valid Type values
 * 
 * @author jplater
 * 
 */
public enum Type {
    CCSkiing("Cc Skiing"),
    Commute("Commute"),
    CoreFitness("Core Fitness"),
    Crossfit("Crossfit"),
    CrossTraining("Cross Training"),
    Cycling("Cycling"),
    Elliptical("Elliptical"),
    Fitness("Fitness"),
    Hiking("Hiking"),
    InlineSkating("Inline Skating"),
    RockClimbing("Rock Climbing"),
    Rowing("Rowing"),
    Running("Running"),
    Spinning("Spinning"),
    Swimming("Swimming"),
    Unknown("Unknown"),
    Walking("Walking"),
    Weights("Weights"),
    Yoga("Yoga");
    
    private String apiValue;
    
    private Type(String apiValue) {
        this.apiValue = apiValue;
    }
    
    public static Type fromApiValue(String value) {
        for (Type rtn : values()) {
            if (rtn.apiValue.equals(value)) {
                return rtn;
            }
        }
        
        return Unknown;
    }
    
    public String getApiValue() {
        return apiValue;
    }
}
