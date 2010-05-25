package com.pc.dailymile.domain;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

public class Comment implements Comparable<Comment> {

    private User user;
    @SerializedName("created_at")
    private Date date;
    @SerializedName("body")
    private String message;

    public Comment() {

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("date", date).append("message", message).append(
                "user", user).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 31).append(message).append(user).append(date).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Comment other = (Comment) obj;
        return new EqualsBuilder().append(message, other.message).append(user, other.user).append(
                date, other.date).isEquals();
    }

    public int compareTo(Comment o) {
        int dateVal = o.getDate().compareTo(getDate());
        if (dateVal == 0) {
            if (this.equals(o)) {
                return dateVal;
            }
            // use the message as the tie breaker
            return o.getMessage().compareTo(getMessage());
        }
        return dateVal;
    }
}
