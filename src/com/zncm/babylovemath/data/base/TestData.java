package com.zncm.babylovemath.data.base;

import com.j256.ormlite.field.DatabaseField;
import com.zncm.babylovemath.data.BaseData;

import java.io.Serializable;

public class TestData extends BaseData implements Serializable {

    private static final long serialVersionUID = 8838725426885988959L;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int type;
    @DatabaseField
    private String content; //   2014年3月15日测试XXX
    @DatabaseField
    private int questionCount;
    @DatabaseField
    private int rightCount;
    @DatabaseField
    private int wrongCount;
    @DatabaseField
    private float score;
    @DatabaseField
    private int spendTime;//分钟
    @DatabaseField
    private int avgSpendTime;//分
    @DatabaseField
    private long time;
    @DatabaseField
    private long modifyTime;

    public TestData() {
    }


    public TestData(int type, String content, int questionCount) {
        this.type = type;
        this.content = content;
        this.questionCount = questionCount;
        this.rightCount = 0;
        this.wrongCount = 0;
        this.score = 0;
        this.spendTime = 0;
        this.avgSpendTime = 0;
        this.time = System.currentTimeMillis();
        this.modifyTime = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getRightCount() {
        return rightCount;
    }

    public void setRightCount(int rightCount) {
        this.rightCount = rightCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(int wrongCount) {
        this.wrongCount = wrongCount;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(int spendTime) {
        this.spendTime = spendTime;
    }

    public int getAvgSpendTime() {
        return avgSpendTime;
    }

    public void setAvgSpendTime(int avgSpendTime) {
        this.avgSpendTime = avgSpendTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Override
    public String toString() {
        return "TestData{" +
                "id=" + id +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", questionCount=" + questionCount +
                ", rightCount=" + rightCount +
                ", wrongCount=" + wrongCount +
                ", score=" + score +
                ", spendTime=" + spendTime +
                ", avgSpendTime=" + avgSpendTime +
                ", time=" + time +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
