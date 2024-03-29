SELECT
  ordergoods.goodsseq
, ordergoods.goodsgroupcode
, ordergoods.goodscode
, ordergoods.individualdeliverytype
, ordergoods.goodsprice
, goods.jancode
, goods.catalogcode
, ordergoods.goodsgroupname
, goods.unitvalue1
, goods.unitvalue2
, ordergoods.goodscount
FROM (
  SELECT
    ordergoods.goodsseq
  , ordergoods.goodsgroupcode
  , ordergoods.goodscode
  , ordergoods.individualdeliverytype
  , ordergoods.goodsprice
  , ordergoods.goodsgroupname
  , SUM(ordergoods.goodscount) AS goodscount
  FROM
    orderindex
    INNER JOIN ordersummary
      ON orderindex.orderversionno = ordersummary.orderversionno
      AND orderindex.orderseq = ordersummary.orderseq
    INNER JOIN ordergoods
      ON orderindex.ordergoodsversionno = ordergoods.ordergoodsversionno
      AND orderindex.orderseq = ordergoods.orderseq
    INNER JOIN orderperson
      ON orderindex.orderseq = orderperson.orderseq
      AND orderindex.orderpersonversionno = orderperson.orderpersonversionno
    INNER JOIN orderdelivery
      ON orderindex.orderseq = orderdelivery.orderseq
      AND orderindex.orderdeliveryversionno = orderdelivery.orderdeliveryversionno
      AND ordergoods.orderConsecutiveNo = orderdelivery.orderConsecutiveNo
    INNER JOIN ordersettlement
      ON orderindex.orderseq = ordersettlement.orderseq
      AND orderindex.ordersettlementversionno = ordersettlement.ordersettlementversionno
    INNER JOIN orderbill
      ON orderindex.orderseq = orderbill.orderseq
      AND orderindex.orderbillversionno = orderbill.orderbillversionno
    LEFT OUTER JOIN periodicorder
      ON ordersummary.periodicorderseq = periodicorder.periodicorderseq
    LEFT OUTER JOIN coupon
      ON orderindex.couponseq = coupon.couponseq
      AND orderindex.couponversionno = coupon.couponversionno
  WHERE ordersummary.shopseq = /*conditionDto.shopSeq*/'1001'
    /*%if conditionDto.includeCancelGoodsFlag == false*/ AND ordergoods.goodscount <> 0 /*%end*/
    /*%if conditionDto.orderSiteTypeList != null*/ AND ordersummary.orderSiteType IN /*conditionDto.orderSiteTypeList*/() /*%end*/
    /*%if conditionDto.orderStatus != null */
      /*%if conditionDto.orderStatus == "4" */
        AND ordersummary.orderStatus <> '3'
        AND orderdelivery.shipmentstatus = '0'
      /*%else*/
        AND ordersummary.orderStatus = /*conditionDto.orderStatus*/''
      /*%end*/
    /*%end*/
    /*%if conditionDto.cancelFlag != null */ AND ordersummary.cancelflag = /*conditionDto.cancelFlag*/'' /*%end*/
    /*%if conditionDto.waitingFlag != null */ AND ordersummary.waitingflag = /*conditionDto.waitingFlag*/'' /*%end*/
    /*%if conditionDto.emergencyFlag != null */ AND orderbill.emergencyflag = /*conditionDto.emergencyFlag*/'' /*%end*/
    /*%if conditionDto.orderCode != null */ AND ordersummary.orderCode LIKE '%' || /*conditionDto.orderCode*/'' || '%' /*%end*/
    /*%if conditionDto.orderTypeList != null*/ AND ordersummary.ordertype IN /*conditionDto.orderTypeList*/() /*%end*/
    /*%if conditionDto.memberInfoSeq != null */ AND ordersummary.memberinfoseq = /*conditionDto.memberInfoSeq*/'' /*%end*/
    /*%if conditionDto.timeType == "1" */ AND ordersummary.orderTime >= /*conditionDto.timeFrom*/'' AND ordersummary.orderTime <= /*conditionDto.timeTo*/'' /*%end*/
    /*%if conditionDto.timeType == "2" */ AND ordersummary.orderTime >= /*conditionDto.timeFrom*/'' /*%end*/
    /*%if conditionDto.timeType == "3" */ AND ordersummary.orderTime <= /*conditionDto.timeTo*/'' /*%end*/
    /*%if conditionDto.timeType == "4" */ AND orderdelivery.shipmentdate >= /*conditionDto.timeFrom*/'' AND orderdelivery.shipmentdate <= /*conditionDto.timeTo*/'' /*%end*/
    /*%if conditionDto.timeType == "5" */ AND orderdelivery.shipmentdate >= /*conditionDto.timeFrom*/'' /*%end*/
    /*%if conditionDto.timeType == "6" */ AND orderdelivery.shipmentdate <= /*conditionDto.timeTo*/'' /*%end*/
    /*%if conditionDto.timeType == "16" */ AND orderbill.paymenttimelimitdate >= /*conditionDto.timeFrom*/'' AND orderbill.paymenttimelimitdate <= /*conditionDto.timeTo*/'' /*%end*/
    /*%if conditionDto.timeType == "17" */ AND orderbill.paymenttimelimitdate >= /*conditionDto.timeFrom*/'' /*%end*/
    /*%if conditionDto.timeType == "18" */ AND orderbill.paymenttimelimitdate <= /*conditionDto.timeTo*/'' /*%end*/
    /*%if conditionDto.timeType == "19" */ AND orderdelivery.receiverdate >= /*conditionDto.timeFrom*/'' AND orderdelivery.receiverdate <= /*conditionDto.timeTo*/'' /*%end*/
    /*%if conditionDto.timeType == "20" */ AND orderdelivery.receiverdate >= /*conditionDto.timeFrom*/'' /*%end*/
    /*%if conditionDto.timeType == "21" */ AND orderdelivery.receiverdate <= /*conditionDto.timeTo*/'' /*%end*/
    /*%if conditionDto.orderName != null */ AND ((orderperson.orderlastname || COALESCE(orderperson.orderfirstname, '')) LIKE '%' || /*conditionDto.orderName*/'' || '%' OR  (orderperson.orderlastkana || orderperson.orderfirstkana) LIKE '%' || /*conditionDto.orderName*/'' || '%') /*%end*/
    /*%if conditionDto.orderTel != null */ AND (orderperson.orderTel LIKE '%' || /*conditionDto.orderTel*/'' || '%' OR orderperson.ordercontacttel LIKE '%' || /*conditionDto.orderTel*/'' || '%') /*%end*/
    /*%if conditionDto.orderMail != null */ AND orderperson.ordermail LIKE '%' || /*conditionDto.orderMail*/'' || '%' /*%end*/
    /*%if conditionDto.receiverName != null */ AND ((orderdelivery.receiverlastname || COALESCE(orderdelivery.receiverfirstname, '')) LIKE '%' || /*conditionDto.receiverName*/'' || '%' OR  (orderdelivery.receiverlastkana || orderdelivery.receiverfirstkana) LIKE '%' || /*conditionDto.receiverName*/'' || '%') /*%end*/
    /*%if conditionDto.receiverTel != null */ AND orderdelivery.receivertel LIKE '%' || /*conditionDto.receiverTel*/'' || '%'/*%end*/
    /*%if conditionDto.deliveryMethodSeq != null */ AND orderdelivery.deliverymethodseq = /*conditionDto.deliveryMethodSeq*/'' /*%end*/
    /*%if conditionDto.deliveryCode != null */ AND orderdelivery.deliverycode = /*conditionDto.deliveryCode*/'' /*%end*/
    /*%if conditionDto.shipmentStatus != null */ AND orderdelivery.shipmentstatus IN /*conditionDto.shipmentStatus*/() /*%end*/
    /*%if conditionDto.settlementMethodSeq != null */ AND ordersummary.settlementmethodseq = /*conditionDto.settlementMethodSeq*/'' /*%end*/
    /*%if conditionDto.billType != null */ AND ordersettlement.billtype = /*conditionDto.billType*/'' /*%end*/
    /*%if conditionDto.paymentStatus == "1" */ AND ordersummary.receiptpricetotal = 0 /*%end*/
    /*%if conditionDto.paymentStatus == "2" */
      AND (
          EXISTS (
            SELECT * FROM ordersummary AS SUMMARY
            WHERE ordersummary.orderseq = SUMMARY.orderseq
            AND SUMMARY.orderprice > 0 AND SUMMARY.receiptpricetotal = SUMMARY.orderprice
          )
          -- 全額割引（ポイント、クーポン）は入金状態に関係なく対象とする
          OR ordersettlement.settlementmethodtype = 'E'
      )
    /*%end*/
    /*%if conditionDto.paymentStatus == "3" */ AND ordersummary.receiptpricetotal != 0 AND ordersummary.receiptpricetotal != ordersummary.orderprice /*%end*/
    /*%if conditionDto.paymentStatus == "4" */ AND ordersummary.receiptpricetotal != ordersummary.orderprice /*%end*/
    /*%if conditionDto.billStatus != null */ AND orderbill.billstatus = /*conditionDto.billStatus*/'' /*%end*/
    /*%if conditionDto.couponId != null */ AND coupon.couponid = /*conditionDto.couponId*/'' /*%end*/
    /*%if conditionDto.couponCode != null */ AND coupon.couponcode = /*conditionDto.couponCode*/'' /*%end*/
    /*%if conditionDto.goodsGroupCode != null */ AND ordergoods.goodsgroupcode LIKE '%' || /*conditionDto.goodsGroupCode*/'' || '%'/*%end*/
    /*%if conditionDto.goodsCode != null */ AND ordergoods.goodscode LIKE '%' || /*conditionDto.goodsCode*/'' || '%' /*%end*/
    /*%if conditionDto.individualDeliveryType == "1" */ AND ordergoods.individualdeliverytype = /*conditionDto.individualDeliveryType*/'' /*%end*/
    /*%if conditionDto.orderCodeList != null */ AND ordersummary.orderCode IN /*conditionDto.orderCodeList*/() /*%end*/

    /*%if conditionDto.janCode != null
    || conditionDto.catalogCode != null
    || conditionDto.goodsGroupName != null
    || conditionDto.saleStartTimeFrom != null
    || conditionDto.saleStartTimeTo  != null*/
      AND EXISTS (
        SELECT DISTINCT goods.goodsseq
        FROM goods, goodsgroup
        WHERE goods.goodsseq = ordergoods.goodsseq
        AND goods.goodsgroupseq = goodsgroup.goodsgroupseq
        /*%if conditionDto.janCode != null */ AND goods.jancode LIKE '%' || /*conditionDto.janCode*/0 || '%' /*%end*/
        /*%if conditionDto.catalogCode != null */ AND goods.catalogcode LIKE '%' || /*conditionDto.catalogCode*/0 || '%' /*%end*/
        /*%if conditionDto.goodsGroupName != null */ AND (goodsgroup.goodsgroupname LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%' OR goods.unitvalue1 LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%' OR goods.unitvalue2 LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%')/*%end*/
        /*%if conditionDto.saleStartTimeFrom != null */
          AND (goods.saleStartTimePC >= /*conditionDto.saleStartTimeFrom*/'' OR goods.saleStartTimeMB >= /*conditionDto.saleStartTimeFrom*/'' )
        /*%end*/
        /*%if conditionDto.saleStartTimeTo != null */
          AND (goods.saleStartTimePC <= /*conditionDto.saleStartTimeTo*/'' OR goods.saleStartTimeMB <= /*conditionDto.saleStartTimeTo*/'' )
        /*%end*/
      )
    /*%end*/

    /*%if conditionDto.timeType == "7" || conditionDto.timeType == "8" || conditionDto.timeType == "9" || conditionDto.timeType == "10" || conditionDto.timeType == "11" || conditionDto.timeType == "12" */
      AND (
          EXISTS (
            SELECT DISTINCT orderreceiptofmoney.orderseq FROM orderreceiptofmoney
            WHERE orderreceiptofmoney.orderseq = ordersummary.orderseq
            /*%if conditionDto.timeType == "7" */ AND receiptprice >= 0 AND receipttime >= /*conditionDto.timeFrom*/'' AND receipttime <= /*conditionDto.timeTo*/'' /*%end*/
            /*%if conditionDto.timeType == "8" */ AND receiptprice >= 0 AND receipttime >= /*conditionDto.timeFrom*/'' /*%end*/
            /*%if conditionDto.timeType == "9" */ AND receiptprice >= 0 AND receipttime <= /*conditionDto.timeTo*/'' /*%end*/
            /*%if conditionDto.timeType == "10" */ AND receiptprice < 0 AND receipttime >= /*conditionDto.timeFrom*/'' AND receipttime <= /*conditionDto.timeTo*/'' /*%end*/
            /*%if conditionDto.timeType == "11" */ AND receiptprice < 0 AND receipttime >= /*conditionDto.timeFrom*/'' /*%end*/
            /*%if conditionDto.timeType == "12" */ AND receiptprice < 0 AND receipttime <= /*conditionDto.timeTo*/'' /*%end*/
          )
          /*%if conditionDto.timeType == "7" || conditionDto.timeType == "8" || conditionDto.timeType == "9"*/
	         -- 入金日時で検索の場合、全額割引（ポイント、クーポン）は受注日時で検索する
	      OR (
	        ordersettlement.settlementmethodtype = 'E'
	        /*%if conditionDto.timeType == "7"*/AND ordersummary.orderTime >= /*conditionDto.timeFrom*/'' AND ordersummary.orderTime <= /*conditionDto.timeTo*/''/*%end*/
	        /*%if conditionDto.timeType == "8"*/AND ordersummary.orderTime >= /*conditionDto.timeFrom*/'' /*%end*/
	        /*%if conditionDto.timeType == "9"*/AND ordersummary.orderTime <= /*conditionDto.timeTo*/'' /*%end*/
	      )
	     /*%end*/
      )

    /*%end*/

    /*%if conditionDto.timeType == "13" || conditionDto.timeType == "14" || conditionDto.timeType == "15" */
      AND EXISTS (
        SELECT DISTINCT orderindex.orderseq
        FROM orderindex
        WHERE orderindex.orderseq = ordersummary.orderseq
        AND orderindex.processtype = 'O02'
        /*%if conditionDto.timeType == "13" */ AND orderindex.registtime >= /*conditionDto.timeFrom*/'' AND orderindex.registtime <= /*conditionDto.timeTo*/'' /*%end*/
        /*%if conditionDto.timeType == "14" */ AND orderindex.registtime >= /*conditionDto.timeFrom*/'' /*%end*/
        /*%if conditionDto.timeType == "15" */ AND orderindex.registtime <= /*conditionDto.timeTo*/'' /*%end*/
      )
    /*%end*/
    GROUP BY
      ordergoods.goodsseq
    , ordergoods.goodsgroupcode
    , ordergoods.goodscode
    , ordergoods.individualdeliverytype
    , ordergoods.goodsprice
    , ordergoods.goodsgroupname
) ordergoods
LEFT OUTER JOIN goods
  ON ordergoods.goodsseq = goods.goodsseq

ORDER BY
    /*%if conditionDto.pageInfo.orderField == "goodsGroupCode"*/
        ordergoods.goodsGroupCode /*%if conditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/

    /*%elseif conditionDto.pageInfo.orderField == "goodsCode"*/
        ordergoods.goodsCode /*%if conditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/

    /*%elseif conditionDto.pageInfo.orderField == "janCode"*/
        goods.jancode /*%if conditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/

    /*%elseif conditionDto.pageInfo.orderField == "catalogCode"*/
       goods.catalogcode /*%if conditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/

    /*%elseif conditionDto.pageInfo.orderField == "unitValue1"*/
        goods.unitValue1 /*%if conditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/

    /*%elseif conditionDto.pageInfo.orderField == "unitValue2"*/
        goods.unitValue2 /*%if conditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/

    /*%elseif conditionDto.pageInfo.orderField == "individualDeliveryType"*/
        ordergoods.individualDeliveryType /*%if conditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/

    /*%elseif conditionDto.pageInfo.orderField == "goodsPrice"*/
        ordergoods.goodsprice /*%if conditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/

    /*%elseif conditionDto.pageInfo.orderField == "goodsCount"*/
        ordergoods.goodscount /*%if conditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/
    /*%else*/
    1 ASC
    /*%end*/

, ordergoods.goodsGroupCode
, ordergoods.goodsCode
, ordergoods.individualDeliveryType
, ordergoods.goodsPrice