package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.freearea.registupdate.validation;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.UploadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.freearea.registupdate.FreeareaRegistUpdateModel;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUkFeedInfoSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.FreeAreaViewableMemberCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.validator.HCsvHeaderValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validation;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class FreeareaRegistUpdateValidator implements SmartValidator {

    /**
     * 対象会員リスト
     */
    public static final String FILED_NAME_UPLOAD_FILE = "uploadFile";

    /**
     * CSVヘッダバリデーションNG時メッセージ
     */
    public static final String CSV_HEADER_VALIDATOR_MESSAGE_ID = "HCsvHeaderValidator.INVALID_detail";

    /**
     * エラーコード：必須
     */
    public static final String MSGCD_REQUIRED_INPUT =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HRequiredValidator.INPUT_detail";

    /**
     * スペースを含んでいた場合のエラーメッセージ
     */
    public static final String MSGCD_CONTAINS_SPACE = "PDR-2023RENEW-61-67-95-001-E";

    /**
     * フリーエリアタイトル
     */
    public static final String FILED_NAME_FREEAREA_TITLE = "freeAreaTitle";

    /**
     * UK遷移先URL
     */
    public static final String FILED_NAME_UK_TRANSITION_URL = "ukTransitionUrl";

    /**
     * UK検索キーワード
     */
    public static final String FILED_NAME_UK_SEARCH_KEYWORD = "ukSearchKeyword";

    /**
     * @param target
     * @param errors
     * @param validationHints
     */
    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        if (Objects.equals(validationHints, null)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (UploadGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupがUploadGroupの場合、チェックする
            FreeareaRegistUpdateModel model = (FreeareaRegistUpdateModel) target;

            validate(model.getUploadFile(), errors, FreeAreaViewableMemberCsvDto.class);
        }
        // 2023-renew No36-1, No61,67,95 from here
        if (ConfirmGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupがConfirmGroupの場合、チェックする
            FreeareaRegistUpdateModel model = (FreeareaRegistUpdateModel) target;

            // ユニサーチ連携フラグがONの場合、チェックする
            if (HTypeUkFeedInfoSendFlag.ON.getValue().equals(model.getUkFeedInfoSendFlag())) {
                // タイトルの必須チェック
                if (StringUtils.isEmpty(model.getFreeAreaTitle())) {
                    errors.rejectValue(FILED_NAME_FREEAREA_TITLE, MSGCD_REQUIRED_INPUT, new String[] {"タイトル"}, null);
                }
                // 遷移先URLの必須チェック
                if (StringUtils.isEmpty(model.getUkTransitionUrl())) {
                    errors.rejectValue(FILED_NAME_UK_TRANSITION_URL, MSGCD_REQUIRED_INPUT, new String[] {"遷移先URL"},
                                       null
                                      );
                }
            }

            // 検索キーワードの空白チェック
            String searchKeyword = model.getUkSearchKeyword();
            if (!StringUtils.isEmpty(searchKeyword)) {
                if (Pattern.compile(" ").matcher(searchKeyword).find() || Pattern.compile("　")
                                                                                 .matcher(searchKeyword)
                                                                                 .find()) {
                    errors.rejectValue(FILED_NAME_UK_SEARCH_KEYWORD, MSGCD_CONTAINS_SPACE, new String[] {}, null);
                }
            }
        }
        // 2023-renew No36-1, No61,67,95 to here
    }

    protected void validate(MultipartFile value, Errors errors, Class<?> csvDtoClass) {
        // CSVヘッダバリデータ
        HCsvHeaderValidator validator = Validation.buildDefaultValidatorFactory()
                                                  .getConstraintValidatorFactory()
                                                  .getInstance(HCsvHeaderValidator.class);

        validator.setRestrict(true);
        validator.setCsvDtoClass(csvDtoClass);
        validator.setCsvCharset("MS932");

        boolean result = validator.isValid(value, null);
        if (!result) {
            errors.rejectValue(FILED_NAME_UPLOAD_FILE, CSV_HEADER_VALIDATOR_MESSAGE_ID, new String[] {}, null);
        }
    }

    /**
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FreeareaRegistUpdateModel.class.isAssignableFrom(clazz);
    }

    /**
     * 未使用
     */
    @Override
    public void validate(Object target, Errors errors) {
        // 未使用
    }
}
