// 廃止不要機能
///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.utility;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
//import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoBase;
//import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoUser;
//import jp.co.hankyuhanshin.itec.hitmall.front.entity.inquiry.InquiryGroupEntity;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 問合せ系ユーティリティ
// *
// * @author kizaki
// * @version $Revision:$
// */
//@Component
//public class InquiryUtility {
//
//    /**
//     * コンストラクタ
//     */
//    public InquiryUtility() {
//    }
//
//    /**
//     * 問い合わせ分類ラジオボタン作成
//     *
//     * @param list 問い合わせ分類エンティティDtoのリスト
//     * @return 問い合わせ分類ラジオボタン
//     */
//    public Map<String, String> makeInquiryGroupMap(List<InquiryGroupEntity> list) {
//        Map<String, String> map = new LinkedHashMap<>();
//        for (InquiryGroupEntity entity : list) {
//            map.put(entity.getInquiryGroupSeq().toString(), entity.getInquiryGroupName());
//        }
//        return map;
//    }
//
//    /**
//     * ShopScopeのSessionに認証済みのお問い合わせ番号として登録
//     *
//     * @param inquiryCode お問い合わせ番号
//     */
//    public void saveCode(String inquiryCode) {
//        if (StringUtils.isEmpty(inquiryCode)) {
//            return;
//        }
//        List<String> list = getAttestedCodeList();
//        if (list.contains(inquiryCode)) {
//            // 既に登録済の場合は処理しない
//            return;
//        }
//        list.add(inquiryCode);
//
//        // CommonInfoOtherにセット
//        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);
//        commonInfo.getCommonInfoBase().setInquiryCodeAttestationList(list);
//    }
//
//    /**
//     * 指定した問合せが認証済かをチェックする
//     *
//     * @param inquiryCode お問い合わせ番号
//     * @return true：認証済
//     */
//    public boolean isAttested(String inquiryCode) {
//        if (StringUtils.isEmpty(inquiryCode)) {
//            return false;
//        }
//        List<String> list = getAttestedCodeList();
//        return list.contains(inquiryCode);
//    }
//
//    /**
//     * 認証済のお問い合わせ番号のリストを取得
//     *
//     * @return お問い合わせ番号のリスト
//     */
//    private List<String> getAttestedCodeList() {
//        // CommonInfoOtherより取得
//        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);
//        List<String> list = commonInfo.getCommonInfoBase().getInquiryCodeAttestationList();
//        if (list == null) {
//            list = new ArrayList<>();
//        }
//        return list;
//    }
//
//    /**
//     * 問い合わせ追跡用のログ出力を行う
//     * <pre>
//     * 下記の内容のログを出力する。
//     * 2014-05-22 18:17:41,035 FREE 053C68E833223B5B706D6392DE373D93 INQUIRY
//     * /view/front/order/confirm.html inquiryCode : K60609000020 memberInfoSeq :
//     * 10000001 AUI : 1001140513134908
//     *
//     * 「2014-05-22 18:17:41,035 FREE」 ここまでは自動で出力されるので、それ以降を作成する。
//     * 【出力情報】
//     * セッションID / URL / お問い合わせ番号 / 会員SEQ / 端末識別番号
//     * </pre>
//     *
//     * @param page        該当ページ（出力対象のページ）
//     * @param inquiryCode お問い合わせ番号
//     */
//    public void writeInquiryLog(String inquiryCode) {
//        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);
//        if (commonInfo == null) {
//            return;
//        }
//        CommonInfoBase base = commonInfo.getCommonInfoBase();
//        CommonInfoUser user = commonInfo.getCommonInfoUser();
//        if (base == null || user == null) {
//            return;
//        }
//
//        // メッセージ作成
//        Map<String, Serializable> paramMap = new LinkedHashMap<>();
//        paramMap.put("inquiryCode", inquiryCode);
//        paramMap.put("memberInfoSeq", user.getMemberInfoSeq() == null ? "GUEST" : user.getMemberInfoSeq());
//        paramMap.put("AUI", base.getAccessUid());
//
//        // ログ出力
//        ApplicationLogUtility applicationLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
//        applicationLogUtility.writeFreeLog("INQUIRY", base.getPageId(), null, paramMap);
//    }
//
//}
