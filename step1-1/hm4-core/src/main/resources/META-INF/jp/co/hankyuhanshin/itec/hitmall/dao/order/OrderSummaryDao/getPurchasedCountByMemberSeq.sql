SELECT
	TMP.goodsGroupSeq
	, COUNT(*) AS cnt
FROM (
	SELECT
		os.orderSeq
		, g.goodsGroupSeq
	FROM
		orderSummary AS os
		INNER JOIN orderIndex AS oi
			ON oi.orderSeq = os.orderSeq
			AND oi.orderVersionNo = os.orderVersionNo
		INNER JOIN orderGoods AS og
			ON og.orderSeq = oi.orderSeq
			AND og.orderGoodsVersionNo = oi.orderGoodsVersionNo
		INNER JOIN goods AS g
			ON g.goodsSeq = og.goodsSeq
	WHERE
		os.shopSeq = /*shopSeq*/0
		AND os.memberInfoSeq = /*memberInfoSeq*/0
		AND os.cancelFlag = '0'
		AND EXISTS (
			SELECT * FROM goods
			WHERE goodsSeq IN /*goodsSeqList*/(0)
			AND goodsGroupSeq = g.goodsGroupSeq
		)
	GROUP BY os.orderSeq, g.goodsGroupSeq
) AS TMP
GROUP BY TMP.goodsGroupSeq
