SELECT
    goodsGroup.goodsGroupCode
    , goods.goodsCode
    , goodsGroup.goodsGroupNameAdmin AS goodsName
    , memberInfo.customerNo
    , memberInfo.memberInfoId
    , reStockData.deliveryId
    , reStockData.reStockStatus
    , reStockData.deliveryStatus
    , reStockData.reStockTime
    , reStockData.reStockRegistTime
FROM
    (SELECT (CAST(reStockAnnounceMail.goodsSeq AS VARCHAR)
         || reStockAnnounceGoods.reStockStatus
         || COALESCE(reStockAnnounceMail.deliveryId,'')
         || reStockAnnounceMail.deliveryStatus
         || reStockAnnounceMail.memberInfoSeq
         || CAST(reStockAnnounceMail.versionNo AS VARCHAR)) AS key
        , reStockAnnounceMail.goodsSeq
        , reStockAnnounceMail.memberInfoSeq
        , reStockAnnounceMail.deliveryId
        , reStockAnnounceGoods.reStockStatus
        , reStockAnnounceMail.deliveryStatus
        , reStockAnnounceGoods.reStockTime
        , reStockAnnounce.updateTime AS reStockRegistTime
    FROM reStockAnnounceMail
    LEFT JOIN
        reStockAnnounce
    ON
        reStockAnnounce.goodsSeq = reStockAnnounceMail.goodsSeq
    AND reStockAnnounce.memberInfoSeq = reStockAnnounceMail.memberInfoSeq
    INNER JOIN
        reStockAnnounceGoods
    ON
        reStockAnnounceGoods.goodsSeq = reStockAnnounceMail.goodsSeq
    ) AS reStockData
INNER JOIN
    (SELECT
        CASE WHEN (saleStartTimePc IS NOT NULL AND saleStartTimePc > CURRENT_TIMESTAMP) OR (saleEndTimePc IS NOT NULL AND saleEndTimePc < CURRENT_TIMESTAMP) THEN '0'
        WHEN saleStatusPc <> '1' THEN '0'
        ELSE '1'
        END AS saleSort
        ,goods.*
    FROM
        goods) goods
ON
   reStockData.goodsSeq = goods.goodsSeq
INNER JOIN
    (SELECT
        CASE WHEN (openStartTimePc IS NOT NULL AND openStartTimePc > CURRENT_TIMESTAMP) OR (openEndTimePc IS NOT NULL AND openEndTimePc < CURRENT_TIMESTAMP) THEN '0'
        WHEN goodsOpenStatusPc <> '1' THEN '0'
        ELSE '1'
        END AS openStatusSort
        ,goodsGroup.*
    FROM
        goodsGroup) goodsGroup
ON
    goods.goodsGroupSeq = goodsGroup.goodsGroupSeq
INNER JOIN
    memberInfo
ON
    reStockData.memberInfoSeq = memberInfo.memberInfoSeq
WHERE
    1 = 1
/*%if keyList != null*/
AND reStockData.key in /*keyList*/(0)
/*%end*/

/*************** sort ***************/
order by goods.saleSort DESC, goodsGroup.openStatusSort DESC, goodsGroup.goodsGroupCode, goods.goodsCode, memberInfo.customerNo
