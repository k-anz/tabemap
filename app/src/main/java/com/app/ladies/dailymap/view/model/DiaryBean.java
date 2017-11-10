package com.app.ladies.dailymap.view.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kyoko1 on 2017/03/13.
 */

public class DiaryBean implements Serializable {
    /** ID */
    private String storeId;

    /** seqNo */
    private Integer seqNo;

    /** タイトル */
    private String title;

    /** コメント */
    private String comment;

    /** 登録日 */
    private Date regDate;

    /** 評価 */
    private Integer evaluation;

    public DiaryBean() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }
}
