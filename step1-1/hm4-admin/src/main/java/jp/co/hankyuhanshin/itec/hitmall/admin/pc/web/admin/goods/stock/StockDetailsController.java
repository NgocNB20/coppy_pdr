/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockResultSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.stock.StockDetailsGetByGoodsSeqService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.stock.StockResultListGetService;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.Optional;

/**
 * 在庫詳細画面コントロール
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("goods/stock/details")
@Controller
@SessionAttributes(value = "stockDetailsModel")
@PreAuthorize("hasAnyAuthority('GOODS:4')")
public class StockDetailsController extends AbstractController {

    /**
     * Helper
     */
    private final StockDetailsHelper stockDetailsHelper;

    /**
     * 入庫実績リスト取得サービス<br/>
     */
    private final StockResultListGetService stockResultListGetService;

    /**
     * 在庫詳細情報取得サービス<br/>
     */
    private final StockDetailsGetByGoodsSeqService stockDetailsGetByGoodsSeqService;

    /**
     * 在庫検索：デフォルト：ソート項目
     */
    private static final String DEFAULT_STOCKSEARCH_ORDER_FIELD = "stockResultSeq";

    /**
     * 在庫検索：デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_STOCKSEARCH_ORDER_ASC = false;

    /**
     * 在庫検索：デフォルト：最大表示件数
     */
    private static final int DEFAULT_STOCKSEARCH_LIMIT = 100;

    /**
     * コンストラクター
     *
     * @param stockDetailsHelper
     * @param stockResultListGetService
     * @param stockDetailsGetByGoodsSeqService
     */
    @Autowired
    public StockDetailsController(StockDetailsHelper stockDetailsHelper,
                                  StockResultListGetService stockResultListGetService,
                                  StockDetailsGetByGoodsSeqService stockDetailsGetByGoodsSeqService) {
        this.stockDetailsHelper = stockDetailsHelper;
        this.stockResultListGetService = stockResultListGetService;
        this.stockDetailsGetByGoodsSeqService = stockDetailsGetByGoodsSeqService;
    }

    /**
     * 在庫詳細ダイアログ表示 （Ajax）<br/>
     *
     * @param goodsSeq
     * @return 在庫詳細ダイアログ
     */
    @GetMapping(value = "/ajax")
    @ResponseBody
    public ResponseEntity<StockDetailsModel> doLoadIndex(@RequestParam(required = false) Optional<String> goodsSeq) {
        StockDetailsModel stockDetailsModel = new StockDetailsModel();
        // トップ画面アラートから遷移したとき、商品SEQを取得する。
        if (!goodsSeq.isPresent()) {
            return ResponseEntity.ok(stockDetailsModel);
        }
        String requestGoodsSeq = goodsSeq.get();
        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        stockDetailsModel.setGoodsSeq(conversionUtility.toInteger(requestGoodsSeq));
        // 在庫詳細情報取得実行
        getStockDetailsInformationAjax(stockDetailsModel);
        return ResponseEntity.ok(stockDetailsModel);
    }

    /**
     * 在庫詳細情報取得<br/>
     *
     * @param stockDetailsModel
     */
    private void getStockDetailsInformationAjax(StockDetailsModel stockDetailsModel) {
        // 在庫詳細情報取得サービス実行
        StockDetailsDto stockDetailsDto = stockDetailsGetByGoodsSeqService.execute(stockDetailsModel.getGoodsSeq());
        if (stockDetailsDto == null) {
            return;
        }
        // 入庫実績Dao用検索条件DTOを作成。
        StockResultSearchForDaoConditionDto stockResultSearchForDaoConditionDto =
                        stockDetailsHelper.toStockResultSearchForDaoConditionDtoForSearch(stockDetailsModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(stockResultSearchForDaoConditionDto, null, DEFAULT_STOCKSEARCH_LIMIT,
                                     DEFAULT_STOCKSEARCH_ORDER_FIELD, DEFAULT_STOCKSEARCH_ORDER_ASC
                                    );

        // 入庫実績リスト取得サービスの実行。
        List<StockResultEntity> stockResultEntityList =
                        stockResultListGetService.execute(stockResultSearchForDaoConditionDto);

        // 取得した入庫実績エンティティリストDTOからPageへ設定
        stockDetailsHelper.toPageForStockResult(
                        stockResultEntityList, stockDetailsDto, stockDetailsModel, stockResultSearchForDaoConditionDto);
    }
}
