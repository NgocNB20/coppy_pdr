/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.multipayment;

import com.gmo_pg.g_pay.client.input.ExecTranInput;
import org.springframework.stereotype.Component;

/**
 * HitMall用に項目追加された XxxInput DTO
 *
 * @author tm27400
 */
@Component
public class HmExecTranInput extends ExecTranInput implements HmPaymentClientInput {

    /**
     * シリアル
     */
    private static final long serialVersionUID = 1L;

    /**
     * OrderSeq
     */
    protected Integer orderSeq;

    /**
     * OrderVersionNo
     */
    protected Integer orderVersionNo;

    /**
     * JobCd
     */
    private String jobCd;

    /**
     * Amount
     */
    private Integer amount;

    @Override
    public Integer getOrderSeq() {
        return this.orderSeq;
    }

    @Override
    public Integer getOrderVersionNo() {
        return this.orderVersionNo;
    }

    @Override
    public void setOrderSeq(Integer orderSeq) {
        this.orderSeq = orderSeq;
    }

    @Override
    public void setOrderVersionNo(Integer orderVersionNo) {
        this.orderVersionNo = orderVersionNo;
    }

    /**
     * @return jobCd
     */
    public String getJobCd() {
        return jobCd;
    }

    /**
     * @param jobCd the jobCd to set
     */
    public void setJobCd(String jobCd) {
        this.jobCd = jobCd;
    }

    /**
     * @return amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
