/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.temp.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.TempMemberInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderPersonGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.temp.TempMemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 仮会員情報取得サービスの実装<br/>
 *
 * @author natume
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class TempMemberInfoGetServiceImpl extends AbstractShopService implements TempMemberInfoGetService {

    /**
     * 確認メール情報取得
     */
    private final ConfirmMailGetLogic confirmMailGetLogic;

    /**
     * 受注サマリ情報取得ロジック
     */
    private final OrderSummaryGetLogic orderSummaryGetLogic;

    /**
     * 受注インデックス情報取得ロジック
     */
    private final OrderIndexGetLogic orderIndexGetLogic;

    /**
     * ご注文主情報取得ロジック
     */
    private final OrderPersonGetLogic orderPersonGetLogic;

    /**
     * 会員情報データチェックロジック
     */
    private final MemberInfoDataCheckLogic memberInfoDataCheckLogic;

    /**
     * MemberInfoUtility
     */
    private final MemberInfoUtility memberInfoUtility;

    @Autowired
    public TempMemberInfoGetServiceImpl(ConfirmMailGetLogic confirmMailGetLogic,
                                        OrderSummaryGetLogic orderSummaryGetLogic,
                                        OrderIndexGetLogic orderIndexGetLogic,
                                        OrderPersonGetLogic orderPersonGetLogic,
                                        MemberInfoDataCheckLogic memberInfoDataCheckLogic,
                                        MemberInfoUtility memberInfoUtility) {
        this.confirmMailGetLogic = confirmMailGetLogic;
        this.orderSummaryGetLogic = orderSummaryGetLogic;
        this.orderIndexGetLogic = orderIndexGetLogic;
        this.orderPersonGetLogic = orderPersonGetLogic;
        this.memberInfoDataCheckLogic = memberInfoDataCheckLogic;
        this.memberInfoUtility = memberInfoUtility;
    }

    /**
     * 仮会員情報取得処理<br/>
     *
     * @param password パスワード
     * @return 会員エンティティ
     */
    @Override
    public TempMemberInfoDto execute(String password) {

        // 入力チェック
        checkParameter(password);

        // 確認メール情報取得
        ConfirmMailEntity confirmMailEntity = getConfirmMail(password);

        // ご注文主情報を取得する
        OrderPersonEntity orderPersonEntity = getOrderPersonEntity(confirmMailEntity);

        // 会員情報を作成する
        MemberInfoEntity memberInfoEntity = createMemberInfoEntity(confirmMailEntity, orderPersonEntity);

        // 会員ID重複チェック
        memberInfoDataCheckLogic.execute(memberInfoEntity);

        // 仮会員登録情報作成
        TempMemberInfoDto tempMemberInfoDto = ApplicationContextUtility.getBean(TempMemberInfoDto.class);
        if (orderPersonEntity != null) {
            tempMemberInfoDto.setOrderSeq(orderPersonEntity.getOrderSeq());
        }
        tempMemberInfoDto.setMemberInfoEntity(memberInfoEntity);

        // 会員エンティティを返す
        return tempMemberInfoDto;
    }

    /**
     * パラメータチェック<br/>
     *
     * @param password メールパスワード
     */
    protected void checkParameter(String password) {
        ArgumentCheckUtil.assertNotEmpty("password", password);
    }

    /**
     * パスワードから有効な確認メール情報を取得する<br/>
     *
     * @param password パスワード
     * @return 確認メールエンティティ
     */
    protected ConfirmMailEntity getConfirmMail(String password) {

        // 確認メール情報取得
        ConfirmMailEntity confirmMailEntity =
                        confirmMailGetLogic.execute(password, HTypeConfirmMailType.TEMP_MEMBERINFO_REGIST);
        if (confirmMailEntity == null) {
            throwMessage(MSGCD_CONFIRMMAILENTITYDTO_NULL);
        }
        return confirmMailEntity;
    }

    /**
     * ご注文主情報を取得する<br/>
     * 受注SEQがある場合のみ<br/>
     *
     * @param confirmMailEntity 確認メールエンティティ
     * @return ご注文主エンティティ
     */
    protected OrderPersonEntity getOrderPersonEntity(ConfirmMailEntity confirmMailEntity) {
        OrderPersonEntity orderPersonEntity = null;
        Integer orderSeq = confirmMailEntity.getOrderSeq();
        if (orderSeq != null && orderSeq != 0) {

            // 受注サマリ情報取得
            OrderSummaryEntity orderSummaryEntity = orderSummaryGetLogic.execute(orderSeq);
            if (orderSummaryEntity == null) {
                throwMessage(MSGCD_ORDERSUMMARYENTITYDTO_NULL);
            }

            // 受注インデックス情報取得
            OrderIndexEntity orderIndexEntity =
                            orderIndexGetLogic.execute(orderSeq, orderSummaryEntity.getOrderVersionNo());
            if (orderIndexEntity == null) {
                throwMessage(MSGCD_ORDERINDEXENTITYDTO_NULL);
            }

            // ご注文主情報取得
            orderPersonEntity = orderPersonGetLogic.execute(orderSeq, orderIndexEntity.getOrderPersonVersionNo());
            if (orderPersonEntity == null) {
                throwMessage(MSGCD_ORDERPERSONENTITYDTO_NULL);
            }
        }
        return orderPersonEntity;
    }

    /**
     * 確認メール情報とご注文主情報から会員情報の作成<br/>
     *
     * @param confirmMailEntity 確認メールエンティティ
     * @param orderPersonEntity ご注文主エンティティ
     * @return 会員エンティティ
     */
    protected MemberInfoEntity createMemberInfoEntity(ConfirmMailEntity confirmMailEntity,
                                                      OrderPersonEntity orderPersonEntity) {
        MemberInfoEntity memberInfoEntity = ApplicationContextUtility.getBean(MemberInfoEntity.class);
        memberInfoEntity.setMemberInfoSeq(confirmMailEntity.getMemberInfoSeq());
        memberInfoEntity.setMemberInfoId(confirmMailEntity.getConfirmMail());
        memberInfoEntity.setMemberInfoMail(confirmMailEntity.getConfirmMail());
        Integer shopSeq = confirmMailEntity.getShopSeq();
        memberInfoEntity.setShopSeq(shopSeq);

        // 会員ユニークIDの作成・セット
        memberInfoEntity.setMemberInfoUniqueId(
                        memberInfoUtility.createShopUniqueId(shopSeq, memberInfoEntity.getMemberInfoMail()));
        if (orderPersonEntity != null) {
            memberInfoEntity.setMemberInfoAddress1(orderPersonEntity.getOrderAddress1());
            memberInfoEntity.setMemberInfoAddress2(orderPersonEntity.getOrderAddress2());
            memberInfoEntity.setMemberInfoAddress3(orderPersonEntity.getOrderAddress3());
            memberInfoEntity.setMemberInfoContactTel(orderPersonEntity.getOrderContactTel());
            memberInfoEntity.setMemberInfoFirstKana(orderPersonEntity.getOrderFirstKana());
            memberInfoEntity.setMemberInfoFirstName(orderPersonEntity.getOrderFirstName());
            memberInfoEntity.setMemberInfoLastKana(orderPersonEntity.getOrderLastKana());
            memberInfoEntity.setMemberInfoLastName(orderPersonEntity.getOrderLastName());
            memberInfoEntity.setMemberInfoPrefecture(orderPersonEntity.getOrderPrefecture());
            memberInfoEntity.setPrefectureType(orderPersonEntity.getPrefectureType());
            memberInfoEntity.setMemberInfoTel(orderPersonEntity.getOrderTel());
            memberInfoEntity.setMemberInfoZipCode(orderPersonEntity.getOrderZipCode());
        }
        return memberInfoEntity;
    }

}
