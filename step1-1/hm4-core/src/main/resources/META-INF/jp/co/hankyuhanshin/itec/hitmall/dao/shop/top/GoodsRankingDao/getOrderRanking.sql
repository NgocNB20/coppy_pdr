SELECT
    *
FROM
    goodsranking
WHERE
/*%if shopSeq != null*/
    shopSeq = /*shopSeq*/0 AND
/*%end*/
   goodsrankingtype = '1'
ORDER BY totalvalue DESC , goodsgroupseq DESC
