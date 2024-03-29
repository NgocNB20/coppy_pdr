select
	goods.goodsSeq,
	goods.goodsGroupSeq,
	goods.shopSeq,
    goods.goodsCode,
    goods.saleStatusPC,
    goods.saleStartTimePC,
    goods.saleEndTimePC,
    goods.stockManagementFlag,
    goods.unitValue1,
    goods.unitValue2,
    goods.janCode,
    stock.updateTime,
    stock.realStock,
    stock.orderReserveStock,
    stocksetting.remainderFewStock,
    stocksetting.orderPointStock,
    stocksetting.safetyStock,
    goodsgroup.goodsGroupCode,
    goodsgroup.goodsgroupName,
    goods.goodsPrice,
    goodsgroup.goodsOpenStatusPC,
    goodsgroup.openStartTimePC,
    goodsgroup.openEndTimePC,
    goodsgroupdisplay.unitTitle1,
    goodsgroupdisplay.unitTitle2,
	(stock.realStock - stock.orderreservestock - stockSetting.safetystock) AS salesPossibleStock
from
    goodsgroup
    inner join
    goodsgroupdisplay on goodsgroup.goodsgroupseq = goodsgroupdisplay.goodsgroupseq
    inner join
    goods on goodsgroup.goodsgroupseq = goods.goodsgroupseq
    inner join
    stock on goods.goodsseq = stock.goodsseq
    inner join
    stocksetting on stock.goodsseq = stocksetting.goodsseq
where
    goods.goodsseq = /*goodsSeq*/0
    and goods.stockManagementFlag = '1'

