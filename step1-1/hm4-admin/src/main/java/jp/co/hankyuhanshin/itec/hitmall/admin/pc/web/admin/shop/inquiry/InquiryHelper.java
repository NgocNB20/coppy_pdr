package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquirySearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * InquiryHelper Class
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class InquiryHelper {

    /**
     * 検索条件を画面に反映
     * 再検索用
     *
     * @param conditionDto 検索条件Dto
     * @param inquiryModel 検索ページ
     */
    public void toPageForLoad(InquirySearchDaoConditionDto conditionDto, InquiryModel inquiryModel) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        /* 各検索条件を画面に反映する */

        // 検索条件：問い合わせ分類SEQ
        inquiryModel.setSearchInquiryGroupSeq(conditionDto.getInquiryGroupSeq());

        // 検索条件：問い合わせ状態
        inquiryModel.setSearchInquiryStatus(EnumTypeUtil.getValue(conditionDto.getInquiryStatus()));

        // 検索条件：問い合わせコード
        inquiryModel.setSearchInquiryCode(conditionDto.getInquiryCode());

        // 検索条件：問い合わせ日時(FROM)
        inquiryModel.setSearchInquiryTimeFrom(conversionUtility.toYmd(conditionDto.getInquiryTimeFrom()));

        // 検索条件：問い合わせ日時(TO)
        inquiryModel.setSearchInquiryTimeTo(conversionUtility.toYmd(conditionDto.getInquiryTimeTo()));

        // 検索条件：問い合わせ者(氏名)
        inquiryModel.setSearchInquiryName(conditionDto.getInquiryName());

        // 検索条件：問い合わせ者(E-Mail)
        inquiryModel.setSearchInquiryMail(conditionDto.getInquiryMail());

        // 検索条件：問い合わせ種別
        inquiryModel.setSearchInquiryType(conditionDto.getInquiryType());

        // 検索条件：注文番号
        inquiryModel.setSearchOrderCode(conditionDto.getOrderCode());

        // 検索条件：電話番号
        inquiryModel.setSearchInquiryTel(conditionDto.getInquiryTel());

        // 検索条件：担当者（最終担当者）
        inquiryModel.setSearchLastRepresentative(conditionDto.getLastRepresentative());

        // 検索条件：連携メモ
        inquiryModel.setSearchCooperationMemo(conditionDto.getCooperationMemo());

        // 検索条件：会員ID(メールアドレス)
        inquiryModel.setSearchMemberInfoMail(conditionDto.getMemberInfoMail());

    }

    /**
     * 画面入力値から検索条件Dtoを作成する
     *
     * @param inquiryModel 検索画面のページ情報
     * @return 問い合わせ検索Dto
     */
    public InquirySearchDaoConditionDto toInquirySearchDaoConditionDtoDtoForSearch(InquiryModel inquiryModel) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        InquirySearchDaoConditionDto inquirySearchDaoConditionDto =
                        ApplicationContextUtility.getBean(InquirySearchDaoConditionDto.class);

        // 問い合わせ分類
        inquirySearchDaoConditionDto.setInquiryGroupSeq(inquiryModel.getSearchInquiryGroupSeq());
        // 問い合わせ状態
        inquirySearchDaoConditionDto.setInquiryStatus(
                        EnumTypeUtil.getEnumFromValue(HTypeInquiryStatus.class, inquiryModel.getSearchInquiryStatus()));
        // 問い合わせコード
        inquirySearchDaoConditionDto.setInquiryCode(inquiryModel.getSearchInquiryCode());
        // 問い合わせ日時(FROM)
        inquirySearchDaoConditionDto.setInquiryTimeFrom(
                        conversionUtility.toTimeStamp(inquiryModel.getSearchInquiryTimeFrom()));
        // 問い合わせ日時(To)
        if (inquiryModel.getSearchInquiryTimeTo() != null) {
            // 日付関連Helper取得
            DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

            inquirySearchDaoConditionDto.setInquiryTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(inquiryModel.getSearchInquiryTimeTo())));
        }
        // メールアドレス
        inquirySearchDaoConditionDto.setInquiryMail(inquiryModel.getSearchInquiryMail());
        // 氏名
        String inquiryName = null;
        if (inquiryModel.getSearchInquiryName() != null) {
            inquiryName = inquiryModel.getSearchInquiryName().replace(" ", "").replace("　", "");
        }
        inquirySearchDaoConditionDto.setInquiryName(inquiryName);

        // 問い合わせ種別
        inquirySearchDaoConditionDto.setInquiryType(inquiryModel.getSearchInquiryType());
        // 注文番号
        inquirySearchDaoConditionDto.setOrderCode(inquiryModel.getSearchOrderCode());
        // 電話番号
        inquirySearchDaoConditionDto.setInquiryTel(inquiryModel.getSearchInquiryTel());
        // 担当者（最終担当者）
        inquirySearchDaoConditionDto.setLastRepresentative(inquiryModel.getSearchLastRepresentative());
        // 連携メモ
        inquirySearchDaoConditionDto.setCooperationMemo(inquiryModel.getSearchCooperationMemo());
        // 会員ID(メールアドレス)
        inquirySearchDaoConditionDto.setMemberInfoMail(inquiryModel.getSearchMemberInfoMail());

        return inquirySearchDaoConditionDto;
    }

    /**
     * 検索結果をページに反映<br/>
     *
     * @param newsEntityList 問い合わせ検索結果Dto
     * @param inquiryModel   問い合わせ検索ページ
     * @param conditionDto   検索条件Dto
     */
    public void toPageForSearch(List<InquirySearchResultDto> newsEntityList,
                                InquiryModel inquiryModel,
                                InquirySearchDaoConditionDto conditionDto) {

        // オフセット + 1をNoにセット
        int index = conditionDto.getOffset() + 1;

        List<InquiryModelItem> resultItemList = new ArrayList<>();

        for (InquirySearchResultDto inquirySearchResultDto : newsEntityList) {
            InquiryModelItem inquiryModelItem = ApplicationContextUtility.getBean(InquiryModelItem.class);

            inquiryModelItem.setResultNo(index++);
            inquiryModelItem.setInquirySeq(inquirySearchResultDto.getInquirySeq());

            if (inquirySearchResultDto.getInquiryStatus() != null) {
                inquiryModelItem.setInquiryStatus(inquirySearchResultDto.getInquiryStatus().getValue());
            }
            inquiryModelItem.setInquiryCode(inquirySearchResultDto.getInquiryCode());
            inquiryModelItem.setInquiryGroupName(inquirySearchResultDto.getInquiryGroupName());
            inquiryModelItem.setInquiryLastName(inquirySearchResultDto.getInquiryLastName());
            inquiryModelItem.setInquiryFirstName(inquirySearchResultDto.getInquiryFirstName());
            inquiryModelItem.setInquiryTime(inquirySearchResultDto.getInquiryTime());
            inquiryModelItem.setAnswerTime(inquirySearchResultDto.getAnswerTime());

            // 検索条件：お問い合わせ種別
            inquiryModelItem.setResultInquiryType(inquirySearchResultDto.getInquiryType());

            // 検索条件：初回お問い合わせ日時
            inquiryModelItem.setResultFirstInquiryTime(inquirySearchResultDto.getFirstInquiryTime());

            // 検索条件：最終お客様お問い合わせ日時
            inquiryModelItem.setResultLastUserInquiryTime(inquirySearchResultDto.getLastUserInquiryTime());

            // 検索条件：注文番号
            inquiryModelItem.setOrderCode(inquirySearchResultDto.getOrderCode());

            // 検索条件：担当者（最終担当者）
            inquiryModelItem.setResultLastRepresentative(inquirySearchResultDto.getLastRepresentative());

            // 検索条件：問い合わせ者氏名
            inquiryModelItem.setResultInquiryName(inquirySearchResultDto.getInquiryLastName());

            // 検索条件：連携メモ
            inquiryModelItem.setResultCooperationMemo(inquirySearchResultDto.getCooperationMemo());

            resultItemList.add(inquiryModelItem);
        }
        inquiryModel.setResultItems(resultItemList);
    }

}
