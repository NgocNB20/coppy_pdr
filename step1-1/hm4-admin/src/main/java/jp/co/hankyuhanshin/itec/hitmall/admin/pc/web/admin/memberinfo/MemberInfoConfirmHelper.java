package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalTreatmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MedicalTreatmentDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiAddUserInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.NonConsultationDayUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 会員確認HELPER
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class MemberInfoConfirmHelper {

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

    /** 診療項目Dto */
    private final MedicalTreatmentDto medicalTreatmentDto;

    /**
     * コンストラクタ
     *
     * @param conversionUtility   変換ユーティリティクラス
     * @param dateUtility         日付関連Utilityクラス
     * @param memberInfoUtility   会員業務ヘルパークラス
     * @param medicalTreatmentDto
     */
    @Autowired
    public MemberInfoConfirmHelper(ConversionUtility conversionUtility,
                                   DateUtility dateUtility,
                                   MemberInfoUtility memberInfoUtility,
                                   MedicalTreatmentDto medicalTreatmentDto) {
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
        this.memberInfoUtility = memberInfoUtility;
        this.medicalTreatmentDto = medicalTreatmentDto;
    }

    /**
     * 会員詳細DTOからページに変換
     *
     * @param memberInfoDetailsDto  会員詳細DTO
     * @param memberInfoUpdateModel 会員情報変更モデル
     */
    public void toPageForLoad(MemberInfoDetailsDto memberInfoDetailsDto, MemberInfoUpdateModel memberInfoUpdateModel) {

        // 編集画面で編集された情報
        MemberInfoEntity inputData = memberInfoDetailsDto.getMemberInfoEntity();
        // PDR Migrate Customization from here
        //        /**
        //         *
        //         * PDR#11 08_データ連携（顧客情報）会員情報の項目追加・変更<br/>
        //         * 会員管理・会員詳細修正確認DXO<br/>
        //         *
        //         */
        // 休診曜日判定Utility
        NonConsultationDayUtility nonConsultationDayUtility =
                        ApplicationContextUtility.getBean(NonConsultationDayUtility.class);

        // PDR Migrate Customization from here
        // 入力画面で承認状態が「未承認」から「承認」に変更された場合に該当の値を更新
        if ((!HTypeApproveStatus.ON.equals(memberInfoUpdateModel.getMemberInfoEntity().getApproveStatus())
             && HTypeApproveStatus.ON.equals(inputData.getApproveStatus()))) {
            memberInfoUpdateModel.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION.getValue());
            inputData.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);
            memberInfoUpdateModel.setOnlineRegistFlag(HTypeOnlineRegistFlag.ON.getValue());
            inputData.setOnlineRegistFlag(HTypeOnlineRegistFlag.ON);
        } else {
            memberInfoUpdateModel.setMemberInfoStatus(inputData.getMemberInfoStatus().getValue());
            memberInfoUpdateModel.setOnlineRegistFlag(inputData.getOnlineRegistFlag().getValue());
        }
        memberInfoUpdateModel.setApproveStatus(inputData.getApproveStatus().getValue());

        // 会員状態情報
        memberInfoUpdateModel.setRegistTime(inputData.getRegistTime());
        memberInfoUpdateModel.setUpdateTime(inputData.getUpdateTime());
        memberInfoUpdateModel.setAdmissionYmd(dateUtility.getYmdFormatValue(inputData.getAdmissionYmd(), "yyyy/MM/dd"));
        memberInfoUpdateModel.setSecessionYmd(dateUtility.getYmdFormatValue(inputData.getSecessionYmd(), "yyyy/MM/dd"));
        memberInfoUpdateModel.setAccountLock(memberInfoUtility.isAccountStatusLock(inputData.getAccountLockTime()));
        memberInfoUpdateModel.setAccountLockTime(inputData.getAccountLockTime());
        memberInfoUpdateModel.setLoginFailureCount(inputData.getLoginFailureCount());
        // PDR Migrate Customization to here

        // 最終ログイン情報
        if (inputData.getLastLoginTime() != null) {
            memberInfoUpdateModel.setLastLoginTime(
                            conversionUtility.toYmd(inputData.getLastLoginTime()) + " " + conversionUtility.toHms(
                                            inputData.getLastLoginTime()));
        }
        if (inputData.getLastLoginDeviceType() != null) {
            memberInfoUpdateModel.setLastLoginDeviceType(EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class,
                                                                                       inputData.getLastLoginDeviceType()
                                                                                                .getValue()
                                                                                      ));
        } else {
            memberInfoUpdateModel.setLastLoginDeviceType(null);
        }
        memberInfoUpdateModel.setLastLoginUserAgent(inputData.getLastLoginUserAgent());

        // お客様情報
        memberInfoUpdateModel.setMemberInfoSeq(inputData.getMemberInfoSeq());
        memberInfoUpdateModel.setMemberInfoId(inputData.getMemberInfoId());

        memberInfoUpdateModel.setCustomerNo(inputData.getCustomerNo());
        // PDR Migrate Customization from here
        // 氏名結合
        memberInfoUpdateModel.setMemberInfoName(inputData.getMemberInfoLastName());
        memberInfoUpdateModel.setMemberInfoLastName(inputData.getMemberInfoLastName());
        memberInfoUpdateModel.setMemberInfoFirstName(inputData.getMemberInfoFirstName());
        // フリガナ結合
        memberInfoUpdateModel.setMemberInfoKana(inputData.getMemberInfoLastKana());
        memberInfoUpdateModel.setMemberInfoLastKana(inputData.getMemberInfoLastKana());
        memberInfoUpdateModel.setMemberInfoFirstKana(inputData.getMemberInfoFirstKana());
        // PDR Migrate Customization to here

        memberInfoUpdateModel.setMemberInfoSex(EnumTypeUtil.getValue(inputData.getMemberInfoSex()));
        memberInfoUpdateModel.setMemberInfoBirthday(conversionUtility.toYmd(inputData.getMemberInfoBirthday()));
        memberInfoUpdateModel.setMemberInfoTel(inputData.getMemberInfoTel());
        memberInfoUpdateModel.setMemberInfoContactTel(inputData.getMemberInfoContactTel());
        memberInfoUpdateModel.setMemberInfoFax(inputData.getMemberInfoFax());
        memberInfoUpdateModel.setMemberInfoZipCode(inputData.getMemberInfoZipCode());

        // PDR Migrate Customization from here
        // 住所結合
        String address = inputData.getMemberInfoAddress1() + inputData.getMemberInfoAddress2();
        if (inputData.getMemberInfoAddress3() != null) {
            address += inputData.getMemberInfoAddress3();
        }
        // 住所結合に新規項目追加
        if (inputData.getMemberInfoAddress4() != null) {
            address += inputData.getMemberInfoAddress4();
        }
        if (inputData.getMemberInfoAddress5() != null) {
            address += inputData.getMemberInfoAddress5();
        }
        memberInfoUpdateModel.setMemberInfoAddress(address);

        memberInfoUpdateModel.setMemberInfoAddress1(inputData.getMemberInfoAddress1());
        memberInfoUpdateModel.setMemberInfoAddress2(inputData.getMemberInfoAddress2());
        memberInfoUpdateModel.setMemberInfoAddress3(inputData.getMemberInfoAddress3());
        memberInfoUpdateModel.setMemberInfoAddress4(inputData.getMemberInfoAddress4());
        memberInfoUpdateModel.setMemberInfoAddress5(inputData.getMemberInfoAddress5());
        // 顧客番号・パスワード通知メール送信
        if (memberInfoUpdateModel.isSendNoticeMailFlag()) {
            memberInfoUpdateModel.setSendNoticeMailFlag(true);
        } else {
            memberInfoUpdateModel.setSendNoticeMailFlag(false);
        }
        memberInfoUpdateModel.setRepresentativeName(inputData.getRepresentativeName());
        memberInfoUpdateModel.setBusinessType(EnumTypeUtil.getValue(inputData.getBusinessType()));
        memberInfoUpdateModel.setConfDocumentType(EnumTypeUtil.getValue(inputData.getConfDocumentType()));
        memberInfoUpdateModel.setNonConsultationDay(
                        nonConsultationDayUtility.getNonConsultationDay(inputData.getNonConsultationDay()));
        memberInfoUpdateModel.setDrugSalesType(EnumTypeUtil.getValue(inputData.getDrugSalesType()));
        memberInfoUpdateModel.setMedicalEquipmentSalesType(
                        EnumTypeUtil.getValue(inputData.getMedicalEquipmentSalesType()));
        memberInfoUpdateModel.setDentalMonopolySalesType(EnumTypeUtil.getValue(inputData.getDentalMonopolySalesType()));
        memberInfoUpdateModel.setCreditPaymentUseFlag(EnumTypeUtil.getValue(inputData.getCreditPaymentUseFlag()));
        memberInfoUpdateModel.setTransferPaymentUseFlag(EnumTypeUtil.getValue(inputData.getTransferPaymentUseFlag()));
        memberInfoUpdateModel.setCashDeliveryUseFlag(EnumTypeUtil.getValue(inputData.getCashDeliveryUseFlag()));
        memberInfoUpdateModel.setDirectDebitUseFlag(EnumTypeUtil.getValue(inputData.getDirectDebitUseFlag()));
        memberInfoUpdateModel.setMonthlyPayUseFlag(EnumTypeUtil.getValue(inputData.getMonthlyPayUseFlag()));
        memberInfoUpdateModel.setMemberListType(EnumTypeUtil.getValue(inputData.getMemberListType()));
        // PDR Migrate Customization to here

        // 管理用メモ
        memberInfoUpdateModel.setMemo(inputData.getMemo());

        // 反社会的勢力ではないことの保証
        memberInfoUpdateModel.setNoAntiSocialFlag(HTypeNoAntiSocialFlag.ON.equals(inputData.getNoAntiSocialFlag()));

        // ログイン時パスワード変更
        memberInfoUpdateModel.setPasswordNeedChangeFlag(inputData.getPasswordNeedChangeFlag());

        // PDR Migrate Customization from here
        memberInfoUpdateModel.setSendMailPermitFlag(inputData.getSendMailPermitFlag());
        memberInfoUpdateModel.setSendFaxPermitFlag(inputData.getSendFaxPermitFlag());
        memberInfoUpdateModel.setSendDirectMailFlag(inputData.getSendDirectMailFlag());
        // PDR Migrate Customization to here

        // 金属商品価格お知らせメール
        memberInfoUpdateModel.setMetalPermitFlag(inputData.getMetalPermitFlag());

        // 2023-renew No79 from here
        //注文完了メール
        memberInfoUpdateModel.setOrderCompletePermitFlag(inputData.getOrderCompletePermitFlag());

        //発送完了メール
        memberInfoUpdateModel.setDeliveryCompletePermitFlag(inputData.getDeliveryCompletePermitFlag());
        // 2023-renew No79 to here

        // 診療項目
        setMedicalTreatment(inputData, memberInfoUpdateModel);

        // 診療項目メモ
        memberInfoUpdateModel.setMedicalTreatmentMemo(inputData.getMedicalTreatmentMemo());

        // 修正箇所の検出
        MemberInfoEntity original = memberInfoUpdateModel.getMemberInfoEntity();
        MemberInfoEntity modified = memberInfoUpdateModel.getMemberInfoDetailsDto().getMemberInfoEntity();
        List<String> modifiedItemNameList = DiffUtil.diff(original, modified);

        // 診療項目の修正箇所検出
        List<String> checkDiffList = new ArrayList<String>();
        MemberInfoEntity tempMemberInfoEntity = memberInfoUpdateModel.getMemberInfoEntity();
        String[] medicalTreatmentOriginal = String.format("%-10s", tempMemberInfoEntity.getMedicalTreatmentFlag())
                                                  .replace(" ", "0")
                                                  .split("");
        String[] medicalTreatmentModifild =
                        String.format("%-10s", inputData.getMedicalTreatmentFlag()).replace(" ", "0").split("");
        checkDiffMedicalTreatment(
                        checkDiffList, medicalTreatmentOriginal, medicalTreatmentModifild, memberInfoUpdateModel);

        memberInfoUpdateModel.setModifiedList(modifiedItemNameList);
        // PDR Migrate Customization to here
    }

    // PDR Migrate Customization from here

    /**
     * 会員情報登録Web-APIリクエストDtoを生成する<br/>
     *
     * @param memberInfoEntity 会員情報エンティティ
     * @return 会員情報更新リクエストDto
     */
    public WebApiAddUserInformationRequestDto createWebApiAddUserInformationRequestDto(MemberInfoEntity memberInfoEntity) {
        // 会員情報更新リクエストDto
        WebApiAddUserInformationRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiAddUserInformationRequestDto.class);

        // 各情報をセット
        // 顧客番号
        reqDto.setCustomerNo(memberInfoEntity.getCustomerNo());
        // メールアドレス
        reqDto.setMailAddress(memberInfoEntity.getMemberInfoMail());
        // 事業所名
        reqDto.setOfficeName(memberInfoEntity.getMemberInfoLastName());
        // 事業所名フリガナ
        reqDto.setOfficeKana(memberInfoEntity.getMemberInfoLastKana());
        // 代表者名
        reqDto.setRepresentative(memberInfoEntity.getRepresentativeName());
        // 顧客区分
        reqDto.setBusinessType(Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getBusinessType())));
        // 確認書類
        reqDto.setKakuninShoKbn(Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getConfDocumentType())));
        // 電話番号
        reqDto.setTel(memberInfoEntity.getMemberInfoTel());
        // FAX番号
        reqDto.setFax(memberInfoEntity.getMemberInfoFax());
        // 郵便番号
        reqDto.setZipCode(memberInfoEntity.getMemberInfoZipCode());
        // 住所(都道府県・市区町村)
        reqDto.setCity(memberInfoEntity.getMemberInfoAddress1());
        // 住所(丁目・番地)
        reqDto.setAddress(memberInfoEntity.getMemberInfoAddress2());
        if (memberInfoEntity.getMemberInfoAddress3() != null) {
            // 住所(建物名・部屋番号)
            reqDto.setBuilding(memberInfoEntity.getMemberInfoAddress3());
        }
        if (memberInfoEntity.getMemberInfoAddress4() != null) {
            // 住所(方書1)
            reqDto.setOther1(memberInfoEntity.getMemberInfoAddress4());
        }
        if (memberInfoEntity.getMemberInfoAddress5() != null) {
            // 住所(方書2)
            reqDto.setOther2(memberInfoEntity.getMemberInfoAddress5());
        }
        // 休診曜日(nullの場合は"000000000"とする)
        if (StringUtil.isEmpty(memberInfoEntity.getNonConsultationDay())) {
            reqDto.setNonConsultationDay(NonConsultationDayUtility.NON_CONSULTATION_NULL_STRING);
        } else {
            reqDto.setNonConsultationDay(memberInfoEntity.getNonConsultationDay());
        }
        // 診療項目
        reqDto.setTreatmentType(memberInfoEntity.getMedicalTreatmentFlag());
        // その他診療内容メモ
        reqDto.setShinryoMemo(memberInfoEntity.getMedicalTreatmentMemo());
        // Eメールによる情報提供
        reqDto.setMailPermitFlag(Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getSendMailPermitFlag())));
        // FAXによる情報提供
        reqDto.setFaxPermitFlag(Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getSendFaxPermitFlag())));
        // DMによる情報提供
        reqDto.setSendDirectMailFlag(Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getSendDirectMailFlag())));
        // 金属商品価格お知らせメール
        reqDto.setMetalPermitFlag(String.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getMetalPermitFlag())));
        // 歯科専売品販売区分
        reqDto.setDentalMonopolySalesType(
                        Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getDentalMonopolySalesType())));
        // 医療機器販売区分
        reqDto.setMedicalEquipmentSalesType(
                        Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getMedicalEquipmentSalesType())));
        // 医薬品・注射針販売区分
        reqDto.setDrugSalesType(Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getDrugSalesType())));
        // 名簿区分
        reqDto.setMemberListType(Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getMemberListType())));
        // オンライン登録フラグ
        reqDto.setOnlineRegistFlag(Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getOnlineRegistFlag())));
        // 経理区分
        reqDto.setKeiriKbn(Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getAccountingType())));
        // オンラインログイン可否
        reqDto.setOnlineYesNo(Integer.valueOf(EnumTypeUtil.getValue(memberInfoEntity.getOnlineLoginAdvisability())));

        return reqDto;

    }

    /**
     * 診療項目設定<br/>
     *
     * @param memberInfoEntity      会員Entity
     * @param memberInfoUpdateModel page
     */
    protected void setMedicalTreatment(MemberInfoEntity memberInfoEntity, MemberInfoUpdateModel memberInfoUpdateModel) {

        /** 診療項目1_タイトル */
        memberInfoUpdateModel.setMedicalTreatment1Title(medicalTreatmentDto.getMedicalTreatment1Title());
        /** 診療項目2_タイトル */
        memberInfoUpdateModel.setMedicalTreatment2Title(medicalTreatmentDto.getMedicalTreatment2Title());
        /** 診療項目3_タイトル */
        memberInfoUpdateModel.setMedicalTreatment3Title(medicalTreatmentDto.getMedicalTreatment3Title());
        /** 診療項目4_タイトル */
        memberInfoUpdateModel.setMedicalTreatment4Title(medicalTreatmentDto.getMedicalTreatment4Title());
        /** 診療項目5_タイトル */
        memberInfoUpdateModel.setMedicalTreatment5Title(medicalTreatmentDto.getMedicalTreatment5Title());
        /** 診療項目6_タイトル */
        memberInfoUpdateModel.setMedicalTreatment6Title(medicalTreatmentDto.getMedicalTreatment6Title());
        /** 診療項目7_タイトル */
        memberInfoUpdateModel.setMedicalTreatment7Title(medicalTreatmentDto.getMedicalTreatment7Title());
        /** 診療項目8_タイトル */
        memberInfoUpdateModel.setMedicalTreatment8Title(medicalTreatmentDto.getMedicalTreatment8Title());
        /** 診療項目9_タイトル */
        memberInfoUpdateModel.setMedicalTreatment9Title(medicalTreatmentDto.getMedicalTreatment9Title());
        /** 診療項目10_タイトル */
        memberInfoUpdateModel.setMedicalTreatment10Title(medicalTreatmentDto.getMedicalTreatment10Title());

        /** 診療項目1_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment1Disp(medicalTreatmentDto.getMedicalTreatment1Disp());
        /** 診療項目2_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment2Disp(medicalTreatmentDto.getMedicalTreatment2Disp());
        /** 診療項目3_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment3Disp(medicalTreatmentDto.getMedicalTreatment3Disp());
        /** 診療項目4_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment4Disp(medicalTreatmentDto.getMedicalTreatment4Disp());
        /** 診療項目5_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment5Disp(medicalTreatmentDto.getMedicalTreatment5Disp());
        /** 診療項目6_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment6Disp(medicalTreatmentDto.getMedicalTreatment6Disp());
        /** 診療項目7_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment7Disp(medicalTreatmentDto.getMedicalTreatment7Disp());
        /** 診療項目8_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment8Disp(medicalTreatmentDto.getMedicalTreatment8Disp());
        /** 診療項目9_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment9Disp(medicalTreatmentDto.getMedicalTreatment9Disp());
        /** 診療項目10_表示判定 */
        memberInfoUpdateModel.setMedicalTreatment10Disp(medicalTreatmentDto.getMedicalTreatment10Disp());

        // 診療項目
        memberInfoUpdateModel.setMedicalTreatment(memberInfoEntity.getMedicalTreatmentFlag());

        // 診療項目
        String tempMedicalTreatment = "";

        // 入力画面でチェックかつ、表示対象の診療項目を対象とする。
        // 診療項目1
        if (memberInfoUpdateModel.isMedicalTreatment1()) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目2
        if (memberInfoUpdateModel.isMedicalTreatment2()) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目3
        if (memberInfoUpdateModel.isMedicalTreatment3()) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目4
        if (memberInfoUpdateModel.isMedicalTreatment4()) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目5
        if (memberInfoUpdateModel.isMedicalTreatment5()) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目6
        if (memberInfoUpdateModel.isMedicalTreatment6()) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目7
        if (memberInfoUpdateModel.isMedicalTreatment7()) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目8
        if (memberInfoUpdateModel.isMedicalTreatment8()) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目9
        if (memberInfoUpdateModel.isMedicalTreatment9()) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目10
        if (memberInfoUpdateModel.isMedicalTreatment10()) {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.ON.getValue();
        } else {
            tempMedicalTreatment = tempMedicalTreatment + HTypeMedicalTreatmentFlag.OFF.getValue();
        }

        // 診療項目設定
        memberInfoUpdateModel.setMedicalTreatment(tempMedicalTreatment);
    }

    /**
     * 診療項目変更チェック<br/>
     *
     * @param checkDiffList            変更リスト
     * @param medicalTreatmentOriginal 変更前
     * @param medicalTreatmentModifild 変更後
     * @param memberInfoUpdateModel    page
     */
    protected void checkDiffMedicalTreatment(List<String> checkDiffList,
                                             String[] medicalTreatmentOriginal,
                                             String[] medicalTreatmentModifild,
                                             MemberInfoUpdateModel memberInfoUpdateModel) {

        for (int i = 1; i <= medicalTreatmentModifild.length; i++) {
            if (!medicalTreatmentOriginal[i - 1].equals(medicalTreatmentModifild[i - 1])) {
                switch (i) {
                    case 1:
                        checkDiffList.add("medicalTreatment1");
                        continue;
                    case 2:
                        checkDiffList.add("medicalTreatment2");
                        continue;
                    case 3:
                        checkDiffList.add("medicalTreatment3");
                        continue;
                    case 4:
                        checkDiffList.add("medicalTreatment4");
                        continue;
                    case 5:
                        checkDiffList.add("medicalTreatment5");
                        continue;
                    case 6:
                        checkDiffList.add("medicalTreatment6");
                        continue;
                    case 7:
                        checkDiffList.add("medicalTreatment7");
                        continue;
                    case 8:
                        checkDiffList.add("medicalTreatment8");
                        continue;
                    case 9:
                        checkDiffList.add("medicalTreatment9");
                        continue;
                    case 10:
                        checkDiffList.add("medicalTreatment10");
                        continue;
                    default:
                        break;
                }
            }
        }

        // 初期化
        memberInfoUpdateModel.setDiffMedicalTreatment1Class(null);
        memberInfoUpdateModel.setDiffMedicalTreatment2Class(null);
        memberInfoUpdateModel.setDiffMedicalTreatment3Class(null);
        memberInfoUpdateModel.setDiffMedicalTreatment4Class(null);
        memberInfoUpdateModel.setDiffMedicalTreatment5Class(null);
        memberInfoUpdateModel.setDiffMedicalTreatment6Class(null);
        memberInfoUpdateModel.setDiffMedicalTreatment7Class(null);
        memberInfoUpdateModel.setDiffMedicalTreatment8Class(null);
        memberInfoUpdateModel.setDiffMedicalTreatment9Class(null);
        memberInfoUpdateModel.setDiffMedicalTreatment10Class(null);

        // 比較
        String styleValue = "diff";
        memberInfoUpdateModel.setDiffMedicalTreatment1Class(checkDiff(checkDiffList, "medicalTreatment1", styleValue,
                                                                      memberInfoUpdateModel.getDiffMedicalTreatment1Class()
                                                                     ));
        memberInfoUpdateModel.setDiffMedicalTreatment2Class(checkDiff(checkDiffList, "medicalTreatment2", styleValue,
                                                                      memberInfoUpdateModel.getDiffMedicalTreatment2Class()
                                                                     ));
        memberInfoUpdateModel.setDiffMedicalTreatment3Class(checkDiff(checkDiffList, "medicalTreatment3", styleValue,
                                                                      memberInfoUpdateModel.getDiffMedicalTreatment3Class()
                                                                     ));
        memberInfoUpdateModel.setDiffMedicalTreatment4Class(checkDiff(checkDiffList, "medicalTreatment4", styleValue,
                                                                      memberInfoUpdateModel.getDiffMedicalTreatment4Class()
                                                                     ));
        memberInfoUpdateModel.setDiffMedicalTreatment5Class(checkDiff(checkDiffList, "medicalTreatment5", styleValue,
                                                                      memberInfoUpdateModel.getDiffMedicalTreatment5Class()
                                                                     ));
        memberInfoUpdateModel.setDiffMedicalTreatment6Class(checkDiff(checkDiffList, "medicalTreatment6", styleValue,
                                                                      memberInfoUpdateModel.getDiffMedicalTreatment6Class()
                                                                     ));
        memberInfoUpdateModel.setDiffMedicalTreatment7Class(checkDiff(checkDiffList, "medicalTreatment7", styleValue,
                                                                      memberInfoUpdateModel.getDiffMedicalTreatment7Class()
                                                                     ));
        memberInfoUpdateModel.setDiffMedicalTreatment8Class(checkDiff(checkDiffList, "medicalTreatment8", styleValue,
                                                                      memberInfoUpdateModel.getDiffMedicalTreatment8Class()
                                                                     ));
        memberInfoUpdateModel.setDiffMedicalTreatment9Class(checkDiff(checkDiffList, "medicalTreatment9", styleValue,
                                                                      memberInfoUpdateModel.getDiffMedicalTreatment9Class()
                                                                     ));
        memberInfoUpdateModel.setDiffMedicalTreatment10Class(checkDiff(checkDiffList, "medicalTreatment10", styleValue,
                                                                       memberInfoUpdateModel.getDiffMedicalTreatment10Class()
                                                                      ));
    }

    /**
     * 項目変更チェック
     *
     * @param diffList          相違情報リスト
     * @param dataPath          データ項目名（パス形式）
     * @param styleValue        相違点が見つかった時のスタイル設定値
     * @param settingStyleValue HTMLに設定されたスタイル設定値
     * @return スタイル設定値
     */
    protected String checkDiff(List<String> diffList, String dataPath, String styleValue, String settingStyleValue) {
        if (diffList.contains(dataPath)) {
            if (settingStyleValue == null || settingStyleValue.isEmpty()) {
                return styleValue;
            } else if (styleValue == null || styleValue.isEmpty()) {
                return settingStyleValue;
            } else {
                return settingStyleValue + String.valueOf(styleValue.charAt(0)).toUpperCase() + styleValue.substring(1);
            }
        }
        return settingStyleValue;
    }
    // PDR Migrate Customization to here
}
