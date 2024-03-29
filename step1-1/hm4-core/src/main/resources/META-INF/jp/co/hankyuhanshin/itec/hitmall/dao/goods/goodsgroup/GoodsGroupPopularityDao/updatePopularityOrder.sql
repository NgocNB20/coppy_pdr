UPDATE goodsgrouppopularity
   SET popularitycount=xyz.goodsCount, updatetime=CURRENT_TIMESTAMP
FROM
(
    SELECT  (COALESCE(x.goodsCount, 0) + (COALESCE(y.goodsCount, 0) - COALESCE(y.goodsPreCount, 0)) + (COALESCE(z.goodsCount, 0) - COALESCE(z.goodsPreCount, 0)) ) AS goodsCount , goodsgrouppopularity.goodsgroupseq AS goodsgroupseq
    FROM
    goodsgrouppopularity
    LEFT JOIN
    (
        SELECT SUM(a.goodsCount) as goodsCount, goodsgroupseq
        FROM goods,
        (
            SELECT SUM(goodscount) as goodsCount, goodsseq
            FROM ordergoods
            WHERE totalingtype = '0'
            AND processtime > CURRENT_TIMESTAMP + cast(/*totalPeriod*/0 as interval)
            /*%if shopSeq != null*/
            AND shopseq = /*shopSeq*/0
            /*%end*/
            GROUP BY goodsseq
        ) as a
        WHERE goods.goodsseq = a.goodsseq
        GROUP BY goodsgroupseq
    ) as x
    ON goodsgrouppopularity.goodsgroupseq = x.goodsgroupseq
    LEFT JOIN
    (
        SELECT SUM(a.goodsCount) as goodsCount,SUM(a.goodsPreCount) as goodsPreCount, goodsgroupseq
        FROM goods,
        (
            SELECT SUM(goodscount) as goodsCount, SUM(pregoodscount) as goodsPreCount, goodsseq
            FROM ordergoods
            WHERE totalingtype = '9'
            AND processtime > CURRENT_TIMESTAMP + cast(/*totalPeriod*/0 as interval)
            /*%if shopSeq != null*/
            AND shopseq = /*shopSeq*/0
            /*%end*/
            GROUP BY goodsseq
        ) as a
        WHERE goods.goodsseq = a.goodsseq
        GROUP BY goodsgroupseq
    ) as y
    ON goodsgrouppopularity.goodsgroupseq = y.goodsgroupseq
    LEFT JOIN
    (
        SELECT SUM(a.goodsCount) as goodsCount,SUM(a.goodsPreCount) as goodsPreCount, goodsgroupseq
        FROM goods,
        (
            SELECT SUM(goodscount) as goodsCount, SUM(pregoodscount) as goodsPreCount, goodsseq
            FROM ordergoods
            WHERE totalingtype = '2'
            AND processtime > CURRENT_TIMESTAMP + cast(/*totalPeriod*/0 as interval)
            /*%if shopSeq != null*/
            AND shopseq = /*shopSeq*/0
            /*%end*/
            GROUP BY goodsseq
        ) as a
        WHERE goods.goodsseq = a.goodsseq
        GROUP BY goodsgroupseq
    ) as z
    ON goodsgrouppopularity.goodsgroupseq = z.goodsgroupseq
) as xyz
WHERE goodsgrouppopularity.goodsgroupseq = xyz.goodsgroupseq;
