/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.did;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleDaySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleDayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * お届け不可日検索Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class DeliveryDidHelper {

    /**
     * 検索条件生成<br/>
     *
     * @param deliveryDidModel ページ
     * @return 検索条件Dto
     */
    public DeliveryImpossibleDaySearchForDaoConditionDto toConditionDto(DeliveryDidModel deliveryDidModel) {
        // 検索条件Dto取得
        DeliveryImpossibleDaySearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(DeliveryImpossibleDaySearchForDaoConditionDto.class);

        conditionDto.setYear(deliveryDidModel.getYear());
        conditionDto.setDeliveryMethodSeq(deliveryDidModel.getDmcd());

        return conditionDto;
    }

    /**
     * 検索条件設定<br/>
     *
     * @param conditionDto     検索条件Dto
     * @param deliveryDidModel ページ
     */
    public void toPageForLoad(DeliveryImpossibleDaySearchForDaoConditionDto conditionDto,
                              DeliveryDidModel deliveryDidModel) {
        deliveryDidModel.setYear(conditionDto.getYear());
        deliveryDidModel.setDmcd(conditionDto.getDeliveryMethodSeq());
    }

    /**
     * 検索結果生成<br/>
     *
     * @param list             リスト
     * @param deliveryDidModel ページ
     */
    public void toPageIndex(List<DeliveryImpossibleDayEntity> list, DeliveryDidModel deliveryDidModel) {

        List<DeliveryDidModelItem> resultItemList = new ArrayList<>();

        for (DeliveryImpossibleDayEntity deliveryImpossibleDayEntity : list) {
            DeliveryDidModelItem deliveryDidModelItem = ApplicationContextUtility.getBean(DeliveryDidModelItem.class);
            deliveryDidModelItem.setDate(deliveryImpossibleDayEntity.getDate());
            deliveryDidModelItem.setReason(deliveryImpossibleDayEntity.getReason());

            resultItemList.add(deliveryDidModelItem);
        }

        deliveryDidModel.setResultItems(resultItemList);
    }

    /**
     * Pageの値をエンティティへコピーします
     *
     * @param deliveryDidModel ページ
     * @return entity DeliveryImpossibleDayEntity お届け不可日エンティティ
     * @throws ParseException
     */
    public DeliveryImpossibleDayEntity toDeliveryImpossibleDayEntityForRegistUpdate(DeliveryDidModel deliveryDidModel)
                    throws ParseException {

        DeliveryImpossibleDayEntity entity = ApplicationContextUtility.getBean(DeliveryImpossibleDayEntity.class);

        entity.setDate(DateUtils.parseDate(deliveryDidModel.getInputDate(), new String[] {"yyyy/MM/dd"}));

        /** 年 */
        // 入力値から年を得るため、Date型をCalendar型に変換
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(DateUtils.parseDate(deliveryDidModel.getInputDate(), new String[] {"yyyy/MM/dd"}));
        entity.setYear(calendar.get(Calendar.YEAR));

        /** 名前 */
        entity.setReason(deliveryDidModel.getInputReason());

        /** 配送方法SEQ */
        entity.setDeliveryMethodSeq(deliveryDidModel.getDmcd());

        return entity;

    }

    /**
     * 検索結果をIndexPageに反映します
     *
     * @param deliveryDidModel IndexPage
     * @param resultEntity     DeliveryMethodEntity
     */
    public void convertToRegistPageForResult(DeliveryDidModel deliveryDidModel, DeliveryMethodEntity resultEntity) {
        deliveryDidModel.setDeliveryMethodName(resultEntity.getDeliveryMethodName());
        deliveryDidModel.setDeliveryMethodType(resultEntity.getDeliveryMethodType());
        deliveryDidModel.setOpenStatusMB(resultEntity.getOpenStatusMB());
        deliveryDidModel.setOpenStatusPC(resultEntity.getOpenStatusPC());
    }

}
