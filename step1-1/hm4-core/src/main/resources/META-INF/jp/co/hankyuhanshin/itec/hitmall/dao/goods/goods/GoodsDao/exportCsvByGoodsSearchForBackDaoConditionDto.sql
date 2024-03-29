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
  --2023-renew No21 from here
  array_to_string(ARRAY(select gg.goodsGroupCode from goodstogetherbuy, goodsgroup gg where goodstogetherbuy.goodstogetherbuygroupseq = gg.goodsGroupSeq AND goodstogetherbuy.goodsgroupseq = goodsgroup.goodsgroupseq ORDER BY goodstogetherbuy.orderDisplay ASC), '/') as goodsTogetherBuyGroupGoodsGroupCode,
  --2023-renew No21 to here
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
--   2023-renew No0 from here
  goodsgroupdisplay.goodsNote14,
  goodsgroupdisplay.goodsNote15,
  goodsgroupdisplay.goodsNote16,
  goodsgroupdisplay.goodsNote17,
  goodsgroupdisplay.goodsNote18,
  goodsgroupdisplay.goodsNote19,
  goodsgroupdisplay.goodsNote20,
--   2023-renew No11 from here
  goodsgroupdisplay.goodsNote21,
--   2023-renew No11 to here
  goodsgroupdisplay.goodsNote22,
--   2023-renew No0 to here
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
  goodsgroup.snsLinkFlag,
  goodsgroup.taxRate,
  goodsgroup.alcoholFlag

from
  GoodsGroup goodsgroup
  , GoodsGroupDisplay goodsgroupdisplay
  , Goods goods
  , StockSetting stocksetting
  , Stock stock
/*%if conditionDto.categoryPath != null*/
  , CategoryGoods categorygoods
  , Category category

/*%end*/
where
  goodsgroup.goodsgroupseq = goodsgroupdisplay.goodsgroupseq
  AND goodsgroup.goodsgroupseq = goods.goodsgroupseq
  AND goods.goodsSeq = stocksetting.goodsSeq
  AND goods.goodsSeq = stock.goodsSeq
  -- PDR Migrate Customization from here
  AND goods.emotionpricetype <> '2'
  -- PDR Migrate Customization to here
/*%if conditionDto.categoryPath != null*/
  AND goodsgroup.goodsGroupSeq = categorygoods.goodsGroupSeq
  AND categorygoods.categorySeq = category.categorySeq
/*%end*/
/*%if conditionDto.shopSeq != null*/
  AND goodsgroup.shopSeq = /*conditionDto.shopSeq*/0
  AND goods.shopSeq = /*conditionDto.shopSeq*/0
/*%end*/
/*%if conditionDto.categoryPath != null*/
  AND category.categoryPath LIKE /*conditionDto.categoryPath*/0 || '%'
/*%end*/
/*%if conditionDto.goodsGroupCode != null*/
  AND goodsgroup.goodsGroupCode LIKE '%' || /*conditionDto.goodsGroupCode*/0 || '%'
/*%end*/
/*%if conditionDto.goodsCode != null*/
  AND goods.goodsCode LIKE '%' || /*conditionDto.goodsCode*/0 || '%'
/*%end*/
/*%if conditionDto.janCode != null*/
  AND goods.janCode LIKE '%' || /*conditionDto.janCode*/0 || '%'
/*%end*/
/*%if conditionDto.multiCode == "0" && conditionDto.multiCodeList != null*/
  AND goodsgroup.goodsGroupCode IN /*conditionDto.multiCodeList*/(0)
/*%end*/
/*%if conditionDto.multiCode == "1" && conditionDto.multiCodeList != null*/
  AND goods.goodsCode IN /*conditionDto.multiCodeList*/(0)
/*%end*/
/*%if conditionDto.multiCode == "2" && conditionDto.multiCodeList != null*/
  AND goods.janCode IN /*conditionDto.multiCodeList*/(0)
