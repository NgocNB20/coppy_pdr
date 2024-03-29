// 廃止不要機能
///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.inquiry;
//
//import java.lang.reflect.InvocationTargetException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.beanutils.BeanUtils;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeInquiryRequestType;
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeInquiryStatus;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.inquiry.InquiryDetailsDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.inquiry.InquiryDetailEntity;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.inquiry.InquiryEntity;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CopyUtil;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
//
///**
// * 問合せヘルパー基底クラス<br/>
// *
// * @author kn23834
// * @version $Revision: 1.0 $
// */
//public abstract class AbstractInquiryHelper {
//
//    /**
//     * ページ変換、初期表示<br/>
//     *
//     * @param abstractInquiryModel 問合せ詳細履歴Model
//     * @param dto                  問い合わせ詳細Dto
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    public void toPageForLoad(AbstractInquiryModel abstractInquiryModel, InquiryDetailsDto dto) throws IllegalAccessException, InvocationTargetException {
//        // 問い合わせ情報を設定
//        InquiryEntity inquiryEntity = dto.getInquiryEntity();
//        BeanUtils.copyProperties(abstractInquiryModel, inquiryEntity);
//        abstractInquiryModel.setInquiryStatus(inquiryEntity.getInquiryStatus().getLabel());
//        abstractInquiryModel.setInquiryStatusValue(inquiryEntity.getInquiryStatus().getValue());
//        abstractInquiryModel.setInquiryName(inquiryEntity.getInquiryLastName() + " " + inquiryEntity.getInquiryFirstName());
//        abstractInquiryModel.setInquiryKana(inquiryEntity.getInquiryLastKana() + " " + inquiryEntity.getInquiryFirstKana());
//        abstractInquiryModel.setReedOnlyDto(dto);
//
//        // 問い合わせ分類情報設定 */
//        abstractInquiryModel.setInquiryGroupName(dto.getInquiryGroupName());
//
//        // お問い合わせ詳細画面アイテムの設定 */
//        abstractInquiryModel.setInquiryModelItems(createModelItemList(dto));
//
//        // 保持用
//        abstractInquiryModel.setIcd(inquiryEntity.getInquiryCode());
//        abstractInquiryModel.setSaveIcd(inquiryEntity.getInquiryCode());
//    }
//
//    /**
//     * お問い合わせ詳細画面アイテムのリストを作成
//     *
//     * @param dto 問い合わせ詳細Dto
//     * @return お問い合わせ詳細画面アイテムのリスト
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    private List<InquiryModelItem> createModelItemList(InquiryDetailsDto dto) throws IllegalAccessException, InvocationTargetException {
//        if (dto.getInquiryDetailEntityList() == null || dto.getInquiryDetailEntityList().isEmpty()) {
//            return null;
//        }
//        List<InquiryModelItem> itemList = new ArrayList<>();
//        for (InquiryDetailEntity inquiryDetailEntity : dto.getInquiryDetailEntityList()) {
//            InquiryModelItem item = createModelItem(inquiryDetailEntity);
//            itemList.add(item);
//        }
//        return itemList;
//    }
//
//    /**
//     * お問い合わせ詳細画面アイテムを作成
//     *
//     * @param inquiryDetail 問い合わせ内容Entity
//     * @return お問い合わせ詳細画面アイテム
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    private InquiryModelItem createModelItem(InquiryDetailEntity inquiryDetail) throws IllegalAccessException, InvocationTargetException {
//        InquiryModelItem item = ApplicationContextUtility.getBean(InquiryModelItem.class);
//        BeanUtils.copyProperties(item, inquiryDetail);
//
//        return item;
//    }
//
//    /**
//     * 登録時処理(問い合わせ内容エンティティ反映)
//     *
//     * @param abstractInquiryModel 問合せ詳細履歴Model
//     * @return 問い合わせ詳細Dto
//     */
//    public InquiryDetailsDto toInquiryDetailsDtoForPage(AbstractInquiryModel abstractInquiryModel) {
//        InquiryDetailsDto inquiryDetailsDto = CopyUtil.deepCopy(abstractInquiryModel.getReedOnlyDto());
//
//        // 問合せを設定
//        InquiryEntity inquiryEntity = inquiryDetailsDto.getInquiryEntity();
//        // 画面入力項目以外を設定
//        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
//        Timestamp time = dateUtility.getCurrentTime();
//        inquiryEntity.setLastUserInquiryTime(time);
//        inquiryEntity.setInquiryStatus(HTypeInquiryStatus.RECEIVING);
//
//        // 問合せ内容を設定
//        InquiryDetailEntity inquiryDetailEntity = ApplicationContextUtility.getBean(InquiryDetailEntity.class);
//        // 画面入力項目を設定
//        inquiryDetailEntity.setInquiryBody(abstractInquiryModel.getInputInquiryBody());
//        // 画面入力項目以外を設定
//        inquiryDetailEntity.setInquirySeq(inquiryEntity.getInquirySeq());
//        inquiryDetailEntity.setInquiryVersionNo(inquiryDetailsDto.getInquiryDetailEntityList().size() + 1);
//        inquiryDetailEntity.setRequestType(HTypeInquiryRequestType.CONSUMER);
//        inquiryDetailEntity.setInquiryTime(time);
//
//        // 新規登録する問い合わせ内容を追加する
//        inquiryDetailsDto.getInquiryDetailEntityList().add(inquiryDetailEntity);
//
//        return inquiryDetailsDto;
//    }
//
//    /**
//     * 画面の状態を受付完了にする
//     *
//     * @param abstractInquiryModel 問合せ詳細履歴Model
//     */
//    public void changeAccepted(AbstractInquiryModel abstractInquiryModel) {
//        abstractInquiryModel.setReceived(true);
//        abstractInquiryModel.setInputInquiryBody(null);
//    }
//
//}
