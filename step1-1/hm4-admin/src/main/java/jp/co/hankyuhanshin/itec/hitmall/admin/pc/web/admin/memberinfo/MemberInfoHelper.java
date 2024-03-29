package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMainMemberType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalTreatmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadExtension;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MedicalTreatmentDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.image.MemberImageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.NonConsultationDayUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 会員検索HELPER
 * <p>
 * PDR#11 08_データ連携（顧客情報）会員情報の検索結果項目追加<br/>
 * 会員検索結果情報取得<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */

@Component
public class MemberInfoHelper {

    /**
     * 変換Utility取得
     */
    private final ConversionUtility conversionUtility;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * 会員業務ヘルパークラス
     */
    private final MemberInfoUtility memberInfoUtility;

    /**
     * 診療項目Dto
     */
    private final MedicalTreatmentDto medicalTreatmentDto;

    // 2023-renew No85-2 from here
    /**
     * 改行コード CRLF
     */
    private final static String CRLF = "\r\n";
    // 2023-renew No85-2 to here

    // 2023-renew No22 from here
    /**
     * 日付時刻の形式を設定する
     */
    private static final String FORMAT_DATE_TIME = "yyyyMMddHHmmssSSS";
    // 2023-renew No22 to here

    /**
     * コンストラクター
     *
     * @param conversionUtility   変換ユーティリティクラス
     * @param dateUtility         日付関連Utilityクラス
     * @param memberInfoUtility   会員業務ヘルパークラス
     * @param medicalTreatmentDto 診療項目Dto
     */
    @Autowired
    public MemberInfoHelper(ConversionUtility conversionUtility,
                            DateUtility dateUtility,
                            MemberInfoUtility memberInfoUtility,
                            MedicalTreatmentDto medicalTreatmentDto) {
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
        this.memberInfoUtility = memberInfoUtility;
        this.medicalTreatmentDto = medicalTreatmentDto;
    }

    /**
     * ページに反映
     *
     * @param detailsDto             会員詳細Dto
     * @param memberInfoDetailsModel ページ
     */
    public void toModelDetailsForLoad(MemberInfoDetailsDto detailsDto, MemberInfoDetailsModel memberInfoDetailsModel) {
        // 会員状態情報
        setupMemberStatusInfo(detailsDto, memberInfoDetailsModel);
        // お客様情報
        setupMemberInfo(detailsDto, memberInfoDetailsModel);
        // 最終ログイン情報
        setupLastLoginInfo(detailsDto, memberInfoDetailsModel);

        // 会員パスワード情報
        setupPasswordInfo(detailsDto, memberInfoDetailsModel);

        // メール関連
        setupMailInfo(detailsDto, memberInfoDetailsModel);

        // 受注履歴はこのメソッド内に含めないようにする
        // ※コントローラから別途呼び出す想定

        // 会員エンティティ
        memberInfoDetailsModel.setMemberInfoEntity(detailsDto.getMemberInfoEntity());
    }

    /**
     * 会員パスワード情報をページに反映する
     *
     * @param detailsDto            会員詳細Dtoクラス
     * @param memberInfoUpdateModel 会員詳細モデル
     */
    protected void setupPasswordInfo(MemberInfoDetailsDto detailsDto, MemberInfoDetailsModel memberInfoUpdateModel) {
        MemberInfoEntity entity = detailsDto.getMemberInfoEntity();

        memberInfoUpdateModel.setPasswordNeedChangeFlag(entity.getPasswordNeedChangeFlag().getValue());
    }

