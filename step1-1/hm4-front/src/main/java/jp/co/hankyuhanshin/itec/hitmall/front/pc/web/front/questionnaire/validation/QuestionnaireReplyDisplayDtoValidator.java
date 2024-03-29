/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.questionnaire.validation;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReplyValidatePattern;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.questionnaire.QuestionnaireReplyDisplayDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.validation.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.questionnaire.QuestionnaireModel;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 動的バリデータ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
public class QuestionnaireReplyDisplayDtoValidator implements SmartValidator {

    /**
     * メッセージコード：正規表現エラー(制限なし)
     */
    public static final String MSGCD_REGULAR_EXPRESSION_ERR = "PKG34-3552-302-A-";
    /**
     * メッセージコード：正規表現エラー(全角のみ)
     */
    public static final String MSGCD_REGULAR_EXPRESSION_EM_SIZE_ERR = "PKG34-3552-303-A-";
    /**
     * メッセージコード：正規表現エラー(半角英数のみ)
     */
    public static final String MSGCD_REGULAR_EXPRESSION_AN_CHAR_ERR = "PKG34-3552-304-A-";
    /**
     * メッセージコード：正規表現エラー(半角英字のみ)
     */
    public static final String MSGCD_REGULAR_EXPRESSION_A_CHAR_ERR = "PKG34-3552-305-A-";
    /**
     * メッセージコード：正規表現エラー(半角数字のみ)
     */
    public static final String MSGCD_REGULAR_EXPRESSION_N_CHAR_ERR = "PKG34-3552-306-A-";
    /**
     * メッセージコード：アンケートが回答不可（非公開状態/公開終了/削除/存在しないのいずれか）の場合エラー
     */
    public static final String MSGCD_CANNOT_REPLY_QESTIONNAIRE = "PKG34-3552-103-A-";

    public static final String MSGCD_W = "W";

    /**
     * エラーメッセージ：タイトル属性のプレフィックス
     */
    protected static final String ERR_MSG_TITLE_PREFIX = "設問：";

    /**
     * questionnaireReplyDisplayDtoItems フィールド名
     **/
    protected static final String FIELD_NAME_QUESTION_ITEMS = "questionnaireReplyDisplayDtoItems[";

    /**
     * questionTextbox フィールド名
     */
    protected static final String FIELD_NAME_QUESTION_TEXTBOX = "].questionTextBox";

    /**
     * questionTextarea フィールド名
     */
    protected static final String FIELD_NAME_QUESTION_TEXTAREA = "].questionTextArea";

    /**
     * questionRadio フィールド名
     */
    protected static final String FIELD_NAME_QUESTION_RADIO = "].questionRadio";

    /**
     * questionPulldown フィールド名
     */
    protected static final String FIELD_NAME_QUESTION_PULLDOWN = "].questionPullDown";

    /**
     * questionCheckbox フィールド名
     */
    protected static final String FIELD_NAME_QUESTION_CHECKBOX = "].questionCheckBox";

    /**
     * メッセージID:入力必須チェック
     **/
    public static final String MSGCD_REQUIRED_INPUT = "ORDER-PAYMENT-001";

    /**
     * メッセージID:選択必須チェック
     **/
    public static final String MSGCD_REQUIRED_SELECT = "ORDER-PAYMENT-010";

    /**
     * メッセージID:セキュリティコード桁数誤りチェック
     */
    public static final String MSGCD_SECURITY_CODE_LENGTH = "ORDER-PAYMENT-008";

    public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.LengthValidator.MAXIMUM_detail";

