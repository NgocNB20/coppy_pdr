package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.goodssearch;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.GoodsSearchModel;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.goodssearch.group.GoodsSearchGroup;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.util.Objects;

@Data
@Component
public class SelectRegitOrUpdateValidator implements SmartValidator {

    /**
     * メッセージID:入力必須チェック
     **/
    private static final String MSGCD_SELECT_REQUIRED = "AOX001202W";

    /**
     * 登録日／更新日選択フラグ
     **/
    protected static final String FILED_NAME_SELECT = "selectRegitOrUpdate";

    @Override
    public boolean supports(Class<?> clazz) {
        return GoodsSearchModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {

        if (Objects.equals(validationHints, null)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!GoodsSearchGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、GoodsSearchGroup以外の場合、チェックしない
            return;
        }

        GoodsSearchModel model = GoodsSearchModel.class.cast(target);

        String valueFrom = model.getSearchRegOrUpTimeFrom();
        String valueTo = model.getSearchRegOrUpTimeTo();

        if (!StringUtils.isEmpty(valueFrom) || !StringUtils.isEmpty(valueTo)) {
            errors = checkSelectRegitOrUpdate(model, errors);
        }

        return;
    }

    /**
     * クーポンID or クーポンコードが選択されているかチェック
     *
     * @param model  GoodsSearchModel
     * @param errors Errors
     * @return errors
     */
    public Errors checkSelectRegitOrUpdate(GoodsSearchModel model, Errors errors) {
        // 必須
        if (StringUtils.isEmpty(model.getSelectRegitOrUpdate())) {
            errors.rejectValue(FILED_NAME_SELECT, MSGCD_SELECT_REQUIRED, new String[] {"更新／登録日"}, null);
        }
        return errors;
    }

    /**
     * 未使用
     */
    @Override
    public void validate(Object target, Errors errors) {
        // 未使用
    }
}
