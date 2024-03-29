/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IconHelper {

    /**
     * 検索結果をページに反映<br/>
     *
     * @param goodsInformationIconDtoList 検索結果リスト
     * @param iconModel                   アイコンモデル
     */
    public void toPageForLoad(List<GoodsInformationIconDto> goodsInformationIconDtoList, IconModel iconModel) {

        List<IconModelItem> resultItemList = new ArrayList<>();

        Map<Integer, GoodsInformationIconDto> iconDtoMap = new HashMap<>();

        int orderDisplay = 1;
        for (GoodsInformationIconDto goodsInformationIconDto : goodsInformationIconDtoList) {

            // アイコン情報
            GoodsInformationIconEntity entity = goodsInformationIconDto.getGoodsInformationIconEntity();

            IconModelItem indexPageItem = ApplicationContextUtility.getBean(IconModelItem.class);
            indexPageItem.setIconSeq(entity.getIconSeq());
            indexPageItem.setIconName(entity.getIconName());
            indexPageItem.setColorCode(entity.getColorCode());

            // 表示順はリストのindex+1をセットする
            indexPageItem.setOrderDisplayRadioValue(String.valueOf(orderDisplay));
            indexPageItem.setOrderDisplayRadioLabel(String.valueOf(orderDisplay));

            // １行分の情報をセット
            resultItemList.add(indexPageItem);
            iconDtoMap.put(entity.getIconSeq(), goodsInformationIconDto);

            orderDisplay++;
        }

        // 結果をページに反映
        iconModel.setResultItems(resultItemList);
        iconModel.setIconDtoMap(iconDtoMap);
    }

    /**
     * 表示順を変更する(セッション情報)
     * ただし変更後の表示順が範囲外の場合は処理なし
     *
     * @param index     変更前の表示順(実際のindex)
     * @param addIndex  変更後の表示順
     * @param iconModel アイコンモデル
     */
    public void toPageForChangeOrderDisplay(int index, int addIndex, IconModel iconModel) {
        List<IconModelItem> iconList = iconModel.getResultItems();

        // 変更可能な範囲であれば、表示順変更
        if (0 <= addIndex && addIndex <= iconList.size() - 1) {
            IconModelItem item = iconList.remove(index);
            iconList.add(addIndex, item);
            Integer selectedIconSeq = item.getIconSeq();

            // 表示順を再設定
            int orderDisplay = 1;
            String strOrderDisplay = null;
            for (IconModelItem indexPageItem : iconList) {
                strOrderDisplay = String.valueOf(orderDisplay);
                indexPageItem.setOrderDisplayRadioLabel(strOrderDisplay);
                indexPageItem.setOrderDisplayRadioValue(strOrderDisplay);

                if (selectedIconSeq.equals(indexPageItem.getIconSeq())) {
                    // 選択値保持
                    iconModel.setOrderDisplay(orderDisplay);
                }

                orderDisplay++;
            }
        }
    }

    /**
     * 表示順確定のため、アイコンDtoリストを作成
     *
     * @param iconModel アイコンモデル
     * @return アイコンDtoリスト
     */
    public List<GoodsInformationIconDto> toGoodsInformationIconDtoListForOrderDisplayUpdate(IconModel iconModel) {

        List<GoodsInformationIconDto> iconDtoList = new ArrayList<>();

        List<IconModelItem> indexPageItemList = iconModel.getResultItems();
        Map<Integer, GoodsInformationIconDto> iconDtoMap = iconModel.getIconDtoMap();

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 表示されている件数分ループ
        for (IconModelItem indexPageItem : indexPageItemList) {
            Integer iconSeq = indexPageItem.getIconSeq();
            Integer orderDisplay = conversionUtility.toInteger(indexPageItem.getOrderDisplayRadioValue());

            GoodsInformationIconDto iconDto = iconDtoMap.get(iconSeq);
            // 現在の表示順をセット
            iconDto.getGoodsInformationIconEntity().setOrderDisplay(orderDisplay);
            iconDtoList.add(iconDto);
        }
        return iconDtoList;
    }
}
