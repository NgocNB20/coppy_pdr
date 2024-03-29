SELECT 
	*
FROM
	coupon

INNER JOIN
	couponindex
ON
	coupon.couponseq = couponindex.couponseq
AND
	coupon.couponversionno = couponindex.couponversionno

WHERE
	coupon.couponcode = /*couponCode*/'couponcode'

ORDER BY
	coupon.couponstarttime DESC   
LIMIT 1


	
