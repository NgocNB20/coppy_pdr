/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.common.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.common.AccessUidDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.common.AccessUidCreateLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Calendar;

/**
 * 端末識別番号作成ロジック<br/>
 *
 * @author natume
 * @version $Revision: 1.7 $
 */
@Component
public class AccessUidCreateLogicImpl extends AbstractShopLogic implements AccessUidCreateLogic {

    /**
     * AccessUidDao<br/>
     */
    private final AccessUidDao accessUidDao;

    @Autowired
    public AccessUidCreateLogicImpl(AccessUidDao accessUidDao) {
        this.accessUidDao = accessUidDao;
    }

    /**
     * 端末識別番号作成処理<br/>
     *
     * @return 端末識別番号
     */
    @Override
    public String execute() {

        // 4桁SEQ + 現在日時
        StringBuilder accessUid = new StringBuilder(16);
        accessUid.append(new DecimalFormat("0000").format(this.getAccessUidSeq()));

        // 年2桁 + 月2桁 + 日2桁 + 時間2桁 + 分2桁 + 秒2桁 = 12桁
        Calendar current = Calendar.getInstance();
        Format format = new DecimalFormat("00");
        accessUid.append(String.valueOf(current.get(Calendar.YEAR)).substring(2));
        accessUid.append(format.format((current.get(Calendar.MONTH) + 1)));
        accessUid.append(format.format(current.get(Calendar.DAY_OF_MONTH)));
        accessUid.append(format.format(current.get(Calendar.HOUR_OF_DAY)));
        accessUid.append(format.format(current.get(Calendar.MINUTE)));
        accessUid.append(format.format(current.get(Calendar.SECOND)));

        // 端末識別番号
        return accessUid.toString();
    }

    /**
     * 端末識別SEQを取得<br/>
     *
     * @return 端末識別SEQ
     */
    protected Integer getAccessUidSeq() {
        return Integer.parseInt(accessUidDao.getNextVal());
    }

}
