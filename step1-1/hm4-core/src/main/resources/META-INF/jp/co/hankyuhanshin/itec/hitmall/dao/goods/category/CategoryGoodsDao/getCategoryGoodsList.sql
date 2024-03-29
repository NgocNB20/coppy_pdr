SELECT *
FROM categorygoods
WHERE goodsGroupSeq in /*goodsGroupSeqList*/(1,2,3)
ORDER BY goodsGroupSeq, orderDisplay
