package com.zncm.babylovemath.data.base;

import com.j256.ormlite.field.DatabaseField;
import com.zncm.babylovemath.data.BaseData;

import java.io.Serializable;

public class QuestionData extends BaseData implements Serializable {

    private static final long serialVersionUID = 8838725426885988959L;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int testId;
    @DatabaseField
    private int type;
    @DatabaseField
    private String content; //    1+1
    @DatabaseField
    private String answerA;
    @DatabaseField
    private String answerB;
    @DatabaseField
    private String answerC;
    @DatabaseField
    private String answerD;
    @DatabaseField
    private String choose;//A
    @DatabaseField
    private String answer;//A B C D
    @DatabaseField
    private int status;//对错
    @DatabaseField
    private long time;
    @DatabaseField
    private long modifyTime;

    public QuestionData() {
    }

    public QuestionData(int testId, int type, String content, String answerA, String answerB, String answerC, String answerD, String answer) {
        this.testId = testId;
        this.type = type;
        this.content = content;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.choose = "";
        this.answer = answer;
        this.status = 0;
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

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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


    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }


    @Override
    public String toString() {
        return "QuestionData{" +
                "id=" + id +
                ", testId=" + testId +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", answerD='" + answerD + '\'' +
                ", choose='" + choose + '\'' +
                ", answer='" + answer + '\'' +
                ", status=" + status +
                ", time=" + time +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
