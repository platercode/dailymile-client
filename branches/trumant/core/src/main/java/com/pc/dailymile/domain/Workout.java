package com.pc.dailymile.domain;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;
import com.pc.dailymile.utils.Feeling;
import com.pc.dailymile.utils.Type;
import com.pc.dailymile.utils.Units;

/*
 *           {"felt":"good",
 *            "type":"running",
 *            "duration":1645,
 *            "distance":{"units":"miles",
 *                        "value":3.1},
 *            "completed_at":"2010-01-01 00:00:01"
 *            },
 */
public class Workout {

    private Feeling felt;
    private Type type;
    private Long duration;
    private Distance distance;
    @SerializedName("completed_at")
    private Date completedDate;
    private String title;

    public Workout() {
        this.distance = new Distance();
    }

    public Feeling getFelt() {
        return felt;
    }

    public void setFelt(Feeling felt) {
        this.felt = felt;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return the duration in seconds
     */
    public Long getDuration() {
        return duration;
    }

    /**
     * @param duration
     *            the duration in seconds
     */
    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setDistanceUnits(Units units) {
        distance.setUnits(units);
    }

    public Units getDistanceUnits() {
        return distance.getUnits();
    }

    public void setDistanceValue(String value) {
        distance.setValue(value);
    }

    public String getDistanceValue() {
        return distance.getValue();
    }

    public Date getCompletedDate() {
        // 2010-12-25 12:15:00
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("completed_at", completedDate).append("distance",
                distance).append("duration", duration).append("felt", felt).append("type", type)
                .append("title", title).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7, 29).append(completedDate).append(distance).append(duration)
                .append(felt).append(type).append(title).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Workout other = (Workout) obj;
        return new EqualsBuilder().append(completedDate, other.completedDate).append(distance,
                other.distance).append(duration, other.duration).append(felt, other.felt).append(
                type, other.type).append(title, other.title).isEquals();
    }
}
