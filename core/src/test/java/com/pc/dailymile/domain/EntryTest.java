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
package com.pc.dailymile.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.TreeSet;

import org.junit.Test;

public class EntryTest {

    @Test
    public void testCommentsReturnedInDateDescendingOrder() {
        Entry entry = new Entry();

        Comment comment_1 = new Comment();
        comment_1.setDate(new Date(System.currentTimeMillis()));

        Comment comment_2 = new Comment();
        comment_2.setDate(new Date(System.currentTimeMillis() + 5000));
        
        entry.setComments(new TreeSet<Comment>(Arrays.asList(comment_1, comment_2)));
        
        assertEquals(comment_2, entry.getComments().toArray()[0]);
    }
    
    @Test
    public void ensureThatGetCommentsAlwaysReturnsNonNull() {
        Entry entry = new Entry();
        assertNotNull(entry.getComments());
    }
}
