INSERT INTO goodsranking(
            shopseq, goodsrankingtype, goodsgroupseq, totalvalue, totaltargetname, registtime, 
            updatetime)
(
SELECT 
    /*shopSeq*/0, 
    '0', 
    accessinfo.goodsgroupseq AS goodsGroupSeq,
    SUM(accessinfo.accesscount) AS clickCount, 
    MAX(goodsgroup.goodsgroupname) AS goodsGroupName, 
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM accessinfo,goodsgroup
WHERE 
    /*%if shopSeq != null*/
    accessinfo.shopSeq = /*shopSeq*/0 AND 
    /*%end*/
    accessinfo.goodsgroupseq = goodsgroup.goodsgroupseq AND 
    accessinfo.accessdate >= CURRENT_TIMESTAMP + cast(/*totalPeriod*/0 as interval) AND 
    accessinfo.accesstype = 'G001' 
GROUP BY accessinfo.goodsgroupseq
ORDER BY clickCount DESC , goodsGroupSeq DESC
LIMIT /*limit*/0
)
