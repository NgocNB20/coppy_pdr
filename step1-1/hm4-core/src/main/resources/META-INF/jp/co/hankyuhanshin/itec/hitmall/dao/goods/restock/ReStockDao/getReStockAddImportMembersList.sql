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
	, goodsGroup.goodsGroupNameAdmin AS goodsName
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
	, reStockData.versionNo
	, reStockData.reStockGoodsSeq
FROM
    (SELECT (CAST(reStockAnnounceMail.goodsSeq AS VARCHAR)
         || reStockAnnounceGoods.reStockStatus
         || COALESCE(reStockAnnounceMail.deliveryId,'')
         || reStockAnnounceMail.deliveryStatus
         || reStockAnnounceMail.memberInfoSeq
         || CAST(reStockAnnounceMail.versionNo AS VARCHAR)) AS key
    , reStockAnnounceMail.goodsSeq
    , reStockAnnounceMail.memberInfoSeq
    , reStockAnnounceMail.versionNo
    , reStockAnnounceMail.updateTime
    , reStockAnnounce.goodsSeq AS reStockGoodsSeq
    FROM
        reStockAnnounceMail
    INNER JOIN
        reStockAnnounceGoods
    ON
        reStockAnnounceMail.goodsSeq = reStockAnnounceGoods.goodsSeq
    LEFT JOIN
        reStockAnnounce
    ON
        reStockAnnounceMail.memberInfoSeq = reStockAnnounce.memberInfoSeq
    AND reStockAnnounceMail.goodsSeq = reStockAnnounce.goodsSeq
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
LEFT JOIN
	goodsImage
ON
	goodsImage.goodsGroupSeq = goods.goodsGroupSeq
AND goodsImage.goodsSeq = goods.goodsSeq
LEFT JOIN
	goodsGroupDisplay
ON
	goodsGroupDisplay.goodsGroupSeq = goods.goodsGroupSeq
WHERE
    1 = 1
/*%if keyList != null*/
AND reStockData.key in /*keyList*/(0)
/*%end*/
ORDER BY memberInfo.customerNo, goods.goodsCode