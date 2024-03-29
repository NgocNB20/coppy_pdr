select distinct
  goodsgroup.goodsGroupSeq,
  goodsgroup.goodsGroupCode,
  -- 2023-renew No64 from here
  goodsgroup.goodsGroupNameAdmin,
  -- 2023-renew No64 to here
  goodsgroup.goodsGroupName,
--   2023-renew No64 from here
  goodsgroup.goodsGroupName1,
  goodsgroup.goodsGroupName2,
  goodsgroup.goodsGroupName1OpenStartTime,
  goodsgroup.goodsGroupName2OpenStartTime,
--   2023-renew No64 to here
  goods.individualDeliveryType,
--   2023-renew AddNo5 from here
  goods.goodsPriceInTaxHight as goodsPriceInTax,
  goods.preDiscountPriceHight as preDiscountPrice,
--   2023-renew AddNo5 to here
  goods.freeDeliveryFlag,
  goodsgroupdisplay.deliverytype,
  goodsgroup.goodsOpenStatusPC,
  goodsgroup.openStartTimePC,
  goodsgroup.openEndTimePC,
  goodsgroup.whatsnewDate,
  array_to_string(ARRAY(select category.categoryId from category, categorygoods where category.categorySeq = categorygoods.categorySeq AND categorygoods.goodsgroupseq = goodsgroup.goodsgroupseq ORDER BY category.categoryId), '/') as categoryGoodsSetting,
  goodsgroupdisplay.goodsNote1,
--   2023-renew No64 from here
  goodsgroupdisplay.goodsNote1Sub,
  goodsgroupdisplay.goodsNote1OpenStartTime,
  goodsgroupdisplay.goodsNote1SubOpenStartTime,
--   2023-renew No64 to here
  goodsgroupdisplay.goodsNote2,
--   2023-renew No64 from here
  goodsgroupdisplay.goodsNote2Sub,
  goodsgroupdisplay.goodsNote2OpenStartTime,
  goodsgroupdisplay.goodsNote2SubOpenStartTime,
--   2023-renew No64 to here
  goodsgroupdisplay.goodsNote3,
  goodsgroupdisplay.goodsNote4,
--   2023-renew No64 from here
  goodsgroupdisplay.goodsNote4Sub,
  goodsgroupdisplay.goodsNote4OpenStartTime,
  goodsgroupdisplay.goodsNote4SubOpenStartTime,
--   2023-renew No64 to here
  goodsgroupdisplay.goodsNote5,
  goodsgroupdisplay.goodsNote6,
  goodsgroupdisplay.goodsNote7,
  goodsgroupdisplay.goodsNote8,
  goodsgroupdisplay.goodsNote9,
  goodsgroupdisplay.goodsNote10,
--   2023-renew No64 from here
  goodsgroupdisplay.goodsNote10Sub,
  goodsgroupdisplay.goodsNote10OpenStartTime,
  goodsgroupdisplay.goodsNote10SubOpenStartTime,
--   2023-renew No64 to here
  goodsgroupdisplay.goodsNote11,
  goodsgroupdisplay.goodsNote12,
  goodsgroupdisplay.goodsNote13,
--2023-renew No0 from here
  goodsgroupdisplay.goodsNote14,
  goodsgroupdisplay.goodsNote15,
  goodsgroupdisplay.goodsNote16,
  goodsgroupdisplay.goodsNote17,
  goodsgroupdisplay.goodsNote18,
  goodsgroupdisplay.goodsNote19,
  goodsgroupdisplay.goodsNote20,
--2023-renew No11 from here
  goodsgroupdisplay.goodsNote21,
--2023-renew No11 to here
  goodsgroupdisplay.goodsNote22,
