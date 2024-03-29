package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.validation;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.GoodsModel;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.util.Objects;

@Data
@Component
public class GoodsValidator implements SmartValidator {

    /**
     * エラーコード：必須
     */
    public static final String MSGCD_REQUIRED = "HRequiredValidator.REQUIRED_detail";
    public static final String MSGCD_NOT_EMPTY = "javax.validation.constraints.NotEmpty.message";
    public static final String MSGCD_NOT_EMPTY_BLANK = "REQUIRED-EMPTY-MSG";

    /**
     * 登録日／更新日選択フラグ
     */
    public static final String FILED_NAME_PERIOD_TYPE = "selectRegistOrUpdate";
    public static final String FILED_NAME_DATE_FROM = "searchRegOrUpTimeFrom";
    public static final String FILED_NAME_DATE_TO = "searchRegOrUpTimeTo";

    @Override
    public boolean supports(Class<?> clazz) {
        return GoodsModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        if (Objects.equals(validationHints, null)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!SearchGroup.class.equals(validationHints[0]) && !AllDownloadGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、SearchGroupとAllDownloadGroup以外の場合、チェックしない
            return;
        }

        GoodsModel model = (GoodsModel) target;

        // 更新／登録日バリデータ
        if (!StringUtils.isEmpty(model.getSearchRegOrUpTimeFrom()) || !StringUtils.isEmpty(
                        model.getSearchRegOrUpTimeTo())) {
            if (StringUtils.isEmpty(model.getSelectRegistOrUpdate())) {
                errors.rejectValue(FILED_NAME_PERIOD_TYPE, MSGCD_REQUIRED);
            }
        }
        if ("0".equals(model.getSelectRegistOrUpdate()) || "1".equals(model.getSelectRegistOrUpdate())) {
            if (StringUtils.isEmpty(model.getSearchRegOrUpTimeFrom()) && StringUtils.isEmpty(
                            model.getSearchRegOrUpTimeTo())) {
                // 登録日or更新日のラジオボタンが選択されているが、期間が設定されていない場合はラジオボタンをクリアする ※チェックはしない
                model.setSelectRegistOrUpdate(null);
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
