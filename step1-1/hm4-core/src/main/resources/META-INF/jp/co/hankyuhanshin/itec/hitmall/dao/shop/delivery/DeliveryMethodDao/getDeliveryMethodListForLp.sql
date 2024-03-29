SELECT
	* 
FROM
	deliveryMethod
WHERE
	shopSeq = /*shopSeq*/0
	AND openStatusPc = '1'
	/*%if possibleDeliveryMethodSeqList.size() > 0*/
		AND deliveryMethodSeq IN /*possibleDeliveryMethodSeqList*/(0)
	/*%end*/
ORDER BY orderDisplay
LIMIT 5
