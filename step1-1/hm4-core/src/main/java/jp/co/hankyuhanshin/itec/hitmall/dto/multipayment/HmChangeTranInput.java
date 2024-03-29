/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.multipayment;

import com.gmo_pg.g_pay.client.input.ChangeTranInput;
import org.springframework.stereotype.Component;

/**
 * HitMall用に項目追加された XxxInput DTO
 *
 * @author tm27400
 */
@Component
public class HmChangeTranInput extends ChangeTranInput implements HmPaymentClientInput {

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
     * Method
     */
    private String method;

    /**
     * PayTimes
     */
    private Integer payTimes;

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
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return payTimes
     */
    public Integer getPayTimes() {
        return payTimes;
    }

    /**
     * @param payTimes the payTimes to set
     */
    public void setPayTimes(Integer payTimes) {
        this.payTimes = payTimes;
    }

}
