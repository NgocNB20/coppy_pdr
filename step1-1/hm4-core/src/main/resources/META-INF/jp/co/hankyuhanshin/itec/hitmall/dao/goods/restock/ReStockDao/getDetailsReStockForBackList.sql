SELECT
    (CAST(reStockData.goodsSeq AS VARCHAR)
        || reStockData.reStockStatus
        || COALESCE(reStockData.deliveryId, '')
        || reStockData.deliveryStatus
        || reStockData.memberInfoSeq
        || CAST(reStockData.versionNo AS VARCHAR)
    )AS detailsKey
    , reStockData.goodsSeq
    , goods.goodsCode
    , goodsGroup.goodsGroupNameAdmin AS goodsName
    , reStockData.reStockStatus
    , reStockData.deliveryId
    , reStockData.reStockTime
    , memberInfo.memberInfoSeq
    , memberInfo.customerNo
    , memberInfo.memberInfoLastName
    , memberInfo.memberInfoId
    , reStockData.registtime
    , reStockData.deliveryStatus
    , reStockData.versionNo
FROM
    (
        SELECT (CAST(reStockAnnounceMail.goodsSeq AS VARCHAR)
              || reStockAnnounceGoods.reStockStatus
              || COALESCE(reStockAnnounceMail.deliveryId,'')
              || reStockAnnounceMail.deliveryStatus) AS key
              , reStockAnnounceMail.goodsSeq
              , reStockAnnounceMail.memberInfoSeq
              , reStockAnnounceMail.versionNo
              , reStockAnnounce.registTime
              , reStockAnnounceGoods.reStockStatus
              , reStockAnnounceGoods.reStockTime
              , reStockAnnounceMail.deliveryId
              , reStockAnnounceMail.deliveryStatus
        FROM
            reStockAnnounceMail
        INNER JOIN
            reStockAnnounceGoods
        ON
            reStockAnnounceGoods.goodsSeq = reStockAnnounceMail.goodsSeq
        LEFT JOIN
            reStockAnnounce
        ON
            reStockAnnounce.goodsSeq = reStockAnnounceMail.goodsSeq
        AND reStockAnnounce.memberInfoSeq = reStockAnnounceMail.memberInfoSeq
    ) AS reStockData
    INNER JOIN
        memberInfo
    ON
        memberInfo.memberInfoSeq = reStockData.memberInfoSeq
    INNER JOIN
        goods
    ON
        goods.goodsSeq = reStockData.goodsSeq
    INNER JOIN
        goodsGroup
    ON
        goodsGroup.goodsGroupSeq = goods.goodsGroupSeq
WHERE
    1 = 1
/*%if key != null*/
AND reStockData.key = /*key*/''
/*%end*/
/*************** sort ***************/
ORDER BY
    reStockData.registTime DESC
