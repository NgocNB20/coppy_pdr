SELECT
  orderGoods.*
FROM
  orderGoods
WHERE
  orderSeq = /*orderSeq*/0
AND
  orderGoodsVersionNo = /*orderGoodsVersionNo*/0
AND
  orderConsecutiveNo = /*orderConsecutiveNo*/0
ORDER BY
  orderDisplay