/*%end*/
/*%if conditionDto.goodsGroupName != null*/
  -- 2023-renew No64 from here
  AND (goodsgroup.goodsGroupNameAdmin LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%'
  -- 2023-renew No64 to here
       OR goods.unitValue1 LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%'
       OR goods.unitValue2 LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%')
/*%end*/
/*%if conditionDto.individualDeliveryType != null && conditionDto.individualDeliveryType.value != "0"*/
  AND goods.individualDeliveryType = /*conditionDto.individualDeliveryType.value*/0
/*%end*/
/*%if conditionDto.minPrice != null*/
  AND goods.goodsPrice >= /*conditionDto.minPrice*/0
/*%end*/
/*%if conditionDto.maxPrice != null*/
  AND goods.goodsPrice <= /*conditionDto.maxPrice*/0
/*%end*/

/*%if conditionDto.site != "2" */
AND
	(
		/*%if conditionDto.goodsOpenStatusList != null*/
			/*%if conditionDto.saleStatusList != null*/
				goodsgroup.goodsOpenStatusPC IN /*conditionDto.goodsOpenStatusList*/(0) AND goods.saleStatusPC IN /*conditionDto.saleStatusList*/(0)
			/*%else*/
				goodsgroup.goodsOpenStatusPC IN /*conditionDto.goodsOpenStatusList*/(0) AND goods.saleStatusPC != '9'
			/*%end*/
		/*%else*/
			/*%if conditionDto.saleStatusList != null*/
				goodsgroup.goodsOpenStatusPC != '9' AND goods.saleStatusPC IN /*conditionDto.saleStatusList*/(0)
			/*%else*/
				goodsgroup.goodsOpenStatusPC != '9' AND goods.saleStatusPC != '9'
			/*%end*/
		/*%end*/

		/*%if conditionDto.goodsOpenStartTimeFrom != null*/ AND goodsgroup.openStartTimePC >= /*conditionDto.goodsOpenStartTimeFrom*/0 /*%end*/
		/*%if conditionDto.goodsOpenStartTimeTo != null*/ AND goodsgroup.openStartTimePC <= /*conditionDto.goodsOpenStartTimeTo*/0 /*%end*/
		/*%if conditionDto.goodsOpenEndTimeFrom != null*/ AND goodsgroup.openEndTimePC >= /*conditionDto.goodsOpenEndTimeFrom*/0 /*%end*/
		/*%if conditionDto.goodsOpenEndTimeTo != null*/ AND goodsgroup.openEndTimePC <= /*conditionDto.goodsOpenEndTimeTo*/0 /*%end*/
		/*%if conditionDto.saleStartTimeFrom != null*/ AND goods.saleStartTimePC >= /*conditionDto.saleStartTimeFrom*/0 /*%end*/
		/*%if conditionDto.saleStartTimeTo != null*/ AND goods.saleStartTimePC <= /*conditionDto.saleStartTimeTo*/0 /*%end*/
		/*%if conditionDto.saleEndTimeFrom != null*/ AND goods.saleEndTimePC >= /*conditionDto.saleEndTimeFrom*/0 /*%end*/
		/*%if conditionDto.saleEndTimeTo != null*/ AND goods.saleEndTimePC <= /*conditionDto.saleEndTimeTo*/0 /*%end*/
	)
/*%end*/

/*%if conditionDto.registTimeFrom != null*/
  AND goodsgroup.registTime >= /*conditionDto.registTimeFrom*/0
/*%end*/
/*%if conditionDto.registTimeTo != null*/
  AND goodsgroup.registTime <= /*conditionDto.registTimeTo*/0
/*%end*/
/*%if conditionDto.updateTimeFrom != null*/
  AND goodsgroup.updateTime >= /*conditionDto.updateTimeFrom*/0
/*%end*/
/*%if conditionDto.updateTimeTo != null*/
  AND goodsgroup.updateTime <= /*conditionDto.updateTimeTo*/0
/*%end*/
/*%if conditionDto.snsLinkFlag != null*/
  AND goodsgroup.snslinkflag = /*conditionDto.snsLinkFlag.value*/0
/*%end*/
/*%if conditionDto.minSalesPossibleStock != null*/
  AND CASE WHEN goods.stockManagementFlag = '1' THEN (stock.realStock - stock.orderreservestock - stockSetting.safetystock) ELSE 0 END >= /*conditionDto.minSalesPossibleStock*/0
/*%end*/
/*%if conditionDto.maxSalesPossibleStock != null*/
  AND CASE WHEN goods.stockManagementFlag = '1' THEN (stock.realStock - stock.orderreservestock - stockSetting.safetystock) ELSE 0 END <= /*conditionDto.maxSalesPossibleStock*/0
/*%end*/
/*************** sort ***************/
order by goodsgroup.goodsGroupCode ASC, goods.goodsCode ASC
