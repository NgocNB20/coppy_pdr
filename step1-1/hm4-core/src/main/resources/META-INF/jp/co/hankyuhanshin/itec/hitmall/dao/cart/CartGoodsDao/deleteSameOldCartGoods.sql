DELETE
FROM
    cartgoods
WHERE
    cartseq IN (
        SELECT
            mincart.minseq
        FROM
            (SELECT
                MIN(cartseq) AS minseq,
                COUNT(cartseq) AS goodscnt
            FROM
                cartgoods
            WHERE
                memberinfoseq != 0
            AND
                memberinfoseq = /*memberInfoSeq*/0
            /*%if shopSeq != null*/
            AND
                cartgoods.shopseq = /*shopSeq*/0
            /*%end*/
            GROUP BY
                memberinfoseq,
                goodsseq,
                shopseq
            ) AS mincart
        WHERE
            mincart.goodscnt > 1
    )
    -- 2023-renew No14 from here
    and reserveFlag != '1'
    -- 2023-renew No14 to here
