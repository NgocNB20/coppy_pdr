SELECT
    orderData.mail AS mail,
    orderData.lastName AS lastName,
    orderData.firstName AS firstName,
    orderData.carrierType AS carrierType,
    orderData.lastOrderTime AS lastOrderTime,
    orderData.totalOrderCount AS totalOrderCount,
    orderData.totalOrderPrice AS totalOrderPrice,
    memberinfo.memberinfoid AS memberInfoId,
    memberinfo.memberinfostatus AS memberInfoStatus,
    memberinfo.memberinfosex AS memberInfoSex,
    memberinfo.memberinfobirthday AS memberInfoBirthday,
FROM
    (
    SELECT
        subOrderData.ordermail AS mail,
        subOrderData.orderlastname AS lastName,
        subOrderData.orderfirstname AS firstName,
        subOrderData.addresstype AS carrierType,
        SUM(subOrderData.orderprice) AS totalOrderPrice,
        COUNT(subOrderData.orderseq) AS totalOrderCount,
        MAX(subOrderData.ordertime) AS lastOrderTime,
        subOrderData.memberinfoseq AS memberinfoseq
    FROM
        (
        SELECT
            DISTINCT
            orderperson.ordermail,
            orderperson.orderlastname,
            orderperson.orderfirstname,
            orderperson.addresstype,
            -- PDR Migrate Customization from here
            (ordersettlement.goodsPriceTotal + ordersettlement.carriage - ordersettlement.couponDiscountPrice + ordersettlement.othersPriceTotal + ordersettlement.settlementCommission) as orderprice,
            -- PDR Migrate Customization to here
            ordersummary.orderseq,
            ordersummary.ordertime,
            ordersummary.memberinfoseq AS memberinfoseq
        FROM
            ordersummary,
            -- PDR Migrate Customization from here
            ordersettlement,
            -- PDR Migrate Customization to here
            orderindex,
            orderperson,
            ordergoods
        WHERE
            -- PDR Migrate Customization from here
            ordersettlement.orderseq = orderindex.orderseq AND
            orderindex.ordersettlementversionno = ordersettlement.ordersettlementversionno AND
            -- PDR Migrate Customization to here
            ordersummary.orderseq = orderindex.orderseq AND
            ordersummary.orderversionno = orderindex.orderversionno AND
            ordersummary.cancelflag = '0' AND
            orderindex.orderseq = orderperson.orderseq AND
            orderindex.orderpersonversionno = orderperson.orderpersonversionno AND
            orderindex.orderseq = ordergoods.orderseq AND
            orderindex.ordergoodsversionno = ordergoods.ordergoodsversionno AND
            /*%if conditionDto.orderTimeFrom != null*/ ordersummary.ordertime >= /*conditionDto.orderTimeFrom*/0 AND /*%end*/
            /*%if conditionDto.orderTimeTo != null*/ ordersummary.ordertime <= /*conditionDto.orderTimeTo*/0 AND /*%end*/
            /*%if conditionDto.carrierType == "0"*/ orderperson.addresstype = '0' AND /*%end*/
            /*%if conditionDto.carrierType == "1"*/ orderperson.addresstype = '1' AND /*%end*/
            /*%if conditionDto.orderZipCode != null*/ orderperson.orderzipcode LIKE /*conditionDto.orderZipCode*/0 || '%' AND /*%end*/
            /*%if conditionDto.orderPrefecture != null*/ orderperson.orderprefecture LIKE '%' || /*conditionDto.orderPrefecture*/0 || '%' AND /*%end*/
            /*%if conditionDto.orderAddress != null*/ orderperson.orderaddress1 LIKE '%' || /*conditionDto.orderAddress*/0 || '%' AND /*%end*/
            /*%if conditionDto.goodsName != null*/ ordergoods.goodsgroupname LIKE '%' || /*conditionDto.goodsName*/0 || '%' AND /*%end*/
            /*%if conditionDto.goodsGroupCode != null*/ ordergoods.goodsgroupcode LIKE /*conditionDto.goodsGroupCode*/0 || '%' AND /*%end*/
            /*%if conditionDto.goodsCode != null*/ ordergoods.goodscode LIKE /*conditionDto.goodsCode*/0 || '%' AND /*%end*/
            /*%if conditionDto.goodsName != null || conditionDto.goodsGroupCode != null || conditionDto.goodsCode != null*/
                ordergoods.goodscount > 0 AND
            /*%end*/

            1 = 1
        ) subOrderData
    GROUP BY
        subOrderData.ordermail,
        subOrderData.orderlastname,
        subOrderData.orderfirstname,
        subOrderData.addresstype,
        subOrderData.memberinfoseq
    HAVING
        /*%if conditionDto.totalOrderPriceFrom != null*/ SUM(subOrderData.orderprice) >= /*conditionDto.totalOrderPriceFrom*/0 AND /*%end*/
        /*%if conditionDto.totalOrderPriceTo != null*/ SUM(subOrderData.orderprice) <= /*conditionDto.totalOrderPriceTo*/0 AND /*%end*/
        /*%if conditionDto.totalOrderCountFrom != null*/ COUNT(subOrderData.orderseq) >= /*conditionDto.totalOrderCountFrom*/0 AND /*%end*/
        /*%if conditionDto.totalOrderCountTo != null*/ COUNT(subOrderData.orderseq) <= /*conditionDto.totalOrderCountTo*/0 AND /*%end*/
        /*%if conditionDto.lastOrderTimeFrom != null*/ MAX(subOrderData.ordertime) >= /*conditionDto.lastOrderTimeFrom*/0 AND /*%end*/
        /*%if conditionDto.lastOrderTimeTo != null*/ MAX(subOrderData.ordertime) <= /*conditionDto.lastOrderTimeTo*/0 AND /*%end*/
        1 = 1
    ) AS orderData
    LEFT JOIN memberinfo ON memberinfo.memberinfoseq = orderData.memberinfoseq
WHERE
    /*%if conditionDto.memberInfoSex == "1"*/ memberinfo.memberinfosex = '1' AND /*%end*/
    /*%if conditionDto.memberInfoSex == "2"*/ memberinfo.memberinfosex = '2' AND /*%end*/
    /*%if conditionDto.memberInfoSex == "0"*/ memberinfo.memberinfosex = '0' AND /*%end*/
    /*%if conditionDto.memberInfoBirthdayFrom != null*/ memberinfo.memberinfobirthday >= /*conditionDto.memberInfoBirthdayFrom*/0 AND /*%end*/
    /*%if conditionDto.memberInfoBirthdayTo != null*/ memberinfo.memberinfobirthday <= /*conditionDto.memberInfoBirthdayTo*/0 AND /*%end*/
    1 = 1

/*************** sort ***************/
ORDER BY
/*%if conditionDto.pageInfo.orderField != null*/
    /*%if conditionDto.pageInfo.orderField == "mail"*/
    mail /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "lastName"*/
    lastName /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    , firstName /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "firstName"*/
    firstName /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "carrierType"*/
    carrierType /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "totalOrderPrice"*/
    totalOrderPrice /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "totalOrderCount"*/
    totalOrderCount /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "lastOrderTime"*/
    lastOrderTime /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "memberInfoId"*/
    memberInfoId /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "memberInfoStatus"*/
    memberInfoStatus /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "memberInfoSex"*/
    memberInfoSex /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/

    /*%if conditionDto.pageInfo.orderField == "memberInfoBirthday"*/
    memberInfoBirthday /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
    /*%end*/
/*%else*/
    1 ASC
/*%end*/
