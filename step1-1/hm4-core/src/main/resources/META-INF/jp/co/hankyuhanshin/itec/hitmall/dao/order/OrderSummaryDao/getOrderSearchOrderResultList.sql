SELECT DISTINCT
  BASE.orderseq
, BASE.orderversionno
, BASE.ordercode
, BASE.ordertype
, BASE.ordertime
, BASE.salestime
, BASE.canceltime
, BASE.salesflag
, BASE.cancelflag
, BASE.expiredSentFlag
, BASE.waitingflag
, BASE.orderstatus
, BASE.orderStatusForSearchResult
, BASE.goodspricetotal
, BASE.orderprice
, BASE.ordersitetype
, BASE.carriertype
, BASE.settlementmethodseq
, BASE.memberinfoseq
, BASE.prefecturetype
, BASE.ordersex
, BASE.orderagetype
, BASE.repeatpurchasetype
, BASE.versionno
, BASE.shopseq
, BASE.receiptpricetotal
, BASE.registtime
, BASE.updatetime
, BASE.ordername
, BASE.orderkana
, BASE.ordertel
, BASE.ordercontacttel
, BASE.ordermail
, orderreceiptofmoney.receipttime
, BASE.emergencyflag
, BASE.paymentStatus
, BASE.settlementmethodname
/*%if conditionDto.shipmentSearch == "1"*/
, BASE.receivername
, BASE.receiverkana
, BASE.receivertel
, BASE.orderconsecutiveno
, BASE.deliverycode
, BASE.shipmentdate
, BASE.shipmentstatus
, BASE.deliverynote
, BASE.deliverymethodseq
, BASE.deliverymethodname
, BASE.reservationdeliveryflag
/*%end*/
FROM
(
  SELECT
    ordersummary.orderseq
  , ordersummary.orderversionno
  , ordersummary.ordercode
  , ordersummary.ordertype
  , ordersummary.ordertime
  , ordersummary.salestime
  , ordersummary.canceltime
  , ordersummary.salesflag
  , ordersummary.cancelflag
  , ordersummary.expiredSentFlag
  , ordersummary.waitingflag
  , ordersummary.orderstatus
  , CASE
      WHEN orderbill.emergencyflag = '1' THEN '7'
      WHEN ordersummary.cancelflag = '1' THEN '5'
      WHEN ordersummary.waitingflag = '1' THEN '6'
      ELSE ordersummary.orderstatus
      END AS orderStatusForSearchResult
  , ordersettlement.goodspricetotal
-- PDR Migrate Customization from here
  , (ordersettlement.goodsPriceTotal + ordersettlement.taxprice + ordersettlement.carriage - ordersettlement.couponDiscountPrice + ordersettlement.othersPriceTotal + ordersettlement.settlementCommission) AS orderprice
-- PDR Migrate Customization to here
  , ordersummary.ordersitetype
  , ordersummary.carriertype
  , ordersummary.settlementmethodseq
  , ordersummary.memberinfoseq
  , ordersummary.prefecturetype
  , ordersummary.ordersex
  , ordersummary.orderagetype
  , ordersummary.repeatpurchasetype
  , ordersummary.versionno
  , ordersummary.shopseq
  , ordersummary.receiptpricetotal
  , ordersummary.registtime
  , ordersummary.updatetime
  , orderperson.orderlastname || ' ' || COALESCE(orderperson.orderfirstname, '') AS ordername
  , orderperson.orderlastkana || ' ' || orderperson.orderfirstkana AS orderkana
  , orderperson.ordertel
  , orderperson.ordercontacttel
  , orderperson.ordermail
  , orderdelivery.orderconsecutiveno
  , orderdelivery.receiverlastname || ' ' || COALESCE(orderdelivery.receiverfirstname, '') AS receivername
  , orderdelivery.receiverlastkana || ' ' || orderdelivery.receiverfirstkana AS receiverkana
  , orderdelivery.receivertel
  , orderdelivery.deliverycode
  , orderdelivery.shipmentdate
  , orderdelivery.shipmentstatus
  , orderdelivery.deliverynote
  , orderdelivery.deliverymethodseq
  , orderdelivery.reservationdeliveryflag
  , orderbill.emergencyflag
  , CASE
      WHEN ordersummary.receiptpricetotal = 0 THEN '1'
      WHEN ordersummary.orderprice > 0
        AND ordersummary.receiptpricetotal = ordersummary.orderprice THEN '2'
      ELSE '3'
      END AS paymentStatus
  , settlementmethod.settlementmethodname
  , deliverymethod.deliverymethodname
  , orderindex.orderreceiptofmoneyversionno
  FROM
    ordersummary
  , orderindex
    LEFT OUTER JOIN coupon
    ON orderindex.couponseq = coupon.couponseq
    AND orderindex.couponversionno = coupon.couponversionno
  , orderperson
  , orderdelivery
  , ordersettlement
  , orderbill
  , settlementmethod
  , deliverymethod
  WHERE
    ordersummary.shopseq = /*conditionDto.shopSeq*/0
    AND ordersummary.orderseq = orderindex.orderseq
    AND ordersummary.orderversionno = orderindex.orderversionno
    AND orderindex.orderseq = orderperson.orderseq
    AND orderindex.orderpersonversionno = orderperson.orderpersonversionno
    AND orderindex.orderseq = orderdelivery.orderseq
    AND orderindex.orderdeliveryversionno = orderdelivery.orderdeliveryversionno
    AND orderindex.orderseq = ordersettlement.orderseq
    AND orderindex.ordersettlementversionno = ordersettlement.ordersettlementversionno
    AND orderindex.orderseq = orderbill.orderseq
    AND orderindex.orderbillversionno = orderbill.orderbillversionno
    AND ordersettlement.settlementmethodseq = settlementmethod.settlementmethodseq
    AND orderdelivery.deliverymethodseq = deliverymethod.deliverymethodseq
    /*%if conditionDto.orderSiteTypeList != null*/
      AND ordersummary.orderSiteType IN /*conditionDto.orderSiteTypeList*/(0)
    /*%end*/
    /*%if conditionDto.orderStatus != null */
      /*%if conditionDto.orderStatus == "4" */
        AND ordersummary.orderStatus <> '3'
        AND orderdelivery.shipmentstatus = '0'
      /*%else*/
        AND ordersummary.orderStatus = /*conditionDto.orderStatus*/0
      /*%end*/
    /*%end*/
    /*%if conditionDto.cancelFlag != null */ AND ordersummary.cancelflag = /*conditionDto.cancelFlag*/0 /*%end*/
    /*%if conditionDto.waitingFlag != null */ AND ordersummary.waitingflag = /*conditionDto.waitingFlag*/0 /*%end*/
    /*%if conditionDto.emergencyFlag != null */ AND orderbill.emergencyflag = /*conditionDto.emergencyFlag*/0 /*%end*/
    /*%if conditionDto.orderCode != null */ AND ordersummary.orderCode LIKE '%' || /*conditionDto.orderCode*/0 || '%' /*%end*/
    /*%if conditionDto.orderTypeList != null*/ AND ordersummary.ordertype IN /*conditionDto.orderTypeList*/(0) /*%end*/
    /*%if conditionDto.memberInfoSeq != null */ AND ordersummary.memberinfoseq = /*conditionDto.memberInfoSeq*/0 /*%end*/
    /*%if conditionDto.timeType == "1" */ AND ordersummary.orderTime >= /*conditionDto.timeFrom*/0 AND ordersummary.orderTime <= /*conditionDto.timeTo*/0 /*%end*/
    /*%if conditionDto.timeType == "2" */ AND ordersummary.orderTime >= /*conditionDto.timeFrom*/0 /*%end*/
    /*%if conditionDto.timeType == "3" */ AND ordersummary.orderTime <= /*conditionDto.timeTo*/0 /*%end*/
    /*%if conditionDto.timeType == "4" */ AND orderdelivery.shipmentdate >= /*conditionDto.timeFrom*/0 AND orderdelivery.shipmentdate <= /*conditionDto.timeTo*/0 /*%end*/
    /*%if conditionDto.timeType == "5" */ AND orderdelivery.shipmentdate >= /*conditionDto.timeFrom*/0 /*%end*/
    /*%if conditionDto.timeType == "6" */ AND orderdelivery.shipmentdate <= /*conditionDto.timeTo*/0 /*%end*/
    /*%if conditionDto.timeType == "16" */ AND orderbill.paymenttimelimitdate >= /*conditionDto.timeFrom*/0 AND orderbill.paymenttimelimitdate <= /*conditionDto.timeTo*/0 /*%end*/
    /*%if conditionDto.timeType == "17" */ AND orderbill.paymenttimelimitdate >= /*conditionDto.timeFrom*/0 /*%end*/
    /*%if conditionDto.timeType == "18" */ AND orderbill.paymenttimelimitdate <= /*conditionDto.timeTo*/0 /*%end*/
    /*%if conditionDto.timeType == "19" */ AND orderdelivery.receiverdate >= /*conditionDto.timeFrom*/0 AND orderdelivery.receiverdate <= /*conditionDto.timeTo*/0 /*%end*/
    /*%if conditionDto.timeType == "20" */ AND orderdelivery.receiverdate >= /*conditionDto.timeFrom*/0 /*%end*/
    /*%if conditionDto.timeType == "21" */ AND orderdelivery.receiverdate <= /*conditionDto.timeTo*/0 /*%end*/
    /*%if conditionDto.orderName != null */ AND ((orderperson.orderlastname || COALESCE(orderperson.orderfirstname, '')) LIKE '%' || /*conditionDto.orderName*/0 || '%' OR  (orderperson.orderlastkana || orderperson.orderfirstkana) LIKE '%' || /*conditionDto.orderName*/0 || '%') /*%end*/
    /*%if conditionDto.orderTel != null */ AND (orderperson.orderTel LIKE '%' || /*conditionDto.orderTel*/0 || '%' OR orderperson.ordercontacttel LIKE '%' || /*conditionDto.orderTel*/0 || '%') /*%end*/
    /*%if conditionDto.orderMail != null */ AND orderperson.ordermail LIKE '%' || /*conditionDto.orderMail*/0 || '%' /*%end*/
    /*%if conditionDto.receiverName != null */ AND ((orderdelivery.receiverlastname || COALESCE(orderdelivery.receiverfirstname, '')) LIKE '%' || /*conditionDto.receiverName*/0 || '%' OR  (orderdelivery.receiverlastkana || orderdelivery.receiverfirstkana) LIKE '%' || /*conditionDto.receiverName*/0 || '%') /*%end*/
    /*%if conditionDto.receiverTel != null */ AND orderdelivery.receivertel LIKE '%' || /*conditionDto.receiverTel*/0 || '%'/*%end*/
    /*%if conditionDto.deliveryMethodSeq != null */ AND orderdelivery.deliverymethodseq = /*conditionDto.deliveryMethodSeq*/0 /*%end*/
    /*%if conditionDto.deliveryCode != null */ AND orderdelivery.deliverycode = /*conditionDto.deliveryCode*/0 /*%end*/
    /*%if conditionDto.shipmentStatus != null */ AND orderdelivery.shipmentstatus IN /*conditionDto.shipmentStatus*/(0) /*%end*/
    /*%if conditionDto.settlementMethodSeq != null */ AND ordersummary.settlementmethodseq = /*conditionDto.settlementMethodSeq*/0 /*%end*/
    /*%if conditionDto.billType != null */ AND ordersettlement.billtype = /*conditionDto.billType*/0 /*%end*/
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
    /*%if conditionDto.allPaymentRegistFlag*/ AND ordersummary.receiptpricetotal != ordersummary.orderprice /*%end*/
    /*%if conditionDto.allShipmentRegistFlag*/ AND orderdelivery.shipmentstatus = '0' /*%end*/
    /*%if conditionDto.billStatus != null */ AND orderbill.billstatus = /*conditionDto.billStatus*/0 /*%end*/
    /*%if conditionDto.couponId != null */ AND coupon.couponid = /*conditionDto.couponId*/0 /*%end*/
    /*%if conditionDto.couponCode != null */ AND coupon.couponcode = /*conditionDto.couponCode*/0 /*%end*/
    /*%if conditionDto.orderCodeList != null */
        AND ordersummary.orderCode IN /*conditionDto.orderCodeList*/(0)
    /*%end*/
    /*%if conditionDto.timeType == "7" || conditionDto.timeType == "8" || conditionDto.timeType == "9" || conditionDto.timeType == "10" || conditionDto.timeType == "11" || conditionDto.timeType == "12" */
      AND (
      EXISTS (
            SELECT DISTINCT orderreceiptofmoney.orderseq FROM orderreceiptofmoney
            WHERE orderreceiptofmoney.orderseq = ordersummary.orderseq
            /*%if conditionDto.timeType == "7" */ AND receiptprice >= 0 AND receipttime >= /*conditionDto.timeFrom*/0 AND receipttime <= /*conditionDto.timeTo*/0 /*%end*/
            /*%if conditionDto.timeType == "8" */ AND receiptprice >= 0 AND receipttime >= /*conditionDto.timeFrom*/0 /*%end*/
            /*%if conditionDto.timeType == "9" */ AND receiptprice >= 0 AND receipttime <= /*conditionDto.timeTo*/0 /*%end*/
            /*%if conditionDto.timeType == "10" */ AND receiptprice < 0 AND receipttime >= /*conditionDto.timeFrom*/0 AND receipttime <= /*conditionDto.timeTo*/0 /*%end*/
            /*%if conditionDto.timeType == "11" */ AND receiptprice < 0 AND receipttime >= /*conditionDto.timeFrom*/0 /*%end*/
            /*%if conditionDto.timeType == "12" */ AND receiptprice < 0 AND receipttime <= /*conditionDto.timeTo*/0 /*%end*/
        )
        /*%if conditionDto.timeType == "7" || conditionDto.timeType == "8" || conditionDto.timeType == "9"*/
	    -- 入金日時で検索の場合、全額割引（ポイント、クーポン）は受注日時で検索する
	    OR (
	        ordersettlement.settlementmethodtype = 'E'
	        /*%if conditionDto.timeType == "7"*/AND ordersummary.orderTime >= /*conditionDto.timeFrom*/0 AND ordersummary.orderTime <= /*conditionDto.timeTo*/0/*%end*/
	        /*%if conditionDto.timeType == "8"*/AND ordersummary.orderTime >= /*conditionDto.timeFrom*/0/*%end*/
	        /*%if conditionDto.timeType == "9"*/AND ordersummary.orderTime <= /*conditionDto.timeTo*/0/*%end*/
	    )
	    /*%end*/
      )
    /*%end*/
    /*%if conditionDto.goodsGroupCode != null
    || conditionDto.goodsCode != null
    || conditionDto.individualDeliveryType == "1"
    || conditionDto.janCode != null
    || conditionDto.catalogCode != null
    || conditionDto.goodsGroupName != null
    || !conditionDto.includeCancelGoodsFlag
    || conditionDto.saleStartTimeFrom != null
    || conditionDto.saleStartTimeTo != null */
      AND EXISTS (
        SELECT DISTINCT ordergoods.orderseq
        FROM ordergoods, goods, goodsgroup
        WHERE orderindex.orderseq = ordergoods.orderseq
        AND orderindex.ordergoodsversionno = ordergoods.ordergoodsversionno
        AND ordergoods.goodsseq = goods.goodsseq
        AND goods.goodsgroupseq = goodsgroup.goodsgroupseq
        /*%if conditionDto.goodsGroupCode != null */ AND ordergoods.goodsgroupcode LIKE '%' || /*conditionDto.goodsGroupCode*/0 || '%'/*%end*/
        /*%if conditionDto.goodsCode != null */ AND ordergoods.goodscode LIKE '%' || /*conditionDto.goodsCode*/0 || '%' /*%end*/
        /*%if conditionDto.individualDeliveryType == "1" */ AND ordergoods.individualdeliverytype = /*conditionDto.individualDeliveryType*/0 /*%end*/
        /*%if conditionDto.janCode != null */ AND goods.jancode LIKE '%' || /*conditionDto.janCode*/0 || '%' /*%end*/
        /*%if conditionDto.catalogCode != null */ AND goods.catalogcode LIKE '%' || /*conditionDto.catalogCode*/0 || '%' /*%end*/
        /*%if conditionDto.goodsGroupName != null */ AND (goodsgroup.goodsgroupname LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%' OR goods.unitvalue1 LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%' OR goods.unitvalue2 LIKE '%' || /*conditionDto.goodsGroupName*/0 || '%')/*%end*/
        /*%if !conditionDto.includeCancelGoodsFlag*/AND ordergoods.goodscount > 0/*%end*/
        /*%if conditionDto.saleStartTimeFrom != null */
          AND (goods.saleStartTimePC >= /*conditionDto.saleStartTimeFrom*/0 )
        /*%end*/
        /*%if conditionDto.saleStartTimeTo != null */
          AND (goods.saleStartTimePC <= /*conditionDto.saleStartTimeTo*/0 )
        /*%end*/
      )
    /*%end*/
    /*%if conditionDto.timeType == "13" || conditionDto.timeType == "14" || conditionDto.timeType == "15" */
      AND EXISTS (
        SELECT DISTINCT orderindex.orderseq
        FROM orderindex
        WHERE orderindex.orderseq = ordersummary.orderseq
        AND orderindex.processtype = 'O02'
        /*%if conditionDto.timeType == "13" */ AND orderindex.registtime >= /*conditionDto.timeFrom*/0 AND orderindex.registtime <= /*conditionDto.timeTo*/0 /*%end*/
        /*%if conditionDto.timeType == "14" */ AND orderindex.registtime >= /*conditionDto.timeFrom*/0 /*%end*/
        /*%if conditionDto.timeType == "15" */ AND orderindex.registtime <= /*conditionDto.timeTo*/0 /*%end*/
      )
    /*%end*/
  ) AS BASE
  LEFT JOIN orderreceiptofmoney
  ON BASE.orderseq = orderreceiptofmoney.orderseq
  AND BASE.orderreceiptofmoneyversionno = orderreceiptofmoney.orderreceiptofmoneyversionno
