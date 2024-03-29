SELECT
    *
FROM
    goodsranking
WHERE
/*%if shopSeq != null*/
    shopSeq = /*shopSeq*/0 AND
/*%end*/
   goodsrankingtype = '0'
ORDER BY totalvalue DESC , goodsgroupseq DESC
