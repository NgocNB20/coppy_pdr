/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.dia;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.ZipCodeAddressDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleAreaResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
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
 * 配送方法設定：特別料金エリア設定検索画面用Dxo
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class DeliveryDiaHelper {

    /**
     * 検索結果をdeliveryDiaModelItemsに反映します
     *
     * @param resultList       List&lt;DeliveryImpossibleAreaResultDto&gt;
     * @param deliveryDiaModel DeliveryDiaModel
     * @param conditionDto     DeliveryImpossibleAreaConditionDto
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void convertToIndexPageItemForResult(List<DeliveryImpossibleAreaResultDto> resultList,
                                                DeliveryDiaModel deliveryDiaModel,
                                                DeliveryImpossibleAreaConditionDto conditionDto)
                    throws IllegalAccessException, InvocationTargetException {

        List<DeliveryDiaModelItem> deliveryDiaModelItems = new ArrayList<>();

        // オフセット+1
        int index = conditionDto.getOffset() + 1;

        if ((resultList != null) && !resultList.isEmpty()) {
            for (DeliveryImpossibleAreaResultDto resultDto : resultList) {
                DeliveryDiaModelItem pageItem = ApplicationContextUtility.getBean(DeliveryDiaModelItem.class);
                pageItem.setResultDataIndex(index++);
                BeanUtils.copyProperties(pageItem, resultDto);
                deliveryDiaModelItems.add(pageItem);
            }
        }
        deliveryDiaModel.setResultItems(deliveryDiaModelItems);
    }

    /**
     * 検索結果をdeliveryDiaModelに反映します
     *
     * @param deliveryDiaModel deliveryDiaModel
     * @param resultEntity     DeliveryMethodEntity
     */
    public void convertToRegistPageForResult(DeliveryDiaModel deliveryDiaModel, DeliveryMethodEntity resultEntity) {
        deliveryDiaModel.setDeliveryMethodName(resultEntity.getDeliveryMethodName());
        deliveryDiaModel.setDeliveryMethodType(resultEntity.getDeliveryMethodType());
        deliveryDiaModel.setOpenStatusPC(resultEntity.getOpenStatusPC());
    }

    /**
     * DeliveryDiaModelから削除対象リストを作成します
     *
     * @param deliveryDiaModel DeliveryDiaModel
     * @return List&lt;DeliveryImpossibleAreaEntity&gt;
     */
    public List<DeliveryImpossibleAreaEntity> convertToDeliveryImpossibleAreaEntityForDelete(DeliveryDiaModel deliveryDiaModel) {
        List<DeliveryDiaModelItem> deliveryDiaModelItems = deliveryDiaModel.getResultItems();
        List<DeliveryImpossibleAreaEntity> deleteList = new ArrayList<>();

        if ((deliveryDiaModelItems != null) && !deliveryDiaModelItems.isEmpty()) {
            DeliveryImpossibleAreaEntity entity = null;

            for (DeliveryDiaModelItem pageItem : deliveryDiaModelItems) {
                if (pageItem.isCheck()) {
                    entity = ApplicationContextUtility.getBean(DeliveryImpossibleAreaEntity.class);
                    entity.setDeliveryMethodSeq(pageItem.getDeliveryMethodSeq());
                    entity.setZipCode(pageItem.getZipcode());
                    deleteList.add(entity);
                }
            }
        }

        return deleteList;
    }

    /**
     * IndexPageを検索条件Dtoに変換します
     *
     * @param conditionDto     DeliveryImpossibleAreaConditionDto
     * @param deliveryDiaModel DeliveryDiaModel
     */
    public void convertToDeliveryImpossibleAreaConditionDtoForSearch(DeliveryImpossibleAreaConditionDto conditionDto,
                                                                     DeliveryDiaModel deliveryDiaModel) {
        conditionDto.setDeliveryMethodSeq(deliveryDiaModel.getDmcd());
        if (StringUtils.isEmpty(deliveryDiaModel.getPrefectureName())) {
            conditionDto.setPrefecture("");
        } else {
            conditionDto.setPrefecture(EnumTypeUtil.getEnumFromValue(HTypePrefectureType.class,
                                                                     deliveryDiaModel.getPrefectureName()
                                                                    ).getLabel());
        }
    }

    /**
     * 画面入力値を配送特別料金エンティティに変換します
     *
     * @param entity           DeliveryImpossibleAreaEntity
     * @param deliveryDiaModel DeliveryDiaModel
     */
    public void convertToDeliveryImpossibleAreaEntityForRegist(DeliveryImpossibleAreaEntity entity,
                                                               DeliveryDiaModel deliveryDiaModel) {

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        entity.setDeliveryMethodSeq(deliveryDiaModel.getDmcd());
        entity.setZipCode(deliveryDiaModel.getZipCode());
        entity.setRegistTime(dateUtility.getCurrentTime());
        entity.setUpdateTime(dateUtility.getCurrentTime());
    }

    /**
     * 郵便番号住所情報をDeliveryDiaModel.addressに変換します
     *
     * @param deliveryDiaModel DeliveryDiaModel
     * @param entityList       List&lt;ZipCodeAddressDto&gt;
     */
    public void convertToRegistPageForZipCodeResult(DeliveryDiaModel deliveryDiaModel,
                                                    List<ZipCodeAddressDto> entityList) {
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

        deliveryDiaModel.setAddress(builder.toString());
    }
}
