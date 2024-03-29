SELECT
    goodsgroup.goodsgroupcode
    , goods.goodscode
    , memberinfo.customerno
    , salegoods.salestatus
    , salegoods.salecd
    , salegoods.saleto
    , goods.goodsseq
    , memberinfo.memberinfoseq
FROM
    favorite
    INNER JOIN memberinfo
        ON favorite.memberinfoseq = memberinfo.memberinfoseq
    INNER JOIN (
        SELECT
            CASE
                WHEN (
                    saleStartTimePc IS NOT NULL
                    AND saleStartTimePc > CURRENT_TIMESTAMP
                )
                OR (
                    saleEndTimePc IS NOT NULL
                    AND saleEndTimePc < CURRENT_TIMESTAMP
                )
                    THEN '0'
                WHEN saleStatusPc <> '1'
                    THEN '0'
                ELSE '1'
                END AS saleSort
            , goods.*
        FROM
            goods
    ) goods
        ON favorite.goodsseq = goods.goodsseq
    INNER JOIN (
        SELECT
            CASE
                WHEN (
                    openStartTimePc IS NOT NULL
                    AND openStartTimePc > CURRENT_TIMESTAMP
                )
                OR (
                    openEndTimePc IS NOT NULL
                    AND openEndTimePc < CURRENT_TIMESTAMP
                )
                    THEN '0'
                WHEN goodsOpenStatusPc <> '1'
                    THEN '0'
                ELSE '1'
                END AS openStatusSort
            , goodsGroup.*
        FROM
            goodsGroup
    ) goodsgroup
        ON goods.goodsgroupseq = goodsgroup.goodsgroupseq
    LEFT JOIN salegoods
        ON favorite.goodsseq = salegoods.goodsseq
    WHERE
        memberinfo.sendmailpermitflag = '1'
AND EXISTS(
    SELECT * FROM loginadvisability
    WHERE loginadvisability.memberinfostatus = memberinfo.memberinfostatus
    AND loginadvisability.approvestatus = memberinfo.approvestatus
    AND loginadvisability.onlineloginadvisability = memberinfo.onlineloginadvisability
    AND loginadvisability.memberlisttype = memberinfo.memberlisttype
    AND loginadvisability.accountingtype = memberinfo.accountingtype
    AND loginadvisability.loginadvisabilitytype = '1'
)
/*%if conditionDto.goodsGroupCode != null*/
  AND goodsgroup.goodsGroupCode LIKE '%' || /*conditionDto.goodsGroupCode*/0 || '%'
/*%end*/
/*%if conditionDto.goodsCode != null*/
  AND goods.goodsCode LIKE '%' || /*conditionDto.goodsCode*/0 || '%'
/*%end*/
/*%if conditionDto.goodsGroupNameAdmin != null*/
  AND goodsgroup.goodsGroupNameAdmin LIKE '%' || /*conditionDto.goodsGroupNameAdmin*/0 || '%'
/*%end*/
/*%if conditionDto.customerNo != null*/
  AND memberinfo.customerno = /*conditionDto.customerNo*/0
/*%end*/
/*%if conditionDto.favoriteSaleStatusList != null*/
  AND salegoods.salestatus IN /*conditionDto.favoriteSaleStatusList*/(0)
/*%end*/
/*%if conditionDto.saleCode != null*/
  AND salegoods.salecd LIKE '%' || /*conditionDto.saleCode*/0 || '%'
/*%end*/
/*%if conditionDto.searchSaleStartTimeFrom != null*/
 AND salegoods.salefrom >= /*conditionDto.searchSaleStartTimeFrom*/0
 /*%end*/
/*%if conditionDto.searchSaleStartTimeTo != null*/
 AND salegoods.salefrom <= /*conditionDto.searchSaleStartTimeTo*/0
 /*%end*/
/*%if conditionDto.searchSaleEndTimeFrom != null*/
 AND salegoods.saleto >= /*conditionDto.searchSaleEndTimeFrom*/0
 /*%end*/
/*%if conditionDto.searchSaleEndTimeTo != null*/
 AND salegoods.saleto <= /*conditionDto.searchSaleEndTimeTo*/0
 /*%end*/
/*%if conditionDto.goodsCodeList != null*/
  AND goods.goodsCode IN /*conditionDto.goodsCodeList*/(0)
/*%end*/

  -- PDR Migrate Customization from here
  AND goods.emotionpricetype <> '2'
  -- PDR Migrate Customization to here

/*************** sort ***************/
ORDER BY
    goodsGroup.openStatusSort DESC , goods.saleSort DESC
/*%if conditionDto.pageInfo.orderField != null*/

/*%if conditionDto.pageInfo.orderField == "goodsGroupCode"*/
,goodsgroup.goodsGroupCode /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "goodsCode"*/
,goods.goodsCode /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "customerNo"*/
,memberinfo.customerNo /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "saleStatus"*/
,salegoods.salestatus /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "saleCd"*/
,salegoods.salecd /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "saleTo"*/
,salegoods.saleto /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%else*/
     , goodsGroup.goodsGroupCode, goods.goodsCode
/*%end*/
