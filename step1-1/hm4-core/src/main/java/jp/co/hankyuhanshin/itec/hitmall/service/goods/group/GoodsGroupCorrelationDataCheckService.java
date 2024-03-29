/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.group;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品グループDto相関チェックサービス<br/>
 */
public interface GoodsGroupCorrelationDataCheckService {

    /**
     * 処理種別（画面からの登録更新）<br/>
     * <code>PROCESS_TYPE_FROM_SCREEN</code>
     */
    public static final int PROCESS_TYPE_FROM_SCREEN = 0;
    /**
     * 処理種別（CSVからの登録更新）<br/>
     * <code>PROCESS_TYPE_FROM_CSV</code>
     */
    public static final int PROCESS_TYPE_FROM_CSV = 1;

    /**
     * 正規表現チェック(商品グループコード・商品コード用)
     */
    public static final String regPatternForCode = "[a-zA-Z0-9_-]+";
    /**
     * 正規表現チェック(JANコード用)
     */
    public static final String regPatternForJanCode = "[0-9]+";

    /**
     * 商品種別：試供品で購入限度回数が入力されていない場合エラー：PKG-3559-001-S-
     */
    public static final String MSGCD_NOT_INPUT_SAMPLE_LIMIT = "PKG-3559-001-S-";
    /**
     * 商品種別：試供品で試供品のみ購入未選択の場合エラー：PKG-3559-002-S-
     */
    public static final String MSGCD_NOT_SELECT_ONLY_BUY = "PKG-3559-002-S-";
    /**
     * 商品種別：試供品以外で購入限度回数が入力されている場合エラー：PKG-3559-003-S-
     */
    public static final String MSGCD_INPUT_SAMPLE_LIMIT = "PKG-3559-003-S-";
    /**
     * 商品種別：試供品以外で試供品のみ購入選択の場合エラー：PKG-3559-004-S-
     */
    public static final String MSGCD_SELECT_ONLY_BUY = "PKG-3559-004-S-";

    /**
     * 0～99999999円の範囲外の場合エラー：SGP001053E
     */
    public static final String MSGCD_PRICE_LIMIT_OUT = "SGP001053E";

    /**
     * 税率未入力エラー
     */
    public static final String MSGCD_NOSET_TAX_RATE = "PKG-3680-019-S-";
    /**
     * 税率設定値エラー
     */
    public static final String MSGCD_TAX_RATE_OUT = "PKG-3680-021-S-";
    /**
     * 税率
     */
    public static final String MSG_ARGS_TAX_RATE = "税率";

    // 2023-renew No76 from here
    /**
     * 規格画像有無が設定されていない場合エラー
     */
    public static final String MSGCD_NOSET_UNITIMAGEFLAG = "PDR-2023RENEW-76-001-";
    // 2023-renew No76 to here

    /**
     * 実行メソッド<br/>
     *
     * @param goodsGroupDto           商品グループDto
     * @param goodsRelationEntityList 関連商品エンティティリスト
     * @param processType             処理種別（画面/CSV）
     * @param commonCsvInfoMap        CSVアップロード共通情報
     */
    void execute(GoodsGroupDto goodsGroupDto,
                 List<GoodsRelationEntity> goodsRelationEntityList,
                 // 2023-renew No21 from here
                 List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                 // 2023-renew No21 to here
                 int processType,
                 Map<String, Object> commonCsvInfoMap);
}
