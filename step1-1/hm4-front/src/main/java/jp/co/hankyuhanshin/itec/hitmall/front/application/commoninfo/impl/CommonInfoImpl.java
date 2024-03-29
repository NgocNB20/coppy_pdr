/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoAdministrator;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoBase;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoShop;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoUser;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;

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
     * ショップ情報
     */
    private CommonInfoShop commonInfoShop;

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
     * @return the commonInfoUser
     */
    @Override
    public CommonInfoUser getCommonInfoUser() {
        // ※ユーザー情報を生成して返却
        CommonInfoUtility commonProcessUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        return commonProcessUtility.createCommonInfoUser();
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

    /**
     * @return the commonInfoShop
     */
    @Override
    public CommonInfoShop getCommonInfoShop() {
        return commonInfoShop;
    }

    /**
     * @param commonInfoShop the commonInfoShop to set
     */
    public void setCommonInfoShop(CommonInfoShop commonInfoShop) {
        this.commonInfoShop = commonInfoShop;
    }

}
