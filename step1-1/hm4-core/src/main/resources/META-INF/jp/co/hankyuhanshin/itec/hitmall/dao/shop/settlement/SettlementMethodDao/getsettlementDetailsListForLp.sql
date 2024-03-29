SELECT
	*
FROM
	settlementMethod
WHERE
	shopSeq = /*shopSeq*/0
	AND openStatusPc = '1'
	/*%if settlementMethodSeqList.size() > 0*/
		AND settlementMethodSeq IN /*settlementMethodSeqList*/(0)
	/*%end*/
	AND settlementMethodSeq NOT IN /*notInSeqList*/(0)
	AND (
		deliveryMethodSeq IS NULL
		OR deliveryMethodSeq IN /*deliveryMethodSeqList*/(0)
	)
ORDER BY orderDisplay
