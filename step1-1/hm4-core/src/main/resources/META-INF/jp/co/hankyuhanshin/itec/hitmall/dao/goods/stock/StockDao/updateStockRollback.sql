UPDATE stock
SET orderreservestock = orderreservestock - orderGoodsCount.sumGoodsCount,
    updatetime = CURRENT_TIMESTAMP
FROM (SELECT goodsseq,
        SUM(goodscount) as sumGoodsCount
    FROM ordergoods
    WHERE orderseq = /*orderSeq*/0
    AND ordergoodsversionno = /*orderGoodsVersionNo*/0
    GROUP BY goodsseq) as orderGoodsCount
WHERE orderGoodsCount.goodsseq = stock.goodsseq
