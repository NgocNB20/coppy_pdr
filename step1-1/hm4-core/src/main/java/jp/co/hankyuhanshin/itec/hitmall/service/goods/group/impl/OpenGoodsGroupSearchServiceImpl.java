/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.group.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.OpenGoodsGroupSearchService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公開関商品グループ情報検索<br/>
 * 検索条件に該当する公開中の商品情報の商品グループのリストを取得する<br/>
 *
 * @author ozaki
 * @version $Revision: 1.3 $
 */
@Service
public class OpenGoodsGroupSearchServiceImpl extends AbstractShopService implements OpenGoodsGroupSearchService {

    /**
     * 商品グループリスト取得ロジッククラス
     */
    private final GoodsGroupListGetLogic goodsGroupListGetLogic;

    @Autowired
    public OpenGoodsGroupSearchServiceImpl(GoodsGroupListGetLogic goodsGroupListGetLogic) {
        this.goodsGroupListGetLogic = goodsGroupListGetLogic;
    }

    /**
     * 公開関商品グループ情報検索<br/>
     * 検索条件に該当する公開中の商品情報の商品グループのリストを取得する<br/>
     *
     * @param conditionDto 商品グループ検索条件DTO
     * @return 商品グループDTOリスト
     */
    @Override
    public List<GoodsGroupDto> execute(GoodsGroupSearchForDaoConditionDto conditionDto) {
        //
        // (1) パラメータチェック
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);

        // (2) 共通情報チェック
        // ・ショップSEQ ： null(or空文字) の場合 エラーとして処理を終了する
        // ・サイト区分 ： null(or空文字) の場合 エラーとして処理を終了する
        Integer shopSeq = 1001;
        HTypeSiteType siteType = HTypeSiteType.FRONT_PC;

        // (3) 商品情報検索処理実行
        // ・商品グループDao用検索条件Dtoを作成する
        // ・商品グループDao用検索条件Dto
        // ‥ショップSEQ =共通情報.ショップSEQ
        // ‥サイト区分 =共通情報.サイト区分
        conditionDto.setShopSeq(shopSeq);
        conditionDto.setSiteType(siteType);

        // ･商品情報検索処理実行
        // ※『logic基本設計書（商品グループリスト取得（検索））.xls』参照
        // Logic GoodsGroupListGetLogic
        // パラメータ 商品グループDao用検索条件Dto
        // (公開状態=公開中)
        // 戻り値 商品グループDTOリスト
        List<GoodsGroupDto> goodsGroupDtoList = goodsGroupListGetLogic.execute(conditionDto);

        return goodsGroupDtoList;
    }

}
