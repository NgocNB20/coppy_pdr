/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.cart.CartGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.AbstractReserveHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.ReserveDetailItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.ReserveItem;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商品_セールde予約HELPER
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
// 2023-renew No14 from here
public class GoodsReserveHelper extends AbstractReserveHelper {

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * コンストラクタ
     */
    @Autowired
    public GoodsReserveHelper(GoodsUtility goodsUtility, ConversionUtility conversionUtility, DateUtility dateUtility) {
        super(goodsUtility, conversionUtility, dateUtility);
        this.dateUtility = dateUtility;
        this.conversionUtility = conversionUtility;
    }

    /**
     * リクエストパラメータをModel保持
     *
     * @param goodsReserveModel セールde予約画面 Model
     * @param gcd 商品コード（リクエストパラメータ）
     * @param ggcd 商品グループコード（リクエストパラメータ）
     * @param cid カテゴリーID（リクエストパラメータ）
     */
    public void setRequestParam(GoodsReserveModel goodsReserveModel, String gcd, String ggcd, String cid) {
        super.setRequestParam(goodsReserveModel, gcd);
        goodsReserveModel.setGgcd(ggcd);
        goodsReserveModel.setCid(cid);
    }

    /**
     * カート情報を基にセールde予約情報Itemを作成
     *
     * @param goodsReserveModel セールde予約画面 Model
     * @param cartDto カートDTO（セールde予約）
     */
    public void setReserveItem(GoodsReserveModel goodsReserveModel, CartDto cartDto) {

        // カート情報を基にセールde予約情報Itemを作成
        goodsReserveModel.setReserveItem(ApplicationContextUtility.getBean(ReserveItem.class));
        goodsReserveModel.getReserveItem().setReserveDetailItemList(new ArrayList<>());

        for (CartGoodsDto dto : cartDto.getCartGoodsDtoList()) {
            CartGoodsEntity cartGoodsEntity = dto.getCartGoodsEntity();
            // 詳細Itemを生成
            ReserveDetailItem reserveDetailItem = ApplicationContextUtility.getBean(ReserveDetailItem.class);
            reserveDetailItem.setReserveDeliveryDate(
                            dateUtility.formatYmdWithSlash(cartGoodsEntity.getReserveDeliveryDate()));
            reserveDetailItem.setInputGoodsCount(String.valueOf(cartGoodsEntity.getCartGoodsCount()));
            goodsReserveModel.getReserveItem().getReserveDetailItemList().add(reserveDetailItem);
        }

        // 最大件数分のセールde予約情報 詳細Itemを追加生成し、初期表示時の行数を設定
        setDefaultReserveItem(goodsReserveModel);

        // 当画面の商品がセールde予約として既にカートINされているかどうか
        goodsReserveModel.setExistCart(CollectionUtil.isNotEmpty(cartDto.getCartGoodsDtoList()));

    }

    /**
     * カート情報を基にチェック用の数量（今すぐお届け分）をセット
     *
     * @param goodsReserveModel セールde予約画面 Model
     * @param cartDto カートDTO（今すぐお届け分）
     */
    public void setReserveItemForInputDeliveryNowGoodsCount(GoodsReserveModel goodsReserveModel, CartDto cartDto) {

        // カート情報を基にチェック用の数量（今すぐお届け分）をセット
        BigDecimal goodsTotalCount = BigDecimal.ZERO;
        for (CartGoodsDto dto : cartDto.getCartGoodsDtoList()) {
            goodsTotalCount = dto.getCartGoodsEntity().getCartGoodsCount();
        }
        goodsReserveModel.getReserveItem().setInputDeliveryNowGoodsCount(goodsTotalCount);

    }

    /**
     * 入力された情報からカート一括登録用引継DTOリストを作成する
     *
     * @param goodsReserveModel セールde予約画面 Model
     * @return カート一括登録用引継DTOリスト
     */
    protected List<CartGoodsForTakeOverDto> getCartGoodsForTakeOverDtoList(GoodsReserveModel goodsReserveModel) {

        // 入力された情報から引継ぎ用のセールde予約情報Itemを再生成する（未入力行の除去とお届け希望日のソートも行う）
        ReserveItem reserveItem = getOrderReserveItem(goodsReserveModel);

        // カート一括登録用引継DTOリストに変換
        List<CartGoodsForTakeOverDto> cartGoodsForTakeOverDtoList = new ArrayList<>();
        reserveItem.getReserveDetailItemList().forEach(item -> {
            CartGoodsForTakeOverDto cartGoodsForTakeOverDto =
                            ApplicationContextUtility.getBean(CartGoodsForTakeOverDto.class);
            cartGoodsForTakeOverDto.setGoodsGroupSeq(goodsReserveModel.getGoodsGroupSeq());
            cartGoodsForTakeOverDto.setGoodsSeq(goodsReserveModel.getGoodsSeq());
            cartGoodsForTakeOverDto.setGoodsCode(goodsReserveModel.getGoodsCode());
            cartGoodsForTakeOverDto.setGoodsCount(conversionUtility.toBigDecimal(item.getInputGoodsCount()));
            cartGoodsForTakeOverDto.setReserveDeliveryDate(
                            conversionUtility.toTimeStamp(item.getReserveDeliveryDate()));
            cartGoodsForTakeOverDto.setReserveFlag(HTypeReserveDeliveryFlag.ON);
            cartGoodsForTakeOverDtoList.add(cartGoodsForTakeOverDto);
        });

        // 逆順ソート
        // セールde予約は通常カート商品と異なる取得条件なので、登録時の逆順ソートはMUSTではない。
        // ただし、カート商品TBL内データの整理のためにも同じように逆順ソートして登録しておく
        Collections.reverse(cartGoodsForTakeOverDtoList);

        return cartGoodsForTakeOverDtoList;
    }

}
// 2023-renew No14 to here
