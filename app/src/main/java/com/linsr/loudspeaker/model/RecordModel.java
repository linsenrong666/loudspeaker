package com.linsr.loudspeaker.model;

/**
 * Description
 *
 * @author linsenrong on 2017/7/12 11:05
 */

public class RecordModel extends BaseModel {

    private String recordName;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }
}
