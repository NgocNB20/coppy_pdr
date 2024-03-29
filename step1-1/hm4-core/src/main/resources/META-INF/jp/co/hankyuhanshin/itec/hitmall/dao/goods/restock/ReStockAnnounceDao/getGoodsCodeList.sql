SELECT
    DISTINCT goods.goodsCode
FROM
    reStockAnnounce
INNER JOIN
    goods
ON
    goods.goodsSeq = reStockAnnounce.goodsSeq
WHERE
    goods.emotionPriceType <> '2'
;