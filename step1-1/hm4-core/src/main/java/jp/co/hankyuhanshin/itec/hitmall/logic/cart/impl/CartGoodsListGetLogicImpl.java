/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDetailsMapGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsInformationIconMapGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * カート商品リスト取得<br/>
 * カート商品情報に登録されている商品リストを取得します。<br/>
 *
 * @author ozaki
 */
@Component
public class CartGoodsListGetLogicImpl extends AbstractShopLogic implements CartGoodsListGetLogic {

    /**
     * カート商品情報DAO
     */
    private final CartGoodsDao cartGoodsDao;

    /**
     * 商品詳細情報リスト取得ロジッククラス
     */
    private final GoodsDetailsMapGetLogic goodsDetailsMapGetLogic;

    /**
     * 商品アイコン情報取得ロジッククラス
     */
    private final GoodsInformationIconMapGetLogic goodsInformationIconMapGetLogic;

    @Autowired
    public CartGoodsListGetLogicImpl(CartGoodsDao cartGoodsDao,
                                     GoodsDetailsMapGetLogic goodsDetailsMapGetLogic,
                                     GoodsInformationIconMapGetLogic goodsInformationIconMapGetLogic) {
        this.cartGoodsDao = cartGoodsDao;
        this.goodsDetailsMapGetLogic = goodsDetailsMapGetLogic;
        this.goodsInformationIconMapGetLogic = goodsInformationIconMapGetLogic;
    }

    /**
     * カート商品リスト取得<br/>
     * カート商品情報に登録されている商品リストを取得します。<br/>
     *
     * @param cartGoodsConditionDto カート商品DAO用検索条件DTO
     * @return カートDTO
     */
    @Override
    public CartDto execute(CartGoodsForDaoConditionDto cartGoodsConditionDto) {
        // (1) パラメータチェック
        // カート商品Dao用検索条件DTOがnullでないことをチェック
        ArgumentCheckUtil.assertNotNull("cartGoodsConditionDto", cartGoodsConditionDto);

        // (2) カート商品情報リスト取得
        List<CartGoodsEntity> cartGoodsEntityList = getCartGoodsEntityList(cartGoodsConditionDto);

        // (3) 商品SEQリスト生成
        List<Integer> goodsSeqList = makeGoodsSeqList(cartGoodsEntityList);

        // (4) 商品詳細DTOマップ生成
        Map<Integer, GoodsDetailsDto> goodsDetailsMap = makeGoodsDetailsMap(goodsSeqList);

        // (5) 商品情報アイコン詳細DTOマップ生成
        Map<Integer, List<GoodsInformationIconDetailsDto>> goodsIconMap =
                        makeGoodsIconMap(cartGoodsConditionDto, goodsSeqList, goodsDetailsMap);

        // (6) カートDTO生成
        CartDto cartDto = makeCartDto(cartGoodsEntityList, goodsDetailsMap, goodsIconMap);

        // (7) 戻り値
        // 戻り値用カートDTOを返す。
        return cartDto;
    }

    /**
     * カート商品エンティティリスト取得<br/>
     *
     * @param cartGoodsConditionDto カート商品DAO用検索条件DTO
     * @param customParams          案件用引数
     * @return カート商品エンティティリスト
     */
    protected List<CartGoodsEntity> getCartGoodsEntityList(CartGoodsForDaoConditionDto cartGoodsConditionDto,
                                                           Object... customParams) {
        // カート商品Daoのカート商品情報リスト取得を実行する。
        // メソッド List<カート商品エンティティ> getCartGoodsList( カート商品Dao用検索条件DTO)
        List<CartGoodsEntity> cartGoodsEntityList = cartGoodsDao.getCartGoodsList(cartGoodsConditionDto);
        return cartGoodsEntityList;
    }

    /**
     * 商品SEQリスト生成<br/>
     *
     * @param cartGoodsEntityList カート商品エンティティリスト
     * @param customParams        案件用引数
     * @return 商品SEQリスト
     */
    protected List<Integer> makeGoodsSeqList(List<CartGoodsEntity> cartGoodsEntityList, Object... customParams) {
        List<Integer> goodsSeqList = new ArrayList<>();
        for (CartGoodsEntity cartGoodsEntity : cartGoodsEntityList) {
            goodsSeqList.add(cartGoodsEntity.getGoodsSeq());
        }
        return goodsSeqList;
    }

