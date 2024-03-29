SELECT COUNT (
  orderIndex.couponSeq
  )
FROM
  orderIndex
INNER JOIN 
  orderSummary
ON
  ordersummary.orderseq = orderindex.orderseq
AND
  ordersummary.orderversionno = orderindex.orderversionno
INNER JOIN
  coupon
ON
  orderindex.couponSeq = coupon.couponSeq
AND
  orderindex.couponVersionNo = coupon.couponVersionNo
WHERE 
  orderSummary.memberInfoSeq = /*memberInfoSeq*/0
AND
  orderIndex.couponSeq = /*couponSeq*/0
AND
  (orderIndex.couponLimitTargetType = '1' or orderIndex.couponLimitTargetType = '2')
AND
  coupon.couponStartTime = /*couponStartTime*/0
