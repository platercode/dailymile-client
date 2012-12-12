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

/*
 * {"entries":[{
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
 * "likes":[]}
 * ]}
 */
public class UserStream implements Serializable {
    private Set<Entry> entries;

    public Set<Entry> getEntries() {
        if (entries == null) {
            return Collections.emptySet();
        }
        return new TreeSet<Entry>(entries);
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = new TreeSet<Entry>(entries);
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("entries", getEntries()).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 31).append(getEntries()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserStream other = (UserStream) obj;
        return new EqualsBuilder().append(getEntries(), other.getEntries()).isEquals();
    }
}
