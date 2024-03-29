SELECT DISTINCT ordergoods.orderseq as orderseq
FROM
    ordergoods LEFT JOIN ordersummary
    ON ordergoods.orderSeq = ordersummary.orderSeq
WHERE
    ordergoods.shopSeq = /*shopSeq*/0
AND
    ordergoods.registtime <= /*thresholdTime*/0
AND
    ordersummary.orderseq IS NULL
ORDER BY
    orderseq ASC
