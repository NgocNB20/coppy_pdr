// PDR Migrate Customization from here

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CheckMessageDtoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoConfirmScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMedicalTreatmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberImage;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeNoAntiSocialFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.MedicalTreatmentDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm.update.MemberConfirmUpdateModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.RegistUploadFile;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.NonConsultationDayUtility;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 会員情報画面 Helperクラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class MemberConfirmHelper {

    private final DateUtility dateUtility;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    // 2023-renew AddNo2 from here
    /**
     * 休診曜日Utility
     */
    private final NonConsultationDayUtility nonConsultationDayUtility;

    /**
     * 診療項目Dto
     */
    private final MedicalTreatmentDto medicalTreatmentDto;
    // 2023-renew AddNo2 to here

    /**
     * コンストラクタ
     *
     * @param dateUtility       日付関連Utilityクラス
     * @param conversionUtility
     */
    @Autowired
    public MemberConfirmHelper(DateUtility dateUtility,
                               ConversionUtility conversionUtility,
                               // 2023-renew AddNo2 from here
                               NonConsultationDayUtility nonConsultationDayUtility,
                               MedicalTreatmentDto medicalTreatmentDto) {
        // 2023-renew AddNo2 to here
        this.dateUtility = dateUtility;
        this.conversionUtility = conversionUtility;
        // 2023-renew AddNo2 from here
        this.nonConsultationDayUtility = nonConsultationDayUtility;
        this.medicalTreatmentDto = medicalTreatmentDto;
        // 2023-renew AddNo2 to here
    }

    /**
     * 初期処理の画面表示
     * 取得した会員情報をページの各項目にセット
     * <pre>
     * 性別と生年月日のセット処理を削除
     * 郵便番号のセット処理を追加
     * </pre>
     *
     * @param memberInfoEntity 会員エンティティ
     * @param indexPage        会員情報確認画面
     */
    public void toPageForLoad(MemberInfoEntity memberInfoEntity, MemberConfirmModel indexPage) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // フィールドコピー
        conversionUtility.copyProperties(memberInfoEntity, indexPage);

        // PDR Migrate Customization from here
        // 郵便番号
        if (memberInfoEntity.getMemberInfoZipCode() != null) {
            String[] zipCodeArray = conversionUtility.toZipCodeArray(memberInfoEntity.getMemberInfoZipCode());
            indexPage.setMemberInfoZipCode1(zipCodeArray[0]);
            indexPage.setMemberInfoZipCode2(zipCodeArray[1]);
        }
        // PDR Migrate Customization to here

        // メールアドレス
        indexPage.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());
        String[] birthdayArray = conversionUtility.toYmdArray(memberInfoEntity.getMemberInfoBirthday());
        indexPage.setMemberInfoBirthdayYear(birthdayArray[0]);
        indexPage.setMemberInfoBirthdayMonth(birthdayArray[1]);
        indexPage.setMemberInfoBirthdayDay(birthdayArray[2]);

        // 2023-renew AddNo2 from here
        indexPage.setNonConsultationDayDisp(
                        nonConsultationDayUtility.getNonConsultationDay(indexPage.getNonConsultationDay()));
        // 2023-renew AddNo2 to here
    }

    // 2023-renew AddNo2 from here

    /**
     * 初期処理の画面表示
     * 取得した会員情報をページの各項目にセット
     * <pre>
     * 性別と生年月日のセット処理を削除
     * 郵便番号のセット処理を追加
     * </pre>
     *
     * @param memberInfoEntity 会員エンティティ
     * @param indexPage        会員情報確認画面
     */
    public void toPageForLoad(MemberInfoEntity memberInfoEntity,
                              MemberConfirmUpdateModel indexPage,
                              boolean notSessionData) {
        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // フィールドコピー
        if (notSessionData) {
            conversionUtility.copyProperties(memberInfoEntity, indexPage);

            // 郵便番号
            if (memberInfoEntity.getMemberInfoZipCode() != null) {
                String[] zipCodeArray = conversionUtility.toZipCodeArray(memberInfoEntity.getMemberInfoZipCode());
                indexPage.setMemberInfoZipCode1(zipCodeArray[0]);
                indexPage.setMemberInfoZipCode2(zipCodeArray[1]);
            }

            // メールアドレス
            indexPage.setMemberInfoMail(memberInfoEntity.getMemberInfoMail());

            // メール情報
            if (indexPage.getSendMailPermitFlag() == HTypeSendMailPermitFlag.ON) {
                indexPage.setSendMail(true);
            }

            // 金属商品価格お知らせメール
            if (indexPage.getMetalPermitFlag() == HTypeMetalPermitFlag.ON) {
                indexPage.setMetalPermit(true);
            }

            // 2023-renew No79 from here
            // 注文完了メール
            if (indexPage.getOrderCompletePermitFlag() == HTypeOrderCompletePermitFlag.ON) {
                indexPage.setOrderCompletePermit(true);
            }

            // 発送完了メール
            if (indexPage.getDeliveryCompletePermitFlag() == HTypeDeliveryCompletePermitFlag.ON) {
                indexPage.setDeliveryCompletePermit(true);
            }
            // 2023-renew No79 to here
            // 診療項目
            setMedicalTreatment(memberInfoEntity, indexPage);

            // 休診曜日取得処理
            // AM欄用チェック配列取得
            boolean[] amCheckArray = nonConsultationDayUtility.setAmNonConsultationCheckList(
                            memberInfoEntity.getNonConsultationDay());
            // PM欄用チェック配列取得
            boolean[] pmCheckArray = nonConsultationDayUtility.setPmNonConsultationCheckList(
                            memberInfoEntity.getNonConsultationDay());

            // 各AM休診曜日に真偽値代入
            indexPage.setAmSunNonConsultation(amCheckArray[0]);
            indexPage.setAmMonNonConsultation(amCheckArray[1]);
            indexPage.setAmTueNonConsultation(amCheckArray[2]);
            indexPage.setAmWedNonConsultation(amCheckArray[3]);
            indexPage.setAmThuNonConsultation(amCheckArray[4]);
            indexPage.setAmFriNonConsultation(amCheckArray[5]);
            indexPage.setAmSatNonConsultation(amCheckArray[6]);
            indexPage.setAmHolidayNonConsultation(amCheckArray[7]);

            // 各PM休診曜日に真偽値代入
            indexPage.setPmSunNonConsultation(pmCheckArray[0]);
            indexPage.setPmMonNonConsultation(pmCheckArray[1]);
            indexPage.setPmTueNonConsultation(pmCheckArray[2]);
            indexPage.setPmWedNonConsultation(pmCheckArray[3]);
            indexPage.setPmThuNonConsultation(pmCheckArray[4]);
            indexPage.setPmFriNonConsultation(pmCheckArray[5]);
            indexPage.setPmSatNonConsultation(pmCheckArray[6]);
            indexPage.setPmHolidayNonConsultation(pmCheckArray[7]);

            // 無休
            indexPage.setAllConsultation(nonConsultationDayUtility.setNoHolidayNonConsultation(
                            memberInfoEntity.getNonConsultationDay()));

            indexPage.setNonConsultationDayDisp(
                            nonConsultationDayUtility.getNonConsultationDay(indexPage.getNonConsultationDay()));

            MemberConfirmInfoDetailModel memberConfirmInfoDetail = new MemberConfirmInfoDetailModel();
            conversionUtility.copyProperties(memberInfoEntity, memberConfirmInfoDetail);
            conversionUtility.copyProperties(indexPage, memberConfirmInfoDetail);
            indexPage.setMemberInfoDetail(memberConfirmInfoDetail);
        }

        // 修正箇所の検出
        MemberInfoEntity original = memberInfoEntity;
        MemberInfoEntity modified = SerializationUtils.clone(memberInfoEntity);
        if (!notSessionData) {
            modified.setMemberInfoZipCode(indexPage.getMemberInfoZipCode1() + indexPage.getMemberInfoZipCode2());
            // メール送信希望フラグ
            if (indexPage.isSendMail()) {
                indexPage.setSendMailPermitFlag(HTypeSendMailPermitFlag.ON);
            } else {
                indexPage.setSendMailPermitFlag(HTypeSendMailPermitFlag.OFF);
            }

            // 金属商品価格お知らせメール
            if (indexPage.isMetalPermit()) {
                indexPage.setMetalPermitFlag(HTypeMetalPermitFlag.ON);
            } else {
                indexPage.setMetalPermitFlag(HTypeMetalPermitFlag.OFF);
            }

            // 2023-renew No79 from here
            // 注文完了メール
            if (indexPage.isOrderCompletePermit()) {
                indexPage.setOrderCompletePermitFlag(HTypeOrderCompletePermitFlag.ON);
            } else {
                indexPage.setOrderCompletePermitFlag(HTypeOrderCompletePermitFlag.OFF);
            }

            // 発送完了メール
            if (indexPage.isDeliveryCompletePermit()) {
                indexPage.setDeliveryCompletePermitFlag(HTypeDeliveryCompletePermitFlag.ON);
            } else {
                indexPage.setDeliveryCompletePermitFlag(HTypeDeliveryCompletePermitFlag.OFF);
            }
            // 2023-renew No79 to here

            // 診療項目
            String tempMedicalTreatmentFlag = "";
            if (indexPage.isMedicalTreatment1()) {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
            } else {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
            }
            if (indexPage.isMedicalTreatment2()) {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
            } else {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
            }
            if (indexPage.isMedicalTreatment3()) {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
            } else {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
            }
            if (indexPage.isMedicalTreatment4()) {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
            } else {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
            }
            if (indexPage.isMedicalTreatment5()) {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
            } else {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
            }
            if (indexPage.isMedicalTreatment6()) {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
            } else {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
            }
            if (indexPage.isMedicalTreatment7()) {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
            } else {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
            }
            if (indexPage.isMedicalTreatment8()) {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
            } else {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
            }
            if (indexPage.isMedicalTreatment9()) {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
            } else {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
            }
            if (indexPage.isMedicalTreatment10()) {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.ON.getValue();
            } else {
                tempMedicalTreatmentFlag = tempMedicalTreatmentFlag + HTypeMedicalTreatmentFlag.OFF.getValue();
            }
            indexPage.setMedicalTreatment(tempMedicalTreatmentFlag);

            // 休診曜日処理
            // 画面表示のAM休診曜日を配列へ
            boolean[] pageAmArray =
                            new boolean[] {indexPage.isAmSunNonConsultation(), indexPage.isAmMonNonConsultation(),
                                            indexPage.isAmTueNonConsultation(), indexPage.isAmWedNonConsultation(),
                                            indexPage.isAmThuNonConsultation(), indexPage.isAmFriNonConsultation(),
                                            indexPage.isAmSatNonConsultation(), indexPage.isAmHolidayNonConsultation(),
                                            indexPage.isAllConsultation()};
            // 画面表示のPM休診曜日を配列へ
            boolean[] pagePmArray =
                            new boolean[] {indexPage.isPmSunNonConsultation(), indexPage.isPmMonNonConsultation(),
                                            indexPage.isPmTueNonConsultation(), indexPage.isPmWedNonConsultation(),
                                            indexPage.isPmThuNonConsultation(), indexPage.isPmFriNonConsultation(),
                                            indexPage.isPmSatNonConsultation(), indexPage.isPmHolidayNonConsultation(),
                                            indexPage.isAllConsultation()};
            // 画面の休診曜日から休診曜日文字列を取得
            String nonConsultationStr = nonConsultationDayUtility.getNonConsultationDayString(pageAmArray, pagePmArray);
            // 休診曜日文字列がすべて"0"の場合は休診曜日は変更なしとする(次画面での修正差分チェックが当たらないようにするため)
            if (nonConsultationStr.equals(NonConsultationDayUtility.NON_CONSULTATION_NULL_STRING)) {
                indexPage.setNonConsultationDay(indexPage.getMemberInfoDetail().getNonConsultationDay());
            } else {
                indexPage.setNonConsultationDay(nonConsultationStr);
            }

            conversionUtility.copyProperties(indexPage, modified);
            modified.setMedicalTreatmentFlag(indexPage.getMedicalTreatment());
            List<String> modifiedItemNameList = DiffUtil.diff(original, modified);

            // 診療項目の修正箇所検出
            List<String> checkDiffList = new ArrayList<String>();
            String[] medicalTreatmentOriginal =
                            String.format("%-10s", original.getMedicalTreatmentFlag()).replace(" ", "0").split("");
            String[] medicalTreatmentModified =
                            String.format("%-10s", modified.getMedicalTreatmentFlag()).replace(" ", "0").split("");
            checkDiffMedicalTreatment(checkDiffList, medicalTreatmentOriginal, medicalTreatmentModified, indexPage);

            indexPage.setModifiedList(modifiedItemNameList);

            indexPage.setNonConsultationDayDisp(
                            nonConsultationDayUtility.getNonConsultationDay(indexPage.getNonConsultationDay()));
        }
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
                                             MemberConfirmUpdateModel memberInfoUpdateModel) {

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

    private void setMedicalTreatment(MemberInfoEntity memberInfoEntity,
                                     MemberConfirmUpdateModel memberConfirmUpdateModel) {
        /** 診療項目1_タイトル */
        memberConfirmUpdateModel.setMedicalTreatment1Title(medicalTreatmentDto.getMedicalTreatment1Title());
        /** 診療項目2_タイトル */
        memberConfirmUpdateModel.setMedicalTreatment2Title(medicalTreatmentDto.getMedicalTreatment2Title());
        /** 診療項目3_タイトル */
        memberConfirmUpdateModel.setMedicalTreatment3Title(medicalTreatmentDto.getMedicalTreatment3Title());
        /** 診療項目4_タイトル */
        memberConfirmUpdateModel.setMedicalTreatment4Title(medicalTreatmentDto.getMedicalTreatment4Title());
        /** 診療項目5_タイトル */
        memberConfirmUpdateModel.setMedicalTreatment5Title(medicalTreatmentDto.getMedicalTreatment5Title());
        /** 診療項目6_タイトル */
        memberConfirmUpdateModel.setMedicalTreatment6Title(medicalTreatmentDto.getMedicalTreatment6Title());
        /** 診療項目7_タイトル */
        memberConfirmUpdateModel.setMedicalTreatment7Title(medicalTreatmentDto.getMedicalTreatment7Title());
        /** 診療項目8_タイトル */
        memberConfirmUpdateModel.setMedicalTreatment8Title(medicalTreatmentDto.getMedicalTreatment8Title());
        /** 診療項目9_タイトル */
        memberConfirmUpdateModel.setMedicalTreatment9Title(medicalTreatmentDto.getMedicalTreatment9Title());
        /** 診療項目10_タイトル */
        memberConfirmUpdateModel.setMedicalTreatment10Title(medicalTreatmentDto.getMedicalTreatment10Title());

        /** 診療項目1_表示判定 */
        memberConfirmUpdateModel.setMedicalTreatment1Disp(medicalTreatmentDto.getMedicalTreatment1Disp());
        /** 診療項目2_表示判定 */
        memberConfirmUpdateModel.setMedicalTreatment2Disp(medicalTreatmentDto.getMedicalTreatment2Disp());
        /** 診療項目3_表示判定 */
        memberConfirmUpdateModel.setMedicalTreatment3Disp(medicalTreatmentDto.getMedicalTreatment3Disp());
        /** 診療項目4_表示判定 */
        memberConfirmUpdateModel.setMedicalTreatment4Disp(medicalTreatmentDto.getMedicalTreatment4Disp());
        /** 診療項目5_表示判定 */
        memberConfirmUpdateModel.setMedicalTreatment5Disp(medicalTreatmentDto.getMedicalTreatment5Disp());
        /** 診療項目6_表示判定 */
        memberConfirmUpdateModel.setMedicalTreatment6Disp(medicalTreatmentDto.getMedicalTreatment6Disp());
        /** 診療項目7_表示判定 */
        memberConfirmUpdateModel.setMedicalTreatment7Disp(medicalTreatmentDto.getMedicalTreatment7Disp());
        /** 診療項目8_表示判定 */
        memberConfirmUpdateModel.setMedicalTreatment8Disp(medicalTreatmentDto.getMedicalTreatment8Disp());
        /** 診療項目9_表示判定 */
        memberConfirmUpdateModel.setMedicalTreatment9Disp(medicalTreatmentDto.getMedicalTreatment9Disp());
        /** 診療項目10_表示判定 */
        memberConfirmUpdateModel.setMedicalTreatment10Disp(medicalTreatmentDto.getMedicalTreatment10Disp());

        memberConfirmUpdateModel.setMedicalTreatment(memberInfoEntity.getMedicalTreatmentFlag());

        String[] medicalTreatment =
                        String.format("%-10s", memberInfoEntity.getMedicalTreatmentFlag()).replace(" ", "0").split("");
        for (int i = 1; i <= medicalTreatment.length; i++) {
            if (HTypeMedicalTreatmentFlag.ON.getValue().equals(medicalTreatment[i - 1])) {
                switch (i) {
                    case 1:
                        memberConfirmUpdateModel.setMedicalTreatment1(true);
                        continue;
                    case 2:
                        memberConfirmUpdateModel.setMedicalTreatment2(true);
                        continue;
                    case 3:
                        memberConfirmUpdateModel.setMedicalTreatment3(true);
                        continue;
                    case 4:
                        memberConfirmUpdateModel.setMedicalTreatment4(true);
                        continue;
                    case 5:
                        memberConfirmUpdateModel.setMedicalTreatment5(true);
                        continue;
                    case 6:
                        memberConfirmUpdateModel.setMedicalTreatment6(true);
                        continue;
                    case 7:
                        memberConfirmUpdateModel.setMedicalTreatment7(true);
                        continue;
                    case 8:
                        memberConfirmUpdateModel.setMedicalTreatment8(true);
                        continue;
                    case 9:
                        memberConfirmUpdateModel.setMedicalTreatment9(true);
                        continue;
                    case 10:
                        memberConfirmUpdateModel.setMedicalTreatment10(true);
                        continue;
                    default:
                        break;
                }
            } else {
                switch (i) {
                    case 1:
                        memberConfirmUpdateModel.setMedicalTreatment1(false);
                        continue;
                    case 2:
                        memberConfirmUpdateModel.setMedicalTreatment2(false);
                        continue;
                    case 3:
                        memberConfirmUpdateModel.setMedicalTreatment3(false);
                        continue;
                    case 4:
                        memberConfirmUpdateModel.setMedicalTreatment4(false);
                        continue;
                    case 5:
                        memberConfirmUpdateModel.setMedicalTreatment5(false);
                        continue;
                    case 6:
                        memberConfirmUpdateModel.setMedicalTreatment6(false);
                        continue;
                    case 7:
                        memberConfirmUpdateModel.setMedicalTreatment7(false);
                        continue;
                    case 8:
                        memberConfirmUpdateModel.setMedicalTreatment8(false);
                        continue;
                    case 9:
                        memberConfirmUpdateModel.setMedicalTreatment9(false);
                        continue;
                    case 10:
                        memberConfirmUpdateModel.setMedicalTreatment10(false);
                        continue;
                    default:
                        break;
                }
            }
        }
    }
    // 2023-renew AddNo2 to here

    /**
     * ペイジェントから取得したカード情報をページに反映する
     *
     * @param cardInfo           登録済みカード情報
     * @param memberConfirmModel アドレス帳画面 Model
     */
    protected void setRegistedPaygentCardInfo(ComResultDto cardInfo, MemberConfirmModel memberConfirmModel) {

        List<MemberRegistedCardItem> registedCardItemList = new ArrayList<>();
        // 登録済みのカード情報複数件表示
        for (Map<String, String> registCardInfo : cardInfo.getResultMapList()) {

            MemberRegistedCardItem registedCardItem = ApplicationContextUtility.getBean(MemberRegistedCardItem.class);

            // 顧客カードID
            registedCardItem.setCardId(registCardInfo.get(ComResultDto.KEY_CUSTOMER_CARD_ID));

            // カード番号
            registedCardItem.setCardNumber(registCardInfo.get(ComResultDto.KEY_CARD_NUMBER));
            String cardValidTerm = registCardInfo.get(ComResultDto.KEY_CARD_VALID_TERM);
            // 有効期限：月
            registedCardItem.setExpirationDateMonth(cardValidTerm.substring(0, 2));

            // 有効期限：年(現在年上二桁 + paygentから取得した値 )
            registedCardItem.setExpirationDateYear(
                            dateUtility.getCurrentY().substring(0, 2) + cardValidTerm.substring(2));

            // カード会社
            registedCardItem.setCardBrand(registCardInfo.get(ComResultDto.KEY_CARD_BRAND));

            registedCardItemList.add(registedCardItem);
        }
        // カード情報リストをページにセット
        memberConfirmModel.setRegistedCardItems(registedCardItemList);
        // カード情報が取得できた場合
        if (!CollectionUtil.isEmpty(registedCardItemList)) {
            // カード情報存在フラグON
            memberConfirmModel.setRegistedCardFlag(true);

        }
    }

    /**
     * 会員クラスに反映
     *
     * @param res 会員Entityレスポンス
     * @return 会員クラス
     */
    public MemberInfoEntity toMemberInfoEntity(MemberInfoEntityResponse res) {
        if (ObjectUtils.isEmpty(res)) {
            return null;
        }

        MemberInfoEntity response = new MemberInfoEntity();
        response.setMemberInfoSeq(res.getMemberInfoSeq());
        response.setMemberInfoStatus(
                        EnumTypeUtil.getEnumFromValue(HTypeMemberInfoStatus.class, res.getMemberInfoStatus()));
        response.setMemberInfoUniqueId(res.getMemberInfoUniqueId());
        response.setMemberInfoId(res.getMemberInfoId());
        response.setMemberInfoPassword(res.getMemberInfoPassword());
        response.setMemberInfoLastName(res.getMemberInfoLastName());
        response.setMemberInfoFirstName(res.getMemberInfoFirstName());
        response.setMemberInfoLastKana(res.getMemberInfoLastKana());
        response.setMemberInfoFirstKana(res.getMemberInfoFirstKana());
        response.setMemberInfoSex(
                        EnumTypeUtil.getEnumFromValue(HTypeSexUnnecessaryAnswer.class, res.getMemberInfoSex()));
        response.setMemberInfoBirthday(conversionUtility.toTimeStamp(res.getMemberInfoBirthday()));
        response.setMemberInfoTel(res.getMemberInfoTel());
        response.setMemberInfoContactTel(res.getMemberInfoContactTel());
        response.setMemberInfoZipCode(res.getMemberInfoZipCode());
        response.setMemberInfoPrefecture(res.getMemberInfoPrefecture());
        response.setPrefectureType(EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class, res.getPrefectureType()));
        response.setMemberInfoAddress1(res.getMemberInfoAddress1());
        response.setMemberInfoAddress2(res.getMemberInfoAddress2());
        response.setMemberInfoAddress3(res.getMemberInfoAddress3());
        response.setMemberInfoMail(res.getMemberInfoMail());
        response.setShopSeq(1001);
        response.setAccessUid(res.getAccessUid());
        response.setVersionNo(res.getVersionNo());
        response.setAdmissionYmd(res.getAdmissionYmd());
        response.setSecessionYmd(res.getSecessionYmd());
        response.setMemo(res.getMemo());
        response.setMemberInfoFax(res.getMemberInfoFax());
        response.setLastLoginTime(conversionUtility.toTimeStamp(res.getLastLoginTime()));
        response.setLastLoginUserAgent(res.getLastLoginUserAgent());
        response.setPaymentMemberId(res.getPaymentMemberId());
        response.setPaymentCardRegistType(
                        EnumTypeUtil.getEnumFromValue(HTypeCardRegistType.class, res.getPaymentCardRegistType()));
        response.setPasswordNeedChangeFlag(EnumTypeUtil.getEnumFromValue(HTypePasswordNeedChangeFlag.class,
                                                                         res.getPasswordNeedChangeFlag()
                                                                        ));
        response.setLastLoginDeviceType(
                        EnumTypeUtil.getEnumFromValue(HTypeDeviceType.class, res.getLastLoginDeviceType()));
        response.setLoginFailureCount(res.getLoginFailureCount());
        response.setAccountLockTime(conversionUtility.toTimeStamp(res.getAccountLockTime()));
        response.setRegistTime(conversionUtility.toTimeStamp(res.getRegistTime()));
        response.setUpdateTime(conversionUtility.toTimeStamp(res.getUpdateTime()));
        response.setCustomerNo(res.getCustomerNo());
        response.setRepresentativeName(res.getRepresentativeName());
        response.setMemberInfoAddress4(res.getMemberInfoAddress4());
        response.setMemberInfoAddress5(res.getMemberInfoAddress5());
        response.setBusinessType(EnumTypeUtil.getEnumFromValue(HTypeBusinessType.class, res.getBusinessType()));
        response.setSendFaxPermitFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSendFaxPermitFlag.class, res.getSendFaxPermitFlag()));
        response.setSendDirectMailFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSendDirectMailFlag.class, res.getSendDirectMailFlag()));
        response.setNonConsultationDay(res.getNonConsultationDay());
        response.setApproveStatus(EnumTypeUtil.getEnumFromValue(HTypeApproveStatus.class, res.getApproveStatus()));
        response.setCreditPaymentUseFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeCreditPaymentUseFlag.class, res.getCreditPaymentUseFlag()));
        response.setTransferPaymentUseFlag(EnumTypeUtil.getEnumFromValue(HTypeTransferPaymentUseFlag.class,
                                                                         res.getTransferPaymentUseFlag()
                                                                        ));
        response.setCashDeliveryUseFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeCashDeliveryUseFlag.class, res.getCashDeliveryUseFlag()));
        response.setDirectDebitUseFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeDirectDebitUseFlag.class, res.getDirectDebitUseFlag()));
        response.setMonthlyPayUseFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeMonthlyPayUseFlag.class, res.getMonthlyPayUseFlag()));
        response.setMemberListType(EnumTypeUtil.getEnumFromValue(HTypeMemberListType.class, res.getMemberListType()));
        response.setOnlineRegistFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeOnlineRegistFlag.class, res.getOnlineRegistFlag()));
        response.setConfDocumentType(
                        EnumTypeUtil.getEnumFromValue(HTypeConfDocumentType.class, res.getConfDocumentType()));
        response.setMedicalTreatmentFlag(res.getMedicalTreatmentFlag());
        response.setMedicalTreatmentMemo(res.getMedicalTreatmentMemo());
        response.setMetalPermitFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeMetalPermitFlag.class, res.getMetalPermitFlag()));
        // 2023-renew No79 from here
        response.setOrderCompletePermitFlag(EnumTypeUtil.getEnumFromValue(HTypeOrderCompletePermitFlag.class,
                                                                          res.getOrderCompletePermitFlag()
                                                                         ));
        response.setDeliveryCompletePermitFlag(EnumTypeUtil.getEnumFromValue(HTypeDeliveryCompletePermitFlag.class,
                                                                             res.getDeliveryCompletePermitFlag()
                                                                            ));
        // 2023-renew No79 to here
        response.setAccountingType(EnumTypeUtil.getEnumFromValue(HTypeAccountingType.class, res.getAccountingType()));
        response.setOnlineLoginAdvisability(EnumTypeUtil.getEnumFromValue(HTypeOnlineLoginAdvisability.class,
                                                                          res.getOnlineLoginAdvisability()
                                                                         ));
        response.setSendMailPermitFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSendMailPermitFlag.class, res.getSendMailPermitFlag()));
        response.setSendMailStartTime(conversionUtility.toTimeStamp(res.getSendMailStartTime()));
        response.setSendMailStopTime(conversionUtility.toTimeStamp(res.getSendMailStopTime()));
        response.setNoAntiSocialFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeNoAntiSocialFlag.class, res.getNoAntiSocialFlag()));
        return response;
    }

    // 2023-renew AddNo2 from here
    //    /**
    //     * 会員情報確認画面用会員更新リクエストに反映
    //     *
    //     * @param memberInfoEntity 会員クラス
    //     * @return 会員情報確認画面用会員更新リクエスト
    //     */
    //    public MemberInfoConfirmScreenUpdateRequest toMemberInfoConfirmScreenUpdateRequest(MemberInfoEntity memberInfoEntity) {
    //        if (ObjectUtils.isEmpty(memberInfoEntity)) {
    //            return null;
    //        }
    //
    //        MemberInfoConfirmScreenUpdateRequest request = new MemberInfoConfirmScreenUpdateRequest();
    //
    //        request.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
    //
    //        if (memberInfoEntity.getMetalPermitFlag().equals(HTypeMetalPermitFlag.ON)) {
    //            request.setMetalPermitFlag(true);
    //        } else {
    //            request.setMetalPermitFlag(false);
    //        }
    //
    //        if (memberInfoEntity.getSendMailPermitFlag().equals(HTypeSendMailPermitFlag.ON)) {
    //            request.setMetalPermitFlag(true);
    //        } else {
    //            request.setMetalPermitFlag(false);
    //        }
    //
    //        return request;
    //    }
    // 2023-renew AddNo2 to here

    /**
     * 決済通信結果返却用Dtoに反映
     *
     * @param response カード情報取得レスポンス
     * @return 決済通信結果返却用Dto
     */
    public ComResultDto toComResultDto(CardInfoResponse response) {
        if (ObjectUtils.isEmpty(response)) {
            return null;
        }

        ComResultDto comResultDto = new ComResultDto();
        comResultDto.setResultMap(response.getResultMap());
        comResultDto.setResultStatus(response.getResultStatus());
        comResultDto.setResultMapList(response.getResultMapList());
        comResultDto.setMessageList(checkMessageDtoList(response.getMessageList()));
        comResultDto.setResponseCode(response.getResponseCode());
        comResultDto.setResponseDetail(response.getResponseDetail());

        return comResultDto;
    }

    /**
     * チェックメッセージDtoクラスに反映
     *
     * @param checkMessageDtoResponseList チェックメッセージDtoクラスレスポンス
     * @return チェックメッセージDtoクラス
     */
    public List<CheckMessageDto> checkMessageDtoList(List<CheckMessageDtoResponse> checkMessageDtoResponseList) {
        if (CollectionUtils.isEmpty(checkMessageDtoResponseList)) {
            return new ArrayList<>();
        }
        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();
        for (CheckMessageDtoResponse dto : checkMessageDtoResponseList) {
            CheckMessageDto checkMessageDto = new CheckMessageDto();
            checkMessageDto.setMessageId(dto.getMessageId());
            checkMessageDto.setMessage(dto.getMessage());
            checkMessageDto.setOrderConsecutiveNo(dto.getOrderConsecutiveNo());
            if (dto.getError() != null) {
                checkMessageDto.setError(dto.getError());
            }
            if (Objects.requireNonNull(dto.getArgs()).isEmpty()) {
                checkMessageDto.setArgs(dto.getArgs().toArray());
            }
            checkMessageDtoList.add(checkMessageDto);
        }

        return checkMessageDtoList;
    }

    // 2023-renew AddNo2 from here

    /**
     * 会員更新リクエストに反映
     *
     * @param memberConfirmUpdateModel
     * @return
     */
    public MemberInfoConfirmScreenUpdateRequest toMemberInfoUpdateRequest(MemberConfirmUpdateModel memberConfirmUpdateModel) {
        if (Objects.isNull(memberConfirmUpdateModel))
            return null;
        MemberInfoConfirmScreenUpdateRequest target = new MemberInfoConfirmScreenUpdateRequest();
        target.setMemberInfoSeq(memberConfirmUpdateModel.getMemberInfoDetail().getMemberInfoSeq());
        target.setMemberInfoLastName(memberConfirmUpdateModel.getMemberInfoLastName());
        target.setMemberInfoLastKana(memberConfirmUpdateModel.getMemberInfoLastKana());
        target.setRepresentativeName(memberConfirmUpdateModel.getRepresentativeName());
        target.setMemberInfoTel(memberConfirmUpdateModel.getMemberInfoTel());
        target.setMemberInfoFax(memberConfirmUpdateModel.getMemberInfoFax());
        target.setMemberInfoZipCode(memberConfirmUpdateModel.getMemberInfoZipCode1()
                                    + memberConfirmUpdateModel.getMemberInfoZipCode2());
        target.setMemberInfoAddress1(memberConfirmUpdateModel.getMemberInfoAddress1());
        target.setMemberInfoAddress2(memberConfirmUpdateModel.getMemberInfoAddress2());
        target.setMemberInfoAddress3(memberConfirmUpdateModel.getMemberInfoAddress3());
        target.setNonConsultationDay(memberConfirmUpdateModel.getNonConsultationDay());
        target.setMedicalTreatmentFlag(memberConfirmUpdateModel.getMedicalTreatment());
        target.setMedicalTreatmentMemo(memberConfirmUpdateModel.getMedicalTreatmentMemo());
        target.setSendMailPermitFlag(memberConfirmUpdateModel.getSendMailPermitFlag().getValue());
        target.setMetalPermitFlag(memberConfirmUpdateModel.getMetalPermitFlag().getValue());
        // 2023-renew No79 from here
        target.setOrderCompletePermitFlag(memberConfirmUpdateModel.getOrderCompletePermitFlag().getValue());
        target.setDeliveryCompletePermitFlag(memberConfirmUpdateModel.getDeliveryCompletePermitFlag().getValue());
        // 2023-renew No79 to here
        target.setImageAddCount((int) memberConfirmUpdateModel.getUploadFileModelList()
                                                              .stream()
                                                              .filter(RegistUploadFile::isSavedImage)
                                                              .count());
        target.setModifiedList(memberConfirmUpdateModel.getModifiedList());
        target.setMedicalTreatmentTitleList(List.of(memberConfirmUpdateModel.getMedicalTreatment1Title(),
                                                    memberConfirmUpdateModel.getMedicalTreatment2Title(),
                                                    memberConfirmUpdateModel.getMedicalTreatment3Title(),
                                                    memberConfirmUpdateModel.getMedicalTreatment4Title(),
                                                    memberConfirmUpdateModel.getMedicalTreatment5Title(),
                                                    memberConfirmUpdateModel.getMedicalTreatment6Title(),
                                                    memberConfirmUpdateModel.getMedicalTreatment7Title(),
                                                    memberConfirmUpdateModel.getMedicalTreatment8Title(),
                                                    memberConfirmUpdateModel.getMedicalTreatment9Title(),
                                                    memberConfirmUpdateModel.getMedicalTreatment10Title()
                                                   ));
        return target;
    }

    // 2023-renew AddNo2 to here
    // 2023-renew No22 from here

    /**
     * dtoをモデルに変換する
     * @param memberInfoImageEntityResponseList
     * @return 登録されているアップロードモデルのリスト
     * */
    public List<RegistUploadFile> toRegistUploadFile(List<MemberInfoImageEntityResponse> memberInfoImageEntityResponseList,
                                                     String path) {

        List<RegistUploadFile> registUploadFiles = new ArrayList<>();

        for (MemberInfoImageEntityResponse memberInfoImageEntityResponse : memberInfoImageEntityResponseList) {
            RegistUploadFile registUploadFile = new RegistUploadFile();
            registUploadFile.setFileNo(memberInfoImageEntityResponse.getMemberImageNo());
            registUploadFile.setFileName(memberInfoImageEntityResponse.getMemberImageFileName());

            //元のファイル名を取得する
            String pattern = "(^.*)(_\\d{17})\\.(\\w{3,4})";
            String originName = memberInfoImageEntityResponse.getMemberImageFileName().replaceAll(pattern, "$1\\.$3");
            registUploadFile.setOriginName(originName);
            registUploadFile.setFilePath(path + memberInfoImageEntityResponse.getMemberImageFileName());

            String extension = memberInfoImageEntityResponse.getMemberImageFileName().replaceAll(pattern, "$3");
            if (HTypeMemberImage.PDF.getLabel().equals(extension.toUpperCase())) {
                registUploadFile.setExtensionType(HTypeMemberImage.PDF);
            } else if (HTypeMemberImage.PNG.getLabel().equals(extension.toUpperCase())) {
                registUploadFile.setExtensionType(HTypeMemberImage.PNG);
            }
            registUploadFiles.add(registUploadFile);
        }

        return registUploadFiles;
    }
    // 2023-renew No22 to here
}
// PDR Migrate Customization to here
