package com.pc.dailymile;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
        Calendar c = Calendar.getInstance();
        //2010-03-29T04:00:00Z
        c.set(2010, 29, 2, 4, 0, 0);
        Entry expected = new Entry();
        expected.setId(1321909L);
        //TODO: set the date in a better way
        expected.setDate(new Date(1269849600000L));
        expected.setMessage("Testing new signpost lib");
        
        User user = new User();
        user.setUsername("jplaterTest");
        user.setName("Jeff");
        user.setImageUrl("http://www.dailymile.com/images/defaults/user_avatar.jpg");
        user.setUrl("http://www.dailymile.com/people/jplaterTest");
        
        expected.setUser(user);
        
        Comment comment = new Comment();
        comment.setMessage("good work!");
        comment.setDate(new Date(1327655105000L));
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
        
        assertEquals(expected, e);
    }
}
