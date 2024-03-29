SELECT 
    orderindex.*
FROM
    orderindex,
    orderbill,
    (SELECT orderbillversionno
     FROM orderbill
     WHERE
       orderseq = /*orderSeq*/0
       AND (emergencyFlag = '0'
           OR orderbillversionno = 1)
     ORDER BY orderbillversionno DESC
     LIMIT 1) beforeEm
WHERE
    orderindex.orderseq = orderbill.orderseq
AND orderindex.orderseq = /*orderSeq*/0
AND orderindex.orderbillversionno = orderbill.orderbillversionno
AND orderbill.emergencyFlag = '1'
AND orderbill.orderbillversionno >= beforeEm.orderbillversionno
ORDER BY orderbillversionno ASC
LIMIT 1
