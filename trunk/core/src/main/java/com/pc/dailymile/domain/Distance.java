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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.pc.dailymile.utils.Units;

/*
 *"distance":{"units":"miles",
 *            "value":3.1}
 *           },
 */
public class Distance {

    private Units units;
    private String value;

    public Distance() {
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("units", units).append("value", value).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(15, 35).append(units).append(value).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Distance other = (Distance) obj;
        return new EqualsBuilder().append(units, other.units).append(value, other.value).isEquals();
    }

}
