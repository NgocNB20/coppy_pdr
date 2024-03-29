SELECT
  'true'
FROM
  ordersettlement,
  (SELECT ordersettlementversionno
   FROM ordersettlement
   WHERE orderseq = /*orderSeq*/0
   AND settlementMethodSeq != /*settlementMethodSeq*/0
   ORDER BY ordersettlementversionno DESC
   LIMIT 1) oldsettlement
WHERE
  ordersettlement.orderseq = /*orderSeq*/0
AND
  ordersettlement.settlementMethodType = /*settlementMethodType.value*/0
AND
  ordersettlement.ordersettlementversionno <= oldsettlement.ordersettlementversionno
LIMIT 1
