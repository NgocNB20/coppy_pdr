SELECT *
FROM mulPayBill 
WHERE
    orderseq = /*orderSeq*/0
AND
    orderversionno <= /*orderVersionNo*/0
ORDER BY
    mulpaybillseq DESC
LIMIT 1
