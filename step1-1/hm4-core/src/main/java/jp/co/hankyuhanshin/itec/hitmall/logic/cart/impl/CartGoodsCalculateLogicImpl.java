/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsCalculateLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * PDR#007 04_数量割引サービス<br/>
 * <p>
 * カート内計算<br/>
 *
 * @author ozaki
 */
@Component
public class CartGoodsCalculateLogicImpl extends AbstractShopLogic implements CartGoodsCalculateLogic {

    /**
     * カート内計算
     * <pre>
     * 会員価格が存在する場合は、そちらで計算
     * セット子商品は、0円 として計算を行う
     * </pre>
     *
     * @param cartDto カートDTO
     */
    @Override
    public void execute(CartDto cartDto) {
        ArgumentCheckUtil.assertNotNull("cartDto", cartDto);

        // (2) 各カート商品の計算処理
        // ・合計値の初期化
        cartDto.setGoodsTotalCount(BigDecimal.ZERO);
        cartDto.setGoodsTotalPrice(BigDecimal.ZERO);

        // ・カート商品Dtoリストが null または 0件でない場合、カート商品Dtoリストの件数分、以下の処理を行う。
        List<CartGoodsDto> cartGoodsDtoList = cartDto.getCartGoodsDtoList();
        if (cartGoodsDtoList != null) {
            // 小計、合計金額算出
            for (CartGoodsDto cartGoodsDto : cartGoodsDtoList) {
                execute(cartDto, cartGoodsDto);
            }
        }

        // PDR Migrate Customization from here
        cartDto.setGoodsTotalPriceInTax(cartDto.getGoodsTotalPrice().add(cartDto.getTotalPriceTax()));
        // PDR Migrate Customization to here
    }

    /**
     * 明細単位の計算
     * <pre>
     * カート商品DTO．金額（税抜） ＝ カート商品DTO．商品詳細DTO．単価（税抜） × カート商品DTO．カート商品エンティティ．数量
     * カート商品DTO．金額（税込） ＝ (カート商品DTO．商品詳細DTO．単価（税込） × カート商品DTO．カート商品エンティティ．数量) × 税率
     * カートDTO．商品合計点数 ＝ カート商品DTO．カート商品エンティティ．数量 の合計
     * カートDTO．商品合計金額（税別） ＝ カート商品DTO．金額（税抜） の合計
     * カートDTO．商品合計点数（税込） ＝ カート商品DTO．金額（税込） の合計
     * </pre>
     *
     * @param cartDto      カートDto
     * @param cartGoodsDto カート商品Dto
     */
    protected void execute(CartDto cartDto, CartGoodsDto cartGoodsDto) {
        /* カート商品の金額計算
         * <pre>
         * カート商品DTO．金額（税抜） ＝ カート商品DTO．商品詳細DTO．単価（税抜） × カート商品DTO．カート商品エンティティ．数量
         * カート商品DTO．金額（税込） ＝ (カート商品DTO．商品詳細DTO．単価（税抜） × カート商品DTO．カート商品エンティティ．数量) × 税率
         * </pre>
         */
        CalculatePriceUtility calculatePriceUtility = ApplicationContextUtility.getBean(CalculatePriceUtility.class);
        cartGoodsDto.setGoodsPriceSubtotal(cartGoodsDto.getGoodsDetailsDto()
                                                       .getGoodsPrice()
                                                       .multiply(cartGoodsDto.getCartGoodsEntity()
                                                                             .getCartGoodsCount()));
        cartGoodsDto.getGoodsDetailsDto().setGoodsPriceInTax(calculatePriceUtility.getTaxIncludedPrice(
                        cartGoodsDto.getGoodsDetailsDto().getGoodsPrice(),
                        cartGoodsDto.getGoodsDetailsDto().getTaxRate()
                                                                                                      ));
        cartGoodsDto.getGoodsDetailsDto().setPreDisCountPriceInTax(calculatePriceUtility.getTaxIncludedPrice(
                        cartGoodsDto.getGoodsDetailsDto().getPreDiscountPrice(),
                        cartGoodsDto.getGoodsDetailsDto().getTaxRate()
                                                                                                            ));
        cartGoodsDto.setGoodsPriceInTaxSubtotal(cartGoodsDto.getGoodsDetailsDto()
                                                            .getGoodsPriceInTax()
                                                            .multiply(cartGoodsDto.getCartGoodsEntity()
                                                                                  .getCartGoodsCount()));

        /* カート全体の金額計算
         * <pre>
         * カートDTO．商品合計点数 ＝ カート商品DTO．カート商品エンティティ．数量 の合計
         * カートDTO．商品合計点数（税込） ＝ カート商品DTO．金額（税込） の合計
         * </pre>
         */
        cartDto.setGoodsTotalCount(
                        cartDto.getGoodsTotalCount().add(cartGoodsDto.getCartGoodsEntity().getCartGoodsCount()));
        cartDto.setGoodsTotalPrice(cartDto.getGoodsTotalPrice().add(cartGoodsDto.getGoodsPriceSubtotal()));
    }
}
