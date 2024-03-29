SELECT
    *
FROM
    ordersummary
WHERE
    ordersummary.orderseq in /*orderSeqList*/(0)
ORDER BY
    ordersummary.orderseq
