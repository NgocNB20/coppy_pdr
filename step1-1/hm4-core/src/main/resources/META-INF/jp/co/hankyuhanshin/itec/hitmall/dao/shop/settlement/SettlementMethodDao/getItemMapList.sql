SELECT
    settlementmethodseq,
    settlementmethodname
FROM
    settlementmethod
WHERE
    shopseq = /*shopSeq*/0
AND
	openstatuspc <> '9'
AND
	openstatusmb <> '9'
ORDER BY
    orderdisplay,
    settlementmethodseq
