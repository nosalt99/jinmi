package com.cyl.musicapi.qq;

import java.util.List;



public class QQApiKey {

    private int code;
    private String testfile2g;
    private String testfilewifi;
    private String key;
    private List<String> sip;
    private List<String> thirdip;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTestfile2g() {
        return testfile2g;
    }

    public void setTestfile2g(String testfile2g) {
        this.testfile2g = testfile2g;
    }

    public String getTestfilewifi() {
        return testfilewifi;
    }

    public void setTestfilewifi(String testfilewifi) {
        this.testfilewifi = testfilewifi;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getSip() {
        return sip;
    }

    public void setSip(List<String> sip) {
        this.sip = sip;
    }

    public List<String> getThirdip() {
        return thirdip;
    }

    public void setThirdip(List<String> thirdip) {
        this.thirdip = thirdip;
    }
}
