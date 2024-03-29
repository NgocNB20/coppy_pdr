SELECT
    memberInfo.memberInfoSeq
    , memberInfo.customerNo
    , memberInfo.memberInfoMail
    , memberInfo.memberInfoLastName
    , memberInfo.sendMailPermitFlag
    , goodsImage.imageFileName
    , goodsGroupDisplay.saleIconFlag
    , goodsGroupDisplay.reserveIconFlag
    , goodsGroupDisplay.newIconFlag
    , goodsGroupDisplay.outletIconFlag
    , goods.goodsSeq
    , goods.goodsCode
    , goodsGroup.goodsGroupName AS goodsName
    , goodsGroupDisplay.unitTitle1
    , goods.unitValue1
    , goodsGroupDisplay.unitTitle2
    , goods.unitValue2
    , goodsGroup.goodsPreDiscountPrice
    , goods.goodsPriceInTaxLow
    , goods.goodsPriceInTaxHight
    , goods.preDiscountPriceLow
    , goods.preDiscountPriceHight
    , goods.saleStatusPc
    , goods.saleStartTimePc
    , goods.saleEndTimePc
    , goodsGroup.goodsOpenStatusPc
    , goodsGroup.openStartTimePc
    , goodsGroup.openEndTimePc
    , saleAnonounce.saleGoodsSeq
FROM
    (
        SELECT
            CAST(saleannouncemail.goodsSeq AS VARCHAR) || CAST(saleannouncemail.memberInfoSeq AS VARCHAR) AS key
            , saleannouncemail.goodsSeq
            , saleannouncemail.memberInfoSeq
            , favorite.goodsseq AS saleGoodsSeq
            , favorite.registtime
        FROM
            saleannouncemail
            LEFT JOIN favorite
                ON saleannouncemail.memberinfoseq = favorite.memberinfoseq
                AND saleannouncemail.goodsseq = favorite.goodsseq
    ) AS saleAnonounce
    INNER JOIN saleannouncemail
        ON saleannouncemail.goodsseq = saleAnonounce.goodsSeq
        AND saleannouncemail.memberinfoseq = saleAnonounce.memberInfoSeq
    LEFT JOIN memberinfo
        ON saleannouncemail.memberinfoseq = memberinfo.memberinfoseq
    LEFT JOIN goods
        ON saleannouncemail.goodsseq = goods.goodsseq
    LEFT JOIN goodsgroup
        ON goods.goodsgroupseq = goodsgroup.goodsgroupseq
    LEFT JOIN goodsImage
        ON goodsImage.goodsGroupSeq = goods.goodsGroupSeq
        AND goodsImage.goodsSeq = goods.goodsSeq
    LEFT JOIN goodsgroupdisplay
        ON goodsgroup.goodsgroupseq = goodsgroupdisplay.goodsgroupseq
WHERE
    1 = 1
/*%if keyList != null*/
    AND saleAnonounce.key in /*keyList*/(0)
/*%end*/
ORDER BY
    memberInfo.customerNo
    , saleAnonounce.registtime DESC
    , goods.goodsCode
