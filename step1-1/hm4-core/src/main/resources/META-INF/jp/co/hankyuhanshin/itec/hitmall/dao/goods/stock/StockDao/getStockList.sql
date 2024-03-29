SELECT
    stock.goodsseq,
    stock.shopseq,
	CASE WHEN goods.stockManagementFlag = '1' THEN (stock.realStock - stock.orderreservestock - stockSetting.safetystock) ELSE 0 END AS salesPossibleStock,
    stock.realstock,
    CASE WHEN goods.stockManagementFlag = '1' THEN stock.orderreservestock ELSE 0 END AS orderreservestock,
    stocksetting.remainderfewstock,
    stocksetting.orderpointstock,
    stocksetting.safetystock,
    stocksetting.registtime,
    stocksetting.updatetime
FROM
    Stock stock,
    StockSetting stocksetting,
    Goods goods
WHERE
    stock.goodsseq = stocksetting.goodsseq
    AND stock.goodsseq in /*goodsSeqList*/(1,2,3)
    AND stock.goodsseq = goods.goodsseq
