SELECT
  COALESCE(sum(ordersettlement.goodsPriceTotal),0)
FROM
  ordersummary
  INNER JOIN orderindex
    ON ordersummary.orderseq = orderindex.orderseq
    AND ordersummary.orderversionno = orderindex.orderversionno
  INNER JOIN orderbill
    ON orderindex.orderseq = orderbill.orderseq
    AND orderindex.orderbillversionno = orderbill.orderbillversionno
  INNER JOIN ordersettlement
    ON orderindex.orderseq = ordersettlement.orderseq
    AND orderindex.ordersettlementversionno = ordersettlement.ordersettlementversionno
WHERE
  ordersummary.memberinfoseq = /*memberInfoSeq*/0
AND
  ordersummary.cancelflag = '0'
AND
  ordersummary.waitingflag = '0'
AND
  orderbill.emergencyflag = '0'
AND
  ordersummary.orderstatus = '3'
AND
  ordersummary.salestime >= /*startTime*/0
AND
  ordersummary.salestime <= /*endTime*/0
