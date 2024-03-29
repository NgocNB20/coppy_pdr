SELECT DISTINCT
	goodsgroup.goodsGroupseq
	, goodsgroup.goodsGroupCode
	, goodsgroup.goodsOpenStatusPC
	, goodsgroup.openStartTimePC
	, goodsgroup.openEndTimePC
	-- 2023-renew No64 from here
	, goodsgroup.goodsGroupNameAdmin
	-- 2023-renew No64 to here
	--2023-renew No21 from here
	/*%if !conditionDto.relationGoodsSearchFlag && !conditionDto.goodsTogetherBuyGroupSearchFlag*/
    --2023-renew No21 to here
		, goods.goodsPrice
		, goods.goodsSeq
		, goods.goodsCode
		, goods.unitValue1
		, goods.unitValue2
		, goods.saleStatusPC
		, goods.saleStartTimePC
		, goods.saleEndTimePC
		, goods.individualDeliveryType
		, goods.stockmanagementflag
		, CASE WHEN goods.stockManagementFlag = '1' THEN (stock.realStock - stock.orderreservestock - stockSetting.safetystock) ELSE 0 END AS salesPossibleStock
		, stock.realStock
	/*%end*/
	--2023-renew No21 from here
    /*%if conditionDto.goodsTogetherBuyGroupSearchFlag*/
    , goodstogetherbuy.registmethod
    /*%end*/
    --2023-renew No21 to here
FROM
  GoodsGroup goodsgroup
  --2023-renew No21 from here
  /*%if conditionDto.goodsTogetherBuyGroupSearchFlag*/
  left join goodstogetherbuy goodstogetherbuy on goodsgroup.goodsgroupseq  = goodstogetherbuy.goodsgroupseq
  /*%end*/
  --2023-renew No21 to here
  , GoodsGroupDisplay goodsgroupdisplay
  , Goods goods
/*%if conditionDto.categoryPath != null*/
  , CategoryGoods categorygoods
  , Category category
/*%end*/
  , Stock stock
  , StockSetting stockSetting
where
  goodsgroup.goodsgroupseq = goodsgroupdisplay.goodsgroupseq
  AND goodsgroup.goodsgroupseq = goods.goodsgroupseq
  AND goods.goodsSeq = stock.goodsSeq
  AND goods.goodsSeq = stockSetting.goodsSeq
/*%if conditionDto.categoryPath != null*/
  AND goodsgroup.goodsGroupSeq = categorygoods.goodsGroupSeq
  AND categorygoods.categorySeq = category.categorySeq
/*%end*/
--2023-renew No21 from here
/*%if conditionDto.goodsTogetherBuyGroupSearchFlag*/
  AND goodsgroup.excludingflag = '0'
/*%end*/
--2023-renew No21 to here
/*%if conditionDto.shopSeq != null*/
  AND goodsgroup.shopSeq = /*conditionDto.shopSeq*/0
  AND goods.shopSeq = /*conditionDto.shopSeq*/0
/*%end*/
/*%if conditionDto.keywordLikeCondition1 != null*/
  AND goodsgroupdisplay.searchKeyword Like '%' || /*conditionDto.keywordLikeCondition1*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition2 != null*/
  AND goodsgroupdisplay.searchKeyword Like '%' || /*conditionDto.keywordLikeCondition2*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition3 != null*/
  AND goodsgroupdisplay.searchKeyword Like '%' || /*conditionDto.keywordLikeCondition3*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition4 != null*/
  AND goodsgroupdisplay.searchKeyword Like '%' || /*conditionDto.keywordLikeCondition4*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition5 != null*/
  AND goodsgroupdisplay.searchKeyword Like '%' || /*conditionDto.keywordLikeCondition5*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition6 != null*/
  AND goodsgroupdisplay.searchKeyword Like '%' || /*conditionDto.keywordLikeCondition6*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition7 != null*/
  AND goodsgroupdisplay.searchKeyword Like '%' || /*conditionDto.keywordLikeCondition7*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition8 != null*/
  AND goodsgroupdisplay.searchKeyword Like '%' || /*conditionDto.keywordLikeCondition8*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition9 != null*/
  AND goodsgroupdisplay.searchKeyword Like '%' || /*conditionDto.keywordLikeCondition9*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition10 != null*/
  AND goodsgroupdisplay.searchKeyword Like '%' || /*conditionDto.keywordLikeCondition10*/0 || '%'
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
/*%if conditionDto.catalogCode != null*/
  AND goods.catalogCode LIKE '%' || /*conditionDto.catalogCode*/0 || '%'
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
/*%if conditionDto.multiCode == "2" && conditionDto.multiCodeList != null*/
  AND goods.catalogCode IN /*conditionDto.multiCodeList*/(0)
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
AND (
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
  -- PDR Migrate Customization from here
  AND goods.emotionpricetype <> '2'
  -- PDR Migrate Customization to here

/*************** sort ***************/
ORDER BY
/*%if conditionDto.pageInfo.orderField != null*/

/*%if conditionDto.pageInfo.orderField == "goodsGroupCode"*/
goodsgroup.goodsGroupCode /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    --2023-renew No21 from here
	/*%if !conditionDto.relationGoodsSearchFlag && !conditionDto.goodsTogetherBuyGroupSearchFlag*/
	--2023-renew No21 to here
		, goods.goodsCode /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
	/*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "goodsCode"*/
goods.goodsCode /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "goodsGroupName"*/
-- 2023-renew No64 from here
goodsgroup.goodsGroupNameAdmin /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
-- 2023-renew No64 to here
/*%end*/

/*%if conditionDto.pageInfo.orderField == "unitValue1"*/
goods.unitValue1 /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "unitValue2"*/
goods.unitValue2 /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "goodsOpenStatusPC"*/
goodsgroup.goodsOpenStatusPC /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "goodsOpenStartTimePC"*/
goodsgroup.openStartTimePC /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "goodsOpenEndTimePC"*/
goodsgroup.openEndTimePC /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "saleStatusPC"*/
goods.saleStatusPC /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "saleStartTimePC"*/
goods.saleStartTimePC /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "saleEndTimePC"*/
goods.saleEndTimePC /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "goodsPrice"*/
goods.goodsPrice /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "salesPossibleStock"*/
salesPossibleStock /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "realStock"*/
stock.realStock /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "individualDeliveryType"*/
goods.individualDeliveryType /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%else*/
    1 ASC
/*%end*/
