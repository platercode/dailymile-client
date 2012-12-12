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
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/*
 * 
 *         {"created_at":"2012-04-12T04:36:17Z",
 *           "user":{"username":"jplaterTest",
 *                   "display_name":"Joey",
 *                   "photo_url":"http://s2.dmimg.com/pictures/users/54006/1329982339_avatar.jpg",
 *                   "url":"http://www.dailymile.com/people/jplaterTest"
 *                   }
 *          }]
 *
 */
public class Like implements Comparable<Like>, Serializable {

    private User user;
    @SerializedName("created_at")
    private Date date;

    public Like() {

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
        return new ToStringBuilder(this).append("date", date).append("user", user).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 31).append(user).append(date).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Like other = (Like) obj;
        return new EqualsBuilder().append(user, other.user).append(date, other.date).isEquals();
    }

    public int compareTo(Like o) {
        int dateVal = o.getDate().compareTo(getDate());
        if (dateVal == 0) {
            if (this.equals(o)) {
                return dateVal;
            }
            // use the username as the tie breaker
            return o.getUser().getUsername().compareTo(getUser().getUsername());
        }
        return dateVal;
    }
}
