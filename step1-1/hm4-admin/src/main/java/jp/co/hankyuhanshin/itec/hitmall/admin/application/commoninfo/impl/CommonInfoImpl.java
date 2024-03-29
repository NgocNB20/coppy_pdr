/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.application.commoninfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.admin.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.admin.application.commoninfo.CommonInfoAdministrator;
import jp.co.hankyuhanshin.itec.hitmall.admin.application.commoninfo.CommonInfoBase;
import jp.co.hankyuhanshin.itec.hitmall.admin.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;

/**
 * 共通情報<br/>
 * 各共通情報保持クラス<br/>
 *
 * @author thang
 */
public class CommonInfoImpl implements CommonInfo {

    /**
     * シリアルID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 共通情報
     */
    private CommonInfoBase commonInfoBase;

    /**
     * @return the commonInfoBase
     */
    @Override
    public CommonInfoBase getCommonInfoBase() {
        return commonInfoBase;
    }

    /**
     * @param commonInfoBase the commonInfoBase to set
     */
    public void setCommonInfoBase(CommonInfoBase commonInfoBase) {
        this.commonInfoBase = commonInfoBase;
    }

    /**
     * @return the commonInfoAdministrator
     */
    @Override
    public CommonInfoAdministrator getCommonInfoAdministrator() {
        // ※管理者情報を生成して返却
        CommonInfoUtility commonProcessUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        return commonProcessUtility.createCommonInfoAdministrator();
    }

}
