UPDATE stock
SET
	/*%if reserveMode == 2 */
	orderreservestock = orderreservestock - (differenceOrderGoodsCount.sumGoodsCount - differenceOrderGoodsCount.preSumGoodsCount),
	/*%else*/ orderreservestock = orderreservestock + (differenceOrderGoodsCount.sumGoodsCount - differenceOrderGoodsCount.preSumGoodsCount),
	/*%end*/
	updatetime = CURRENT_TIMESTAMP
FROM
(
SELECT
  CASE
    WHEN orderGoodsCount.goodsseq > 0 THEN orderGoodsCount.goodsseq
    ELSE preOrderGoodsCount.goodsseq
    END AS goodsseq ,
  CASE
    WHEN preOrderGoodsCount.sumGoodsCount > 0 THEN preOrderGoodsCount.sumGoodsCount
    ELSE 0
    END AS preSumGoodsCount ,
  CASE
    WHEN orderGoodsCount.sumGoodsCount > 0 THEN orderGoodsCount.sumGoodsCount
    ELSE 0
    END AS sumGoodsCount
FROM
    (SELECT goodsseq,
        SUM(goodscount) AS sumGoodsCount
    FROM ordergoods
    WHERE orderseq = /*preOrderSeq*/0
    AND ordergoodsversionno = /*preOrderGoodsVersionNo*/0
    AND orderconsecutiveno = /*orderConsecutiveNo*/0
    GROUP BY goodsseq) AS preOrderGoodsCount
    FULL JOIN
    (SELECT goodsseq,
        SUM(goodscount) AS sumGoodsCount
    FROM ordergoods
    WHERE orderseq = /*orderSeq*/0
    AND ordergoodsversionno = /*orderGoodsVersionNo*/0
    AND orderconsecutiveno = /*orderConsecutiveNo*/0
    GROUP BY goodsseq) AS orderGoodsCount
    ON preOrderGoodsCount.goodsseq = orderGoodsCount.goodsseq
) AS differenceOrderGoodsCount , goods , stocksetting
WHERE differenceOrderGoodsCount.goodsseq = stock.goodsseq
AND stocksetting.goodsseq = stock.goodsseq
AND goods.goodsseq = stock.goodsseq
/*%if reserveMode == 1 */
AND
(
    (
     goods.stockmanagementflag = '1'
     AND stock.realstock - stocksetting.safetystock - (stock.orderreservestock + differenceOrderGoodsCount.sumGoodsCount - differenceOrderGoodsCount.preSumGoodsCount) >= 0
    )
    OR
    (
     goods.stockmanagementflag = '0'
    )
)
/*%end*/
/*%if reserveMode > 0 */
/*%if reserveMode == 3 */
AND differenceOrderGoodsCount.sumGoodsCount < differenceOrderGoodsCount.preSumGoodsCount
/*%else*/ AND differenceOrderGoodsCount.sumGoodsCount > differenceOrderGoodsCount.preSumGoodsCount
/*%end*/
/*%end*/
