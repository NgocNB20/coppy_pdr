SELECT
    *
FROM
    ordersummary
WHERE
    ordersummary.orderseq in /*orderSeqList*/(0)
FOR UPDATE NOWAIT
