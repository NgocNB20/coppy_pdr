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
  ordersummary.shopseq = /*shopSeq*/0
AND
  ordersummary.orderseq IN /*orderSeqList*/(0)
ORDER BY ordersummary.ordertime DESC,ordersummary.orderseq DESC,orderdelivery.orderconsecutiveno ASC,linetype ASC,ordergoods.orderdisplay ASC
