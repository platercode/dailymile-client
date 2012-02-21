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
import com.pc.dailymile.utils.Feeling;
import com.pc.dailymile.utils.Type;
import com.pc.dailymile.utils.Units;

/*
 *           {"felt":"good",
 *            "activity_type":"running",
 *            "duration":1645,
 *            "distance":{"units":"miles",
 *                        "value":3.1},
 *            "title":"my title"
 *            },
 */
public class Workout implements Serializable {

    private Feeling felt;
    @SerializedName("activity_type")
    private Type type;
    private Long duration;
    private Distance distance;
    private String title;
    @SerializedName("completed_at")
    private Date completedDate;
    private Long calories;
    @SerializedName("route_id")
    private Long routeId;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Completed Date is *NOT* set when we fetch entries, use 
     * Entry.getDate() instead
     * 
     * @return
     */
    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("completed_at", completedDate)
                .append("distance", distance).append("duration", duration).append("felt", felt)
                .append("type", type).append("title", title).append("calories", calories).append("routeId", routeId).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7, 29).append(completedDate).append(distance).append(duration)
                .append(felt).append(type).append(title).append(calories).append(routeId).toHashCode();
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
        return new EqualsBuilder().append(completedDate, other.completedDate)
                .append(distance, other.distance).append(duration, other.duration)
                .append(felt, other.felt).append(type, other.type).append(title, other.title)
                .append(calories, other.calories).append(routeId, other.routeId).isEquals();
    }

}