WHERE
  1 = 1

/*************** sort ***************/
ORDER BY
/*%if conditionDto.pageInfo.orderField != null*/

/*%if conditionDto.pageInfo.orderField == "orderCode"*/
BASE.orderCode /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "orderConsecutiveNo"*/
BASE.orderConsecutiveNo /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "orderTime"*/
BASE.orderTime /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "orderStatus"*/
BASE.orderStatus /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "orderStatusForSearchResult"*/
BASE.orderStatusForSearchResult /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "orderName"*/
BASE.orderName /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "orderPrice"*/
BASE.orderPrice /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "settlementMethod"*/
BASE.settlementMethodseq /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "paymentStatus"*/
BASE.paymentStatus /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "receiptTime"*/
orderreceiptofmoney.receipttime /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "receiptPriceTotal"*/
BASE.receiptPriceTotal /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "deliveryMethod"*/
BASE.deliverymethodseq /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "shipmentStatus"*/
BASE.shipmentStatus /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "orderSiteType"*/
BASE.orderSiteType /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "orderType"*/
BASE.orderType /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "deliveryNote"*/
BASE.deliveryNote /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "receiverName"*/
BASE.receiverName /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/
/*%if conditionDto.pageInfo.orderField == "reservationDeliveryFlag"*/
BASE.reservationDeliveryFlag /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/
/*%if conditionDto.shipmentSearch == "1" && conditionDto.pageInfo.orderField != "orderConsecutiveNo"*/
, BASE.orderConsecutiveNo
/*%end*/

/*%else*/
1 ASC
/*%end*/