    /**
     * 商品詳細DTOマップ生成<br/>
     *
     * @param goodsSeqList 商品SEQリスト
     * @param customParams 案件用引数
     * @return 商品詳細DTOマップ
     */
    protected Map<Integer, GoodsDetailsDto> makeGoodsDetailsMap(List<Integer> goodsSeqList, Object... customParams) {
        if (goodsSeqList.isEmpty()) {
            return null;
        }

        // 商品詳細情報リスト取得Logicを利用して、商品詳細Dtoリストを取得する
        // Logic GoodsDetailsMapGetLogic
        // パラメータ 商品SEQリスト
        // 戻り値 商品詳細Dtoマップ
        Map<Integer, GoodsDetailsDto> goodsDetailsMap = goodsDetailsMapGetLogic.execute(goodsSeqList);
        return goodsDetailsMap;
    }

    /**
     * 商品情報アイコン詳細DTOマップ生成<br/>
     *
     * @param cartGoodsConditionDto カート商品DAO用検索条件DTO
     * @param goodsSeqList          商品SEQリスト
     * @param goodsDetailsMap       商品詳細DTOマップ
     * @param customParams          案件用引数
     * @return 商品情報アイコン詳細DTOマップ
     */
    protected Map<Integer, List<GoodsInformationIconDetailsDto>> makeGoodsIconMap(CartGoodsForDaoConditionDto cartGoodsConditionDto,
                                                                                  List<Integer> goodsSeqList,
                                                                                  Map<Integer, GoodsDetailsDto> goodsDetailsMap,
                                                                                  Object... customParams) {
        if (goodsSeqList.isEmpty()) {
            return null;
        }

        List<Integer> goodsGroupSeqList = new ArrayList<>();
        for (Integer goodsSeq : goodsSeqList) {
            GoodsDetailsDto goodsDetailsDto = goodsDetailsMap.get(goodsSeq);
            goodsGroupSeqList.add(goodsDetailsDto.getGoodsGroupSeq());
        }
        Map<Integer, List<GoodsInformationIconDetailsDto>> goodsIconMap =
                        goodsInformationIconMapGetLogic.execute(goodsGroupSeqList, cartGoodsConditionDto.getSiteType());
        return goodsIconMap;
    }

    /**
     * カートDTO生成<br/>
     *
     * @param cartGoodsEntityList カート商品エンティティリスト
     * @param goodsDetailsMap     商品詳細DTOマップ
     * @param goodsIconMap        商品情報アイコン詳細DTOマップ
     * @return カートDTO
     */
    protected CartDto makeCartDto(List<CartGoodsEntity> cartGoodsEntityList,
                                  Map<Integer, GoodsDetailsDto> goodsDetailsMap,
                                  Map<Integer, List<GoodsInformationIconDetailsDto>> goodsIconMap) {
        // カートDTOを作成し、カート商品DTOリスト（カート商品DTOにはカート商品エンティティ、商品詳細DTOを設定）をセットする。
        // 戻り値用カートDTOを初期化する。
        // カート商品DTOリストを初期化する。
        // ・(2)で取得したカート商品エンティティリストについて以下の処理を行う
        // ①カート商品DTOオブジェクトを初期生成する。
        // ②カート商品エンティティ．商品SEQをキー項目として、(4)で取得した商品詳細DTOマップから商品詳細DTOを取得する。
        // マップから商品詳細DTOが取得できた場合、カート商品DTO．商品詳細DTOにセットする
        // ③カート商品DTOをカート商品DTOリストに追加する。
        // カート商品DTOリストをカートDTOにセットする。
        // （パラメータ）カート商品Dao用検索条件DTO．COUNT をカートDTO．全件数にセットする。
        List<CartGoodsDto> cartGoodsDtoList = new ArrayList<>();
        for (CartGoodsEntity cartGoodsEntity : cartGoodsEntityList) {

            CartGoodsDto cartGoodsDto = getComponent(CartGoodsDto.class);
            // カートマージ非対応、セット商品投入時は同一の商品詳細DTOを使用する為コピー
            GoodsDetailsDto goodsDetailsDto = CopyUtil.deepCopy(goodsDetailsMap.get(cartGoodsEntity.getGoodsSeq()));

            List<GoodsInformationIconDetailsDto> goodsInformationIconDetailsDto =
                            goodsIconMap.get(goodsDetailsDto.getGoodsGroupSeq());
            if (goodsInformationIconDetailsDto == null) {
                goodsInformationIconDetailsDto = new ArrayList<>();
            }
            cartGoodsDto.setCartGoodsEntity(cartGoodsEntity);
            cartGoodsDto.setGoodsDetailsDto(goodsDetailsDto);
            cartGoodsDto.setGoodsInformationIconDetailsDtoList(goodsInformationIconDetailsDto);

            cartGoodsDtoList.add(cartGoodsDto);
        }
        CartDto cartDto = getComponent(CartDto.class);
        cartDto.setCartGoodsDtoList(cartGoodsDtoList);
        cartDto.setGoodsTotalCount(new BigDecimal(cartGoodsDtoList.size()));
        return cartDto;
    }
}
