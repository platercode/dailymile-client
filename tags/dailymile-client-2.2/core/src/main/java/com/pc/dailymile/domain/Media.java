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

/*
 * "media": [{"preview": {"type":"image", "height":75, 
 *                        "width":75, "url":\"http://....jpg"
 *                       },
 *            "content": {"type":"image","url":"http://....jpg","height":301,
 *                        "width":520
 *                       }
 */
public class Media implements Serializable {

    private MediaInfo preview;
    private MediaInfo content;

    public MediaInfo getPreview() {
        return preview;
    }

    public void setPreview(MediaInfo preview) {
        this.preview = preview;
    }

    public MediaInfo getContent() {
        return content;
    }

    public void setContent(MediaInfo content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("preview", preview).append("content", content)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(preview).append(content).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Media other = (Media) obj;
        return new EqualsBuilder().append(preview, other.preview).append(content, other.content)
                .isEquals();
    }
}
