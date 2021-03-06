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
package com.pc.dailymile.domain;

import java.util.Iterator;

import com.pc.dailymile.DailyMileClient;
import com.pc.dailymile.utils.EntryCriteria;

/**
 * 
 * @author jplater
 *
 */
public class DailymissionStreamIterator extends BaseStreamIterator {

    protected final DailyMileClient client;
    private final EntryCriteria query;
    private int currentPage = 1;
    
    public DailymissionStreamIterator(DailyMileClient client) {
        this(client, null);
    }
    
    public DailymissionStreamIterator(DailyMileClient client, EntryCriteria query) {
        this.client = client;
        this.query = query;
    }

    @Override
    protected Iterator<Entry> getPageOfEntries() {
        UserStream stream = client.getDailymissionStream(currentPage++, query);
        if (stream.getEntries() != null) {
            return stream.getEntries().iterator();
        }
        return null;
    }
}
