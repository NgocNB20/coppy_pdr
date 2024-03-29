SELECT DISTINCT
	goods.goodsSeq,
	goodsgroup.goodsGroupName,
	goods.goodsCode,
	goods.saleStatusPC,
	goods.saleStartTimePC,
	goods.saleEndTimePC,
	goods.unitManagementFlag,
	goods.unitValue1,
	goods.unitValue2,
	goods.stockManagementFlag,
	(stock.realStock - stock.orderreservestock - stockSetting.safetystock) AS salesPossibleStock,
	stock.realStock,
	stock.orderReserveStock,
	stocksetting.remainderFewStock,
	stocksetting.orderPointStock,
	stocksetting.safetyStock,
	goodsgroup.goodsGroupCode,
	goodsgroup.goodsOpenStatusPC,
	goodsgroup.openStartTimePC,
	goodsgroup.openEndTimePC,
	goodsgroupdisplay.unitManagementFlag
FROM
    goodsgroup
    inner join
    goodsgroupdisplay on goodsgroup.goodsgroupseq = goodsgroupdisplay.goodsgroupseq
/*%if conditionDto.categoryPath != null*/
    inner join
	categorygoods on goodsgroup.goodsgroupseq = categorygoods.goodsgroupseq
    inner join
	category on categorygoods.categoryseq = category.categoryseq
/*%end*/
    inner join
    goods on goods.goodsgroupseq = goodsgroup.goodsgroupseq
    inner join
    stock on goods.goodsseq = stock.goodsseq
    inner join
    stocksetting on stock.goodsseq = stocksetting.goodsseq
WHERE
goodsgroup.shopseq = /*conditionDto.shopSeq*/0
AND goods.shopSeq = /*conditionDto.shopSeq*/0

AND goods.stockManagementFlag = '1'
/*%if conditionDto.keywordLikeCondition1 != null*/
AND
	goodsgroupdisplay.searchKeyword LIKE '%' || /*conditionDto.keywordLikeCondition1*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition2 != null*/
AND
	goodsgroupdisplay.searchKeyword LIKE '%' || /*conditionDto.keywordLikeCondition2*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition3 != null*/
AND
	goodsgroupdisplay.searchKeyword LIKE '%' || /*conditionDto.keywordLikeCondition3*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition4 != null*/
AND
	goodsgroupdisplay.searchKeyword LIKE '%' || /*conditionDto.keywordLikeCondition4*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition5 != null*/
AND
	goodsgroupdisplay.searchKeyword LIKE '%' || /*conditionDto.keywordLikeCondition5*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition6 != null*/
AND
	goodsgroupdisplay.searchKeyword LIKE '%' || /*conditionDto.keywordLikeCondition6*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition7 != null*/
AND
	goodsgroupdisplay.searchKeyword LIKE '%' || /*conditionDto.keywordLikeCondition7*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition8 != null*/
AND
	goodsgroupdisplay.searchKeyword LIKE '%' || /*conditionDto.keywordLikeCondition8*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition9 != null*/
AND
	goodsgroupdisplay.searchKeyword LIKE '%' || /*conditionDto.keywordLikeCondition9*/0 || '%'
/*%end*/
/*%if conditionDto.keywordLikeCondition10 != null*/
AND
	goodsgroupdisplay.searchKeyword LIKE '%' || /*conditionDto.keywordLikeCondition10*/0 || '%'
/*%end*/
/*%if conditionDto.categoryPath != null*/
AND
	category.categoryPath LIKE /*conditionDto.categoryPath*/0 || '%'
/*%end*/
/*%if conditionDto.goodsGroupCode != null*/
AND
	goodsgroup.goodsGroupCode LIKE '%' || /*conditionDto.goodsGroupCode*/0 || '%'
/*%end*/
/*%if conditionDto.goodsCode != null*/
AND
	goods.goodsCode LIKE '%' || /*conditionDto.goodsCode*/0 || '%'
/*%end*/
/*%if conditionDto.janCode != null*/
AND (goods.janCode LIKE '%' || /*conditionDto.janCode*/0 || '%')
/*%end*/
/*%if conditionDto.multiCode == "0" && conditionDto.multiCodeList != null*/
  AND goodsgroup.goodsGroupCode IN /*conditionDto.multiCodeList*/(1,2,3)
