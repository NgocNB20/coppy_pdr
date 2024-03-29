// 廃止不要機能
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.inquiry;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Component;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.inquiry.InquirySearchDaoConditionDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.dto.inquiry.InquirySearchResultDto;
//import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.inquiry.AbstractInquiryHelper;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
//
///**
// * お問い合わせ一覧画面 Helper
// *
// * @author kimura
// */
//@Component
//public class MemberInquiryHelper extends AbstractInquiryHelper {
//
//    /**
//     * 問い合わせDao用検索条件Dtoの作成<br/>
//     *
//     * @param memberInquiryModel 問い合わせ一覧画面Model
//     * @return 問い合わせDao用検索条件Dto
//     */
//    public InquirySearchDaoConditionDto toInquirySearchDaoConditionDtoForLoad(MemberInquiryModel memberInquiryModel) {
//
//        InquirySearchDaoConditionDto conditionDto = ApplicationContextUtility.getBean(InquirySearchDaoConditionDto.class);
//
//        conditionDto.setMemberInfoSeq(memberInquiryModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
//
//        return conditionDto;
//    }
//
//    /**
//     * Model変換、初期表示<br/>
//     *
//     * @param resultList         問い合わせDao用検索結果Dto
//     * @param conditionDto       問い合わせDao用検索条件Dto
//     * @param memberInquiryModel 問い合わせ一覧画面Model
//     */
//    public void toPageForLoad(List<InquirySearchResultDto> resultList, InquirySearchDaoConditionDto conditionDto, MemberInquiryModel memberInquiryModel) {
//
//        List<MemberInquiryModelItem> itemlist = new ArrayList<>();
//
//        for (InquirySearchResultDto resultDto : resultList) {
//            MemberInquiryModelItem item = ApplicationContextUtility.getBean(MemberInquiryModelItem.class);
//            item.setInquiryCode(resultDto.getInquiryCode());
//            item.setFirstInquiryTime(resultDto.getFirstInquiryTime());
//            item.setInquiryStatus(resultDto.getInquiryStatus().getLabel());
//            item.setInquiryStatusValue(resultDto.getInquiryStatus().getValue());
//            item.setInquiryType(resultDto.getInquiryType());
//            item.setInquiryGroupName(resultDto.getInquiryGroupName());
//
//            itemlist.add(item);
//        }
//
//        memberInquiryModel.setMemberInquiryModelItems(itemlist);
//    }
//}
