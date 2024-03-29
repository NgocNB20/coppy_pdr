SELECT
	goodsgroup.*
     , preDiscountMaxMin.preDiscountMaxPrice
     , preDiscountMaxMin.preDiscountMinPrice
FROM
	goodsgroup
    LEFT OUTER JOIN (SELECT goodsgroupseq
                          , MAX(prediscountprice) AS preDiscountMaxPrice
                          , MIN(prediscountprice) AS preDiscountMinPrice
                     FROM goods
                     where goods.salestatuspc = '1'
                     GROUP BY goodsgroupseq) AS preDiscountMaxMin
                    ON (preDiscountMaxMin.goodsgroupseq = goodsgroup.goodsgroupseq)
WHERE
	goodsgroup.shopseq = /*shopSeq*/0
	/*%if goodsGroupCode != null && goodsGroupCode != ""*/
		AND goodsgroup.goodsGroupCode = /*goodsGroupCode*/0
	/*%end*/
	/*%if goodsCode != null && goodsCode != ""*/
		AND EXISTS (
			SELECT * FROM goods
			WHERE goodsgroupseq = goodsgroup.goodsgroupseq AND goodscode = /*goodsCode*/0
			-- PDR Migrate Customization from here
			AND goods.emotionpricetype <> '2'
			-- PDR Migrate Customization to here
		)
	/*%end*/
	/*%if openStatus!=null && siteType != null*/
		/*%if siteType.value == "0" || siteType.value == "1" */
			AND (goodsgroup.openstarttimepc <= CURRENT_TIMESTAMP OR goodsgroup.openstarttimepc is null)
			AND (goodsgroup.openendtimepc >= CURRENT_TIMESTAMP OR goodsgroup.openendtimepc is null)
			AND goodsgroup.goodsopenstatuspc = /*openStatus.value*/0
		/*%end*/
	/*%end*/