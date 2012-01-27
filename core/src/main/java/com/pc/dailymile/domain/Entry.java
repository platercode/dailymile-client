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

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/*
 * {
 * "url":"http://www.dailymile.com/entries/978880",
 * "message":"",
 * "at":"2010-02-14T15:55:52-06:00",
 * "user":{"url":"http://www.dailymile.com/people/jplater",
 *         "display_name":"Jeff",
 *         "photo_url":"http://www.dailymile.com/images/defaults/user_avatar.jpg",
 *         "username":"jplaterTest"
 *         },
 * "workout":{"felt":"good",
 *            "activity_type":"running",
 *            "duration":1645,
 *            "distance":{"units":"miles",
 *                        "value":3.1}
 *            },
 * "id":978880,
 * "comments":[{"body":"Good job!  Welcome to DailyMile!",
 *              "created_at":"2010-02-15T05:54:16-06:00",
 *              "user":{"url":"http://www.dailymile.com/people/chazzerguy",
 *                      "display_name":"Chaz H.",
 *                      "photo_url":"http://s3.dmimg.com/pictures/users/1373/1251135465_avatar.jpg"
 *                      }
 *              }],
 * "likes":[]
 * }
 */

public class Entry implements Comparable<Entry>, Serializable {

    private Long id;
    private Workout workout;
    private String message;
    private User user;
    @SerializedName("at")
    private Date date;
    private Set<Comment> comments;

    public Entry() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public Set<Comment> getComments() {
        if (comments == null) {
            return Collections.emptySet();
        }
        return new TreeSet<Comment>(comments);
    }

    public void setComments(Set<Comment> comments) {
        this.comments = new TreeSet<Comment>(comments);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("message", message).append("user",
                user).append("workout", workout).append("date", date).append("comments",
                getComments()).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 31).append(id).append(message).append(user).append(workout)
                .append(date).append(comments).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entry other = (Entry) obj;
        return new EqualsBuilder().append(id, other.id).append(message, other.message).append(user,
                other.user).append(workout, other.workout).append(date, other.date).append(
                comments, other.comments).isEquals();
    }

    public int compareTo(Entry o) {
        int dateVal = o.getDate().compareTo(getDate());
        if (dateVal == 0) {
            if (this.equals(o)) {
                // they are truly equal
                return dateVal;
            }
            // use the id as the tie breaker
            return o.getId().compareTo(getId());
        }
        return dateVal;
    }

}
