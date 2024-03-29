// 2023-renew AddNo2 from here
package jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailTemplateDummyMapUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.NonConsultationDayUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class MemberInfoUpdateTransformHelper implements Transformer {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoUpdateTransformHelper.class);

    /**
     * ダミープレースホルダを返す
     *
     * @return ダミープレースホルダ
     */
    @Override
    public Map<String, String> getDummyPlaceholderMap() {
        // メールテンプレート用ダミー値マップ取得用Helper取得
        MailTemplateDummyMapUtility mailTemplateDummyMapUtility =
                        ApplicationContextUtility.getBean(MailTemplateDummyMapUtility.class);

        return mailTemplateDummyMapUtility.getDummyValueMap(getResourceName());
    }

    /**
     * テンプレートタイプ43の
     * メール送信に使用する値マップを作成する。
     *
     * @param arguments 引数
     * @return 値マップ
     */
    @Override
    public Map<String, String> toValueMap(Object... arguments) {

        // 引数チェック
        this.checkArguments(arguments);
        // メールテンプレート用ダミー値マップ取得用Helper取得
        MailTemplateDummyMapUtility mailTemplateDummyMapUtility =
                        ApplicationContextUtility.getBean(MailTemplateDummyMapUtility.class);

        if (arguments == null) {
            return mailTemplateDummyMapUtility.getDummyValueMap(getResourceName());
        } else if (arguments.length == 0) {
            return new HashMap<>();
        }

        MemberInfoEntity memberInfoAfterChange = (MemberInfoEntity) arguments[0];
        MemberInfoEntity memberInfoBeforeChange = (MemberInfoEntity) arguments[1];
        Integer imageAddCount = (Integer) arguments[2];
        List<String> modifiedList = (List<String>) arguments[3];
        List<String> medicalTreatmentTitleList = (List<String>) arguments[4];

        Map<String, String> valueMap = new LinkedHashMap<>();

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        NonConsultationDayUtility nonConsultationDayUtility =
                        ApplicationContextUtility.getBean(NonConsultationDayUtility.class);
        String[] zipcodeAfterChanges = conversionUtility.toZipCodeArray(memberInfoAfterChange.getMemberInfoZipCode());
        String[] zipcodeBeforeChanges = conversionUtility.toZipCodeArray(memberInfoBeforeChange.getMemberInfoZipCode());

        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);

        // 日付関連Helper取得

        valueMap.put("M_LAST_NAME", memberInfoBeforeChange.getMemberInfoLastName());
        valueMap.put("M_CUSTOMERNO", String.valueOf(memberInfoBeforeChange.getCustomerNo()));

        valueMap.put("M_OFFICE_NAME", memberInfoBeforeChange.getMemberInfoLastKana());
        valueMap.put("M_BUSINESS_NAME", memberInfoBeforeChange.getMemberInfoLastName());
        valueMap.put("M_REPRESENTATIVE_NAME", memberInfoBeforeChange.getRepresentativeName());
        valueMap.put("M_TEL", memberInfoBeforeChange.getMemberInfoTel());
        valueMap.put("M_FAX", memberInfoBeforeChange.getMemberInfoFax());
        valueMap.put("M_ZIP_CODE", zipcodeBeforeChanges[0] + "-" + zipcodeBeforeChanges[1]);
        valueMap.put("M_ADDRESS1", memberInfoBeforeChange.getMemberInfoAddress1());
        valueMap.put("M_ADDRESS2", memberInfoBeforeChange.getMemberInfoAddress2());
        valueMap.put("M_ADDRESS3", memberInfoBeforeChange.getMemberInfoAddress3());
        valueMap.put("M_NON_CONSULTATION_DAY",
                     nonConsultationDayUtility.getNonConsultationDay(memberInfoBeforeChange.getNonConsultationDay())
                    );
        valueMap.put("M_MEDICAL_DEPARTMENT",
                     memberInfoUtility.getMedicalTreatment(memberInfoBeforeChange.getMedicalTreatmentFlag(),
                                                           medicalTreatmentTitleList
                                                          )
                    );
        valueMap.put("M_OTHER_MEDICAL_DEPARTMENT", memberInfoBeforeChange.getMedicalTreatmentMemo());
        valueMap.put("M_SPECIAL_OFFERS_VIA_EMAIL", memberInfoBeforeChange.getSendMailPermitFlag().getLabel());
        valueMap.put("M_METAL_PRODUCT_PRICE_NOTIFICATION_EMAIL",
                     memberInfoBeforeChange.getMetalPermitFlag().getLabel()
                    );
        // 2023-renew No79 from here
        valueMap.put("M_ORDER_COMPLETE_PERMIT", memberInfoBeforeChange.getOrderCompletePermitFlag().getLabel());
        valueMap.put("M_DELIVERY_COMPLETE_PERMIT", memberInfoBeforeChange.getDeliveryCompletePermitFlag().getLabel());
        // 2023-renew No79 to here
        if (imageAddCount > 0) {
            valueMap.put("CONFIRMATION_DOCUMENT_IMAGE_ADD_COUNT", String.valueOf(imageAddCount));
        }

        boolean nonConsultationDayHasChange = this.fieldHasChange("nonConsultationDay", modifiedList);
        valueMap.put("NON_CONSULTATION_DAY_HAS_CHANGE", String.valueOf(nonConsultationDayHasChange));
        if (nonConsultationDayHasChange) {
            valueMap.put(
                            "NON_CONSULTATION_DAY_AFTER_CHANGE", nonConsultationDayUtility.getNonConsultationDay(
                                            memberInfoAfterChange.getNonConsultationDay()));
        }

        boolean medicalTreatmentFlagHasChange = this.fieldHasChange("medicalTreatmentFlag", modifiedList);
        valueMap.put("MEDICAL_DEPARTMENT_HAS_CHANGE", String.valueOf(medicalTreatmentFlagHasChange));
        if (medicalTreatmentFlagHasChange) {
            valueMap.put("MEDICAL_DEPARTMENT_AFTER_CHANGE",
                         memberInfoUtility.getMedicalTreatment(memberInfoAfterChange.getMedicalTreatmentFlag(),
                                                               medicalTreatmentTitleList
                                                              )
                        );
        }

        boolean medicalTreatmentMemoHasChange = this.fieldHasChange("medicalTreatmentMemo", modifiedList);
        valueMap.put("OTHER_MEDICAL_DEPARTMENT_HAS_CHANGE", String.valueOf(medicalTreatmentMemoHasChange));
        if (medicalTreatmentMemoHasChange) {
            valueMap.put("OTHER_MEDICAL_DEPARTMENT_AFTER_CHANGE", memberInfoAfterChange.getMedicalTreatmentMemo());
        }

        boolean sendMailPermitFlagHasChange = this.fieldHasChange("sendMailPermitFlag", modifiedList);
        valueMap.put("SPECIAL_OFFERS_VIA_EMAIL_HAS_CHANGE", String.valueOf(sendMailPermitFlagHasChange));
        if (sendMailPermitFlagHasChange) {
            valueMap.put("SPECIAL_OFFERS_VIA_EMAIL_AFTER_CHANGE",
                         memberInfoAfterChange.getSendMailPermitFlag().getLabel()
                        );
        }

        boolean metalPermitFlagHasChange = this.fieldHasChange("metalPermitFlag", modifiedList);
        valueMap.put("METAL_PRODUCT_PRICE_NOTIFICATION_EMAIL_HAS_CHANGE", String.valueOf(metalPermitFlagHasChange));
        if (metalPermitFlagHasChange) {
            valueMap.put("METAL_PRODUCT_PRICE_NOTIFICATION_EMAIL_AFTER_CHANGE",
                         memberInfoAfterChange.getMetalPermitFlag().getLabel()
                        );
        }

        // 2023-renew No79 from here
        boolean orderCompletePermitFlagHasChange = this.fieldHasChange("orderCompletePermitFlag", modifiedList);
        valueMap.put("ORDER_COMPLETE_PERMIT_HAS_CHANGE", String.valueOf(orderCompletePermitFlagHasChange));
        if (orderCompletePermitFlagHasChange) {
            valueMap.put("ORDER_COMPLETE_PERMIT_AFTER_CHANGE",
                         memberInfoAfterChange.getOrderCompletePermitFlag().getLabel()
                        );
        }

        boolean deliveryCompletePermitFlagHasChange = this.fieldHasChange("deliveryCompletePermitFlag", modifiedList);
        valueMap.put("DELIVERY_COMPLETE_PERMIT_HAS_CHANGE", String.valueOf(deliveryCompletePermitFlagHasChange));
        if (deliveryCompletePermitFlagHasChange) {
            valueMap.put("DELIVERY_COMPLETE_PERMIT_AFTER_CHANGE",
                         memberInfoAfterChange.getDeliveryCompletePermitFlag().getLabel()
                        );
        }
        // 2023-renew No79 to here

        boolean memberInfoLastKanaHasChange = this.fieldHasChange("memberInfoLastKana", modifiedList);
        valueMap.put("OFFICE_NAME_HAS_CHANGE", String.valueOf(memberInfoLastKanaHasChange));
        if (memberInfoLastKanaHasChange) {
            valueMap.put("OFFICE_NAME_AFTER_CHANGE", memberInfoAfterChange.getMemberInfoLastKana());
        }

        boolean memberInfoLastNameHasChange = this.fieldHasChange("memberInfoLastName", modifiedList);
        valueMap.put("BUSINESS_NAME_HAS_CHANGE", String.valueOf(memberInfoLastNameHasChange));
        if (memberInfoLastNameHasChange) {
            valueMap.put("BUSINESS_NAME_AFTER_CHANGE", memberInfoAfterChange.getMemberInfoLastName());
        }

        boolean representativeNameHasChange = this.fieldHasChange("representativeName", modifiedList);
        valueMap.put("REPRESENTATIVE_NAME_HAS_CHANGE", String.valueOf(representativeNameHasChange));
        if (representativeNameHasChange) {
            valueMap.put("REPRESENTATIVE_NAME_AFTER_CHANGE", memberInfoAfterChange.getRepresentativeName());
        }

        boolean memberInfoTelHasChange = this.fieldHasChange("memberInfoTel", modifiedList);
        valueMap.put("TEL_HAS_CHANGE", String.valueOf(memberInfoTelHasChange));
        if (memberInfoTelHasChange) {
            valueMap.put("TEL_AFTER_CHANGE", memberInfoAfterChange.getMemberInfoTel());
        }

        boolean memberInfoFaxHasChange = this.fieldHasChange("memberInfoFax", modifiedList);
        valueMap.put("FAX_HAS_CHANGE", String.valueOf(memberInfoFaxHasChange));
        if (memberInfoFaxHasChange) {
            valueMap.put("FAX_AFTER_CHANGE", memberInfoAfterChange.getMemberInfoFax());
        }

        boolean memberInfoZipCodeHasChange = this.fieldHasChange("memberInfoZipCode", modifiedList);
        valueMap.put("ZIP_CODE_HAS_CHANGE", String.valueOf(memberInfoZipCodeHasChange));
        if (memberInfoZipCodeHasChange) {
            valueMap.put("ZIP_CODE_AFTER_CHANGE", zipcodeAfterChanges[0] + "-" + zipcodeAfterChanges[1]);
        }

        boolean memberInfoAddress1HasChange = this.fieldHasChange("memberInfoAddress1", modifiedList);
        valueMap.put("ADDRESS1_HAS_CHANGE", String.valueOf(memberInfoAddress1HasChange));
        if (memberInfoAddress1HasChange) {
            valueMap.put("ADDRESS1_AFTER_CHANGE", memberInfoAfterChange.getMemberInfoAddress1());
        }

        boolean memberInfoAddress2HasChange = this.fieldHasChange("memberInfoAddress2", modifiedList);
        valueMap.put("ADDRESS2_HAS_CHANGE", String.valueOf(memberInfoAddress2HasChange));
        if (memberInfoAddress2HasChange) {
            valueMap.put("ADDRESS2_AFTER_CHANGE", memberInfoAfterChange.getMemberInfoAddress2());
        }

        boolean memberInfoAddress3HasChange = this.fieldHasChange("memberInfoAddress3", modifiedList);
        valueMap.put("ADDRESS3_HAS_CHANGE", String.valueOf(memberInfoAddress3HasChange));
        if (memberInfoAddress3HasChange) {
            valueMap.put("ADDRESS3_AFTER_CHANGE", memberInfoAfterChange.getMemberInfoAddress3());
        }
        return valueMap;
    }

    /**
     * 引数の有効性を確認する
     *
     * @param arguments 引数
     */
    protected void checkArguments(Object[] arguments) {

        // オブジェクトがない場合はテスト送信用とみなす
        if (arguments == null) {
            return;
        }

        if (arguments.length != 5) {
            RuntimeException e = new IllegalArgumentException(
                            "プレースホルダ用値マップに変換できません：引数の数が合いません。");
            LOGGER.warn("引数チェックエラー", e);
            throw e;
        }

        if (!(arguments[0] instanceof MemberInfoEntity) || !(arguments[1] instanceof MemberInfoEntity)
            || !(arguments[2] instanceof Integer) || !(arguments[3] instanceof List) || !(arguments[4] instanceof List)) {
            RuntimeException e = new IllegalArgumentException(
                            "プレースホルダ用値マップに変換できません：引数の型が合いません。");
            LOGGER.warn("引数チェックエラー", e);
            throw e;
        }
    }

    /**
     * リソースファイル名<br/>
     *
     * @return リソースファイル名
     */
    @Override
    public String getResourceName() {
        return "MemberInfoUpdateTransformHelper";
    }

    public boolean isSendMailAdmin(List<String> modifiedList) {
        List<String> adminLabelFields =
                        List.of("memberInfoLastName", "memberInfoLastKana", "representativeName", "memberInfoTel",
                                "memberInfoFax", "memberInfoZipCode", "memberInfoAddress1", "memberInfoAddress2",
                                "memberInfoAddress3"
                               );
        return adminLabelFields.stream()
                               .anyMatch(adminLabelFieldName -> this.fieldHasChange(adminLabelFieldName, modifiedList));
    }

    private boolean fieldHasChange(String fieldName, List<String> modifiedList) {
        return modifiedList.stream().anyMatch(modifiedField -> modifiedField.contains(fieldName));
    }
}
// 2023-renew AddNo2 to here
