SELECT
    *
FROM
    ordersummary
WHERE
    ordersummary.ordercode in /*orderCodeList*/(0)
    and ordersummary.shopseq = /*shopSeq*/0
FOR UPDATE NOWAIT
