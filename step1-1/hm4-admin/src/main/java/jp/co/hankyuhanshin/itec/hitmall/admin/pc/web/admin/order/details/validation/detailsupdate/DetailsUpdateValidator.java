package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.detailsupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.DetailsUpdateModel;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.OrderReceiverUpdateItem;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDetailsDto;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.util.Map;
import java.util.Objects;

@Data
@Component
public class DetailsUpdateValidator implements SmartValidator {

    /**
     * メッセージID:選択必須チェック
     **/
    private static final String MSGCD_SELECT_REQUIRED = "AOX001202W";

    private static final String MSGCD_SELECT_OUT_OF_RANGE = "ORDER-TIME_ZONE-001";

    /**
     * お届け時間帯
     **/
    private static final String FILED_NAME_RECEIVER = "orderReceiverItem.receiverTimeZone";

    private static final String FILED_NAME_CONVENIENCE = "convenience";

    @Override
    public boolean supports(Class<?> clazz) {
        return DetailsUpdateModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {

        if (Objects.equals(validationHints, null)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!ConfirmGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、ConfirmGroup以外の場合、チェックしない
            return;
        }

        DetailsUpdateModel model = DetailsUpdateModel.class.cast(target);

        errors = checkReceiverTimeZone(model, errors);

        if (model.getSettlementDtoMap() == null || StringUtils.isEmpty(model.getUpdateSettlementMethodSeq())) {
            // 不正操作または決済方法未選択のため、ここではチェックしない
            return;
        } else {
            errors = checkConvenience(model, errors);
        }

        return;

    }

    /**
     * 決済方法がコンビニの場合、対象コンビニが選択されているかチェック
     *
     * @param model  DetailsUpdateModel
     * @param errors Errors
     * @return errors
     */
    private Errors checkConvenience(DetailsUpdateModel model, Errors errors) {
        SettlementDetailsDto settlementDetailsDto =
                        model.getSettlementDtoMap().get(model.getUpdateSettlementMethodSeq()).getSettlementDetailsDto();

        if (settlementDetailsDto.getSettlementMethodType().equals(HTypeSettlementMethodType.CONVENIENCE)) {
            if (StringUtils.isEmpty(model.getConvenience())) {
                errors.rejectValue(FILED_NAME_CONVENIENCE, MSGCD_SELECT_REQUIRED, new String[] {"お支払いを行うコンビニ"}, null);
            }
        }

        return errors;
    }

    /**
     * 「お届け時間帯」が設定されている配送方法の場合、必須チェック
     *
     * @param model  DetailsUpdateModel
     * @param errors Errors
     * @return errors
     */
    private Errors checkReceiverTimeZone(DetailsUpdateModel model, Errors errors) {

        OrderReceiverUpdateItem item = model.getOrderReceiverItem();

        if (StringUtils.isEmpty(item.getUpdateDeliveryMethodSeq())) {
            if (StringUtils.isEmpty(item.getReceiverTimeZone())) {
                errors.rejectValue(FILED_NAME_RECEIVER, MSGCD_SELECT_REQUIRED, new String[] {"お届け時間帯"}, null);
            } else if (!item.getReceiverTimeZoneItems().containsKey(item.getReceiverTimeZone())) {
                item.setReceiverTimeZone(null);
            }
        } else {
            Map<Object, Object> deliveryTimeZoneMap = item.getDeliveryTimeZoneList();
            if (item.getUpdateDeliveryMethodSeq().equals(deliveryTimeZoneMap.get("deliveryMethodSeq").toString())) {
                if (Boolean.parseBoolean(deliveryTimeZoneMap.get("existTimeZone").toString())) {
                    if (StringUtils.isEmpty(item.getReceiverTimeZone())) {
                        errors.rejectValue(FILED_NAME_RECEIVER, MSGCD_SELECT_REQUIRED, new String[] {"お届け時間帯"}, null);
                    } else if (!item.getReceiverTimeZoneItems().containsKey(item.getReceiverTimeZone())) {
                        errors.rejectValue(FILED_NAME_RECEIVER, MSGCD_SELECT_OUT_OF_RANGE);
                    }
                } else {
                    if (!StringUtils.isEmpty(item.getReceiverTimeZone())) {
                        errors.rejectValue(FILED_NAME_RECEIVER, MSGCD_SELECT_OUT_OF_RANGE);
                    }
                }
            }
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
