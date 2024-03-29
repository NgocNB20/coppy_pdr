/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.change;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoConfirmScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * 会員情報変更 Helper
 *
 * @author kimura
 */
@Component
public class MemberChangeHelper {

    /**
     * 初期処理の画面表示<br/>
     * 取得した会員情報をページの各項目にセット<br/>
     *
     * @param memberInfoEntity  会員エンティティ
     * @param memberChangeModel 会員情報変更入力画面
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void toPageForLoad(MemberInfoEntity memberInfoEntity, MemberChangeModel memberChangeModel)
                    throws IllegalAccessException, InvocationTargetException {

        // 取得した会員情報をセッションに保存
        memberChangeModel.setMemberInfoEntity(memberInfoEntity);

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // フィールドコピー
        BeanUtils.copyProperties(memberChangeModel, memberInfoEntity);

        // 性別
        if (memberInfoEntity.getMemberInfoSex() != null)
            memberChangeModel.setMemberInfoSex(memberInfoEntity.getMemberInfoSex().getValue());

        // 生年月日
        if (memberInfoEntity.getMemberInfoBirthday() != null) {

            String[] birthdayArray = conversionUtility.toYmdArray(memberInfoEntity.getMemberInfoBirthday());
            memberChangeModel.setMemberInfoBirthdayYear(birthdayArray[0]);
            memberChangeModel.setMemberInfoBirthdayMonth(birthdayArray[1]);
            memberChangeModel.setMemberInfoBirthdayDay(birthdayArray[2]);
        }

        // 郵便番号
        String[] zipCodeArray = conversionUtility.toZipCodeArray(memberInfoEntity.getMemberInfoZipCode());
        memberChangeModel.setMemberInfoZipCode1(zipCodeArray[0]);
        memberChangeModel.setMemberInfoZipCode2(zipCodeArray[1]);
    }

    /**
     * 変更する会員情報を作成する<br/>
     *
     * @param memberChangeModel 会員情報変更確認画面
     * @return MemberInfoEntity 変更する会員情報
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public MemberInfoEntity toMemberInfoEntityForMemberInfoUpdate(MemberChangeModel memberChangeModel)
                    throws IllegalAccessException, InvocationTargetException {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 元会員情報コピー
        MemberInfoEntity memberInfoEntity = ApplicationContextUtility.getBean(MemberInfoEntity.class);
        BeanUtils.copyProperties(memberInfoEntity, memberChangeModel.getMemberInfoEntity());

        // 画面入力情報で上書き
        memberInfoEntity.setMemberInfoFirstKana(memberChangeModel.getMemberInfoFirstKana());
        memberInfoEntity.setMemberInfoFirstName(memberChangeModel.getMemberInfoFirstName());
        memberInfoEntity.setMemberInfoLastKana(memberChangeModel.getMemberInfoLastKana());
        memberInfoEntity.setMemberInfoLastName(memberChangeModel.getMemberInfoLastName());
        memberInfoEntity.setMemberInfoPrefecture(memberChangeModel.getMemberInfoPrefecture());
        memberInfoEntity.setMemberInfoAddress1(memberChangeModel.getMemberInfoAddress1());
        memberInfoEntity.setMemberInfoAddress2(memberChangeModel.getMemberInfoAddress2());
        memberInfoEntity.setMemberInfoAddress3(memberChangeModel.getMemberInfoAddress3());
        memberInfoEntity.setMemberInfoTel(memberChangeModel.getMemberInfoTel());
        memberInfoEntity.setMemberInfoContactTel(memberChangeModel.getMemberInfoContactTel());
        memberInfoEntity.setMemberInfoFax(memberChangeModel.getMemberInfoFax());

        // 都道府県区分
        memberInfoEntity.setPrefectureType(EnumTypeUtil.getEnumFromLabel(HTypePrefectureType.class,
                                                                         memberInfoEntity.getMemberInfoPrefecture()
                                                                        ));

        // 性別
        memberInfoEntity.setMemberInfoSex(EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class,
                                                                        memberChangeModel.getMemberInfoSex()
                                                                       ));

        // 生年月日
        if (memberChangeModel.getMemberInfoBirthdayYear() != null) {
            memberInfoEntity.setMemberInfoBirthday(
                            conversionUtility.toTimeStamp(memberChangeModel.getMemberInfoBirthdayYear(),
                                                          memberChangeModel.getMemberInfoBirthdayMonth(),
                                                          memberChangeModel.getMemberInfoBirthdayDay()
                                                         ));
        }

        // 郵便番号
        memberInfoEntity.setMemberInfoZipCode(
                        memberChangeModel.getMemberInfoZipCode1() + memberChangeModel.getMemberInfoZipCode2());

        return memberInfoEntity;
    }

    /**
     * 会員クラスに反映
     *
     * @param response 会員Entityレスポンス
     * @return 会員クラス
     */
    public MemberInfoEntity toMemberInfoEntity(MemberInfoEntityResponse response)
                    throws InvocationTargetException, IllegalAccessException {
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        BeanUtils.copyProperties(memberInfoEntity, response);

        return memberInfoEntity;
    }

    // 2023-renew AddNo2 from here
//    /**
//     * 会員情報確認画面用会員更新リクエストに反映
//     *
//     * @param memberInfoEntity 会員クラス
//     * @return 会員情報確認画面用会員更新リクエスト
//     */
//    public MemberInfoConfirmScreenUpdateRequest toMemberInfoConfirmScreenUpdateRequest(MemberInfoEntity memberInfoEntity) {
//        MemberInfoConfirmScreenUpdateRequest request = new MemberInfoConfirmScreenUpdateRequest();
//        request.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
//        if (memberInfoEntity.getSendMailPermitFlag() == HTypeSendMailPermitFlag.ON) {
//            request.setSendMail(true);
//        }
//        if (memberInfoEntity.getMetalPermitFlag() == HTypeMetalPermitFlag.ON) {
//            request.setMetalPermitFlag(true);
//        }
//
//        return request;
//    }
    // 2023-renew AddNo2 to here
}
