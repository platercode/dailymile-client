package com.pc.dailymile.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("display_name")
    private String name;

    private String url;

    @SerializedName("photo_url")
    private String imageUrl;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("imageUrl", imageUrl).append("name", name).append(
                "url", url).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(imageUrl).append(name).append(url).toHashCode();
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
        return new EqualsBuilder().append(imageUrl, other.imageUrl).append(name, other.name)
                .append(url, other.url).isEquals();
    }
}
