UPDATE stock
SET realstock = realstock + orderGoodsCount.sumGoodsCount,
    updatetime = CURRENT_TIMESTAMP
FROM (SELECT orderGoods.goodsseq,
    CASE WHEN goods.stockmanagementflag = '1' then
        orderGoods.sumGoodsCount
    ELSE
        0
    END as sumGoodsCount
    FROM
        (SELECT goodsseq,
        SUM(pregoodscount) - SUM(goodscount) as sumGoodsCount
        FROM ordergoods
        WHERE orderseq = /*orderSeq*/0
        AND ordergoodsversionno = /*orderGoodsVersionNo*/0
        AND orderconsecutiveno = /*orderConsecutiveNo*/0
        AND goodscount - pregoodscount < 0
        GROUP BY goodsseq) as orderGoods, goods
    WHERE orderGoods.goodsseq = goods.goodsseq) as orderGoodsCount
WHERE orderGoodsCount.goodsseq = stock.goodsseq
