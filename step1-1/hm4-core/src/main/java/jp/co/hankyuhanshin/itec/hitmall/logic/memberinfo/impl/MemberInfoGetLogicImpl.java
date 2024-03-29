/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoForMemberMemnuDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.NumberUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 会員情報取得<br/>
 *
 * @author natume
 * @version $Revision: 1.7 $
 */
@Component
public class MemberInfoGetLogicImpl extends AbstractShopLogic implements MemberInfoGetLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoGetLogicImpl.class);

    /**
     * MemberInfoDao<br/>
     */
    private final MemberInfoDao memberInfoDao;

    /**
     * 数字関連ヘルパークラス
     */
    private final NumberUtility numberUtility;

    @Autowired
    public MemberInfoGetLogicImpl(MemberInfoDao memberInfoDao, NumberUtility numberUtility) {
        this.memberInfoDao = memberInfoDao;
        this.numberUtility = numberUtility;
    }

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoSeq 会員情報SEQ
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity execute(Integer memberInfoSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // 会員情報取得
        return memberInfoDao.getEntity(memberInfoSeq);
    }

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoSeq    会員情報SEQ
     * @param memberInfoStatus 会員状態
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity execute(Integer memberInfoSeq, HTypeMemberInfoStatus memberInfoStatus) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotNull("memberInfoStatus", memberInfoStatus);

        // 会員情報取得
        return memberInfoDao.getEntityByStatus(memberInfoSeq, memberInfoStatus);
    }

    /**
     * 会員情報を取得する<br />
     *
     * @param shopSeq            ショップSEQ
     * @param memberInfoId       会員ID
     * @param memberInfoBirthDay 会員生年月日
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity execute(Integer shopSeq, String memberInfoId, Timestamp memberInfoBirthDay) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("memberInfoId", memberInfoId);
        ArgumentCheckUtil.assertNotNull("memberInfoBirthDay", memberInfoBirthDay);

        // 会員情報取得
        return memberInfoDao.getEntityByIdBirthDay(shopSeq, memberInfoId, memberInfoBirthDay);
    }

    /**
     * 会員情報を取得する<br />
     *
     * @param shopSeq          ショップSEQ
     * @param memberInfoId     会員ID
     * @param memberInfoStatus 会員状態
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity execute(Integer shopSeq, String memberInfoId, HTypeMemberInfoStatus memberInfoStatus) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("memberInfoId", memberInfoId);
        ArgumentCheckUtil.assertNotNull("memberInfoStatus", memberInfoStatus);

        // 会員情報取得
        return memberInfoDao.getEntityByIdStatus(shopSeq, memberInfoId, memberInfoStatus);
    }

    /**
     * 会員情報を取得する<br />
     *
     * @param shopSeq   ショップSEQ
     * @param accessUid 端末識別番号
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity execute(Integer shopSeq, String accessUid) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("accessUid", accessUid);

        // 会員情報取得
        return memberInfoDao.getEntityByAccessUid(shopSeq, accessUid);
    }

    /**
     * 会員情報を取得する<br />
     *
     * @param shopUniqueId ショップユニークId
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity execute(String shopUniqueId) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("shopUniqueId", shopUniqueId);

        // 会員情報取得
        return memberInfoDao.getEntityByMemberInfoUniqueId(shopUniqueId);
    }

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoSeq 会員情報SEQ
     * @return MemberInfoForMemberTopDto フロント表示用会員情報Dto
     */
    @Override
    public MemberInfoForMemberMemnuDto getMemberMenuDto(Integer memberInfoSeq) {
        MemberInfoForMemberMemnuDto memberDto = ApplicationContextUtility.getBean(MemberInfoForMemberMemnuDto.class);

        return memberDto;
    }

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoMail   メールアドレス
     * @param memberInfoStatus 会員ステータス
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity executeByMailStatus(String memberInfoMail, HTypeMemberInfoStatus memberInfoStatus) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("memberInfoMail", memberInfoMail);
        ArgumentCheckUtil.assertNotNull("memberInfoStatus", memberInfoStatus);

        // 会員情報取得
        return memberInfoDao.getEntityByMailStatus(memberInfoMail, memberInfoStatus);
    }

    /**
     * 暫定会員情報を取得する<br />
     *
     * @param memberInfoUniqueId 会員ユニークID
     * @param memberInfoMail     メールアドレス
     * @return 暫定会員情報エンティティ
     */
    @Override
    public MemberInfoEntity executeForProvisional(String memberInfoUniqueId, String memberInfoMail) {

        // ユニークIDで会員情報取得
        MemberInfoEntity memberInfoEntityByUniqueId = memberInfoDao.getEntityByMemberInfoUniqueId(memberInfoUniqueId);

        // メールアドレスとステータスで会員情報取得
        MemberInfoEntity memberInfoEntityByMail =
                        memberInfoDao.getEntityByMailStatus(memberInfoMail, HTypeMemberInfoStatus.ADMISSION);

        if (memberInfoEntityByUniqueId == null && memberInfoEntityByMail != null) {
            // 暫定会員の情報のみ登録されている場合
            return memberInfoEntityByMail;
        }

        return null;
    }
    // PDR Migrate Customization from here

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoIdOrCustomerNo 会員ID 又は 顧客番号
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity getMemberInfoEntityByMemberInfoIdOrCustomerNo(String memberInfoIdOrCustomerNo) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoIdOrCustomerNo", memberInfoIdOrCustomerNo);

        Integer customerNo = null;

        // memberInfoIdOrCustomerNoが全て数値かチェックを行う。
        if (numberUtility.isNumber(memberInfoIdOrCustomerNo)) {
            try {
                customerNo = Integer.valueOf(memberInfoIdOrCustomerNo);
            } catch (NumberFormatException e) {
                // 顧客番号の数値変換に失敗した場合(桁数オーバー)
                // 一致会員なしとするためnullを返却
                LOGGER.error("例外処理が発生しました", e);
                return null;
            }
        }

        return memberInfoDao.getEntityByMemberInfoMailOrCustomerNo(memberInfoIdOrCustomerNo, customerNo);
    }

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoTel 電話番号
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity getMemberInfoEntityByMemberInfoTel(String memberInfoTel) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoTel", memberInfoTel);

        return memberInfoDao.getEntityByMemberInfoTel(memberInfoTel);
    }

    /**
     * メールを取得する
     *
     * @param memberInfoMail メール
     * @return メールエンティティ
     */
    // 2023-renew No79 from here
    @Override
    public MemberInfoEntity getMemberInfoEntityByMemberInfoMail(String memberInfoMail) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoMail", memberInfoMail);

        return memberInfoDao.getEntityByMemberInfoMail(memberInfoMail);
    }
    // 2023-renew No79 to here

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoTel 電話番号
     * @param customerNo    顧客番号
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity getMemberInfoEntityByMemberInfoTelAndCustomerNo(String memberInfoTel, String customerNo) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoTel", memberInfoTel);
        ArgumentCheckUtil.assertNotNull("customerNo", customerNo);

        Integer customerNoValue = null;
        if (numberUtility.isNumber(customerNo)) {
            customerNoValue = Integer.valueOf(customerNo);
        }

        return memberInfoDao.getEntityByMemberInfoTelAndCustomerNo(memberInfoTel, customerNoValue);
    }

    /**
     * 会員情報を取得する<br />
     *
     * @param customerNo 顧客番号
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity getMemberInfoEntityByCustomerNo(Integer customerNo) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("customerNo", customerNo);

        return memberInfoDao.getEntityByCustomerNo(customerNo);
    }
    // PDR Migrate Customization to here

    /**
     * 会員情報を取得する(ロック)<br />
     *
     * @param memberInfoSeq 会員情報SEQ
     * @return 会員情報エンティティ
     */
    @Override
    public MemberInfoEntity getEntityForUpdate(Integer memberInfoSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // 会員情報取得
        return memberInfoDao.getEntityForUpdate(memberInfoSeq);
    }
}
