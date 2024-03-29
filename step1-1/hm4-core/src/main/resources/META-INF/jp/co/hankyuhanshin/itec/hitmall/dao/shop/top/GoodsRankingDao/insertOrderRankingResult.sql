INSERT INTO goodsranking(
            shopseq, goodsrankingtype, goodsgroupseq, totalvalue, totaltargetname, registtime, 
            updatetime)
(
SELECT 
    /*shopSeq*/0, 
    '1', 
    goodsgroup.goodsgroupseq AS goodsGroupSeq,
    SUM(accessinfo.accesscount) AS goodsCount, 
    MAX(goodsgroup.goodsgroupname) AS goodsGroupName, 
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM accessinfo,goods,goodsgroup
WHERE 
    /*%if shopSeq != null*/
    accessinfo.shopSeq = /*shopSeq*/0 AND 
    /*%end*/
    goods.goodsgroupseq = goodsgroup.goodsgroupseq AND 
    accessinfo.goodsgroupseq = goods.goodsseq AND 
    accessinfo.accessdate >= CURRENT_TIMESTAMP + cast(/*totalPeriod*/0 as interval) AND 
    accessinfo.accesstype = 'G004' 
GROUP BY goodsgroup.goodsgroupseq
ORDER BY goodsCount DESC , goodsGroupSeq DESC
LIMIT /*limit*/0
)
