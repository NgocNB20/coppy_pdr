// 廃止不要機能
///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.inquiry;
//
//import java.lang.reflect.InvocationTargetException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeInquiryRequestType;
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeInquiryStatus;
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeInquiryType;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.inquiry.InquiryDetailsDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.inquiry.InquiryDetailEntity;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.inquiry.InquiryEntity;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
//import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
//
///**
// * 問い合わせ Helperクラス<br/>
// *
// * @author kaneda
// */
//@Component
//public class InquiryHelper {
//
//    /**
//     * CommonInfoUtility
//     */
//    private CommonInfoUtility commonInfoUtility;
//
//    /**
//     * コンストラクタ
//     *
//     * @param commonInfoUtility
//     */
//    @Autowired
//    public InquiryHelper(CommonInfoUtility commonInfoUtility) {
//        this.commonInfoUtility = commonInfoUtility;
//    }
//
//    /**
//     * Modelへの変換処理
//     *
//     * @param memberInfoEntity 会員情報
//     * @param inquiryModel     問い合わせModel
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    public void toPageForLoad(MemberInfoEntity memberInfoEntity, InquiryModel inquiryModel) throws IllegalAccessException, InvocationTargetException {
//
//        // フィールドコピー
//        BeanUtils.copyProperties(inquiryModel, memberInfoEntity);
//
//        // 取得した会員情報をページに変換
//        inquiryModel.setInquiryLastName(memberInfoEntity.getMemberInfoLastName());
//        inquiryModel.setInquiryFirstName(memberInfoEntity.getMemberInfoFirstName());
//        inquiryModel.setInquiryLastKana(memberInfoEntity.getMemberInfoLastKana());
//        inquiryModel.setInquiryFirstKana(memberInfoEntity.getMemberInfoFirstKana());
//        inquiryModel.setInquiryMail(memberInfoEntity.getMemberInfoMail());
//        inquiryModel.setInquiryTel(memberInfoEntity.getMemberInfoTel());
//    }
//
//    /**
//     * Modelへの変換処理
//     *
//     * @param indexPage 問い合わせ入力画面
//     */
//    public void toPageForConfirm(InquiryModel inquiryModel) {
//        // 問い合わせ分類SEQから名称を取得
//        Map<String, String> inquiryGroupItems = inquiryModel.getInquiryGroupItems();
//
//        inquiryModel.setInquiryGroupName(inquiryGroupItems.get(inquiryModel.getInquiryGroup()));
//    }
//
//    /**
//     * 問い合わせエンティティへ変換
//     *
//     * @param inquiryModel 問い合わせModel
//     * @return 問い合わせエンティティDTO
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    public InquiryDetailsDto toEntityDtoForPage(InquiryModel inquiryModel) throws IllegalAccessException, InvocationTargetException {
//
//        InquiryEntity inquiryEntity = ApplicationContextUtility.getBean(InquiryEntity.class);
//        BeanUtils.copyProperties(inquiryEntity, inquiryModel);
//        inquiryEntity.setInquiryGroupSeq(Integer.parseInt(inquiryModel.getInquiryGroup()));
//
//        // 画面入力項目以外を設定
//        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
//        Timestamp time = dateUtility.getCurrentTime();
//        inquiryEntity.setLastUserInquiryTime(time);
//        inquiryEntity.setFirstInquiryTime(time);
//        inquiryEntity.setInquiryStatus((HTypeInquiryStatus.RECEIVING));
//        inquiryEntity.setInquiryType((HTypeInquiryType.GENERAL));
//
//        if (commonInfoUtility.isLogin(inquiryModel.getCommonInfo())) {
//            // ログイン中の場合は会員SEQを取得
//            inquiryEntity.setMemberInfoSeq(inquiryModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
//        }
//
//        // 問合せ内容を設定
//        InquiryDetailEntity inquiryDetail = ApplicationContextUtility.getBean(InquiryDetailEntity.class);
//        // 画面入力項目を設定
//        inquiryDetail.setInquiryBody(inquiryModel.getInquiryBody());
//        // 画面入力項目以外を設定
//        inquiryDetail.setInquiryVersionNo(1);
//        inquiryDetail.setRequestType(HTypeInquiryRequestType.CONSUMER);
//        inquiryDetail.setInquiryTime(time);
//
//        List<InquiryDetailEntity> list = new ArrayList<>();
//        list.add(inquiryDetail);
//
//        // お問い合わせエンティティDtoに設定
//        InquiryDetailsDto detailsDto = ApplicationContextUtility.getBean(InquiryDetailsDto.class);
//        detailsDto.setInquiryEntity(inquiryEntity);
//        detailsDto.setInquiryDetailEntityList(list);
//
//        return detailsDto;
//    }
//}
