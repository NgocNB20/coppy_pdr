SELECT
    goodsGroup.goodsGroupCode
    , goods.goodsCode
    , goodsGroup.goodsGroupNameAdmin AS goodsName
    , memberInfo.customerNo
    , memberInfo.memberInfoId
    , reStockAnnounceMail.deliveryId
    , reStockAnnounceGoods.reStockStatus
    , reStockAnnounceMail.deliveryStatus
    , reStockAnnounceGoods.reStockTime
    , reStockAnnounce.updateTime AS reStockRegistTime
FROM
    reStockAnnounceMail
LEFT JOIN
    reStockAnnounce
ON
    reStockAnnounce.goodsSeq = reStockAnnounceMail.goodsSeq
AND reStockAnnounce.memberInfoSeq = reStockAnnounceMail.memberInfoSeq
INNER JOIN
    reStockAnnounceGoods
ON
    reStockAnnounceGoods.goodsSeq = reStockAnnounceMail.goodsSeq
INNER JOIN
    (SELECT
        CASE WHEN (saleStartTimePc IS NOT NULL AND saleStartTimePc > CURRENT_TIMESTAMP) OR (saleEndTimePc IS NOT NULL AND saleEndTimePc < CURRENT_TIMESTAMP) THEN '0'
        WHEN saleStatusPc <> '1' THEN '0'
        ELSE '1'
        END AS saleSort
        , goods.*
    FROM
        goods) goods
ON goods.goodsSeq = reStockAnnounceMail.goodsSeq
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
INNER JOIN
    memberInfo
ON
    memberInfo.memberInfoSeq = reStockAnnounceMail.memberInfoSeq
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
/*%if conditionDto.customerNo != null*/
    AND memberInfo.customerNo = /*conditionDto.customerNo*/0
/*%end*/
/*%if conditionDto.deliveryId != null*/
    AND reStockAnnounceMail.deliveryId = /*conditionDto.deliveryId*/0
/*%end*/
/*%if conditionDto.reStockStatusList != null*/
    AND reStockAnnounceGoods.reStockStatus IN /*conditionDto.reStockStatusList*/(0)
/*%end*/
/*%if conditionDto.deliveryStatusList != null*/
    AND reStockAnnounceMail.deliveryStatus IN /*conditionDto.deliveryStatusList*/(0)
/*%end*/
/*%if conditionDto.reStockTimeFrom != null*/
    AND reStockAnnounceGoods.reStockTime >= /*conditionDto.reStockTimeFrom*/0
/*%end*/
/*%if conditionDto.reStockTimeTo != null*/
    AND reStockAnnounceGoods.reStockTime <= /*conditionDto.reStockTimeTo*/0
/*%end*/
/*%if conditionDto.multiCodeList != null*/
    AND goods.goodsCode IN /*conditionDto.multiCodeList*/(1,2,3)
/*%end*/
/*************** sort ***************/
ORDER BY goods.saleSort DESC, goodsGroup.openStatusSort DESC, goodsGroup.goodsGroupCode, goods.goodsCode, memberInfo.customerNo
