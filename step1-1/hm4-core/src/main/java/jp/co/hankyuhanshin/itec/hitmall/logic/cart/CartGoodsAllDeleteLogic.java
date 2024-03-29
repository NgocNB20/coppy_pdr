/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

/**
 * カート全商品削除ロジック（今すぐお届けのみ）
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface CartGoodsAllDeleteLogic {

    // LCC0003

    /**
     * カート全商品削除<br/>
     * （自動認証時）会員SEQ、もしくは（未認証時）端末識別情報　を元に、カートをクリアします。<br/>
     *
     * ※reserveFlag（取りおきフラグ）≠1（取りおき不可）が対象
     *   → セールde予約商品は振分商品は発生しないので、カート画面表示時の再投入は行わないため。
     *
     * @param shopSeq       ショップSEQ
     * @param memberInfoSeq 会員SEQ
     * @param accessUid     端末識別番号
     * @return 削除件数
     */
    int execute(Integer shopSeq, Integer memberInfoSeq, String accessUid);

}
