/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartGoodsCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * カート商品チェック<br/>
 * カート商品情報をチェックします。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
@Service
public class CartGoodsCheckServiceImpl extends AbstractShopService implements CartGoodsCheckService {

    /**
     * カート商品チェックロジッククラス
     */
    private final CartGoodsCheckLogic cartGoodsCheckLogic;

    @Autowired
    public CartGoodsCheckServiceImpl(CartGoodsCheckLogic cartGoodsCheckLogic) {
        this.cartGoodsCheckLogic = cartGoodsCheckLogic;
    }

    /**
     * カートチェック<br/>
     * カート内の商品情報をチェックする。<br/>
     *
     * @param cartDto          カート商品情報DTO
     * @param isLogin          会員ログインしているか否かの判定
     * @param businessType     顧客区分
     * @param confDocumentType 確認書類区分
     * @return エラー情報MAP
     */
    @Override
    public Map<Integer, List<CheckMessageDto>> execute(CartDto cartDto,
                                                       Boolean isLogin,
                                                       String businessType,
                                                       String confDocumentType,
                                                       Integer memberInfoSeq) {
        // (1) パラメータチェック
        // カート商品リスト ： null 又は リスト0件の場合
        // 処理を終了する
        if (cartDto == null || cartDto.getCartGoodsDtoList() == null || cartDto.getCartGoodsDtoList().size() == 0) {
            return null;
        }

        // (2)カート商品チェック処理実行
        // ・カートに投入されている商品について以下の内容をチェックする
        // ・公開状態は公開になっているか
        // ・公開期間内であるか
        // ・販売状態は販売になっているか
        // ・販売期間内であるか
        // ・配送方法が選択できる商品の組合せであるか
        // ・決済方法が選択できる商品の組合せであるか
        // ・在庫管理＝在庫管理ありの場合（在庫管理なし、及び予約販売可以外）
        // ‥在庫が不足していないか（カート内で同商品の数量をマージした数が販売在庫数を超えていないか）
        // ‥在庫切れになっていないか
        // ・購入制限数
        // ・カート内最大商品件数を越えていないか
        HTypeSiteType siteType = HTypeSiteType.FRONT_PC;
        Map<Integer, List<CheckMessageDto>> errorMap =
                        cartGoodsCheckLogic.execute(cartDto, siteType, isLogin, businessType, confDocumentType,
                                                    memberInfoSeq
                                                   );

        return errorMap;
    }
}
