SELECT
  orderSummary.*
, periodicOrder.periodicOrderCode
FROM
  orderSummary
LEFT OUTER JOIN
  periodicOrder
ON
  orderSummary.periodicOrderSeq = periodicOrder.periodicOrderSeq
WHERE
  orderSummary.orderCode = /*orderCode*/0
