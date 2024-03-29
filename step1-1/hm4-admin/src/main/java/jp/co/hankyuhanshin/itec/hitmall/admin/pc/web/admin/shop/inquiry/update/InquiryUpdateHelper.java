package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.inquiry.update;

import jp.co.hankyuhanshin.itec.hitmall.admin.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryRequestType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryDetailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * InquiryUpdateHelper Class
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class InquiryUpdateHelper {

    /**
     * 問い合わせ者表示用スペース
     */
    private static final String SPACE = "　";

    /**
     * 問合せ者表示用敬称
     */
    private static final String HONORIFIC = "様";

    /**
     * お客様スタイル
     */
    private static final String BGCOLOR_CONSUMER = "left user";

    /**
     * 運用者スタイル
     */
    private static final String BGCOLOR_OPERATOR = "left operator";

    /**
     * 変更スタイル
     */
    private static final String CHANGE_STYLE_LEFT = "left diff";

    /**
     * 変更スタイル
     */
    private static final String CHANGE_STYLE = "diff";

    /**
     * DateUtility
     */
    public DateUtility dateUtility;

    /**
     * CommonInfoUtility
     */
    public CommonInfoUtility commonInfoUtility;

    /**
     * @param dateUtility
     * @param commonInfoUtility
     */
    @Autowired
    public InquiryUpdateHelper(DateUtility dateUtility, CommonInfoUtility commonInfoUtility) {
        this.dateUtility = dateUtility;
        this.commonInfoUtility = commonInfoUtility;
    }

    /**
     * 問い合わせ内容リストを画面に反映
     *
     * @param inquiryDetailsDto  問い合わせ詳細Dto
     * @param inquiryUpdateModel 問い合わせ詳細画面
     */
    public void toPageForInquiryItem(InquiryDetailsDto inquiryDetailsDto, InquiryUpdateModel inquiryUpdateModel) {

        // 問い合わせ詳細DTO設定
        inquiryUpdateModel.setInquiryDetailsDto(inquiryDetailsDto);
        // 問い合わせ分類名の設定
        inquiryUpdateModel.setInquiryGroupName(inquiryDetailsDto.getInquiryGroupName());
        // 問い合わせ情報の設定
        InquiryEntity inquiryEntity = inquiryDetailsDto.getInquiryEntity();
        // 問い合わせSEQ
        inquiryUpdateModel.setInquirySeq(inquiryEntity.getInquirySeq());
        // ご連絡番号
        inquiryUpdateModel.setInquiryCode(inquiryEntity.getInquiryCode());
        // 問い合わせ種別
        inquiryUpdateModel.setInquiryType(inquiryEntity.getInquiryType().getValue());
        // 問い合わせ状態
        inquiryUpdateModel.setInquiryStatus(inquiryEntity.getInquiryStatus().getValue());
        // 問い合わせ者氏名（姓）
        inquiryUpdateModel.setInquiryLastName(inquiryEntity.getInquiryLastName());
        // 問い合わせ者氏名（名）
        inquiryUpdateModel.setInquiryFirstName(inquiryEntity.getInquiryFirstName());
        // 問い合わせ者氏名フリガナ（姓）
        inquiryUpdateModel.setInquiryLastKana(inquiryEntity.getInquiryLastKana());
        // 問い合わせ者フリガナ（名）
        inquiryUpdateModel.setInquiryFirstKana(inquiryEntity.getInquiryFirstKana());
        // 問い合わせ者電話番号
        inquiryUpdateModel.setInquiryTel(inquiryEntity.getInquiryTel());
        // 問い合わせ者メールアドレス
        inquiryUpdateModel.setInquiryMail(inquiryEntity.getInquiryMail());
        // 会員SEQ
        Integer memberInfoSeq = inquiryEntity.getMemberInfoSeq();
        inquiryUpdateModel.setMemberInfoSeq(memberInfoSeq == 0 ? null : memberInfoSeq.toString());
        // 会員ID
        if (inquiryDetailsDto.getMemberInfoEntity() != null) {
            inquiryUpdateModel.setMemberInfoId(inquiryDetailsDto.getMemberInfoEntity().getMemberInfoId());
        } else if (!inquiryUpdateModel.isResetMemberInfoId()) {
            inquiryUpdateModel.setMemberInfoId(null);
        }
        // 初回問い合わせ日時
        inquiryUpdateModel.setFirstInquiryTime(inquiryEntity.getFirstInquiryTime());
        // 連携メモ
        inquiryUpdateModel.setCooperationMemo(inquiryEntity.getCooperationMemo());
        // 管理メモ
        inquiryUpdateModel.setMemo(inquiryEntity.getMemo());

        // 問い合わせ内容情報リストの設定
        List<InquiryDetailItem> inquiryDetailItemList = new ArrayList<>();

        for (InquiryDetailEntity inquiryDetail : inquiryDetailsDto.getInquiryDetailEntityList()) {
            InquiryDetailItem inquiryDetailItem = ApplicationContextUtility.getBean(InquiryDetailItem.class);
            // 連番
            inquiryDetailItem.setInquiryVersionNo(inquiryDetail.getInquiryVersionNo());
            // 発信者種別
            inquiryDetailItem.setRequestType(inquiryDetail.getRequestType().getValue());
            // 問い合わせ日時
            inquiryDetailItem.setInquiryTime(inquiryDetail.getInquiryTime());
            // 問い合わせ内容
            inquiryDetailItem.setInquiryBody(inquiryDetail.getInquiryBody());
            // 部署名
            inquiryDetailItem.setDivisionName(inquiryDetail.getDivisionName());
            // 担当者
            inquiryDetailItem.setRepresentative(inquiryDetail.getRepresentative());
            // 処理担当者
            inquiryDetailItem.setOperator(inquiryDetail.getOperator());
            // 問い合わせ発信者種別
            if (inquiryDetail.getRequestType() == HTypeInquiryRequestType.CONSUMER) {
                // お客様の場合
                // 問い合わせ者氏名
                inquiryDetailItem.setInquiryMan(inquiryUpdateModel.getInquiryLastName() + SPACE
                                                + inquiryUpdateModel.getInquiryFirstName() + SPACE + HONORIFIC);
                // スタイルの設定
                inquiryDetailItem.setBgColorInquiryDetailManClass(BGCOLOR_CONSUMER);
                inquiryDetailItem.setBgColorInquiryDetailTimeClass(BGCOLOR_CONSUMER);
                inquiryDetailItem.setBgColorInquiryDetailBodyClass(BGCOLOR_CONSUMER);

            } else {
                // 運用者の場合
                // 部署名 + 担当者
                inquiryDetailItem.setInquiryMan(
                                inquiryDetail.getDivisionName() + SPACE + inquiryDetail.getRepresentative());
                // 連絡先TEL
                inquiryDetailItem.setContactTel(inquiryDetail.getContactTel());
                // スタイルの設定
                inquiryDetailItem.setBgColorInquiryDetailManClass(BGCOLOR_OPERATOR);
                inquiryDetailItem.setBgColorInquiryDetailTimeClass(BGCOLOR_OPERATOR);
                inquiryDetailItem.setBgColorInquiryDetailBodyClass(BGCOLOR_OPERATOR);
            }
            // 問い合わせ内容リストに追加
            inquiryDetailItemList.add(inquiryDetailItem);
        }

        // 再検索時入力項目を削除
        // 問い合わせ内容
        inquiryUpdateModel.setInquiryBody(null);
        // 問い合わせ内容(完了)
        inquiryUpdateModel.setInputCompletionReport(null);

        // 問い合わせ内容の設定
        inquiryUpdateModel.setInquiryDetailItems(inquiryDetailItemList);
    }

    /**
     * 会員IDの設定
     *
     * @param inquiryUpdateModel 問い合わせ詳細画面
     * @return inquiryEntity
     */
    public InquiryEntity toPageForInquiryEntityMember(InquiryUpdateModel inquiryUpdateModel) {

        inquiryUpdateModel.getInquiryDetailsDto()
                          .getInquiryEntity()
                          .setMemberInfoId(inquiryUpdateModel.getMemberInfoId());

        return inquiryUpdateModel.getInquiryDetailsDto().getInquiryEntity();
    }

    /**
     * 会員SEQの削除
     *
     * @param inquiryUpdateModel 問い合わせ詳細画面
     * @return inquiryEntity
     */
    public InquiryEntity toPageForInquiryEntityMemberRelease(InquiryUpdateModel inquiryUpdateModel) {

        // 会員SEQ削除
        inquiryUpdateModel.getInquiryDetailsDto().getInquiryEntity().setMemberInfoSeq(0);
        return inquiryUpdateModel.getInquiryDetailsDto().getInquiryEntity();
    }

    /**
     * 管理メモの設定
     *
     * @param inquiryUpdateModel 問い合わせ詳細画面
     * @return inquiryEntity
     */
    public InquiryEntity toPageForInquiryEntityMemo(InquiryUpdateModel inquiryUpdateModel) {

        InquiryEntity inquiryEntity = inquiryUpdateModel.getInquiryDetailsDto().getInquiryEntity();

        // 連携メモ
        inquiryEntity.setCooperationMemo(inquiryUpdateModel.getCooperationMemo());
        // 管理メモ
        inquiryEntity.setMemo(inquiryUpdateModel.getMemo());

        return inquiryEntity;
    }

    /**
     * 初期表示時処理
     *
     * @param inquiryUpdateModel 問い合わせ更新確認画面
     */
    public void toPageForLoad(InquiryUpdateModel inquiryUpdateModel) {
        // 管理者ユーザー（苗字 + 名前）
        String operator = commonInfoUtility.getAdministratorName(inquiryUpdateModel.getCommonInfo());

        // 確認画面用問い合わせ情報の取得
        InquiryEntity inquiryEntity = inquiryUpdateModel.getConfirmInquiryDetailsDto().getInquiryEntity();

        // 最終運用者問い合わせ日時の設定
        inquiryEntity.setLastOperatorInquiryTime(dateUtility.getCurrentTime());
        // 最終担当者の設定
        inquiryEntity.setLastRepresentative(operator);
        // 問い合わせ状態の設定
        inquiryUpdateModel.setInquiryStatus(inquiryUpdateModel.getInquiryStatusFlg());
        // 初回問い合わせ日時の設定
        inquiryUpdateModel.setFirstInquiryTime(inquiryEntity.getFirstInquiryTime());
        // 問い合わせ内容リストの取得
        List<InquiryDetailItem> registInquiryDetailItems = inquiryUpdateModel.getConfirmInquiryDetailItems();
        // 問い合わせ内容の最下行に新規問い合わせ情報を追加する。
        InquiryDetailItem newInquiryDetailItem = ApplicationContextUtility.getBean(InquiryDetailItem.class);
        // 連番
        newInquiryDetailItem.setInquiryVersionNo(registInquiryDetailItems.size() + 1);
        // 問い合わせ日時
        newInquiryDetailItem.setInquiryTime(dateUtility.getCurrentTime());
        // 担当者
        newInquiryDetailItem.setOperator(operator);
        // 問い合わせ状態が完了の場合
        if (inquiryUpdateModel.getInquiryStatus().equals(HTypeInquiryStatus.COMPLETION.getValue())) {
            // リストに完了報告を追加
            newInquiryDetailItem.setInquiryBody(inquiryUpdateModel.getInputCompletionReport());
            // 問い合わせ状態が連絡案内受付中の場合
        } else {
            // リストに 返信問い合わせ内容を追加
            newInquiryDetailItem.setInquiryBody(inquiryUpdateModel.getInputInquiryBody());
        }
        // 問い合わせ状態スタイル設定 変更あれば背景色を設定
        if (!inquiryUpdateModel.getInquiryStatus().equals(inquiryUpdateModel.getPreInquiryStatus())) {
            inquiryUpdateModel.setBgColorConfirmInquiryStatusClass(CHANGE_STYLE);
        }

        // 発信者種別
        newInquiryDetailItem.setRequestType(HTypeInquiryRequestType.OPERATOR.getValue());
        // 運用者の場合
        // スタイルの設定
        newInquiryDetailItem.setBgColorInquiryDetailManClass(CHANGE_STYLE_LEFT);
        newInquiryDetailItem.setBgColorInquiryDetailTimeClass(CHANGE_STYLE_LEFT);
        newInquiryDetailItem.setBgColorInquiryDetailBodyClass(CHANGE_STYLE_LEFT);
        // 新規行を追加
        registInquiryDetailItems.add(newInquiryDetailItem);

    }

    /**
     * 更新時処理(問い合わせエンティティ反映)
     *
     * @param inquiryUpdateModel 問い合わせ更新確認画面
     * @return 問い合わせエンティティ
     */
    public InquiryEntity toInquiryEntityForInquiryUpdate(InquiryUpdateModel inquiryUpdateModel) {

        InquiryEntity inquiryEntity = inquiryUpdateModel.getConfirmInquiryDetailsDto().getInquiryEntity();

        // 画面入力値を反映：問い合わせ状態
        inquiryEntity.setInquiryStatus(
                        EnumTypeUtil.getEnumFromValue(HTypeInquiryStatus.class, inquiryUpdateModel.getInquiryStatus()));
        return inquiryEntity;

    }

    /**
     * 登録時処理(問い合わせ内容エンティティ反映)
     *
     * @param inquiryUpdateModel 問い合わせ更新確認画面
     * @return InquiryDetailsDto 問い合わせ内容Dto
     */
    public InquiryDetailsDto toInquiryDetailForInquiryDetailRegist(InquiryUpdateModel inquiryUpdateModel) {

        InquiryDetailsDto inquiryDetailsDto = CopyUtil.deepCopy(inquiryUpdateModel.getConfirmInquiryDetailsDto());

        // 新規登録する問い合わせ内容エンティティの作成
        InquiryDetailEntity inquiryDetail = ApplicationContextUtility.getBean(InquiryDetailEntity.class);

        // 最下行の問い合わせ内容情報を取得
        InquiryDetailItem inquiryDetailItem = inquiryUpdateModel.getConfirmInquiryDetailItems()
                                                                .get(inquiryUpdateModel.getConfirmInquiryDetailItems()
                                                                                       .size() - 1);
        // 発信者種別:運用者
        inquiryDetail.setRequestType(HTypeInquiryRequestType.OPERATOR);
        // 問い合わせSEQ
        inquiryDetail.setInquirySeq(inquiryUpdateModel.getInquirySeq());
        // 連番
        inquiryDetail.setInquiryVersionNo(Integer.valueOf(inquiryDetailItem.getInquiryVersionNo()));
        // 問い合わせ日時
        inquiryDetail.setInquiryTime(inquiryDetailItem.getInquiryTime());
        // 問い合わせ内容
        inquiryDetail.setInquiryBody(inquiryDetailItem.getInquiryBody());
        // 部署名
        inquiryDetail.setDivisionName(inquiryDetailItem.getDivisionName());
        // 担当者
        inquiryDetail.setRepresentative(inquiryDetailItem.getRepresentative());
        // 連絡先TEL
        inquiryDetail.setContactTel(inquiryDetailItem.getContactTel());
        // 処理担当者
        inquiryDetail.setOperator(inquiryDetailItem.getOperator());
        // 問い合わせ内容の追加
        List<InquiryDetailEntity> list = inquiryDetailsDto.getInquiryDetailEntityList();
        list.add(inquiryDetail);
        inquiryDetailsDto.setInquiryDetailEntityList(list);
        return inquiryDetailsDto;
    }

    /**
     * 登録後、内容初期化<br/>
     *
     * @param inquiryUpdateModel ページクラス
     */
    public void initData(InquiryUpdateModel inquiryUpdateModel) {

        inquiryUpdateModel.setFromConfirm(false);

        // 問い合わせ報告、完了報告初期化
        inquiryUpdateModel.setInputInquiryBody(null);
        inquiryUpdateModel.setInputCompletionReport(null);

        // メール送信フラグ 初期化
        inquiryUpdateModel.setSendMail(true);
        inquiryUpdateModel.setCompletionReportFlag(true);
    }

}
