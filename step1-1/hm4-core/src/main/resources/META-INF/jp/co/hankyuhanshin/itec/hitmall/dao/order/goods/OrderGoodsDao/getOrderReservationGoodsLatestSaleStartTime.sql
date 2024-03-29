SELECT
  MAX (goods.saleStartTimePC) AS saleStartTime
FROM
  ordersummary
  INNER JOIN orderindex
    ON ordersummary.orderseq = orderindex.orderseq
    AND ordersummary.orderversionno = orderindex.orderversionno
  INNER JOIN ordergoods
    ON orderindex.orderseq = ordergoods.orderseq
    AND orderindex.ordergoodsversionno = ordergoods.ordergoodsversionno
  LEFT OUTER JOIN goods
    ON ordergoods.goodsseq = goods.goodsseq
WHERE
  ordersummary.orderseq = /*orderSeq*/0
AND
  ordergoods.orderconsecutiveno = /*orderConsecutiveNo*/0