    /**
     * 会員状態情報をページに反映する
     *
     * @param detailsDto             会員詳細Dto
     * @param memberInfoDetailsModel ページ
     */
    protected void setupMemberStatusInfo(MemberInfoDetailsDto detailsDto,
                                         MemberInfoDetailsModel memberInfoDetailsModel) {

        MemberInfoEntity entity = detailsDto.getMemberInfoEntity();
        // 状態
        memberInfoDetailsModel.setMemberInfoStatus(entity.getMemberInfoStatus());
        // 入会日
        memberInfoDetailsModel.setAdmissionYmd(
                        dateUtility.getYmdFormatValue(entity.getAdmissionYmd(), DateUtility.YMD_SLASH));
        // 退会日
        memberInfoDetailsModel.setSecessionYmd(
                        dateUtility.getYmdFormatValue(entity.getSecessionYmd(), DateUtility.YMD_SLASH));
        // 登録日時
        memberInfoDetailsModel.setRegistTime(entity.getRegistTime());
        // 更新日時
        memberInfoDetailsModel.setUpdateTime(entity.getUpdateTime());

        memberInfoDetailsModel.setAccountLock(memberInfoUtility.isAccountStatusLock(entity.getAccountLockTime()));
        // アカウントロック日時
        memberInfoDetailsModel.setAccountLockTime(entity.getAccountLockTime());
        // ログイン失敗回数
        memberInfoDetailsModel.setLoginFailureCount(entity.getLoginFailureCount());

        // PDR Migrate Customization from here

        // 管理用メモ
        memberInfoDetailsModel.setMemo(entity.getMemo());
        // 承認状態
        memberInfoDetailsModel.setApproveStatus(entity.getApproveStatus());

        // オンライン登録状態
        memberInfoDetailsModel.setOnlineRegistFlag(entity.getOnlineRegistFlag());

        // 反社会的勢力ではないことの保証
        memberInfoDetailsModel.setNoAntiSocialFlag(HTypeNoAntiSocialFlag.ON.equals(entity.getNoAntiSocialFlag()));
        // PDR Migrate Customization to here
    }

