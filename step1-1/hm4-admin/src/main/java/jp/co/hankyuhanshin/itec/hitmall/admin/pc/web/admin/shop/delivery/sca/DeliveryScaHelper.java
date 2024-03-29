/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.sca;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliverySpecialChargeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.BigDecimalConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 配送方法設定：特別料金エリア設定検索画面用Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class DeliveryScaHelper {
    /**
     * 検索結果をpageItemsに反映します
     *
     * @param resultList       List&lt;DeliverySpecialChargeAreaResultDto&gt;
     * @param deliveryScaModel DeliveryScaModel
     * @param conditionDto     DeliverySpecialChargeAreaConditionDto
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void convertToIndexPageItemForResult(List<DeliverySpecialChargeAreaResultDto> resultList,
                                                DeliveryScaModel deliveryScaModel,
                                                DeliverySpecialChargeAreaConditionDto conditionDto)
                    throws IllegalAccessException, InvocationTargetException {
        List<DeliveryScaModelItem> deliveryScaModelItems = new ArrayList<>();

        // オフセット+1
        int index = conditionDto.getOffset() + 1;

        if ((resultList != null) && !resultList.isEmpty()) {
            for (DeliverySpecialChargeAreaResultDto resultDto : resultList) {
                DeliveryScaModelItem deliveryScaModelItem =
                                ApplicationContextUtility.getBean(DeliveryScaModelItem.class);
                deliveryScaModelItem.setResultDataIndex(index++);
                BeanUtils.copyProperties(deliveryScaModelItem, resultDto);
                deliveryScaModelItems.add(deliveryScaModelItem);
            }
        }
        deliveryScaModel.setResultItems(deliveryScaModelItems);
    }

    /**
     * 検索結果をIndexPageに反映します
     *
     * @param deliveryScaModel IndexPage
     * @param resultEntity     DeliveryMethodEntity
     */
    public void convertToRegistPageForResult(DeliveryScaModel deliveryScaModel, DeliveryMethodEntity resultEntity) {
        deliveryScaModel.setDeliveryMethodName(resultEntity.getDeliveryMethodName());
        deliveryScaModel.setDeliveryMethodType(resultEntity.getDeliveryMethodType());
        deliveryScaModel.setOpenStatusPC(resultEntity.getOpenStatusPC());
    }

    /**
     * IndexPageから削除対象リストを作成します
     *
     * @param deliveryScaModel IndexPage
     * @return List&lt;DeliverySpecialChargeAreaEntity&gt;
     */
    public List<DeliverySpecialChargeAreaEntity> convertToDeliverySpecialChargeAreaEntityForDelete(DeliveryScaModel deliveryScaModel) {
        List<DeliveryScaModelItem> deliveryScaModelItems = deliveryScaModel.getResultItems();
        List<DeliverySpecialChargeAreaEntity> deleteList = new ArrayList<>();

        if ((deliveryScaModelItems != null) && !deliveryScaModelItems.isEmpty()) {
            DeliverySpecialChargeAreaEntity entity = null;

            for (DeliveryScaModelItem deliveryScaModelItem : deliveryScaModelItems) {
                if (deliveryScaModelItem.isCheck()) {
                    entity = ApplicationContextUtility.getBean(DeliverySpecialChargeAreaEntity.class);
                    entity.setDeliveryMethodSeq(deliveryScaModelItem.getDeliveryMethodSeq());
                    entity.setZipCode(deliveryScaModelItem.getZipcode());
                    deleteList.add(entity);
                }
            }
        }

        return deleteList;
    }

    /**
     * IndexPageを検索条件Dtoに変換します
     *
     * @param conditionDto     DeliverySpecialChargeAreaConditionDto
     * @param deliveryScaModel IndexPage
     */
    public void convertToDeliverySpecialChargeAreaConditionDtoForSearch(DeliverySpecialChargeAreaConditionDto conditionDto,
                                                                        DeliveryScaModel deliveryScaModel) {
        conditionDto.setDeliveryMethodSeq(deliveryScaModel.getDmcd());
        if (StringUtils.isEmpty(deliveryScaModel.getPrefectureName())) {
            conditionDto.setPrefecture("");
        } else {
            conditionDto.setPrefecture(EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                                     deliveryScaModel.getPrefectureName()
                                                                    ).getLabel());
        }
    }

    /**
     * 画面入力値を配送特別料金エンティティに変換します
     *
     * @param entity     DeliverySpecialChargeAreaEntity
     * @param registPage RegistPage
     */
    public void convertToDeliverySpecialChargeAreaEntityForRegist(DeliverySpecialChargeAreaEntity entity,
                                                                  DeliveryScaModel registPage) {

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        entity.setCarriage(BigDecimalConversionUtil.toBigDecimal(registPage.getCarriage()));
        entity.setDeliveryMethodSeq(registPage.getDmcd());
        entity.setZipCode(registPage.getZipCode());
        entity.setRegistTime(dateUtility.getCurrentTime());
        entity.setUpdateTime(dateUtility.getCurrentTime());
    }

    /**
     * 郵便番号住所情報をRegistPage.addressに変換します
     *
     * @param registPage RegistPage
     * @param entityList List&lt;ZipCodeAddressDto&gt;
     */
    public void convertToRegistPageForZipCodeResult(DeliveryScaModel registPage, List<ZipCodeAddressDto> entityList) {

        // 郵便番号に複数の住所が紐づいている場合にはすべての住所を表示する仕様
        final String lineSeparator = "<br />";
        StringBuilder builder = new StringBuilder();
        Iterator<ZipCodeAddressDto> ite = entityList.iterator();
        ZipCodeAddressDto entityDto = ite.next();

        builder.append(entityDto.getPrefectureName());
        builder.append(entityDto.getCityName());
        builder.append(entityDto.getTownName());
        builder.append(entityDto.getNumbers());

        while (ite.hasNext()) {
            entityDto = ite.next();
            builder.append(lineSeparator);
            builder.append(entityDto.getPrefectureName());
            builder.append(entityDto.getCityName());
            builder.append(entityDto.getTownName());
            builder.append(entityDto.getNumbers());
        }

        registPage.setAddress(builder.toString());
    }
}
