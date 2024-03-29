/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsMergeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoRegistService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 会員登録サービス実装<br/>
 *
 * @author natume
 * 　* @author kaneko　(itec) チケット対応　#2644　訪問者数にクローラが含まれている
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class MemberInfoRegistServiceImpl extends AbstractShopService implements MemberInfoRegistService {

    /**
     * 会員情報登録ロジック<br/>
     */
    private final MemberInfoRegistLogic memberInfoRegistLogic;

    /**
     * 会員情報更新ロジック<br/>
     */
    private final MemberInfoUpdateLogic memberInfoUpdateLogic;

    /**
     * 会員情報取得ロジック<br/>
     */
    private final MemberInfoGetLogic memberInfoGetLogic;

    /**
     * 会員情報データチェックロジック<br/>
     */
    private final MemberInfoDataCheckLogic memberInfoDataCheckLogic;

    /**
     * カートマージロジック<br/>
     */
    private final CartGoodsMergeLogic cartGoodsMergeLogic;

    /**
     * 確認メール削除ロジック
     */
    private final ConfirmMailDeleteLogic confirmMailDeleteLogic;

    /**
     * 会員SEQ取得Dao
     */
    private final MemberInfoDao memberInfoDao;

    /**
     * 会員業務Utility
     */
    private final MemberInfoUtility memberInfoUtility;

    /** 日付関連Utilityクラス */
    private final DateUtility dateUtility;

    @Autowired
    public MemberInfoRegistServiceImpl(MemberInfoRegistLogic memberInfoRegistLogic,
                                       MemberInfoUpdateLogic memberInfoUpdateLogic,
                                       MemberInfoGetLogic memberInfoGetLogic,
                                       MemberInfoDataCheckLogic memberInfoDataCheckLogic,
                                       CartGoodsMergeLogic cartGoodsMergeLogic,
                                       ConfirmMailDeleteLogic confirmMailDeleteLogic,
                                       MemberInfoDao memberInfoDao,
                                       MemberInfoUtility memberInfoUtility,
                                       DateUtility dateUtility) {
        this.memberInfoRegistLogic = memberInfoRegistLogic;
        this.memberInfoUpdateLogic = memberInfoUpdateLogic;
        this.memberInfoGetLogic = memberInfoGetLogic;
        this.memberInfoDataCheckLogic = memberInfoDataCheckLogic;
        this.cartGoodsMergeLogic = cartGoodsMergeLogic;
        this.confirmMailDeleteLogic = confirmMailDeleteLogic;
        this.memberInfoDao = memberInfoDao;
        this.memberInfoUtility = memberInfoUtility;
        this.dateUtility = dateUtility;
    }

    /**
     * ・会員ID重複チェック<br/>
     * ・パスワードの暗号化<br/>
     * ・会員情報の登録を行う <br/>
     * ・メルマガ購読者の登録・更新を行う<br/>
     * ・確認メールの削除を行う<br/>
     *
     * @param memberInfoEntity    会員エンティティ
     * @param confirmMailPassword 確認メールパスワード
     */
    @Override
    public void execute(MemberInfoEntity memberInfoEntity,
                        String confirmMailPassword,
                        Boolean isLogin,
                        String accessUid,
                        Boolean isSiteBack) {
        executeHm(memberInfoEntity, true, isLogin, accessUid, isSiteBack);

        // 確認メール削除ロジック実行（排他チェックは会員情報登録の中で行っているため、ここでは行わない）
        confirmMailDeleteLogic.execute(confirmMailPassword);
    }

    /**
     * ・会員ID重複チェック<br/>
     * ・パスワードの暗号化<br/>
     * ・会員情報の登録を行う <br/>
     * ・メルマガ購読者の登録・更新を行う<br/>
     * ・カート商品のマージを行う（cartMergeがtrueの場合）<br/>
     *
     * @param memberInfoEntity 会員エンティティ
     * @param cartMerge        カートマージをするかどうか。true..する / false..
     */
    @Override
    public void executeHm(MemberInfoEntity memberInfoEntity,
                          boolean cartMerge,
                          Boolean isLogin,
                          String accessUid,
                          Boolean isSiteBack) {

        // 入力チェック
        checkParamater(memberInfoEntity, isSiteBack, accessUid);

        // 会員情報の設定
        setMemberInfoEntity(memberInfoEntity);

        int result = 0;

        // 暫定会員を取得する
        MemberInfoEntity proMemberInfoEntity =
                        memberInfoGetLogic.executeForProvisional(memberInfoEntity.getMemberInfoUniqueId(),
                                                                 memberInfoEntity.getMemberInfoMail()
                                                                );

        if (proMemberInfoEntity != null) {

            // 暫定会員が存在するため情報を設定
            setMemberInfoEntity(memberInfoEntity, proMemberInfoEntity);

            // 本会員へ更新する
            result = memberInfoUpdateLogic.execute(memberInfoEntity);
            if (result == 0) {
                throwMessage(MSGCD_MEMBERINFO_REGIST_FAIL);
            }
        } else {

            // 会員情報チェック
            checkMemberInfo(memberInfoEntity);

            // 会員情報登録
            result = memberInfoRegistLogic.execute(memberInfoEntity);
            if (result == 0) {
                throwMessage(MSGCD_MEMBERINFO_REGIST_FAIL);
            }
        }

        // カートマージをするかどうか
        if (cartMerge) {
            // カート商品の移行 ※ゲストの場合のみ
            if (!isLogin) {
                // カートマージ処理
                cartGoodsMergeLogic.execute(
                                memberInfoEntity.getShopSeq(), 0, accessUid, memberInfoEntity.getMemberInfoSeq());
            }
        }
    }

    /**
     * 会員情報データチェック<br/>
     *
     * @param memberInfoEntity 会員エンティティ
     */
    protected void checkMemberInfo(MemberInfoEntity memberInfoEntity) {
        memberInfoDataCheckLogic.execute(memberInfoEntity);
    }

    /**
     * 入力チェック<br/>
     *
     * @param memberInfoEntity 会員情報エンティティ
     */
    protected void checkParamater(MemberInfoEntity memberInfoEntity, Boolean isSiteBack, String accessUid) {
        /** 共通情報Helper取得 */
        if (!isSiteBack) {
            ArgumentCheckUtil.assertNotEmpty("commonInfo.accessUid", accessUid);
        }
        ArgumentCheckUtil.assertNotNull("memberInfoEntity", memberInfoEntity);
    }

    /**
     * 本会員情報のセット<br/>
     *
     * @param memberInfoEntity 会員エンティティ
     */
    protected void setMemberInfoEntity(MemberInfoEntity memberInfoEntity) {

        // 会員状態：「入会」に設定
        if (memberInfoEntity.getMemberInfoStatus() == null) {
            memberInfoEntity.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);
        }

        // 会員ユニークIDの作成・セット
        Integer shopSeq = 1001;

        // ショップSEQ
        memberInfoEntity.setShopSeq(shopSeq);

        // 会員SEQ取得
        Integer memberInfoSeq = memberInfoDao.getMemberInfoSeqNextVal();
        memberInfoEntity.setMemberInfoSeq(memberInfoSeq);

        // ユニークID
        if (HTypeMemberInfoStatus.ADMISSION.equals(memberInfoEntity.getMemberInfoStatus())) {
            memberInfoEntity.setMemberInfoUniqueId(
                            memberInfoUtility.createShopUniqueId(shopSeq, memberInfoEntity.getMemberInfoMail()));
        }

        // SpringSecurity準拠の方式で暗号化
        PasswordEncoder encoder = ApplicationContextUtility.getBeanByName("encoderMember", PasswordEncoder.class);
        // パスワード暗号化
        if (StringUtils.isNotEmpty(memberInfoEntity.getMemberInfoPassword())) {
            String encryptedPassword = encoder.encode(memberInfoEntity.getMemberInfoPassword());
            memberInfoEntity.setMemberInfoPassword(encryptedPassword);
        }

        // 都道府県区分
        memberInfoEntity.setPrefectureType(EnumTypeUtil.getEnumFromLabel(HTypePrefectureType.class,
                                                                         memberInfoEntity.getMemberInfoPrefecture()
                                                                        ));

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 入会日
        if (StringUtil.isEmpty(memberInfoEntity.getAdmissionYmd())) {
            memberInfoEntity.setAdmissionYmd(dateUtility.getCurrentYmd());
        }
    }

    /**
     * 本会員情報のセット<br/>
     * 暫定会員の情報をセットする
     *
     * @param targetMemberInfoEntity 会員エンティティ
     * @param proMemberInfoEntity    会員エンティティ
     */
    protected void setMemberInfoEntity(MemberInfoEntity targetMemberInfoEntity, MemberInfoEntity proMemberInfoEntity) {

        // 会員状態に差分がある場合、変更する
        if (targetMemberInfoEntity.getMemberInfoStatus() != proMemberInfoEntity.getMemberInfoStatus()) {
            targetMemberInfoEntity.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);
        }

        // 会員SEQを暫定会員登録時の設定値に、変更する
        targetMemberInfoEntity.setMemberInfoSeq(proMemberInfoEntity.getMemberInfoSeq());

        // 必須項目をそのまま再設定
        targetMemberInfoEntity.setPaymentCardRegistType(proMemberInfoEntity.getPaymentCardRegistType());
        targetMemberInfoEntity.setRegistTime(proMemberInfoEntity.getRegistTime());
    }
    // PDR Migrate Customization from here

    /**
     * 会員登録を行います。<br/>
     *
     * <pre>
     * 以下の処理を行います。
     * ・会員ID重複チェック
     * ・パスワードの暗号化
     * ・会員情報の登録を行う
     * </pre>
     *
     * @param memberInfoEntity 会員エンティティ
     * @param onlineFlg        true:オンライン　false:バッチ
     */
    public void execute(MemberInfoEntity memberInfoEntity, boolean onlineFlg, Boolean isSiteBack, String accessUid) {

        // 入力チェック
        checkParamater(memberInfoEntity, isSiteBack, accessUid);

        // 会員情報の設定
        setMemberInfoEntity(memberInfoEntity, onlineFlg);

        // 会員情報チェック
        checkMemberInfo(memberInfoEntity);

        // 会員情報登録
        int result = memberInfoRegistLogic.execute(memberInfoEntity);
        if (result == 0) {
            throwMessage(MSGCD_MEMBERINFO_REGIST_FAIL);
        }
    }

    /**
     * 本会員情報のセット<br/>
     * <pre>
     * 今回必要のない情報設定処理の削除
     * 必要な値の設定処理追加
     * 大まかな流れはPKG標準のソースを流用
     * </pre>
     *
     * @param memberInfoEntity 会員エンティティ
     * @param onlineFlg        true:オンライン　false:バッチ
     */
    protected void setMemberInfoEntity(MemberInfoEntity memberInfoEntity, boolean onlineFlg) {

        // 新規会員登録の場合
        if (onlineFlg) {
            // PDR Migrate Customization from here
            // 会員状態：「退会」に設定
            memberInfoEntity.setMemberInfoStatus(HTypeMemberInfoStatus.REMOVE);
            // PDR Migrate Customization to here

            // 会員ユニークIDの作成・セット
            Integer shopSeq = 1001;

            // ショップSEQ
            memberInfoEntity.setShopSeq(shopSeq);

            // PDR Migrate Customization from here
            // ユニークID
            // 会員業務Helper取得
            if (!StringUtil.isEmpty(memberInfoEntity.getMemberInfoMail())) {
                // メールアドレスが設定されている場合はユニークIDを作成
                MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);
                memberInfoEntity.setMemberInfoUniqueId(
                                memberInfoUtility.createShopUniqueId(shopSeq, memberInfoEntity.getMemberInfoMail()));

            }
            // PDR Migrate Customization to here

            // SpringSecurity準拠の方式で暗号化
            PasswordEncoder encoder = ApplicationContextUtility.getBeanByName("encoderMember", PasswordEncoder.class);
            // パスワード暗号化
            // 2023-renew No25 from here
            String encryptedPassword = encoder.encode(memberInfoUtility.createPassword());
            // 2023-renew No25 to here
            // PDR Migrate Customization to here
            memberInfoEntity.setMemberInfoPassword(encryptedPassword);

            // PDR Migrate Customization from here
            // 都道府県区分 使用しないため削除
            // PDR Migrate Customization to here

            // 日付関連Helper取得
            DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

            /*---------- #216 start----------*/
            // 入会日
            memberInfoEntity.setAdmissionYmd(null);
            // 退会日
            memberInfoEntity.setSecessionYmd(dateUtility.getCurrentYmd());
            /*---------- #216 end----------*/
            // メール配信希望開始日時
            if (memberInfoEntity.getSendMailPermitFlag() == HTypeSendMailPermitFlag.ON) {
                memberInfoEntity.setSendMailStartTime(dateUtility.getCurrentTime());
            }

            // PDR Migrate Customization from here
            // DM配信希望フラグ:0(希望しない)
            ((MemberInfoEntity) memberInfoEntity).setSendDirectMailFlag(HTypeSendDirectMailFlag.OFF);
            // クレジット決済使用可否:1(使用可能)
            ((MemberInfoEntity) memberInfoEntity).setCreditPaymentUseFlag(HTypeCreditPaymentUseFlag.ON);
            // PDR Migrate Customization from here
            // コンビニ・郵便振込使用可否:2(使用可能（初回）)
            ((MemberInfoEntity) memberInfoEntity).setTransferPaymentUseFlag(HTypeTransferPaymentUseFlag.FIRST_TIME);
            // PDR Migrate Customization to here
            // 代金引換使用可否:1(使用可能)
            ((MemberInfoEntity) memberInfoEntity).setCashDeliveryUseFlag(HTypeCashDeliveryUseFlag.ON);
            // 名簿区分
            if (((MemberInfoEntity) memberInfoEntity).getBusinessType().equals(HTypeBusinessType.OTHERS)) {
                // 会員.顧客区分 3：その他 が選択された場合は、一般顧客（EC販促不可）
                ((MemberInfoEntity) memberInfoEntity).setMemberListType(HTypeMemberListType.OFFLINE_GENERAL_CUSTOMER);
            } else {
                // 一般顧客
                ((MemberInfoEntity) memberInfoEntity).setMemberListType(HTypeMemberListType.ONLINE_GENERAL_CUSTOMER);
            }

            // パスワード変更要求フラグ:1(要求する)
            // PDR Migrate Customization from here
            memberInfoEntity.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.ON);
            // PDR Migrate Customization to here
            // オンライン登録フラグ:1(EC会員)
            ((MemberInfoEntity) memberInfoEntity).setOnlineRegistFlag(HTypeOnlineRegistFlag.ON);
            // PDR Migrate Customization to here
        } else {
            /*---------- #216 start----------*/
            // PKG標準処理実行
            setMemberInfoEntity(memberInfoEntity);

            if (HTypeOnlineRegistFlag.ON.equals(((MemberInfoEntity) memberInfoEntity).getOnlineRegistFlag())) {
                // 退会日（入会状態となるため、nullを設定）
                memberInfoEntity.setSecessionYmd(null);
            } else {
                // 入会日（退会状態となるため、nullを設定）
                memberInfoEntity.setAdmissionYmd(null);
                // 退会日（退会状態となるため、現在日付を設定）
                memberInfoEntity.setSecessionYmd(dateUtility.getCurrentYmd());
            }
            /*---------- #216 end----------*/

        }

    }

}