    /**
     * 引数で渡ったクラスが、バリデーション対象か否か
     *
     * @param clazz clazz
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> clazz) {
        // QuestionnaireModelがサポート対象
        return QuestionnaireModel.class.isAssignableFrom(clazz);
    }

    /**
     * アンケート回答画面表示用DTOの動的バリデータ
     *
     * @param target          target
     * @param errors          errors
     * @param validationHints validationHints
     */
    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {

        if (ObjectUtils.isEmpty(validationHints)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!ConfirmGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、ConfirmGroup以外の場合、チェックしない
            return;
        }

        List<QuestionnaireReplyDisplayDto> questionnaireReplyDisplayDtoList = null;

        if (target instanceof QuestionnaireModel) {
            QuestionnaireModel questionnaireModel = (QuestionnaireModel) target;
            questionnaireReplyDisplayDtoList = questionnaireModel.getQuestionnaireReplyDisplayDtoItems();
        }

        if (CollectionUtil.isNotEmpty(questionnaireReplyDisplayDtoList)) {
            int questionIndex = 0;
            for (QuestionnaireReplyDisplayDto dto : questionnaireReplyDisplayDtoList) {
                boolean isRequired = dto.isRequired();
                HTypeReplyValidatePattern pattern = dto.getReplyValidatePattern();
                Integer maxLength = dto.getReplyMaxLength();

                if (dto.isTextBox() && StringUtils.isNotEmpty(dto.getQuestionTextBox()) && maxLength != null
                    && dto.getQuestionTextBox().length() > maxLength) {
                    buildErrorMaxLength(errors, questionIndex, FIELD_NAME_QUESTION_TEXTBOX, maxLength);
                }

                if (dto.isTextArea() && StringUtils.isNotEmpty(dto.getQuestionTextArea()) && maxLength != null
                    && dto.getQuestionTextArea().length() > maxLength) {
                    buildErrorMaxLength(errors, questionIndex, FIELD_NAME_QUESTION_TEXTAREA, maxLength);
                }

                if (dto.isTextBox() && isRequired && StringUtils.isEmpty(dto.getQuestionTextBox())) {
                    buildCommonError(errors, questionIndex, FIELD_NAME_QUESTION_TEXTBOX, MSGCD_REQUIRED_INPUT);
                } else {
                    if (dto.isTextBox()) {
                        buildErrorQuestionTextBox(dto, errors, questionIndex, pattern, FIELD_NAME_QUESTION_TEXTBOX);
                    } else if (dto.isTextArea()) {
                        buildErrorQuestionTextBox(dto, errors, questionIndex, pattern, FIELD_NAME_QUESTION_TEXTAREA);
                    }
                }
                if (dto.isTextArea() && isRequired && StringUtils.isEmpty(dto.getQuestionTextArea())) {
                    buildCommonError(errors, questionIndex, FIELD_NAME_QUESTION_TEXTAREA, MSGCD_REQUIRED_INPUT);
                }
                if (dto.isRadio() && isRequired && StringUtils.isEmpty(dto.getQuestionRadio())) {
                    buildCommonError(errors, questionIndex, FIELD_NAME_QUESTION_RADIO, MSGCD_REQUIRED_SELECT);
                }
                if (dto.isPullDown() && isRequired && StringUtils.isEmpty(dto.getQuestionPullDown())) {
                    buildCommonError(errors, questionIndex, FIELD_NAME_QUESTION_PULLDOWN, MSGCD_REQUIRED_SELECT);
                }
                if (dto.isCheckBox() && isRequired && (dto.getQuestionCheckBox() == null
                                                       || dto.getQuestionCheckBox().length == 0)) {
                    buildCommonError(errors, questionIndex, FIELD_NAME_QUESTION_CHECKBOX, MSGCD_REQUIRED_SELECT);
                }

                questionIndex++;
            }
        }
    }

    /**
     * Build common error.
     *
     * @param errors        errors
     * @param questionIndex questionIndex
     * @param fieldName     fieldName
     * @param type          type
     */
    private static void buildCommonError(Errors errors, int questionIndex, String fieldName, String type) {
        errors.rejectValue(FIELD_NAME_QUESTION_ITEMS + questionIndex + fieldName, type);
    }

