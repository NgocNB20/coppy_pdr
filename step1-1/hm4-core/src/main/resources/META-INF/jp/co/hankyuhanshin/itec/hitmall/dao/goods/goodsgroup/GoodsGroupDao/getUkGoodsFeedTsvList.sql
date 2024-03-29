SELECT
    goods.goodsGroupSeq
    , goodsGroupCode
    , newIconFlag
    , saleIconFlag
    , reserveIconFlag
    , outletIconFlag
    , goodsGroupName
    , goodsPreDiscountPrice
    , goodsGroupMinPricePc
    , goodsGroupMaxPricePc
    , goodsGroupMinPriceMb
    , goodsGroupMaxPriceMb
    , array_to_string(
        ARRAY (
            SELECT
                categorySeq
            FROM
                (
                    SELECT
                        c1.categorySeq
                        , cg.goodsGroupSeq
                    FROM
                        category AS c1
                        INNER JOIN categoryGoods cg
                            ON c1.categorySeq = cg.categorySeq
                    WHERE
                        NOT EXISTS (
                            SELECT
                                *
                            FROM
                                category AS c2
                                INNER JOIN categoryGoods cg2
                                    ON c2.categorySeq = cg2.categorySeq
                            WHERE
                                c2.categorySeqPath LIKE c1.categorySeqPath || '_%'
                                AND cg2.goodsGroupSeq = goodsGroup.goodsGroupSeq
                        )
                    ORDER BY
                        c1.categorySeq
                        , c1.categoryPath
                ) AS categoryQuery
            WHERE
                categoryQuery.goodsGroupSeq = goodsGroup.goodsGroupSeq
        )
        , '/'
    ) AS categorySeqList
    , CASE
        WHEN saleStatusPc = '1' AND saleEndTimePc <= /*currentTime*/0 THEN 20
        WHEN saleStatusPc = '1' THEN 0
        WHEN saleStatusPc = '0' THEN 10
        END AS itemStatus
    , CASE
        WHEN emotionPriceType = '1' THEN 1
        ELSE 0
        END AS spiritFlag
    , goodsClassType
    , goodsNote1
    , goodsNote1OpenStartTime
    , goodsNote1Sub
    , goodsNote1SubOpenStartTime
    , COALESCE(openStartTimePc, '1990-01-01 00:00:00') AS tmpNewDate
    , catalogCode
    , searchKeyword
    , goodsCode
    , unitTitle1
    , unitTitle2
    , unitValue1
    , unitValue2
    , goodsPriceInTaxLow
    , preDiscountPriceLow
FROM
    goods
    INNER JOIN goodsGroup
        ON goods.goodsGroupSeq = goodsGroup.goodsGroupSeq
    INNER JOIN goodsGroupDisplay
        ON goodsGroupDisplay.goodsGroupSeq = goodsGroup.goodsGroupSeq
WHERE
    goodsGroup.goodsOpenStatusPc = '1'
    AND (goodsGroup.openStartTimePc IS NULL OR goodsGroup.openStartTimePc <= /*currentTime*/0)
    AND (goodsGroup.openEndTimePc IS NULL OR goodsGroup.openEndTimePc >= /*currentTime*/0)
    AND goods.emotionPriceType != '2'
    AND goods.saleStatusPc != '9'
ORDER BY
    goodsGroup.goodsGroupCode ASC
    , itemStatus DESC
;
