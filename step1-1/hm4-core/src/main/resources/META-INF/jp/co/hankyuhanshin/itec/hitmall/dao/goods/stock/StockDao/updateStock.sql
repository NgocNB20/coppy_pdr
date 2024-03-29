UPDATE stock
SET orderreservestock = orderreservestock + orderGoodsCount.sumGoodsCount,
    updatetime = CURRENT_TIMESTAMP
FROM (SELECT
        ordergoods.goodsseq AS goodsseq,
        MAX(goods.stockmanagementflag) AS stockmanagementflag,
        SUM(ordergoods.goodscount) AS sumGoodsCount
    FROM ordergoods, goods
    WHERE orderseq = /*orderSeq*/0
    AND ordergoodsversionno = /*orderGoodsVersionNo*/0
    AND ordergoods.goodsseq = goods.goodsseq
    GROUP BY ordergoods.goodsseq) as orderGoodsCount, stockSetting
WHERE orderGoodsCount.goodsseq = stock.goodsseq
AND stock.goodsseq = stockSetting.goodsseq
AND
(
    (
    orderGoodsCount.stockmanagementflag = '1'
    AND stock.realstock - stockSetting.safetystock - (stock.orderreservestock + orderGoodsCount.sumGoodsCount) >= 0
    )
    OR
    (
    orderGoodsCount.stockmanagementflag = '0'
    )
)
