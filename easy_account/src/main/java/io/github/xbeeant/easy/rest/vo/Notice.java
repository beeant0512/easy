package io.github.xbeeant.easy.rest.vo;

import com.google.gson.annotations.SerializedName;

public class Notice {
    /**
     * description
     */
    @SerializedName("description")
    private String description;
    /**
     * href
     */
    @SerializedName("href")
    private String href;
    /**
     * id
     */
    /**
     * id :
     * title : titles[0]
     * logo : avatars[0]
     * description : '那是一种内在的东西，他们到达不了，也无法触及的'
     * updatedAt : new Date()
     * member : '科学搬砖组'
     * href : ''
     * memberLink :  ''
     */

    @SerializedName("id")
    private String id;
    /**
     * logo
     */
    @SerializedName("logo")
    private String logo;
    /**
     * member
     */
    @SerializedName("member")
    private String member;
    /**
     * memberLink
     */
    @SerializedName("memberLink")
    private String memberLink;
    /**
     * title
     */
    @SerializedName("title")
    private String title;
    /**
     * updatedAt
     */
    @SerializedName("updatedAt")
    private String updatedAt;

    public String getDescription() {
        return description;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public String getLogo() {
        return logo;
    }

    public String getMember() {
        return member;
    }

    public String getMemberLink() {
        return memberLink;
    }

    public String getTitle() {
        return title;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public void setMemberLink(String memberLink) {
        this.memberLink = memberLink;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
