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

import com.pc.dailymile.utils.Units;

public class Route implements Comparable<Route>, Serializable {

    private Long id;
    private String name;
    private Distance distance;

    public Route() {
        this.distance = new Distance();
    }
    

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
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


    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(distance)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Route other = (Route) obj;
        return new EqualsBuilder().append(id, other.id).append(name, other.name)
                .append(distance, other.distance).isEquals();
    }


    public int compareTo(Route o) {
        int nameVal = o.getName().compareTo(getName());
        if (nameVal == 0) {
            if (this.equals(o)) {
                // they are truly equal
                return nameVal;
            }
            // use the id as the tie breaker
            return o.getId().compareTo(getId());
        }
        return nameVal;
    }
}
