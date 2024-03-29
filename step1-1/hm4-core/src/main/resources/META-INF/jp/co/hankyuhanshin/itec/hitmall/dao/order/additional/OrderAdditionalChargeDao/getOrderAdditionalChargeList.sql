SELECT
    *
FROM
    orderadditionalcharge
WHERE
    orderseq = /*orderSeq*/0
AND
    orderadditionalchargeversionno = /*orderAdditionalChargeVersionNo*/0
ORDER BY
    orderdisplay
