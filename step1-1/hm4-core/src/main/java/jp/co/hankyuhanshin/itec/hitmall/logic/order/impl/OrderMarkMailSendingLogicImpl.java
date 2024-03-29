/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.index.OrderIndexDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderMarkMailSendingLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 督促・決済期限切れメール送信フラグ変更ロジック　実装クラス<br/>
 * 「督促メール送信済みフラグ」、「決済期限切れメール送信済みフラグ」を変更する<br/>
 *
 * @author MN7017
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderMarkMailSendingLogicImpl extends AbstractShopLogic implements OrderMarkMailSendingLogic {

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;

    /**
     * 受注サマリDao
     */
    private final OrderSummaryDao orderSummaryDao;

    /**
     * 受注インデックスDao
     */
    private final OrderIndexDao orderIndexDao;

    @Autowired
    public OrderMarkMailSendingLogicImpl(DateUtility dateUtility,
                                         OrderSummaryDao orderSummaryDao,
                                         OrderIndexDao orderIndexDao) {

        this.dateUtility = dateUtility;
        this.orderSummaryDao = orderSummaryDao;
        this.orderIndexDao = orderIndexDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq         編集対象のオーダーSEQ
     * @param reminderSentFlag 督促メール送信状態（変更しない場合はnull）
     * @param expiredSentFlag  決済期限切れメール送信状態（変更しない場合はnull）
     * @param orderVersionNo   登録時に指定するorderVersionNo。（インクリメント時はnull）
     * @return orderVersionNo(引数で渡されたもの ）
     */
    @Override
    public Integer markMailSending(Integer orderSeq,
                                   HTypeSend reminderSentFlag,
                                   HTypeSend expiredSentFlag,
                                   Integer orderVersionNo,
                                   String administratorName) {

        // orderSeq引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);

        // orderIndexへの更新判定フラグ
        boolean isOrderIndexUpdate = false;
        if (orderVersionNo != null) {
            isOrderIndexUpdate = true;
        }

        // 受注サマリエンティティの取得
        OrderSummaryEntity orderSummaryEntity = orderSummaryDao.getEntityForUpdatePrimaryKey(orderSeq);

        // orderIndex取得用に退避
        Integer bufOrderVersionNo = orderSummaryEntity.getOrderVersionNo();

        // orderVersionNo編集
        if (orderVersionNo == null) {
            orderVersionNo = orderSummaryEntity.getOrderVersionNo() + 1;
        }

        // 更新entityに値をセット
        orderSummaryEntity.setOrderVersionNo(orderVersionNo);
        if (reminderSentFlag != null) {
            orderSummaryEntity.setReminderSentFlag(reminderSentFlag);
        }
        if (expiredSentFlag != null) {
            orderSummaryEntity.setExpiredSentFlag(expiredSentFlag);
        }

        Timestamp currentTime = dateUtility.getCurrentTime();
        orderSummaryEntity.setUpdateTime(currentTime);

        // 更新実行
        orderSummaryDao.update(orderSummaryEntity);

        // 受注インデックスエンティティの取得
        OrderIndexEntity orderIndexEntity = orderIndexDao.getEntity(orderSeq, bufOrderVersionNo);

        // 登録entityに値をセット
        orderIndexEntity.setOrderVersionNo(orderVersionNo);
        // 処理日時
        orderIndexEntity.setProcessTime(currentTime);
        // 処理担当者名
        orderIndexEntity.setProcessPersonName(administratorName);

        // 督促メール送信処理の場合
        if (reminderSentFlag != null) {
            orderIndexEntity.setReminderSentFlag(reminderSentFlag);
            orderIndexEntity.setProcessType(HTypeProcessType.REMINDER_SEND_MAIL);
        }
        // 期限切れメール送信処理の場合
        if (expiredSentFlag != null) {
            orderIndexEntity.setExpiredSentFlag(expiredSentFlag);
            orderIndexEntity.setProcessType(HTypeProcessType.EXPIRED_SEND_MAIL);
        }
        orderIndexEntity.setUpdateTime(currentTime);

        // 登録／更新実行
        if (isOrderIndexUpdate) {
            // 受注修正の一連の処理内で当クラスが呼ばれる場合を想定（決済変更処理等）。
            // ※現PKGではupdate処理を行うタイミングは無いが、今後当クラスを利用される場合を考え残しておく。
            orderIndexDao.update(orderIndexEntity);
        } else {
            orderIndexEntity.setRegistTime(currentTime);
            orderIndexDao.insert(orderIndexEntity);
        }

        return orderVersionNo;
    }

}
