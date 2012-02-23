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
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class Routes implements Serializable {
    private static final long serialVersionUID = 20120214L;
    private Set<Route> routes;

    public Set<Route> getRoutes() {
        if (routes == null) {
            return Collections.emptySet();
        }
        return new TreeSet<Route>(routes);
    }

    public void setFriends(Set<Route> routes) {
        this.routes = new TreeSet<Route>(routes);
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("routes", getRoutes()).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 31).append(getRoutes()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Routes other = (Routes) obj;
        return new EqualsBuilder().append(getRoutes(), other.getRoutes()).isEquals();
    }
}
