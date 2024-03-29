/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.footprint.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.footprint.FootprintSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint.GoodsFootPrintListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.footprint.OpenFootPrintListGetService;
import jp.co.hankyuhanshin.itec.hitmall.web.PageInfo;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 公開あしあと商品情報取得<br/>
 * 公開されているあしあと商品情報を取得します。<br/>
 *
 * @author ozaki
 * @author kaneko　(itec) チケット対応　#2644　訪問者数にクローラが含まれている
 */
@Service
public class OpenFootPrintListGetServiceImpl extends AbstractShopService implements OpenFootPrintListGetService {

    /**
     * あしあと商品詳細リスト取得ロジック
     */
    private final GoodsFootPrintListGetLogic goodsFootPrintListGetLogic;

    @Autowired
    public OpenFootPrintListGetServiceImpl(GoodsFootPrintListGetLogic goodsFootPrintListGetLogic) {
        this.goodsFootPrintListGetLogic = goodsFootPrintListGetLogic;
    }

    /**
     * 公開あしあと商品リスト取得<br/>
     * 端末識別番号を元にあしあと商品情報のリストを取得する<br/>
     *
     * @param conditionDto あしあと商品検索条件DTO
     * @return 商品グループDTOリスト
     */
    @Override
    public List<GoodsGroupDto> execute(FootprintSearchForDaoConditionDto conditionDto, String accessUid) {
        // (1) 共通情報チェック
        // ・ショップSEQ ： null（or 0）の場合 エラーとして処理を終了する
        // ・端末識別情報 ： null（or空文字）の場合 エラーとして処理を終了する
        // ・サイト区分 ： null(or空文字) の場合 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotEmpty("accessUid", accessUid);

        // (2) 検索条件作成
        // 共通情報
        conditionDto.setShopSeq(1001);
        conditionDto.setAccessUid(accessUid);

        // ・あしあと商品リスト取得処理実行
        // ※『logic基本設計（あしあと商品リスト取得）.xls』参照
        // Logic GoodsFootPrintListGetLogic
        // パラメータ あしあと商品Dao用検索条件Dto
        // 戻り値 商品グループ一覧DTODTO
        List<GoodsGroupDto> goodsGroupDtoList = goodsFootPrintListGetLogic.execute(conditionDto);

        return goodsGroupDtoList;
    }

    /**
     * 公開あしあと商品情報取得
     * 端末識別番号を元にあしあと商品情報のリストを取得する<br/>
     *
     * @param limit               取得件数
     * @param exceptGoodsGroupSeq 取得対象外の商品
     * @return 商品グループDTO一覧
     */
    @Override
    public List<GoodsGroupDto> execute(int limit, Integer exceptGoodsGroupSeq, String accessUid) {
        FootprintSearchForDaoConditionDto conditionDto = getComponent(FootprintSearchForDaoConditionDto.class);

        PageInfo pageInfo = ApplicationContextUtility.getBean(PageInfo.class);

        pageInfo.setLimit(limit);
        pageInfo.setOrder("updateTime", false);
        pageInfo.setSkipCountFlg(true);

        if (limit > 0) {
            pageInfo.setupSelectOptions();
        }

        conditionDto.setPageInfo(pageInfo);

        if (exceptGoodsGroupSeq != null) {
            conditionDto.setGoodsGroupSeqList(new ArrayList<>());
            conditionDto.getGoodsGroupSeqList().add(exceptGoodsGroupSeq);
        }
        return execute(conditionDto, accessUid);
    }

}
