SELECT
	*
FROM
	(SELECT *
	FROM deliveryMethod
	WHERE 1 = 1
	/*%if possibleDeliveryMethodSeqList != null*/
		AND
			deliveryMethodSeq IN /*possibleDeliveryMethodSeqList*/(0)
	/*%end*/
	/*%if shopSeq != null*/
		AND
			shopSeq = /*shopSeq*/0
	/*%end*/
	/*%if siteType.value == "0" || siteType.value == "1"*/
		AND
			openstatuspc = '1'
	/*%end*/
	/*%if siteType.value == "2"*/
		AND
			openstatusmb = '1'
	/*%end*/
	/*%if siteType.value == "3"*/
		AND
			openstatuspc <> '9'
		AND
			openstatusmb <> '9'
	/*%end*/
	ORDER BY
		orderDisplay
	LIMIT 5
	) AS possibleDeliveryMethod
WHERE
	possibleDeliveryMethod.shortfallDisplayFlag = '1'
ORDER BY
	possibleDeliveryMethod.orderDisplay
