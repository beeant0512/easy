package io.github.xbeeant.easy.rest.vo;

import com.google.gson.annotations.SerializedName;

public class SelectOption {
    /**
     * key
     */
    @SerializedName("key")
    private String key;

    @SerializedName("label")
    private String label;

    public SelectOption() {
    }

    public SelectOption(String key) {
        this.key = key;
    }

    public SelectOption(Long key) {
        this.key = String.valueOf(key);
    }

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
