SELECT
    CAST(reStockData.goodsSeq AS VARCHAR)
        || reStockData.reStockStatus
        || COALESCE(reStockData.deliveryId, '')
        || reStockData.deliveryStatus AS key
    , reStockData.goodsSeq
    , goodsGroup.goodsGroupCode
    , goods.goodsCode
    , reStockData.registMemberCount
    , reStockData.reStockStatus
    , reStockData.deliveryId
    , reStockData.deliveryStatus
    , reStockData.reStockTime
FROM
     (
        SELECT
            reStockAnnounceMail.goodsSeq
            , reStockAnnounceGoods.reStockStatus
            , reStockAnnounceMail.deliveryId
            , reStockAnnounceMail.deliveryStatus
            , reStockAnnounceGoods.reStockTime
            , COUNT(*) AS registMemberCount
        FROM
            reStockAnnounceMail
            INNER JOIN reStockAnnounceGoods
                ON reStockAnnounceGoods.goodsSeq = reStockAnnounceMail.goodsSeq
            INNER JOIN memberInfo
                ON memberInfo.memberInfoSeq = reStockAnnounceMail.memberInfoSeq
            WHERE
                1 = 1
            /*%if conditionDto.customerNo != null*/
                AND memberInfo.customerNo = /*conditionDto.customerNo*/0
            /*%end*/
        GROUP BY
            reStockAnnounceMail.goodsSeq
            , reStockAnnounceGoods.reStockStatus
            , reStockAnnounceMail.deliveryId
            , reStockAnnounceMail.deliveryStatus
            , reStockAnnounceGoods.reStockTime
    ) AS reStockData
    INNER JOIN
        (SELECT
            CASE WHEN (saleStartTimePc IS NOT NULL AND saleStartTimePc > CURRENT_TIMESTAMP) OR (saleEndTimePc IS NOT NULL AND saleEndTimePc < CURRENT_TIMESTAMP) THEN '0'
            WHEN saleStatusPc <> '1' THEN '0'
            ELSE '1'
            END AS saleSort
            , goods.*
        FROM
            goods) goods
    ON goods.goodsSeq = reStockData.goodsSeq
    INNER JOIN
        (SELECT
            CASE WHEN (openStartTimePc IS NOT NULL AND openStartTimePc > CURRENT_TIMESTAMP) OR (openEndTimePc IS NOT NULL AND openEndTimePc < CURRENT_TIMESTAMP) THEN '0'
            WHEN goodsOpenStatusPc <> '1' THEN '0'
            ELSE '1'
            END AS openStatusSort
            , goodsGroup.*
        FROM
            goodsGroup) goodsGroup
    ON goodsGroup.goodsGroupSeq = goods.goodsGroupSeq
WHERE
    1 = 1
/*%if conditionDto.goodsGroupCode != null*/
    AND goodsGroup.goodsGroupCode LIKE '%' || /*conditionDto.goodsGroupCode*/0 || '%'
/*%end*/
/*%if conditionDto.goodsCode != null*/
    AND goods.goodsCode LIKE '%' || /*conditionDto.goodsCode*/0 || '%'
/*%end*/
/*%if conditionDto.goodsName != null*/
    AND goodsGroup.goodsGroupNameAdmin LIKE '%' || /*conditionDto.goodsName*/0 || '%'
/*%end*/
/*%if conditionDto.deliveryId != null*/
    AND reStockData.deliveryId = /*conditionDto.deliveryId*/0
/*%end*/
/*%if conditionDto.reStockStatusList != null*/
    AND reStockData.reStockStatus IN /*conditionDto.reStockStatusList*/(0)
/*%end*/
/*%if conditionDto.deliveryStatusList != null*/
    AND reStockData.deliveryStatus IN /*conditionDto.deliveryStatusList*/(0)
/*%end*/
/*%if conditionDto.reStockTimeFrom != null*/
    AND reStockData.reStockTime >= /*conditionDto.reStockTimeFrom*/0
/*%end*/
/*%if conditionDto.reStockTimeTo != null*/
    AND reStockData.reStockTime <= /*conditionDto.reStockTimeTo*/0
/*%end*/
/*%if conditionDto.multiCodeList != null*/
    AND goods.goodsCode IN /*conditionDto.multiCodeList*/(0)
/*%end*/
/*************** sort ***************/
ORDER BY
    goods.saleSort DESC, goodsGroup.openStatusSort DESC
/*%if conditionDto.pageInfo.orderField != null*/

    /*%if conditionDto.pageInfo.orderField == "goodsGroupCode"*/
        , goodsGroup.goodsGroupCode /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "goodsCode"*/
        , goods.goodsCode /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "registMemberCount"*/
        , reStockData.registMemberCount /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "reStockStatus"*/
        , reStockData.reStockStatus /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "deliveryId"*/
        , reStockData.deliveryId /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "deliveryStatus"*/
        , reStockData.deliveryStatus /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "reStockTime"*/
        , reStockData.reStockTime /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
    /*%end*/

/*%else*/
    , goodsGroup.goodsGroupCode, goods.goodsCode
/*%end*/
