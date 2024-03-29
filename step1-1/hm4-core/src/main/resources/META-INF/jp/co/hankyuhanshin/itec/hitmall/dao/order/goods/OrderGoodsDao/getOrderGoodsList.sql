SELECT
    *
FROM
    ordergoods
WHERE
    orderseq = /*orderSeq*/0
and
    ordergoodsversionno = /*orderGoodsVersionNo*/0
ORDER BY
    orderdisplay
