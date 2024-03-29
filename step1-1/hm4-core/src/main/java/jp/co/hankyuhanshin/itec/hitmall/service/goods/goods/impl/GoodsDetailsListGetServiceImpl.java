/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsListGetBySeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupImageGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsInformationIconGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsDetailsListGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品詳細リスト取得<br/>
 *
 * @author USER
 * @version $Revision: 1.3 $
 */
@Service
public class GoodsDetailsListGetServiceImpl extends AbstractShopService implements GoodsDetailsListGetService {

    /**
     * 商品詳細DTOリスト取得ロジック
     */
    private final GoodsDetailsListGetBySeqLogic goodsDetailsListGetBySeqLogic;

    /**
     * 商品グループ画像取得ロジック
     */
    private final GoodsGroupImageGetLogic goodsGroupImageGetLogic;

    /**
     * 商品グループ表示取得ロジック
     */
    private final GoodsGroupDisplayGetLogic goodsGroupDisplayGetLogic;

    /**
     * インフォメーションアイコン表示情報取得ロジック
     */
    private final GoodsInformationIconGetLogic goodsInformationIconGetLogic;

    /**
     * 商品Utility
     */
    private final GoodsUtility goodsUtility;

    @Autowired
    public GoodsDetailsListGetServiceImpl(GoodsDetailsListGetBySeqLogic goodsDetailsListGetBySeqLogic,
                                          GoodsGroupImageGetLogic goodsGroupImageGetLogic,
                                          GoodsGroupDisplayGetLogic goodsGroupDisplayGetLogic,
                                          GoodsInformationIconGetLogic goodsInformationIconGetLogic,
                                          GoodsUtility goodsUtility) {

        this.goodsDetailsListGetBySeqLogic = goodsDetailsListGetBySeqLogic;
        this.goodsGroupImageGetLogic = goodsGroupImageGetLogic;
        this.goodsGroupDisplayGetLogic = goodsGroupDisplayGetLogic;
        this.goodsInformationIconGetLogic = goodsInformationIconGetLogic;
        this.goodsUtility = goodsUtility;
    }

    /**
     * 商品詳細リスト取得<br/>
     *
     * @param goodsSeqList 商品SEQリスト
     * @return 商品詳細DTO
     */
    @Override
    public List<GoodsDetailsDto> execute(List<Integer> goodsSeqList) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("goodsSeqList", goodsSeqList);

        // 商品詳細情報DTOのリストを作成
        List<GoodsDetailsDto> goodsDetailsDtoList = goodsDetailsListGetBySeqLogic.execute(goodsSeqList);

        // 商品グループSEQのリストを作成する。
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        for (GoodsDetailsDto goodsDetailsDto : goodsDetailsDtoList) {
            if (!goodsGroupSeqList.contains(goodsDetailsDto.getGoodsGroupSeq())) {
                goodsGroupSeqList.add(goodsDetailsDto.getGoodsGroupSeq());
            }
        }

        // 商品グループ画像のMAP<商品グループSEQ、商品グループ画像エンティティリスト>を取得
        Map<Integer, List<GoodsGroupImageEntity>> goodsGroupImageListMap =
                        goodsGroupImageGetLogic.execute(goodsGroupSeqList);

        // 商品インフォメーションアイコンのMAP<商品グループSEQ、アイコン詳細DTOリスト>を取得
        Map<Integer, List<GoodsInformationIconDetailsDto>> iconMap = getInfomationIconInfo(goodsGroupSeqList);

        // 商品詳細情報DTOに商品グループ画像リストと商品規格画像リストをセットする。
        for (GoodsDetailsDto goodsDetailsDto : goodsDetailsDtoList) {
            goodsDetailsDto.setGoodsGroupImageEntityList(
                            goodsGroupImageListMap.get(goodsDetailsDto.getGoodsGroupSeq()));
            goodsDetailsDto.setGoodsIconList(iconMap.get(goodsDetailsDto.getGoodsGroupSeq()));
        }

        return goodsDetailsDtoList;
    }

    /**
     * インフォメーションアイコンMAP作成<br/>
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @return インフォメーションアイコン情報MAP<商品グループSEQ 、 アイコン詳細DTOリスト>
     */
    protected Map<Integer, List<GoodsInformationIconDetailsDto>> getInfomationIconInfo(List<Integer> goodsGroupSeqList) {

        Map<Integer, List<GoodsInformationIconDetailsDto>> iconMap = new LinkedHashMap<>();

        // 商品グループ表示エンティティリスト取得
        List<GoodsGroupDisplayEntity> displayList = goodsGroupDisplayGetLogic.execute(goodsGroupSeqList);

        // インフォメーションアイコン取得準備
        for (GoodsGroupDisplayEntity entity : displayList) {

            String iconPc = entity.getInformationIconPC();
            if (StringUtils.isEmpty(iconPc)) {
                // PC、MBどちらもインフォメーションアイコンが設定されていない場合は次の商品グループへ
                continue;
            }

            // 対象サイトごとに設定されているインフォメーションアイコンのSEQリスト作成
            List<Integer> iconSeqList = goodsUtility.createIconSeqList(HTypeSiteType.FRONT_PC, iconPc);
            if (CollectionUtil.getSize(iconSeqList) <= 0) {
                // 対象サイトのインフォメーションアイコンが設定されていない場合は次の商品グループへ
                continue;
            }

            // 作成したSEQリストでインフォメーションアイコン情報を取得
            List<GoodsInformationIconDetailsDto> iconDetailsDtoList = goodsInformationIconGetLogic.execute(iconSeqList);
            if (CollectionUtil.getSize(iconDetailsDtoList) <= 0) {
                // 対象サイトのインフォメーションアイコンが設定されていない場合は次の商品グループへ
                continue;
            }

            // MAP<商品グループSEQ、アイコン詳細DTOリスト>を作成
            iconMap.put(entity.getGoodsGroupSeq(), iconDetailsDtoList);
        }

        return iconMap;
    }

}
