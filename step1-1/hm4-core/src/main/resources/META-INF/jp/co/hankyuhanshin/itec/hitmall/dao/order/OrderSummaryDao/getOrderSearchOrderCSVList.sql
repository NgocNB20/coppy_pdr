SELECT
  CASE
    WHEN minimumorderdisplay.orderdisplay = ordergoods.orderdisplay THEN 0
    ELSE 1
  END AS linetype
  ,ordersummary.ordercode
  ,orderdelivery.orderconsecutiveno
  ,ordersummary.ordertype
  ,ordersummary.ordersitetype
  ,ordersummary.orderdevicetype
  ,ordersummary.useragent
  ,to_char(ordersummary.ordertime, 'yyyy-mm-dd HH24:MI:SS') AS ordertime
  ,ordersummary.orderstatus
  ,to_char(orderdelivery.shipmentdate, 'yyyy-mm-dd') AS shipmentdate
  ,orderdelivery.shipmentstatus
  ,to_char(ordersummary.canceltime, 'yyyy-mm-dd HH24:MI:SS') AS canceltime
-- PDR Migrate Customization from here
  , (ordersettlement.goodsPriceTotal + ordersettlement.taxprice + ordersettlement.carriage - ordersettlement.couponDiscountPrice + ordersettlement.othersPriceTotal + ordersettlement.settlementCommission) AS orderprice
-- PDR Migrate Customization to here
  ,ordersummary.receiptpricetotal
  ,CASE
     WHEN ordersummary.receiptpricetotal = 0 THEN '1'
     WHEN ordersummary.orderprice > 0 AND ordersummary.receiptpricetotal = ordersummary.orderprice THEN '2'
     ELSE '3'
   END AS paymentStatus
  ,to_char(orderreceiptofmoney.receipttime, 'yyyy-mm-dd') AS receiptymd
  ,orderreceiptofmoney.receiptprice
  ,ordergoods.goodsgroupcode
  ,ordergoods.goodscode
  ,ordergoods.jancode
  ,ordergoods.catalogcode
  ,ordergoods.goodsgroupname
  ,ordergoods.unitvalue1
  ,ordergoods.unitvalue2
  ,ordergoods.goodsprice
  ,ordergoods.goodscount
  ,(ordergoods.goodsprice * ordergoods.goodscount) AS summaryPrice
  ,ordergoods.ordersetting1
  ,ordergoods.ordersetting2
  ,ordergoods.ordersetting3
  ,ordergoods.ordersetting4
  ,ordergoods.ordersetting5
  ,ordergoods.ordersetting6
  ,ordergoods.ordersetting7
  ,ordergoods.ordersetting8
  ,ordergoods.ordersetting9
  ,ordergoods.ordersetting10
  ,(SELECT goods.saleStartTimePC FROM goods WHERE goods.goodsseq = ordergoods.goodsseq) AS saleStartTimePC
  ,ordersettlement.goodspricetotal
  ,ordersettlement.settlementcommission
  ,ordersettlement.carriage
  ,ordersettlement.otherspricetotal
  ,coupon.couponid
  ,coupon.couponname
  ,(ordersettlement.coupondiscountprice*-1) AS coupondiscountprice
  ,ordersettlement.taxprice
  ,orderperson.orderlastname
  ,orderperson.orderfirstname
  ,orderperson.orderlastkana
  ,orderperson.orderfirstkana
  ,orderperson.orderzipcode
  ,orderperson.orderprefecture
  ,orderperson.orderaddress1
  ,orderperson.orderaddress2
  ,orderperson.orderaddress3
  ,orderperson.ordertel
  ,orderperson.ordercontacttel
  ,orderperson.ordermail
  ,to_char(orderperson.orderbirthday, 'yyyy-mm-dd') AS orderbirthday
  ,orderperson.orderagetype
  ,orderperson.ordersex
  ,ordersummary.repeatpurchasetype
  ,orderdelivery.receiverlastname
  ,orderdelivery.receiverfirstname
  ,orderdelivery.receiverlastkana
  ,orderdelivery.receiverfirstkana
  ,orderdelivery.receiverzipcode
  ,orderdelivery.receiverprefecture
  ,orderdelivery.receiveraddress1
  ,orderdelivery.receiveraddress2
  ,orderdelivery.receiveraddress3
  ,orderdelivery.receivertel
  ,orderdelivery.carriage AS orderDeliveryCarriage
  ,(SELECT deliverymethod.deliverymethodname FROM deliverymethod WHERE deliverymethod.deliverymethodseq = orderdelivery.deliverymethodseq) AS deliverymethodname
  ,to_char(orderdelivery.receiverdate, 'yyyy-mm-dd') AS receiverdate
  ,orderdelivery.receivertimezone
  ,orderdelivery.invoiceattachmentflag
  ,orderdelivery.deliverynote
  ,orderdelivery.deliverycode
  ,(SELECT settlementmethodname FROM settlementmethod WHERE settlementmethod.settlementmethodseq = ordersummary.settlementmethodseq) AS settlementmethodname
  ,orderbill.billstatus
  ,orderbill.emergencyflag
  ,to_char(orderbill.paymenttimelimitdate, 'yyyy-mm-dd') AS paymenttimelimitdate
  ,ordermemo.memo
  ,orderperson.memberinfoseq
