package io.github.xbeeant.easy.rest.vo;

import com.google.gson.annotations.SerializedName;

public class Tags {
    /**
     * key
     */
    /**
     * key : '0'
     * label :
     */

    @SerializedName("key")
    private String key;
    /**
     * label
     */
    @SerializedName("label")
    private String label;

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
