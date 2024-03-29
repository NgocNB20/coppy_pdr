/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.group.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.OpenGoodsGroupDetailsGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公開商品グループ詳細情報取得<br/>
 * 代表商品SEQを元に、同じ代表商品を持つ公開中の商品情報のリストを取得する。<br/>
 *
 * @author ozaki
 * @author kaneko　(itec) チケット対応　#2644　訪問者数にクローラが含まれている
 * @author Nishigaki (itec) 2011/12/28 チケット #2699 対応
 */
@Service
public class OpenGoodsGroupDetailsGetServiceImpl extends AbstractShopService
                implements OpenGoodsGroupDetailsGetService {

    /**
     * 商品グループ情報取得ロジッククラス
     */
    private final GoodsGroupDetailsGetByCodeLogic goodsGroupDetailsGetByCodeLogic;

    @Autowired
    public OpenGoodsGroupDetailsGetServiceImpl(GoodsGroupDetailsGetByCodeLogic goodsGroupDetailsGetByCodeLogic) {
        this.goodsGroupDetailsGetByCodeLogic = goodsGroupDetailsGetByCodeLogic;
    }

    /**
     * 公開商品グループ詳細情報取得<br/>
     * 代表商品SEQを元に、同じ代表商品を持つ公開中の商品情報のリストを取得する。<br/>
     *
     * @param goodsGroupCode 商品グループコード
     * @param goodsCode      商品コード
     * @return 商品グループ情報DTO
     */
    @Override
    public GoodsGroupDto execute(String goodsGroupCode, String goodsCode) {

        // パラメータNULLチェックは商品グループコードか商品コードのどちらかがあればOK
        String goodsInfo = null;
        if (StringUtil.isNotEmpty(goodsGroupCode)) {
            goodsInfo = goodsGroupCode;
        }
        if (StringUtil.isNotEmpty(goodsCode)) {
            goodsInfo = goodsCode;
        }

        // (1) パラメータチェック
        // ・商品グループコードor商品コード ： null(or 空文字) の場合 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotEmpty("goodsGroupCode", goodsInfo);

        // (2) 共通情報チェック
        // ・ショップSEQ ： null(or 0) の場合 エラーとして処理を終了する
        // ・サイト区分 ： null(or 空文字) の場合 エラーとして処理を終了する
        Integer shopSeq = 1001;
        HTypeSiteType siteType = HTypeSiteType.FRONT_PC;

        // (3) 商品グループ情報を取得する
        // ※『logic基本設計書（商品グループ詳細取得（商品グループコード））.xls』参照
        // Logic GoodsGroupGetLogic
        // パラメータ ショップSEQ = 共通情報.ショップSEQ
        // 商品グループコード = パラメータ.商品グループコード
        // サイト区分 = 共通情報.サイト区分
        // 公開状態 = 公開中
        // 戻り値 商品グループDTO
        GoodsGroupDto goodsGroupDto =
                        goodsGroupDetailsGetByCodeLogic.execute(shopSeq, goodsGroupCode, goodsCode, siteType,
                                                                HTypeOpenDeleteStatus.OPEN
                                                               );

        return goodsGroupDto;
    }

    // 2023-renew No92 from here
    /**
     * 商品グループ詳細情報取得<br/>
     * 商品コードに基づいて商品グループの詳細情報を取得する<br/>
     *
     * @param goodsCodes 商品コードリスト
     * @return 商品グループ情報DTOリスト
     */
    @Override
    public List<GoodsGroupDto> execute(List<String> goodsCodes) {
        // (1) パラメータチェック
        // ・商品コード ： null(or 空文字) の場合 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotEmpty("goodsCodes", goodsCodes);

        // (2) 商品グループ情報を取得する
        // ※『logic基本設計書（商品グループ詳細取得（商品グループコード））.xls』参照
        // Logic GoodsGroupGetLogic
        // パラメータ ショップSEQ = 共通情報.ショップSEQ
        // 商品グループコード = パラメータ.商品グループコード
        // サイト区分 = 共通情報.サイト区分
        // 公開状態 = 公開中
        // 戻り値 商品グループDTO
        return goodsGroupDetailsGetByCodeLogic.execute(goodsCodes);
    }
    // 2023-renew No92 to here

}
