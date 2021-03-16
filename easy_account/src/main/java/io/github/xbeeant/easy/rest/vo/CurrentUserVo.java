package io.github.xbeeant.easy.rest.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentUserVo {
    /**
     * address
     */
    @SerializedName("address")
    private String address;
    /**
     * avatar
     */
    @SerializedName("avatar")
    private String avatar;
    /**
     * country
     */
    @SerializedName("country")
    private String country;
    /**
     * email
     */
    @SerializedName("email")
    private String email;
    /**
     * geographic
     */
    @SerializedName("geographic")
    private GeographicVo geographic;
    /**
     * group
     */
    @SerializedName("group")
    private String group;

    @SerializedName("name")
    private String name;
    /**
     * notice
     */
    @SerializedName("notice")
    private List<Notice> notice;
    /**
     * notifyCount
     */
    @SerializedName("notifyCount")
    private Integer notifyCount;
    /**
     * phone
     */
    @SerializedName("phone")
    private String phone;
    /**
     * signature
     */
    @SerializedName("signature")
    private String signature;
    /**
     * tags
     */
    @SerializedName("tags")
    private List<Tags> tags;
    /**
     * title
     */
    @SerializedName("title")
    private String title;
    /**
     * unreadCount
     */
    @SerializedName("unreadCount")
    private Integer unreadCount;
    /**
     * userid
     */
    @SerializedName("userid")
    private String userid;

    public String getAddress() {
        return address;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public GeographicVo getGeographic() {
        return geographic;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public List<Notice> getNotice() {
        return notice;
    }

    public Integer getNotifyCount() {
        return notifyCount;
    }

    public String getPhone() {
        return phone;
    }

    public String getSignature() {
        return signature;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public String getUserid() {
        return userid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGeographic(GeographicVo geographic) {
        this.geographic = geographic;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotice(List<Notice> notice) {
        this.notice = notice;
    }

    public void setNotifyCount(Integer notifyCount) {
        this.notifyCount = notifyCount;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
