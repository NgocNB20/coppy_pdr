/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.holiday;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.HolidaySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.HolidayEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 休日検索Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class DeliveryHolidayHelper {

    /**
     * 検索条件生成<br/>
     *
     * @param deliveryHolidayModel ページ
     * @return 検索条件Dto
     */
    public HolidaySearchForDaoConditionDto toConditionDto(DeliveryHolidayModel deliveryHolidayModel) {
        // 検索条件Dto取得
        HolidaySearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(HolidaySearchForDaoConditionDto.class);

        conditionDto.setYear(deliveryHolidayModel.getYear());
        conditionDto.setDeliveryMethodSeq(deliveryHolidayModel.getDmcd());

        return conditionDto;
    }

    /**
     * 検索条件設定<br/>
     *
     * @param conditionDto         検索条件Dto
     * @param deliveryHolidayModel ページ
     */
    public void toPageForLoad(HolidaySearchForDaoConditionDto conditionDto, DeliveryHolidayModel deliveryHolidayModel) {
        deliveryHolidayModel.setYear(conditionDto.getYear());
        deliveryHolidayModel.setDmcd(conditionDto.getDeliveryMethodSeq());
    }

    /**
     * 検索結果生成<br/>
     *
     * @param list                 リスト
     * @param deliveryHolidayModel ページ
     */
    public void toPageIndex(List<HolidayEntity> list, DeliveryHolidayModel deliveryHolidayModel) {

        List<DeliveryHolidayModelItem> resultItemList = new ArrayList<>();

        for (HolidayEntity holidayEntity : list) {
            DeliveryHolidayModelItem indexPageItem = ApplicationContextUtility.getBean(DeliveryHolidayModelItem.class);
            indexPageItem.setDate(holidayEntity.getDate());
            indexPageItem.setName(holidayEntity.getName());

            resultItemList.add(indexPageItem);
        }
        deliveryHolidayModel.setResultItems(resultItemList);
    }

    /**
     * Pageの値をエンティティへコピーします
     *
     * @param deliveryHolidayModel ページ
     * @return entity HolidayEntity 休日エンティティ
     * @throws ParseException
     */
    public HolidayEntity toHolidayEntityForRegistUpdate(DeliveryHolidayModel deliveryHolidayModel)
                    throws ParseException {

        HolidayEntity entity = ApplicationContextUtility.getBean(HolidayEntity.class);

        entity.setDate(DateUtils.parseDate(deliveryHolidayModel.getInputDate(), new String[] {"yyyy/MM/dd"}));

        /** 年 */
        // 入力値から年を得るため、Date型をCalendar型に変換
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(DateUtils.parseDate(deliveryHolidayModel.getInputDate(), new String[] {"yyyy/MM/dd"}));
        entity.setYear(calendar.get(Calendar.YEAR));

        /** 名前 */
        entity.setName(deliveryHolidayModel.getInputName());

        /** 配送方法SEQ */
        entity.setDeliveryMethodSeq(deliveryHolidayModel.getDmcd());

        return entity;

    }

    /**
     * 検索結果をDeliveryHolidayModelに反映します
     *
     * @param deliveryHolidayModel DeliveryHolidayModel
     * @param resultEntity         DeliveryMethodEntity
     */
    public void convertToRegistPageForResult(DeliveryHolidayModel deliveryHolidayModel,
                                             DeliveryMethodEntity resultEntity) {
        deliveryHolidayModel.setDeliveryMethodName(resultEntity.getDeliveryMethodName());
        deliveryHolidayModel.setDeliveryMethodType(resultEntity.getDeliveryMethodType());
        deliveryHolidayModel.setOpenStatusMB(resultEntity.getOpenStatusMB());
        deliveryHolidayModel.setOpenStatusPC(resultEntity.getOpenStatusPC());
    }

}
