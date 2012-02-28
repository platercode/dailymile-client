package com.pc.dailymile;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

import com.pc.dailymile.domain.Comment;
import com.pc.dailymile.domain.Entry;
import com.pc.dailymile.domain.User;
import com.pc.dailymile.domain.Workout;
import com.pc.dailymile.utils.Feeling;
import com.pc.dailymile.utils.Type;
import com.pc.dailymile.utils.Units;

public class DailyMileClientLiveTest {

    @Test
    public void testGetEntry() {
        /*
        {   "id":1321909,
            "url":"http://www.dailymile.com/entries/1321909",
            "at":"2010-03-29T04:00:00Z",
            "message":"Testing new signpost lib",
            "comments":[
                {   "body":"good work!",
                    "created_at":"2012-01-27T04:05:05Z",
                    "user":
                        {
                            "username":"jplaterTest",
                            "display_name":"Jeff",
                            "photo_url":"http://www.dailymile.com/images/defaults/user_avatar.jpg",
                            "url":"http://www.dailymile.com/people/jplaterTest"
                         }
                  }],
             "likes":[],
             "user":
                 {
                     "username":"jplaterTest",
                     "display_name":"Jeff",
                     "photo_url":"http://www.dailymile.com/images/defaults/user_avatar.jpg",
                     "url":"http://www.dailymile.com/people/jplaterTest"
                  },
              "workout":
                  {  "activity_type":"Running",
                     "distance":
                         {   "value":3.4144,
                             "units":"kilometers"
                         },
                     "felt":"good",
                     "duration":1163,
                     "title":"Ran Around The Block"
                   }
           }
        */
        DailyMileClient client = new DailyMileClient(null);
        Entry e = client.getEntry(1321909L);
        Entry expected = new Entry();
        expected.setId(1321909L);

        expected.setDate(ISODateTimeFormat.dateTimeNoMillis().parseDateTime("2010-03-29T04:00:00Z")
                .toDate());
        expected.setMessage("Testing new signpost lib");
        
        User user = new User();
        user.setUsername("jplaterTest");
        user.setName("Jeff");
        user.setImageUrl("http://www.dailymile.com/images/defaults/user_avatar.jpg");
        user.setUrl("http://www.dailymile.com/people/jplaterTest");
        
        expected.setUser(user);
        
        Comment comment = new Comment();
        comment.setMessage("good work!");
        comment.setDate(ISODateTimeFormat.dateTimeNoMillis().parseDateTime("2012-01-27T04:05:05Z")
                .toDate());
        comment.setUser(user);
        
        Set<Comment> comments = new HashSet<Comment>();
        comments.add(comment);
        expected.setComments(comments);
        
        Workout workout = new Workout();
        workout.setType(Type.Running);
        workout.setDistanceUnits(Units.kilometers);
        workout.setDistanceValue("3.4144");
        workout.setFelt(Feeling.good);
        workout.setDuration(1163L);
        workout.setTitle("Ran Around The Block");
        
        expected.setWorkout(workout);
        
        //assertEquals(expected, e);
    }
}
