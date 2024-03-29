SELECT
  orderseq
, orderversionno
, ordercode
, ordertime
, salestime
, canceltime
, salesflag
, cancelflag
, waitingflag
, orderstatus
, goodspricetotal
, orderprice
, receiptpricetotal
, ordersitetype
, carriertype
, settlementmethodseq
, deliverymethodseq
, memberinfoseq
, prefecturetype
, ordersex
, orderagetype
, repeatpurchasetype
, versionno
, shopseq
, settlementmailrequired
, remindersentflag
, expiredsentflag
, registtime
, updatetime
, ordergoodsversionno
, coupondiscountprice
, settlementmethodname
, settlementmethoddisplaynamepc
, settlementmethoddisplaynamemb
, receivertimezone
FROM (
  SELECT
    ordersummary.orderseq
  , ordersummary.orderversionno
  , ordersummary.ordercode
  , ordersummary.ordertime
  , ordersummary.salestime
  , ordersummary.canceltime
  , ordersummary.salesflag
  , ordersummary.cancelflag
  , ordersummary.waitingflag
  , ordersummary.orderstatus
  , ordersummary.orderprice
  , ordersummary.receiptpricetotal
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
  , ordersummary.settlementmailrequired
  , ordersummary.remindersentflag
  , ordersummary.expiredsentflag
  , ordersummary.registtime
  , ordersummary.updatetime
  , orderindex.ordergoodsversionno
  , ordersettlement.preBeforeDiscountOrderPrice
  , ordersettlement.goodspricetotal
  , ordersettlement.coupondiscountprice
  , settlementmethod.settlementmethodname
  , settlementmethod.settlementmethoddisplaynamepc
  , settlementmethod.settlementmethoddisplaynamemb
  , orderdelivery.deliverymethodseq
  , orderdelivery.receivertimezone
  FROM
    ordersummary
      JOIN
        orderindex
      ON
        ordersummary.orderseq = orderindex.orderseq
        AND ordersummary.orderversionno = orderindex.orderversionno
      JOIN
        ordersettlement
      ON
        orderindex.orderseq = ordersettlement.orderseq
        AND orderindex.ordersettlementversionno = ordersettlement.ordersettlementversionno
      JOIN
        settlementmethod
      ON
        ordersettlement.settlementmethodseq = settlementmethod.settlementmethodseq
      JOIN
        orderdelivery
      ON
        orderindex.orderseq = orderdelivery.orderseq
        AND orderindex.orderdeliveryversionno = orderdelivery.orderdeliveryversionno
        AND orderdelivery.orderconsecutiveno = 1
    WHERE
        ordersummary.memberInfoSeq = /*conditionDto.memberInfoSeq*/0
) AS allorder
ORDER BY
    ordertime DESC
