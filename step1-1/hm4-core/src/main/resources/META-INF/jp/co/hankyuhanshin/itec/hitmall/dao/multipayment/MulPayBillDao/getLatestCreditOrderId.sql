SELECT 
    orderid
FROM
    mulPayBill
WHERE
    orderseq = /*orderSeq*/0
AND trantype = 'EntryTran'
AND errcode is null
ORDER BY orderversionno DESC
LIMIT 1
