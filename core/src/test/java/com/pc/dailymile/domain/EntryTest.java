package com.pc.dailymile.domain;

import static org.junit.Assert.assertEquals;

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
}