--2023-renew No0 to here
  goodsgroupdisplay.orderSetting1,
  goodsgroupdisplay.orderSetting2,
  goodsgroupdisplay.orderSetting3,
  goodsgroupdisplay.orderSetting4,
  goodsgroupdisplay.orderSetting5,
  goodsgroupdisplay.orderSetting6,
  goodsgroupdisplay.orderSetting7,
  goodsgroupdisplay.orderSetting8,
  goodsgroupdisplay.orderSetting9,
  goodsgroupdisplay.orderSetting10,
  -- 2023-renew searchKeyword-addition from here
  goodsgroupdisplay.searchKeyword,
  -- 2023-renew searchKeyword-addition to here
  goodsgroupdisplay.metaDescription,
  goodsgroupdisplay.metaKeyword,
  goodsgroup.goodsprediscountprice,
  goodsgroupdisplay.unitManagementFlag,
  goodsgroupdisplay.unitTitle1,
  goodsgroupdisplay.unitTitle2,
  goods.goodsCode,
  goods.orderDisplay,
  goods.janCode,
  goods.catalogCode,
  goods.unitValue1,
  goods.unitValue2,
  -- 2023-renew No76 from here
  goods.unitImageFlag,
  -- 2023-renew No76 to here
  goods.saleStatusPC,
  goods.saleStartTimePC,
  goods.saleEndTimePC,
  --   2023-renew AddNo5 from here
--   goods.preDiscountPrice,
  --   2023-renew AddNo5 to here
  goods.purchasedMax,
  goods.stockManagementFlag,
  -- PDR Migrate Customization from here
  goodsgroup.goodsClassType,
  goodsgroup.groupPrice,
  goodsgroup.groupSalePrice,
  goodsgroup.groupPriceMarkDispFlag,
  goodsgroup.groupSalePriceMarkDispFlag,
  goodsgroup.catalogDisplayOrder,
  goods.reserveFlag,
  goods.goodsManagementCode,
  goods.goodsDivisionCode,
  goods.goodsCategory1,
  goods.goodsCategory2,
  goods.goodsCategory3,
  goods.landSendFlag,
  goods.coolSendFlag,
  goods.coolSendFrom,
  goods.coolSendTo,
  goods.unit,
  goods.priceMarkDispFlag,
  goods.salePriceMarkDispFlag,
  goods.emotionpricetype,
  goodsgroup.dentalMonopolySalesFlg,
  -- PDR Migrate Customization to here
  stocksetting.remainderFewStock,
  stocksetting.orderPointStock,
  stocksetting.safetyStock,
  stock.realstock,
  CASE WHEN goods.stockManagementFlag = '1' THEN (stock.realStock - stock.orderreservestock - stockSetting.safetystock) ELSE 0 END AS salesPossibleStock,
  array_to_string(ARRAY(select gg.goodsGroupCode from goodsrelation, goodsgroup gg where goodsrelation.goodsRelationGroupSeq = gg.goodsGroupSeq AND goodsrelation.goodsgroupseq = goodsgroup.goodsgroupseq ORDER BY goodsrelation.orderDisplay ASC), '/') as goodsRelationGoodsGroupCode,
  --2023-renew No21 from here
  array_to_string(ARRAY(select gg.goodsGroupCode from goodstogetherbuy, goodsgroup gg where goodstogetherbuy.goodstogetherbuygroupseq = gg.goodsGroupSeq AND goodstogetherbuy.goodsgroupseq = goodsgroup.goodsgroupseq ORDER BY goodstogetherbuy.orderDisplay ASC), '/') as goodsTogetherBuyGroupGoodsGroupCode,
  --2023-renew No21 to here
  goodsgroup.snsLinkFlag,
  goodsgroup.taxRate,
  goodsgroup.alcoholFlag

from
  GoodsGroup goodsgroup
  , GoodsGroupDisplay goodsgroupdisplay
  , Goods goods
  , StockSetting stocksetting
  , Stock stock
where
  goodsgroup.goodsgroupseq = goodsgroupdisplay.goodsgroupseq
  AND goodsgroup.goodsgroupseq = goods.goodsgroupseq
  AND goods.goodsSeq = stocksetting.goodsSeq
  AND goods.goodsSeq = stock.goodsSeq
  -- PDR Migrate Customization from here
  AND goods.emotionpricetype <> '2'
  -- PDR Migrate Customization to here
/*%if goodsSeqList != null*/
  AND goods.goodsSeq in /*goodsSeqList*/(0)
/*%end*/

/*************** sort ***************/
order by goodsgroup.goodsGroupCode ASC, goods.goodsCode ASC
