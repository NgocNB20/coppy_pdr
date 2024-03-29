SELECT
  *
FROM
  goods
WHERE
  shopseq = /*shopSeq*/0
AND
  goodsgroupseq = (
    SELECT
      goodsgroupseq
    FROM
      goodsgroup
    WHERE
      goodsgroupcode = /*goodsGroupCode*/0
  )
