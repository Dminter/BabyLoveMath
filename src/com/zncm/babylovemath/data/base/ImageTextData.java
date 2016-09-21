package com.zncm.babylovemath.data.base;

import com.j256.ormlite.field.DatabaseField;
import com.zncm.babylovemath.data.BaseData;

import java.io.Serializable;

public class ImageTextData extends BaseData implements Serializable {

    private static final long serialVersionUID = 8838725426885988959L;
    private int id;
    private int imgId;
    private String text;


    public ImageTextData() {
    }

    public ImageTextData(int id, int imgId, String text) {
        this.id = id;
        this.imgId = imgId;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