FROM
  ordersummary
  INNER JOIN orderindex
    ON ordersummary.orderseq = orderindex.orderseq
    AND ordersummary.orderversionno = orderindex.orderversionno
  INNER JOIN ordergoods
    ON orderindex.orderseq = ordergoods.orderseq
    AND orderindex.ordergoodsversionno = ordergoods.ordergoodsversionno
  INNER JOIN orderdelivery
    ON orderindex.orderseq = orderdelivery.orderseq
    AND orderindex.orderdeliveryversionno = orderdelivery.orderdeliveryversionno
    AND ordergoods.orderConsecutiveNo = orderdelivery.orderConsecutiveNo
  INNER JOIN orderperson
    ON orderindex.orderseq = orderperson.orderseq
    AND orderindex.orderpersonversionno = orderperson.orderpersonversionno
  INNER JOIN ordersettlement
    ON orderindex.orderseq = ordersettlement.orderseq
    AND orderindex.ordersettlementversionno = ordersettlement.ordersettlementversionno
  LEFT OUTER JOIN ordermemo
    ON orderindex.orderseq = ordermemo.orderseq
    AND orderindex.ordermemoversionno = ordermemo.ordermemoversionno
  LEFT OUTER JOIN orderreceiptofmoney
    ON orderindex.orderseq = orderreceiptofmoney.orderseq
    AND orderindex.orderreceiptofmoneyversionno = orderreceiptofmoney.orderreceiptofmoneyversionno
  LEFT OUTER JOIN orderbill
    ON orderindex.orderseq = orderbill.orderseq
    AND orderindex.orderbillversionno = orderbill.orderbillversionno
  LEFT OUTER JOIN (
    SELECT
      ordergoods.orderseq
      ,ordergoods.ordergoodsversionno
      ,MIN(ordergoods.orderdisplay) AS orderdisplay
    FROM
      ordergoods
    GROUP BY
      ordergoods.orderseq
      ,ordergoods.ordergoodsversionno
  ) minimumorderdisplay
    ON orderindex.orderseq = minimumorderdisplay.orderseq
    AND orderindex.ordergoodsversionno = minimumorderdisplay.ordergoodsversionno
  LEFT OUTER JOIN coupon
    ON orderindex.couponSeq = coupon.couponseq
    AND orderindex.couponversionno = coupon.couponversionno
WHERE
  ordersummary.shopseq = /*conditionDto.shopSeq*/0
  /*%if conditionDto.orderSiteTypeList != null*/ AND ordersummary.orderSiteType IN /*conditionDto.orderSiteTypeList*/(0) /*%end*/
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
  /*%if conditionDto.billStatus != null */ AND orderbill.billstatus = /*conditionDto.billStatus*/0 /*%end*/
  /*%if conditionDto.couponId != null */ AND coupon.couponid = /*conditionDto.couponId*/0 /*%end*/
  /*%if conditionDto.couponCode != null */ AND coupon.couponcode = /*conditionDto.couponCode*/0 /*%end*/
  /*%if conditionDto.orderCodeList != null */
  AND ordersummary.orderCode IN /*conditionDto.orderCodeList*/(0)
  /*%end*/
  /*%if conditionDto.goodsGroupCode != null
  || conditionDto.goodsCode != null
  || conditionDto.janCode != null
  || conditionDto.catalogCode != null
  || conditionDto.goodsGroupName != null
  || !conditionDto.includeCancelGoodsFlag
  || conditionDto.individualDeliveryType == "1" */
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
      /*%if conditionDto.janCode != null */ AND goods.jancode LIKE '%' || /*conditionDto.janCode*/0 /*%end*/
      /*%if conditionDto.catalogCode != null */ AND goods.catalogcode LIKE '%' || /*conditionDto.catalogCode*/0 /*%end*/
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
  /*%if conditionDto.timeType == "7" || conditionDto.timeType == "8" || conditionDto.timeType == "9" || conditionDto.timeType == "10" || conditionDto.timeType == "11" || conditionDto.timeType == "12" */
    AND (
      EXISTS (
          SELECT DISTINCT orderreceiptofmoney.orderseq  FROM orderreceiptofmoney
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
ORDER BY ordersummary.ordertime DESC,ordersummary.orderseq DESC,orderdelivery.orderconsecutiveno ASC,linetype ASC,ordergoods.orderdisplay ASC
