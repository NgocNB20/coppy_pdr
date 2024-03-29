SELECT
  orderbill.*,
  cardbrand.cardbranddisplaypc,
  cardbrand.cardbranddisplaymb
FROM
  orderbill
  LEFT OUTER JOIN cardbrand ON
  orderbill.creditcompanycode = cardbrand.cardbrandcode
WHERE
  orderseq = /*orderSeq*/0
AND
  orderbillversionno <= /*orderBillVersionNo*/0
ORDER BY orderbillversionno DESC
LIMIT 1
