UPDATE
  categorygoods
SET orderdisplay =
  (SELECT
    COALESCE(MAX(orderdisplay) + 1, 1) AS orderdisplay
   FROM
    categorygoods
   WHERE categorySeq = /*categorySeq*/0)
WHERE
  categorySeq = /*categorySeq*/0
  AND goodsGroupSeq =/*goodsGroupSeq*/0
