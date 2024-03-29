SELECT
	deliveryspecialchargearea.*,
	zipcode.prefecturename AS prefecture,
	zipcode.cityname AS city,
	zipcode.townname AS town,
	zipcode.numbers
FROM
	deliveryspecialchargearea
	LEFT OUTER JOIN
	(
		SELECT
			zipcode,
			prefecturename,
			cityname,
			townname,
			'' AS numbers
		FROM
			zipCode
		-- 郵便番号マスタ（約１２万件）と事業所郵便番号マスタ（約２万件）に対し、個別にSQLを発行した場合のコストと
		-- UNION した SQLを発行した場合のコストに差がなかったため UNION を採用した
		UNION SELECT
			zipcode,
			prefecturename,
			cityname,
			townname,
			numbers AS numbers
		FROM
			officezipcode
	) zipcode
	ON
	deliveryspecialchargearea.zipcode = zipcode.zipcode
WHERE
	deliveryspecialchargearea.deliverymethodseq = /*conditionDto.deliveryMethodSeq*/0
	-- 都道府県名が指定された場合、指定された都道府県名のみに絞り込む
	/*%if conditionDto.prefecture != null && conditionDto.prefecture != ""*/
	AND	prefecturename = /*conditionDto.prefecture*/0
	/*%end*/

ORDER BY
    zipcode
