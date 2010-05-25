package com.pc.dailymile.domain;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/*
 * {
 * "permalink":"http://www.dailymile.com/entries/978880",
 * "message":"",
 * "created_at":"2010-02-14T15:55:52-06:00",
 * "user":{"url":"http://www.dailymile.com/people/jplater",
 *         "display_name":"Jeff",
 *         "photo_url":"http://www.dailymile.com/images/defaults/user_avatar.jpg"
 *         },
 * "workout":{"felt":"good",
 *            "type":"running",
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

public class Entry implements Comparable<Entry> {

    private Long id;
    private Workout workout;
    private String message;
    private User user;
    @SerializedName("created_at")
    private Date date;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("message", message).append("user",
                user).append("workout", workout).append("date", date).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 31).append(id).append(message).append(user).append(workout)
                .append(date).toHashCode();
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
                other.user).append(workout, other.workout).append(date, other.date).isEquals();
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
