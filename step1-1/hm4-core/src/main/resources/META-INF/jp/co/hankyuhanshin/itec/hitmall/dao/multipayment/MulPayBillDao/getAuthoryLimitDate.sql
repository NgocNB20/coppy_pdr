SELECT 
  to_date(mb.trandate,'YYYYMMDDHH24MISS') + cast(/*authoryHoldPeriod*/0 || ' days' AS interval)
FROM mulpaybill mb
WHERE 
  orderseq = /*orderSeq*/0
  AND mb.jobcd = 'AUTH'
  AND mb.trandate IS NOT NULL
ORDER BY mb.mulpaybillseq DESC
LIMIT 1