    /**
     * Build error for maxlength.
     *
     * @param errors        errors
     * @param questionIndex questionIndex
     * @param fieldName     fieldName
     * @param maxLength     maxLength
     */
    private static void buildErrorMaxLength(Errors errors, int questionIndex, String fieldName, Integer maxLength) {
        int displayQuestionNo = questionIndex + 1;
        errors.rejectValue(FIELD_NAME_QUESTION_ITEMS + questionIndex + fieldName, MAXIMUM_MESSAGE_ID,
                           new Object[] {maxLength, displayQuestionNo}, StringUtils.EMPTY
                          );
    }

    /**
     * Build error question text box.
     *
     * @param dto               dto
     * @param errors            errors
     * @param questionIndex     questionIndex
     * @param patternHTypeReply patternHTypeReply
     * @param fieldName         fieldName
     */
    private static void buildErrorQuestionTextBox(QuestionnaireReplyDisplayDto dto,
                                                  Errors errors,
                                                  int questionIndex,
                                                  HTypeReplyValidatePattern patternHTypeReply,
                                                  String fieldName) {

        int displayQuestionNo = questionIndex + 1;

        if (!StringUtils.isEmpty(dto.getQuestionTextBox())) {
            String regex = PropertiesUtil.getSystemPropertiesValue("validatePattern" + patternHTypeReply.getValue());
            Pattern pattern = Pattern.compile(regex);
            if (HTypeReplyValidatePattern.NO_LIMIT.equals(patternHTypeReply) && !pattern.matcher(
                            dto.getQuestionTextBox()).matches()) {
                errors.rejectValue(FIELD_NAME_QUESTION_ITEMS + questionIndex + fieldName,
                                   MSGCD_REGULAR_EXPRESSION_ERR + MSGCD_W, new Object[] {displayQuestionNo},
                                   StringUtils.EMPTY
                                  );
            } else if (HTypeReplyValidatePattern.ONLY_EM_SIZE.equals(patternHTypeReply) && !pattern.matcher(
                            dto.getQuestionTextBox()).matches()) {
                errors.rejectValue(FIELD_NAME_QUESTION_ITEMS + questionIndex + fieldName,
                                   MSGCD_REGULAR_EXPRESSION_EM_SIZE_ERR + MSGCD_W, new Object[] {displayQuestionNo},
                                   StringUtils.EMPTY
                                  );
            } else if (HTypeReplyValidatePattern.ONLY_AN_CHAR.equals(patternHTypeReply) && !pattern.matcher(
                            dto.getQuestionTextBox()).matches()) {
                errors.rejectValue(FIELD_NAME_QUESTION_ITEMS + questionIndex + fieldName,
                                   MSGCD_REGULAR_EXPRESSION_AN_CHAR_ERR + MSGCD_W, new Object[] {displayQuestionNo},
                                   StringUtils.EMPTY
                                  );
            } else if (HTypeReplyValidatePattern.ONLY_A_CHAR.equals(patternHTypeReply) && !pattern.matcher(
                            dto.getQuestionTextBox()).matches()) {
                errors.rejectValue(FIELD_NAME_QUESTION_ITEMS + questionIndex + fieldName,
                                   MSGCD_REGULAR_EXPRESSION_A_CHAR_ERR + MSGCD_W, new Object[] {displayQuestionNo},
                                   StringUtils.EMPTY
                                  );
            } else if (HTypeReplyValidatePattern.ONLY_N_CHAR.equals(patternHTypeReply) && !pattern.matcher(
                            dto.getQuestionTextBox()).matches()) {
                errors.rejectValue(FIELD_NAME_QUESTION_ITEMS + questionIndex + fieldName,
                                   MSGCD_REGULAR_EXPRESSION_N_CHAR_ERR + MSGCD_W, new Object[] {displayQuestionNo},
                                   StringUtils.EMPTY
                                  );
            }
        }

    }

    /**
     * 未使用
     */
    @Override
    public void validate(Object target, Errors errors) {
        // 未使用
    }
}
