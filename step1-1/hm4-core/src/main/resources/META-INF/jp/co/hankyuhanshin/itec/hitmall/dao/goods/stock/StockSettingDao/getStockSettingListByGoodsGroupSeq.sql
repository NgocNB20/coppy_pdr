SELECT
  stocksetting.*
FROM
  stocksetting,
  goods
WHERE
  stocksetting.goodsSeq = goods.goodsseq
  AND goods.goodsgroupseq = /*goodsGroupSeq*/0
ORDER BY stocksetting.goodsseq ASC
