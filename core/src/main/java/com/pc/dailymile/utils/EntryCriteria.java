/*
   Copyright 2012 platers.code

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

import java.util.Date;

/**
 * Allows you to filter Entries by:
 * 
 * until
 *   Fetch all entries with a timestamp less than or equal to the given until.
 * since
 *   Fetch all entries with a timestamp greater than the given since.
 *   
 * @author jplater
 *
 */
public class EntryCriteria {
    private Date since;
    private Date until;
    
    public EntryCriteria() {
        
    }
        
    public void setSince(Date since) {
        this.since = since;
    }
    
    public void setUntil(Date until) {
        this.until = until;
    }
    
    public String buildQueryString() {
        StringBuilder sb = new StringBuilder();
        boolean hasParm = false;
        if (since != null) {
            sb.append("since=").append(toUnixTime(since));
            hasParm = true;
        }
        
        if (until != null) {
            if (hasParm) {
                sb.append("&");
            }
            sb.append("until=").append(toUnixTime(until));
        }
        
        return sb.toString();
    }
    
    private static long toUnixTime(Date date) {
        return date.getTime() / 1000;
    }
    
}