/*%end*/
/*%if conditionDto.multiCode == "1" && conditionDto.multiCodeList != null*/
  AND goods.goodsCode IN /*conditionDto.multiCodeList*/(1,2,3)
/*%end*/
/*%if conditionDto.multiCode == "2" && conditionDto.multiCodeList != null*/
  AND (goods.janCode IN /*conditionDto.multiCodeList*/(1,2,3))
/*%end*/
/*%if conditionDto.goodsGroupName != null*/
AND (goodsgroup.goodsgroupName LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%'
    OR goods.unitValue1 LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%'
    OR goods.unitValue2 LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%')
/*%end*/
/*%if conditionDto.individualDeliveryType != null && conditionDto.individualDeliveryType.value != "0"*/
AND
    goods.individualDeliveryType = /*conditionDto.individualDeliveryType.value*/0
/*%end*/
/*%if conditionDto.minPrice != null*/
AND
	goods.goodsPrice >= /*conditionDto.minPrice*/0
/*%end*/
/*%if conditionDto.maxPrice != null*/
AND
	goods.goodsPrice <= /*conditionDto.maxPrice*/0
/*%end*/


/*%if conditionDto.site != "2" */
AND (
		/*%if conditionDto.goodsOpenStatus != null*/
			/*%if conditionDto.saleStatus != null*/
				( goodsgroup.goodsOpenStatusPC = /*conditionDto.goodsOpenStatus.value*/0 /*%if conditionDto.deleteStatusDsp*/ OR goodsgroup.goodsOpenStatusPC = '9' /*%end*/) AND goods.saleStatusPC = /*conditionDto.saleStatus.value*/0
			/*%else*/
				( goodsgroup.goodsOpenStatusPC = /*conditionDto.goodsOpenStatus.value*/0 /*%if conditionDto.deleteStatusDsp*/ OR goodsgroup.goodsOpenStatusPC = '9' /*%end*/) AND goods.saleStatusPC != '9'
			/*%end*/
		/*%else*/
			/*%if conditionDto.saleStatus != null*/
				( goodsgroup.goodsOpenStatusPC != '9' /*%if conditionDto.deleteStatusDsp*/ OR goodsgroup.goodsOpenStatusPC = '9' /*%end*/) AND goods.saleStatusPC = /*conditionDto.saleStatus.value*/0
			/*%else*/
				( goodsgroup.goodsOpenStatusPC != '9' /*%if conditionDto.deleteStatusDsp*/ OR goodsgroup.goodsOpenStatusPC = '9' /*%end*/) AND goods.saleStatusPC != '9'
			/*%end*/
		/*%end*/

		/*%if conditionDto.openStartTimeFrom != null*/ AND goodsgroup.openStartTimePC >= /*conditionDto.openStartTimeFrom*/0 /*%end*/
		/*%if conditionDto.openStartTimeTo != null*/ AND goodsgroup.openStartTimePC <= /*conditionDto.openStartTimeTo*/0 /*%end*/
		/*%if conditionDto.openEndTimeFrom != null*/ AND goodsgroup.openEndTimePC >= /*conditionDto.openEndTimeFrom*/0 /*%end*/
		/*%if conditionDto.openEndTimeTo != null*/ AND goodsgroup.openEndTimePC <= /*conditionDto.openEndTimeTo*/0 /*%end*/
		/*%if conditionDto.saleStartTimeFrom != null*/ AND goods.saleStartTimePC >= /*conditionDto.saleStartTimeFrom*/0 /*%end*/
		/*%if conditionDto.saleStartTimeTo != null*/ AND goods.saleStartTimePC <= /*conditionDto.saleStartTimeTo*/0 /*%end*/
		/*%if conditionDto.saleEndTimeFrom != null*/ AND goods.saleEndTimePC >= /*conditionDto.saleEndTimeFrom*/0 /*%end*/
		/*%if conditionDto.saleEndTimeTo != null*/ AND goods.saleEndTimePC <= /*conditionDto.saleEndTimeTo*/0 /*%end*/
	)
/*%end*/

/*%if conditionDto.stockTypeFlag != null && conditionDto.minStockCount != null*/
	/*%if conditionDto.stockTypeFlag == "0"*/
AND	(stock.realStock - stock.orderreservestock - stockSetting.safetystock) >= /*conditionDto.minStockCount*/0
	/*%end*/
	/*%if conditionDto.stockTypeFlag == "1"*/
AND	stock.realStock >= /*conditionDto.minStockCount*/0
	/*%end*/
	/*%if conditionDto.stockTypeFlag == "2"*/
AND	stockSetting.safetystock >= /*conditionDto.minStockCount*/0
	/*%end*/
	/*%if conditionDto.stockTypeFlag == "3"*/
AND	stockSetting.remainderfewstock >= /*conditionDto.minStockCount*/0
	/*%end*/
	/*%if conditionDto.stockTypeFlag == "4"*/
AND	stockSetting.orderpointstock >= /*conditionDto.minStockCount*/0
	/*%end*/
/*%end*/
/*%if conditionDto.stockTypeFlag != null && conditionDto.maxStockCount != null*/
	/*%if conditionDto.stockTypeFlag == "0"*/
AND	(stock.realStock - stock.orderreservestock - stockSetting.safetystock) <= /*conditionDto.maxStockCount*/0
	/*%end*/
	/*%if conditionDto.stockTypeFlag == "1"*/
AND	stock.realStock <= /*conditionDto.maxStockCount*/0
	/*%end*/
	/*%if conditionDto.stockTypeFlag == "2"*/
AND	stockSetting.safetystock <= /*conditionDto.maxStockCount*/0
	/*%end*/
	/*%if conditionDto.stockTypeFlag == "3"*/
AND	stockSetting.remainderfewstock <= /*conditionDto.maxStockCount*/0
	/*%end*/
	/*%if conditionDto.stockTypeFlag == "4"*/
AND	stockSetting.orderpointstock <= /*conditionDto.maxStockCount*/0
	/*%end*/
/*%end*/

/*%if conditionDto.orderPointStockBelow*/
AND
    stocksetting.orderpointstock >= (stock.realStock - stock.orderreservestock - stockSetting.safetystock)
/*%end*/

/*%if conditionDto.supplementTimeFrom != null && conditionDto.supplementTimeTo != null*/
AND
    goods.goodsseq IN (SELECT distinct goodsseq FROM stockResult WHERE supplementTime BETWEEN /*conditionDto.supplementTimeFrom*/0 AND /*conditionDto.supplementTimeTo*/0 AND supplementcount != 0)
/*%else*/
    /*%if conditionDto.supplementTimeFrom != null*/
AND
    goods.goodsseq IN (SELECT distinct goodsseq FROM stockResult WHERE supplementTime >= /*conditionDto.supplementTimeFrom*/0 AND supplementcount != 0)
    /*%end*/
    /*%if conditionDto.supplementTimeTo != null*/
AND
    goods.goodsseq IN (SELECT distinct goodsseq FROM stockResult WHERE supplementTime <= /*conditionDto.supplementTimeTo*/0 AND supplementcount != 0)
    /*%end*/
 /*%end*/

-- /*************** sort ***************/
ORDER BY
/*%if conditionDto.pageInfo.orderField != null*/
    /*%if conditionDto.pageInfo.orderField == "goodsGroupCode"*/
    goodsgroup.goodsGroupCode /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    , goods.goodsCode /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "goodsCode"*/
    goods.goodsCode /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "goodsGroupName"*/
    goodsgroup.goodsGroupName /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "unitValue1"*/
    goods.unitValue1 /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "unitValue2"*/
    goods.unitValue2 /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "goodsOpenStatusPC"*/
    goodsgroup.goodsOpenStatusPC /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "openStartTimePC"*/
    goodsgroup.openStartTimePC /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "openEndTimePC"*/
    goodsgroup.openEndTimePC /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "goodsSaleStatusPC"*/
    goods.saleStatusPC /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "saleStartTimePC"*/
    goods.saleStartTimePC /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "saleEndTimePC"*/
    goods.saleEndTimePC /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "stockManagementFlag"*/
    goods.stockManagementFlag /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "realStock"*/
    stock.realStock /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "safetyStock"*/
    stocksetting.safetyStock /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "remainderFewStock"*/
    stocksetting.remainderFewStock /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "orderPointStock"*/
    stocksetting.orderPointStock /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "mailCount"*/
    mailCount /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "salesPossibleStock"*/
    salesPossibleStock /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/
/*%else*/
    1 ASC
/*%end*/


