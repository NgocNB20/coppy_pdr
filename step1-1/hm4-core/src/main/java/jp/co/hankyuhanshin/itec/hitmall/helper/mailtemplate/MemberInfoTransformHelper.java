package jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.crypto.AESHelper;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailTemplateDummyMapUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class MemberInfoTransformHelper implements Transformer {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoTransformHelper.class);

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
     * テンプレートタイプ00の
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

        MemberInfoEntity info = (MemberInfoEntity) arguments[0];

        Map<String, String> valueMap = new LinkedHashMap<>();

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        valueMap.put("M_STATUS", EnumTypeUtil.getValue(info.getMemberInfoStatus()));
        valueMap.put("M_ID", info.getMemberInfoId());
        valueMap.put("M_LOGIN_TIME", dateUtility.format(info.getLastLoginTime(), "yyyy年M月d日H時m分"));
        valueMap.put("M_LAST_NAME", info.getMemberInfoLastName());
        valueMap.put("M_FIRST_NAME", info.getMemberInfoFirstName());
        valueMap.put("M_LAST_KANA", info.getMemberInfoLastKana());
        valueMap.put("M_FIRST_KANA", info.getMemberInfoFirstKana());
        valueMap.put("M_GENDER", EnumTypeUtil.getValue(info.getMemberInfoSex()));
        valueMap.put("M_BIRTHDAY", dateUtility.format(info.getMemberInfoBirthday(), "yyyy年M月d日"));
        valueMap.put("M_TEL", info.getMemberInfoTel());
        valueMap.put("M_CONTACT_TEL", info.getMemberInfoContactTel());
        valueMap.put("M_ZIP_CODE", info.getMemberInfoZipCode());
        String[] zipcode = conversionUtility.toZipCodeArray(info.getMemberInfoZipCode());
        valueMap.put("M_ZIP_CODE1", zipcode[0]);
        valueMap.put("M_ZIP_CODE2", zipcode[1]);
        valueMap.put("M_PREFECTURE", info.getMemberInfoPrefecture());
        valueMap.put("M_ADDRESS1", info.getMemberInfoAddress1());
        valueMap.put("M_ADDRESS2", info.getMemberInfoAddress2());
        valueMap.put("M_ADDRESS3", info.getMemberInfoAddress3());
        valueMap.put("M_MAIL", info.getMemberInfoMail());
        valueMap.put("M_ACCESS_UID", info.getAccessUid());
        valueMap.put("M_ADMISSION_YMD", dateUtility.getYmdFormatValue(info.getAdmissionYmd(), "yyyy年M月d日"));
        valueMap.put("M_SECESSION_YMD", dateUtility.getYmdFormatValue(info.getSecessionYmd(), "yyyy年M月d日"));
        valueMap.put("M_REGIST_TIME", dateUtility.format(info.getRegistTime(), "yyyy年M月d日H時m分"));
        valueMap.put("M_UPDATE_TIME", dateUtility.format(info.getUpdateTime(), "yyyy年M月d日H時m分"));
        valueMap.put("M_MAIL_UPDATE_YMD", dateUtility.format(info.getUpdateTime(), "yyyy年M月d日"));
        valueMap.put("M_FAX", info.getMemberInfoFax());

        // PDR Migrate Customization from here
        String customerNo = null;
        String memberInfoPassword = null;

        if (info.getCustomerNo() != null) {
            customerNo = info.getCustomerNo().toString();
        }
        valueMap.put("M_CUSTOMERNO", customerNo);

        if (StringUtils.isNotEmpty(info.getMemberInfoPassword())) {
            String keyString = PropertiesUtil.getSystemPropertiesValue("memberPassEncryptKey");
            AESHelper aesHelper = ApplicationContextUtility.getBean(AESHelper.class);
            memberInfoPassword = aesHelper.decrypt(info.getMemberInfoPassword(), keyString);
        }
        valueMap.put("M_PASSWORD", memberInfoPassword);
        // PDR Migrate Customization to here

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

        if (arguments.length != 1) {
            RuntimeException e = new IllegalArgumentException("プレースホルダ用値マップに変換できません：引数の数が合いません。");
            LOGGER.warn("引数チェックエラー", e);
            throw e;
        }

        if (!(arguments[0] instanceof MemberInfoEntity)) {
            RuntimeException e = new IllegalArgumentException("プレースホルダ用値マップに変換できません：引数の型が合いません。");
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
        return "MemberInfoTransformHelper";
    }
}