    /**
     * 検索条件Dtoの作成
     *
     * @param memberInfoModel 会員検索ページ
     * @return 会員検索条件Dto
     */
    public MemberInfoSearchForDaoConditionDto toConditionDtoForSearch(MemberInfoModel memberInfoModel) {
        // 検索条件Dto取得
        MemberInfoSearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(MemberInfoSearchForDaoConditionDto.class);

        /* 画面条件 */
        // 会員ID
        conditionDto.setMemberInfoId(memberInfoModel.getMemberInfoId());
        // 会員SEQ
        conditionDto.setMemberInfoSeq(conversionUtility.toInteger(memberInfoModel.getSearchMemberInfoSeq()));
        // 氏名
        String memberInfoName = null;
        if (memberInfoModel.getMemberInfoName() != null) {
            memberInfoName = memberInfoModel.getMemberInfoName().replace(" ", "").replace("　", "");
        }
        conditionDto.setMemberInfoName(memberInfoName);

        // PDR Migrate Customization from here
        // 顧客番号
        if (StringUtils.isNotEmpty(memberInfoModel.getCustomerNo())) {
            conditionDto.setCustomerNo(conversionUtility.toInteger(memberInfoModel.getCustomerNo()));
        }

        // 承認状態
        conditionDto.setApproveStatus(
                        EnumTypeUtil.getEnumFromValue(HTypeApproveStatus.class, memberInfoModel.getApproveStatus()));

        // オンライン登録状態
        conditionDto.setOnlineRegistFlag(EnumTypeUtil.getEnumFromValue(HTypeOnlineRegistFlag.class,
                                                                       memberInfoModel.getOnlineRegistFlag()
                                                                      ));

        // 顧客区分
        if (memberInfoModel.getBusinessType() != null && memberInfoModel.getBusinessType().length != 0) {
            conditionDto.setBusinessType(Arrays.asList(memberInfoModel.getBusinessType()));
        }

        // FAXによるおトク情報希望フラグ
        conditionDto.setSendFaxPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendFaxPermitFlag.class,
                                                                        memberInfoModel.getSendFaxPermitFlag()
                                                                       ));

        // DMによるおトク情報希望フラグ
        conditionDto.setSendDirectMailFlag(EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class,
                                                                         memberInfoModel.getSendDirectMailFlag()
                                                                        ));
        // PDR Migrate Customization to here

        // 性別
        conditionDto.setMemberInfoSex(
                        EnumTypeUtil.getEnumFromValue(HTypeSex.class, memberInfoModel.getMemberInfoSex()));
        // 生年月日
        conditionDto.setMemberInfoBirthday(conversionUtility.toTimeStamp(memberInfoModel.getMemberInfoBirthday()));
        // 電話番号
        conditionDto.setMemberInfoTel(memberInfoModel.getMemberInfoTel());
        // 状態
        conditionDto.setMemberInfoStatus(EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class,
                                                                       memberInfoModel.getMemberInfoStatus()
                                                                      ));
        // 郵便番号
        conditionDto.setMemberInfoZipCode(memberInfoModel.getMemberInfoZipCode());
        // 都道府県
        conditionDto.setMemberInfoPrefecture(memberInfoModel.getMemberInfoPrefecture());
        // 住所
        String memberInfoAddress = null;
        if (memberInfoModel.getMemberInfoAddress() != null) {
            memberInfoAddress = memberInfoModel.getMemberInfoAddress().replace(" ", "").replace("　", "");
        }
        conditionDto.setMemberInfoAddress(memberInfoAddress);
        // 期間種別
        conditionDto.setPeriodType(memberInfoModel.getPeriodType());
        // 期間（FROM）
        conditionDto.setStartDate(memberInfoModel.getStartDate());
        // 期間（TO）
        conditionDto.setEndDate(memberInfoModel.getEndDate());
        // 最終ログインユーザーエージェント
        conditionDto.setLastLoginUserAgent(memberInfoModel.getLastLoginUserAgent());
        // 最終ログインデバイス
        if (ObjectUtils.isNotEmpty(memberInfoModel.getLastLoginDeviceType())) {
            conditionDto.setLastLoginDeviceType(Arrays.asList(memberInfoModel.getLastLoginDeviceType()));
        }

        // 本会員フラグ
        if (memberInfoModel.isMainMemberFlag()) {
            conditionDto.setMainMemberFlag(HTypeMainMemberType.MAIN_MENBER);
        }

        // メール配信希望フラグ
        if (StringUtil.isNotEmpty(memberInfoModel.getSendMailPermitFlag())) {
            conditionDto.setMemberInfoSendMailPermitFlag(EnumTypeUtil.getEnumFromValue(HTypeSendMailPermitFlag.class,
                                                                                       memberInfoModel.getSendMailPermitFlag()
                                                                                      ));
        }

        return conditionDto;
    }

    /**
     * お客様情報をページに反映する
     *
     * @param detailsDto             会員詳細Dto
     * @param memberInfoDetailsModel ページ
     */
    protected void setupMemberInfo(MemberInfoDetailsDto detailsDto, MemberInfoDetailsModel memberInfoDetailsModel) {

        // 休診曜日判定Utility
        NonConsultationDayUtility nonConsultationDayUtility =
                        ApplicationContextUtility.getBean(NonConsultationDayUtility.class);

        MemberInfoEntity entity = detailsDto.getMemberInfoEntity();
        // 会員ID
        memberInfoDetailsModel.setMemberInfoId(entity.getMemberInfoId());

        // 氏名
        memberInfoDetailsModel.setMemberInfoName(
                        buildMemberInfoNameOrKana(entity.getMemberInfoLastName(), entity.getMemberInfoFirstName()));
        // フリガナ
        memberInfoDetailsModel.setMemberInfoKana(
                        buildMemberInfoNameOrKana(entity.getMemberInfoLastKana(), entity.getMemberInfoFirstKana()));
        // 性別
        memberInfoDetailsModel.setMemberInfoSex(entity.getMemberInfoSex());
        // 誕生日
        memberInfoDetailsModel.setMemberInfoBirthday(entity.getMemberInfoBirthday());
        // 電話番号
        memberInfoDetailsModel.setMemberInfoTel(entity.getMemberInfoTel());
        // 連絡先電話番号
        memberInfoDetailsModel.setMemberInfoContactTel(entity.getMemberInfoContactTel());
        // FAX番号
        memberInfoDetailsModel.setMemberInfoFax(entity.getMemberInfoFax());
        // 郵便番号
        memberInfoDetailsModel.setMemberInfoZipCode(entity.getMemberInfoZipCode());

        // PDR Migrate Customization from here
        // 住所
        String address = entity.getMemberInfoAddress1() + entity.getMemberInfoAddress2();
        if (entity.getMemberInfoAddress3() != null) {
            address = address + entity.getMemberInfoAddress3();
        }
        if (entity.getMemberInfoAddress4() != null) {
            address = address + entity.getMemberInfoAddress4();
        }
        if (entity.getMemberInfoAddress5() != null) {
            address = address + entity.getMemberInfoAddress5();
        }
        memberInfoDetailsModel.setMemberInfoAddress(address);
        // 2023-renew general-mail from here
        // メールアドレス
        memberInfoDetailsModel.setMemberInfoMail(entity.getMemberInfoMail());
        // 2023-renew general-mail to here
        // 顧客番号
        memberInfoDetailsModel.setCustomerNo(entity.getCustomerNo());
        // 代表者名
        memberInfoDetailsModel.setRepresentativeName(entity.getRepresentativeName());
        // 顧客区分
        memberInfoDetailsModel.setBusinessType(entity.getBusinessType());
        // 確認書類
        memberInfoDetailsModel.setConfDocumentType(entity.getConfDocumentType());
        // 休診曜日
        memberInfoDetailsModel.setNonConsultationDay(
                        nonConsultationDayUtility.getNonConsultationDay(entity.getNonConsultationDay()));
        // 医薬品・注射針販売区分
        memberInfoDetailsModel.setDrugSalesType(entity.getDrugSalesType());
        // 医療機器販売区分
        memberInfoDetailsModel.setMedicalEquipmentSalesType(entity.getMedicalEquipmentSalesType());
        // 歯科専用品販売区分
        memberInfoDetailsModel.setDentalMonopolySalesType(entity.getDentalMonopolySalesType());
        // クレジット決済使用可否
        memberInfoDetailsModel.setCreditPaymentUseFlag(entity.getCreditPaymentUseFlag());
        // コンビニ・郵便振込使用可否
        memberInfoDetailsModel.setTransferPaymentUseFlag(entity.getTransferPaymentUseFlag());
        // 代金引換使用可否
        memberInfoDetailsModel.setCashDeliveryUseFlag(entity.getCashDeliveryUseFlag());
        // 口座自動引落使用可否
        memberInfoDetailsModel.setDirectDebitUseFlag(entity.getDirectDebitUseFlag());
        // 月締請求使用可否
        memberInfoDetailsModel.setMonthlyPayUseFlag(entity.getMonthlyPayUseFlag());
        // 名簿区分
        memberInfoDetailsModel.setMemberListType(entity.getMemberListType());
        // 診療項目
        setMedicalTreatment(entity, memberInfoDetailsModel);
        // 診療項目メモ
        memberInfoDetailsModel.setMedicalTreatmentMemo(entity.getMedicalTreatmentMemo());
        // 経理区分
        memberInfoDetailsModel.setAccountingType(entity.getAccountingType());
        // オンラインログイン可否
        memberInfoDetailsModel.setOnlineLoginAdvisability(entity.getOnlineLoginAdvisability());
        // 反社会的勢力ではないことの保証
        memberInfoDetailsModel.setNoAntiSocialFlag(HTypeNoAntiSocialFlag.ON.equals(entity.getNoAntiSocialFlag()));
        // PDR Migrate Customization to here
    }

    /**
     * メール関連の情報をページに反映する<br/>
     *
     * @param detailsDto             会員詳細Dto
     * @param memberInfoDetailsModel ページ
     */
    protected void setupMailInfo(MemberInfoDetailsDto detailsDto, MemberInfoDetailsModel memberInfoDetailsModel) {
        // メールによるおトク情報
        memberInfoDetailsModel.setSendMailPermitFlag(detailsDto.getMemberInfoEntity().getSendMailPermitFlag());
        // FAXによるおトク情報
        memberInfoDetailsModel.setSendFaxPermitFlag(detailsDto.getMemberInfoEntity().getSendFaxPermitFlag());
        // DMによるおトク情報
        memberInfoDetailsModel.setSendDirectMailFlag(detailsDto.getMemberInfoEntity().getSendDirectMailFlag());
        // 金属商品価格お知らせメール
        memberInfoDetailsModel.setMetalPermitFlag(detailsDto.getMemberInfoEntity().getMetalPermitFlag());
        // 2023-renew No79 from here
        //注文完了メール
        memberInfoDetailsModel.setOrderCompletePermitFlag(
                        detailsDto.getMemberInfoEntity().getOrderCompletePermitFlag());
        //発送完了メール
        memberInfoDetailsModel.setDeliveryCompletePermitFlag(
                        detailsDto.getMemberInfoEntity().getDeliveryCompletePermitFlag());
        // 2023-renew No79 to here
    }

    /**
     * 空チェック付きの文字列を構築する
     *
     * @param firstName 名前
     * @param lastName  姓
     * @return 姓 + 名前
     */
    public static String buildMemberInfoNameOrKana(String lastName, String firstName) {

        if (StringUtils.isNotEmpty(lastName) && StringUtils.isNotEmpty(firstName)) {
            return lastName + "　" + firstName;
        } else if (StringUtils.isNotEmpty(lastName)) {
            return lastName;
        } else if (StringUtils.isNotEmpty(firstName)) {
            return firstName;
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 最終ログイン情報をページに反映する
     *
     * @param detailsDto             会員詳細Dto
     * @param memberInfoDetailsModel ページ
     */
    protected void setupLastLoginInfo(MemberInfoDetailsDto detailsDto, MemberInfoDetailsModel memberInfoDetailsModel) {
        MemberInfoEntity entity = detailsDto.getMemberInfoEntity(); // .memberInfoEntity;
        // 日時
        if (entity.getLastLoginTime() != null) {
            memberInfoDetailsModel.setLastLoginTime(
                            conversionUtility.toYmd(entity.getLastLoginTime()) + " " + conversionUtility.toHms(
                                            entity.getLastLoginTime()));
        }
        // ユーザーエージェント
        memberInfoDetailsModel.setLastLoginUserAgent(entity.getLastLoginUserAgent());// .lastLoginUserAgent;
        memberInfoDetailsModel.setLastLoginDeviceType(entity.getLastLoginDeviceType());
    }

    /**
     * 受注履歴をページに反映する
     *
     * @param orderHistoryDtoList    受注履歴Dtoリスト
     * @param memberInfoDetailsModel ページ
     * @param orderConditionDto      受注履歴検索Dto
     */
    public void toModelForOrderHistory(List<OrderSearchOrderResultDto> orderHistoryDtoList,
                                       MemberInfoDetailsModel memberInfoDetailsModel,
                                       OrderSearchConditionDto orderConditionDto) {
        // オフセット + 1をNoにセット
        int index = orderConditionDto.getOffset() + 1;
        // 注文履歴
        memberInfoDetailsModel.setOrderHistoryItems(new ArrayList<>());
        // 注文履歴数分ループ
        for (OrderSearchOrderResultDto orderHistoryListDto : orderHistoryDtoList) {
            // 注文履歴情報を作成
            MemberInfoDetailsOrderItem detailsPageOrderItem = createOrderItem(orderHistoryListDto);
            // No
            detailsPageOrderItem.setResultNo(index++);
            // 注文履歴リストに設定
            memberInfoDetailsModel.getOrderHistoryItems().add(detailsPageOrderItem);
        }
    }

    /**
     * 注文履歴Itemを作成する
     *
     * @param orderHistoryListDto 注文履歴Dto
     * @return DetailsPageOrderItem
     */
    protected MemberInfoDetailsOrderItem createOrderItem(OrderSearchOrderResultDto orderHistoryListDto) {
        // 会員詳細画面注文履歴情報を作成
        MemberInfoDetailsOrderItem detailsPageOrderItem =
                        ApplicationContextUtility.getBean(MemberInfoDetailsOrderItem.class);
        // 受注履歴連番
        detailsPageOrderItem.setOrderVersionNo(orderHistoryListDto.getOrderVersionNo());
        // 受注コード
        detailsPageOrderItem.setOrderCode(orderHistoryListDto.getOrderCode()); // .orderCode;
        // 受注日
        detailsPageOrderItem.setOrderTime(orderHistoryListDto.getOrderTime());// .orderTime;
        // 受注金額
        detailsPageOrderItem.setOrderPrice(orderHistoryListDto.getOrderPrice());// .orderPrice;
        // 決済方法名
        detailsPageOrderItem.setSettlementMethodName(
                        orderHistoryListDto.getSettlementMethodName());// .settlementMethodName;
        // 受注状態
        if (HTypeCancelFlag.ON.equals(orderHistoryListDto.getCancelFlag())) {
            // キャンセル
            detailsPageOrderItem.setOrderStatus(orderHistoryListDto.getCancelFlag().getLabel());
        } else if (HTypeWaitingFlag.ON.equals(orderHistoryListDto.getWaitingFlag())) {
            // 保留
            detailsPageOrderItem.setOrderStatus(orderHistoryListDto.getWaitingFlag().getLabel());
        } else if (HTypeEmergencyFlag.ON.equals(orderHistoryListDto.getEmergencyFlag())) {
            // 請求エラー
            detailsPageOrderItem.setOrderStatus(orderHistoryListDto.getEmergencyFlag().getLabel());
        } else {
            // 通常の受注ステータス
            detailsPageOrderItem.setOrderStatus(orderHistoryListDto.getOrderStatus().getLabel());
        }
        // 受注サイト
        detailsPageOrderItem.setOrderSiteType(orderHistoryListDto.getOrderSiteType());

        return detailsPageOrderItem;
    }

    /**
     * 検索結果をページに反映
     *
     * @param memberInfoEntityList 会員エンティティリスト
     * @param memberInforModel     会員検索ページ
     * @param conditionDto         検索Dto
     */
    public void toPageForResultList(List<MemberInfoEntity> memberInfoEntityList,
                                    MemberInfoModel memberInforModel,
                                    MemberInfoSearchForDaoConditionDto conditionDto) {

        // オフセット + 1をNoにセット
        int index = conditionDto.getOffset() + 1;
        memberInforModel.setResultItems(new ArrayList<>());
        for (MemberInfoEntity memberInfoEntity : memberInfoEntityList) {
            MemberInfoResultItem resultItem = createResultItem(memberInfoEntity);
            resultItem.setResultNo(index++);
            memberInforModel.getResultItems().add(resultItem);
        }
    }

    /**
     * 検索結果生成<br/>
     *
     * @param memberInfoEntity 会員情報
     * @return 検索結果
     */
    protected MemberInfoResultItem createResultItem(MemberInfoEntity memberInfoEntity) {
        MemberInfoResultItem resultItem = ApplicationContextUtility.getBean(MemberInfoResultItem.class);
        // 会員SEQ
        resultItem.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        // PDR Migrate Customization from here
        resultItem.setResultCustomerNo(memberInfoEntity.getCustomerNo());
        // PDR Migrate Customization to here
        // 郵便番号
        resultItem.setResultMemberInfoZipCode(memberInfoEntity.getMemberInfoZipCode());
        // 都道府県
        resultItem.setResultMemberInfoPrefecture(memberInfoEntity.getMemberInfoPrefecture());
        // 住所1
        resultItem.setResultMemberInfoAddress1(memberInfoEntity.getMemberInfoAddress1());
        // 住所2
        resultItem.setResultMemberInfoAddress2(memberInfoEntity.getMemberInfoAddress2());
        // 住所3
        resultItem.setResultMemberInfoAddress3(memberInfoEntity.getMemberInfoAddress3());
        // 会員ID
        resultItem.setResultMemberInfoId(memberInfoEntity.getMemberInfoId());
        // 氏名：姓
        resultItem.setResultMemberInfoLastName(memberInfoEntity.getMemberInfoLastName());
        // 氏名：名
        resultItem.setResultMemberInfoFirstName(memberInfoEntity.getMemberInfoFirstName());
        // 会員状態
        resultItem.setResultMemberInfoStatus(memberInfoEntity.getMemberInfoStatus().getValue());
        // 電話番号
        resultItem.setResultMemberInfoTel(memberInfoEntity.getMemberInfoTel());

        // PDR Migrate Customization from here
        // 検索結果:顧客区分
        if (memberInfoEntity.getBusinessType() != null) {
            resultItem.setResultBusinessType(memberInfoEntity.getBusinessType().getValue());
        }
        resultItem.setResultApproveStatus(memberInfoEntity.getApproveStatus().getValue());
        // PDR Migrate Customization to here
        return resultItem;
    }

    public List<Integer> toMemberInfoSeqList(MemberInfoModel memberInfoModel) {
        List<Integer> memberInfoSeqList = new ArrayList<>();
        for (MemberInfoResultItem resultItem : memberInfoModel.getResultItems()) {
            if (resultItem.isResultMemberInfoCheck()) {
                memberInfoSeqList.add(resultItem.getMemberInfoSeq());
            }
        }

        return memberInfoSeqList;
    }

    // PDR Migrate Customization from here

    /**
     * 診療項目設定<br/>
     *
     * @param memberInfoEntity       会員Entity
     * @param memberInfoDetailsModel memberInfoDetailsModel
     */
    protected void setMedicalTreatment(MemberInfoEntity memberInfoEntity,
                                       MemberInfoDetailsModel memberInfoDetailsModel) {

        /** 診療項目1_タイトル */
        memberInfoDetailsModel.setMedicalTreatment1Title(medicalTreatmentDto.getMedicalTreatment1Title());
        /** 診療項目2_タイトル */
        memberInfoDetailsModel.setMedicalTreatment2Title(medicalTreatmentDto.getMedicalTreatment2Title());
        /** 診療項目3_タイトル */
        memberInfoDetailsModel.setMedicalTreatment3Title(medicalTreatmentDto.getMedicalTreatment3Title());
        /** 診療項目4_タイトル */
        memberInfoDetailsModel.setMedicalTreatment4Title(medicalTreatmentDto.getMedicalTreatment4Title());
        /** 診療項目5_タイトル */
        memberInfoDetailsModel.setMedicalTreatment5Title(medicalTreatmentDto.getMedicalTreatment5Title());
        /** 診療項目6_タイトル */
        memberInfoDetailsModel.setMedicalTreatment6Title(medicalTreatmentDto.getMedicalTreatment6Title());
        /** 診療項目7_タイトル */
        memberInfoDetailsModel.setMedicalTreatment7Title(medicalTreatmentDto.getMedicalTreatment7Title());
        /** 診療項目8_タイトル */
        memberInfoDetailsModel.setMedicalTreatment8Title(medicalTreatmentDto.getMedicalTreatment8Title());
        /** 診療項目9_タイトル */
        memberInfoDetailsModel.setMedicalTreatment9Title(medicalTreatmentDto.getMedicalTreatment9Title());
        /** 診療項目10_タイトル */
        memberInfoDetailsModel.setMedicalTreatment10Title(medicalTreatmentDto.getMedicalTreatment10Title());

        /** 診療項目1_表示判定 */
        memberInfoDetailsModel.setMedicalTreatment1Disp(medicalTreatmentDto.getMedicalTreatment1Disp());
        /** 診療項目2_表示判定 */
        memberInfoDetailsModel.setMedicalTreatment2Disp(medicalTreatmentDto.getMedicalTreatment2Disp());
        /** 診療項目3_表示判定 */
        memberInfoDetailsModel.setMedicalTreatment3Disp(medicalTreatmentDto.getMedicalTreatment3Disp());
        /** 診療項目4_表示判定 */
        memberInfoDetailsModel.setMedicalTreatment4Disp(medicalTreatmentDto.getMedicalTreatment4Disp());
        /** 診療項目5_表示判定 */
        memberInfoDetailsModel.setMedicalTreatment5Disp(medicalTreatmentDto.getMedicalTreatment5Disp());
        /** 診療項目6_表示判定 */
        memberInfoDetailsModel.setMedicalTreatment6Disp(medicalTreatmentDto.getMedicalTreatment6Disp());
        /** 診療項目7_表示判定 */
        memberInfoDetailsModel.setMedicalTreatment7Disp(medicalTreatmentDto.getMedicalTreatment7Disp());
        /** 診療項目8_表示判定 */
        memberInfoDetailsModel.setMedicalTreatment8Disp(medicalTreatmentDto.getMedicalTreatment8Disp());
        /** 診療項目9_表示判定 */
        memberInfoDetailsModel.setMedicalTreatment9Disp(medicalTreatmentDto.getMedicalTreatment9Disp());
        /** 診療項目10_表示判定 */
        memberInfoDetailsModel.setMedicalTreatment10Disp(medicalTreatmentDto.getMedicalTreatment10Disp());

        // 診療項目
        memberInfoDetailsModel.setMedicalTreatment(memberInfoEntity.getMedicalTreatmentFlag());

        String[] medicalTreatment =
                        String.format("%-10s", memberInfoEntity.getMedicalTreatmentFlag()).replace(" ", "0").split("");

        for (int i = 1; i <= medicalTreatment.length; i++) {
            if (HTypeMedicalTreatmentFlag.ON.getValue().equals(medicalTreatment[i - 1])) {
                switch (i) {
                    case 1:
                        memberInfoDetailsModel.setMedicalTreatment1(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 2:
                        memberInfoDetailsModel.setMedicalTreatment2(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 3:
                        memberInfoDetailsModel.setMedicalTreatment3(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 4:
                        memberInfoDetailsModel.setMedicalTreatment4(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 5:
                        memberInfoDetailsModel.setMedicalTreatment5(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 6:
                        memberInfoDetailsModel.setMedicalTreatment6(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 7:
                        memberInfoDetailsModel.setMedicalTreatment7(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 8:
                        memberInfoDetailsModel.setMedicalTreatment8(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 9:
                        memberInfoDetailsModel.setMedicalTreatment9(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 10:
                        memberInfoDetailsModel.setMedicalTreatment10(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    default:
                        break;
                }
            } else {
                switch (i) {
                    case 1:
                        memberInfoDetailsModel.setMedicalTreatment1(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 2:
                        memberInfoDetailsModel.setMedicalTreatment2(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 3:
                        memberInfoDetailsModel.setMedicalTreatment3(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 4:
                        memberInfoDetailsModel.setMedicalTreatment4(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 5:
                        memberInfoDetailsModel.setMedicalTreatment5(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 6:
                        memberInfoDetailsModel.setMedicalTreatment6(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 7:
                        memberInfoDetailsModel.setMedicalTreatment7(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 8:
                        memberInfoDetailsModel.setMedicalTreatment8(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 9:
                        memberInfoDetailsModel.setMedicalTreatment9(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 10:
                        memberInfoDetailsModel.setMedicalTreatment10(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    default:
                        break;
                }
            }
        }
    }
    // PDR Migrate Customization to here

    // 2023-renew No85-2 from here

    /**
     * 会員クラス更新
     *
     * @param memberInfoEntity 会員クラス
     */
    public void updateMemberInfoEntity(MemberInfoEntity memberInfoEntity) {
        if (StringUtils.isNotEmpty(memberInfoEntity.getMemo())) {
            String memo = memberInfoEntity.getMemo() + CRLF + memberInfoEntity.getMemberInfoId();
            memberInfoEntity.setMemo(memo);
        } else {
            memberInfoEntity.setMemo(memberInfoEntity.getMemberInfoId());
        }
        memberInfoEntity.setMemberInfoStatus(HTypeMemberInfoStatus.REMOVE);
        memberInfoEntity.setApproveStatus(HTypeApproveStatus.DENIAL);
        memberInfoEntity.setMemberInfoUniqueId(null);
        memberInfoEntity.setMemberInfoId(null);
        memberInfoEntity.setMemberInfoMail(null);
        memberInfoEntity.setMemberInfoPassword(null);
    }
    // 2023-renew No85-2 to here

    // 2023-renew No25 from here

    /**
     * 会員クラス更新
     *
     * @param memberInfoEntity 会員クラス
     */
    public void resetPassword(MemberInfoEntity memberInfoEntity) {
        // 会員業務ヘルパークラスを取得する
        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);

        // SpringSecurity準拠の方式で暗号化
        PasswordEncoder encoder = ApplicationContextUtility.getBeanByName("encoderMember", PasswordEncoder.class);
        // パスワード暗号化
        String encryptedPassword = encoder.encode(memberInfoUtility.createPassword());
        memberInfoEntity.setMemberInfoPassword(encryptedPassword);

        // パスワード変更要求フラグ:1(要求する)
        memberInfoEntity.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.ON);
    }

    // 2023-renew No25 to here
    // 2023-renew No22 from here
    public List<UploadFileModel> toUploadFileModel(List<MemberImageDto> memberImageDtoList,
                                                   String path,
                                                   Integer memberInfoSeq) {
        List<UploadFileModel> uploadFileModelList = new ArrayList<>();
        for (MemberImageDto memberImageDto : memberImageDtoList) {
            UploadFileModel uploadFileModel = new UploadFileModel();
            uploadFileModel.setFileNo(memberImageDto.getMemberImageNo());
            uploadFileModel.setFileName(memberImageDto.getMemberImageFileName());

            //元のファイル名を取得する
            String pattern = "(^.*)(_\\d{17})\\.(\\w{3,4})";
            String originName = memberImageDto.getMemberImageFileName().replaceAll(pattern, "$1\\.$3");
            uploadFileModel.setOriginName(originName);
            uploadFileModel.setFilePath(path + memberInfoSeq + "/" + uploadFileModel.getFileName());
            if (HTypeUploadExtension.PDF.getValue().equals(memberImageDto.getMemberImageType())) {
                uploadFileModel.setExtensionType(HTypeUploadExtension.PDF);
            } else if (HTypeUploadExtension.PNG.getValue().equals(memberImageDto.getMemberImageType())) {
                uploadFileModel.setExtensionType(HTypeUploadExtension.PNG);
            }
            uploadFileModelList.add(uploadFileModel);
        }
        return uploadFileModelList;
    }

    /**
     * ファイルの名前を変更
     * @param fileName: アップロードされたファイルの名前
     *
     * 新しいファイル名を返します。名前と現在時刻を含む変更される名前を返します。
     */
    public String renameFile(String fileName) {
        if (fileName == null)
            return "";
        String[] parts = fileName.split("\\.");
        // 拡張子があるかどうかを確認する
        String extension = parts[parts.length - 1];

        int dotIndex = fileName.lastIndexOf('.');
        String name = fileName.substring(0, dotIndex);
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
        String date = dateFormat.format(new Date());
        return name + "_" + date + "." + extension;
    }
    // 2023-renew No22 to here
}
