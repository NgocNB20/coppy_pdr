SELECT
    categorygoods.categoryseq,
    categorygoods.goodsgroupseq,
    categorygoods.orderdisplay,
    categorygoods.registtime,
    categorygoods.updatetime,
    goodsgroup.goodsgroupcode,
    goodsgroup.whatsnewdate,
    goodsgroup.goodsopenstatuspc,
    goodsgroup.openstarttimepc,
    goodsgroup.openendtimepc,
    goodsgroup.shopseq,
    -- 2023-renew No64 from here
    goodsgroup.goodsgroupnameadmin,
    -- 2023-renew No64 to here
    goodsgroup.goodsprediscountprice
FROM
    categorygoods INNER JOIN goodsgroup ON
    categorygoods.goodsgroupseq = goodsgroup.goodsgroupseq
WHERE
    categorygoods.categoryseq = /*conditionDto.categorySeq*/0

ORDER BY
    orderdisplay DESC
