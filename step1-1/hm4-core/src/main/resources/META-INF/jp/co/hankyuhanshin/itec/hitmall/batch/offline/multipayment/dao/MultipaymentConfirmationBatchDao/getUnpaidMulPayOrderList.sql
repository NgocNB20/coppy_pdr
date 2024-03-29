SELECT *
  FROM
(
    SELECT
           ordersummary.*
      FROM
           ordersummary
           INNER JOIN orderindex
              ON ordersummary.orderseq = orderindex.orderseq
              AND ordersummary.orderversionno = orderindex.orderversionno
           INNER JOIN ordersettlement
              ON orderindex.orderseq = ordersettlement.orderseq
              AND orderindex.ordersettlementversionno = ordersettlement.ordersettlementversionno
      WHERE
            ordersummary.shopseq = /*shopSeq*/0
        AND ordersummary.cancelflag = '0'
        AND ordersummary.receiptpricetotal < ordersummary.orderprice
        AND ordersettlement.settlementmethodtype in ('1','2','3','4')

    UNION ALL

    SELECT
           ordersummary.*
      FROM
           ordersummary
    INNER JOIN mulpaybill
       ON ordersummary.orderseq = mulpaybill.orderseq
      WHERE
            ordersummary.shopseq = /*shopSeq*/0
        AND mulpaybill.amazonpaymentconfirmstatus = '1'
) queryAll
  ORDER BY queryAll.orderseq DESC

