SELECT
	COUNT(*)
FROM
	mulPayResult
WHERE
	orderId = /*orderId*/0
	AND status = /*status*/0
	AND (
		(paytype = '38' AND amount = /*amount*/0)
		OR paytype <> '38'
	)
	/*%if errCode != null*/
		AND errCode = /*errCode*/0
	/*%else*/
		AND errCode is null
	/*%end*/
	/*%if errInfo != null*/
		AND errInfo = /*errInfo*/0
	/*%else*/
		AND errInfo is null
	/*%end*/
