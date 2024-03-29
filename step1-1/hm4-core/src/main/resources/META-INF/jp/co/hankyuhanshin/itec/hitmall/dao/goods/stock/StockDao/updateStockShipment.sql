UPDATE stock
SET
    realstock = realstock - orderGoodsCount.realUpGoodsCont, orderreservestock = orderreservestock - orderGoodsCount.sumGoodsCount,
    updatetime = CURRENT_TIMESTAMP
FROM (  SELECT orderGoods.goodsseq,
    CASE WHEN goods.stockmanagementflag = '1' then
        orderGoods.sumGoodsCount
    ELSE
        0
    END as realUpGoodsCont,
    orderGoods.sumGoodsCount as sumGoodsCount
    FROM
        (SELECT goodsseq,
        /*%if mode == 1*/
        SUM(goodscount) - SUM(pregoodscount) as sumGoodsCount
        /*%else*/
        SUM(goodscount) as sumGoodsCount
        /*%end*/
        FROM ordergoods
        WHERE orderseq = /*orderSeq*/0
        AND ordergoodsversionno = /*orderGoodsVersionNo*/0
        AND orderconsecutiveno = /*orderConsecutiveNo*/0
        /*%if mode == 1*/
        AND goodscount - pregoodscount > 0
        /*%else*/
        AND goodscount > 0
        /*%end*/
        GROUP BY goodsseq) as orderGoods, goods
    WHERE orderGoods.goodsseq = goods.goodsseq) orderGoodsCount
WHERE orderGoodsCount.goodsseq = stock.goodsseq
