/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart;

import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;

import java.util.List;
import java.util.Map;

/**
 * カートチェッククラス<br/>
 * カート内の商品情報をチェックする。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface CartGoodsCheckService {

    /**
     * 決済方法チェックメッセージ<br/>
     * <code>MSGCD_NO_SETTLEMENTMETHOD</code>
     */
    public static final String MSGCD_NO_SETTLEMENTMETHOD = "LCC000614";

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
    Map<Integer, List<CheckMessageDto>> execute(CartDto cartDto,
                                                Boolean isLogin,
                                                String businessType,
                                                String confDocumentType,
                                                Integer memberInfoSeq);
}
