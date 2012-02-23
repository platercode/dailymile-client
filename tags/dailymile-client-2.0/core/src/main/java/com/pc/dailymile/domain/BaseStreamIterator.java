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

/**
 * 
 * @author jplater
 *
 */
public abstract class BaseStreamIterator implements Iterator<Entry> {
    private Iterator<Entry> curPageIter;

    protected abstract Iterator<Entry> getPageOfEntries();
    
    
    public boolean hasNext() {
        if (curPageIter == null || !curPageIter.hasNext()) {
            curPageIter = getPageOfEntries();
        }
        
        return curPageIter != null && curPageIter.hasNext();
    }

    public Entry next() {
        if (hasNext()) {
            return curPageIter.next();
        }
        return null;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
