SELECT
	goodsgroup.*
     , preDiscountMaxMin.preDiscountMaxPrice
     , preDiscountMaxMin.preDiscountMinPrice
	, goods.goodscode
FROM
	goods
	INNER JOIN
	goodsgroup ON( goods.goodsgroupseq = goodsgroup.goodsgroupseq )
LEFT OUTER JOIN
    (SELECT goodsgroupseq
          , MAX(prediscountprice) AS preDiscountMaxPrice
          , MIN(prediscountprice) AS preDiscountMinPrice
     FROM goods
     where goods.salestatuspc = '1'
     GROUP BY goodsgroupseq)
        AS preDiscountMaxMin ON (preDiscountMaxMin.goodsgroupseq = goodsgroup.goodsgroupseq)
WHERE
	goodsgroup.goodsGroupSeq IN /*goodsGroupSeqList*/(0);
