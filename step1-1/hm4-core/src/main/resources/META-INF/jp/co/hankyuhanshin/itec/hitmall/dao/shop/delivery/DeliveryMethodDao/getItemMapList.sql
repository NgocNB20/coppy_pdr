SELECT
    *
FROM
    deliverymethod
WHERE
    shopseq = /*shopSeq*/0
AND
    openstatuspc <> '9'
AND
    openstatusmb <> '9'
ORDER BY
    orderdisplay,
    deliverymethodseq
