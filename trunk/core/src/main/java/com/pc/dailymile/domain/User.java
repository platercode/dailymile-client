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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

public class User implements Comparable<User>, Serializable {

    private String username;
    @SerializedName("display_name")
    private String name;
    private String url;
    @SerializedName("photo_url")
    private String imageUrl;
    private String location;
    private String goal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("imageUrl", imageUrl).append("name", name)
                .append("url", url).append("username", username).append("location", location)
                .append("goal", goal).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(username).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return new EqualsBuilder().append(username, other.username).isEquals();
        
        
    }
    
    public int compareTo(User o) {
        int nameVal = getName().compareTo(o.getName());
        if (nameVal == 0) {
            if (this.equals(o)) {
                // they are truly equal
                return nameVal;
            }
            // use the username as the tie breaker
            return getUsername().compareTo(o.getUsername());
        }
        return nameVal;
    }
}
