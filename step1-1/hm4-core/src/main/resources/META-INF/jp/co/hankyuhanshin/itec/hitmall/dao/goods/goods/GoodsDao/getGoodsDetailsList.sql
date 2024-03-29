SELECT
  goods.*
, goodsgroup.*
, goodsgroup.whatsnewdate
, goodsgroup.goodsopenstatuspc
, goodsgroup.goodsgroupmaxpricepc
, goodsgroup.goodsgroupminpricepc
, goodsgroup.openstarttimepc
, goodsgroup.openendtimepc
, goodsgroup.goodsgroupname
, goodsgroup.snsLinkFlag
, goodsgroup.goodstaxtype
, goodsgroup.taxrate
, goodsgroup.alcoholFlag
, preDiscountMaxMin.preDiscountMaxPrice
, preDiscountMaxMin.preDiscountMinPrice
, goodsgroup.goodsprediscountprice
, goodsgroupdisplay.deliverytype
, goodsgroupdisplay.goodsNote1
, goodsgroupdisplay.goodsNote2
, goodsgroupdisplay.goodsNote3
, goodsgroupdisplay.goodsNote4
, goodsgroupdisplay.goodsNote5
, goodsgroupdisplay.goodsNote6
, goodsgroupdisplay.goodsNote7
, goodsgroupdisplay.goodsNote8
, goodsgroupdisplay.goodsNote9
, goodsgroupdisplay.goodsNote10
, goodsgroupdisplay.orderSetting1
, goodsgroupdisplay.orderSetting2
, goodsgroupdisplay.orderSetting3
, goodsgroupdisplay.orderSetting4
, goodsgroupdisplay.orderSetting5
, goodsgroupdisplay.orderSetting6
, goodsgroupdisplay.orderSetting7
, goodsgroupdisplay.orderSetting8
, goodsgroupdisplay.orderSetting9
, goodsgroupdisplay.orderSetting10
-- PDR Migrate Customization from here
, goodsGroup.goodsclasstype
, goodsgroup.dentalmonopolysalesflg
, goodsgroupdisplay.saleiconflag
, goodsgroupdisplay.reserveiconflag
, goodsgroupdisplay.newiconflag
-- 2023-renew No92 from here
, goodsgroupdisplay.outleticonflag
-- 2023-renew No92 to here
, stockStatusDisplay.stockStatusMb
-- PDR Migrate Customization to here
, goodsgroupdisplay.unittitle1
, goodsgroupdisplay.unittitle2
, goodsgroupdisplay.metaDescription
, stock.realstock
, stock.realStock - stock.orderreservestock - stocksetting.safetystock as salesPossibleStock
, stock.orderreservestock
, stocksetting.remainderfewstock
, stocksetting.orderpointstock
, stocksetting.safetystock
, stockStatusDisplay.stockStatusPc

FROM
  goods
INNER JOIN
    goodsgroup ON( goods.goodsgroupseq = goodsgroup.goodsgroupseq )
LEFT OUTER JOIN
  (SELECT goodsgroupseq
        , MAX(prediscountprice) AS preDiscountMaxPrice
        , MIN(prediscountprice) AS preDiscountMinPrice
               FROM goods
                    where goods.salestatuspc = '1'
                            GROUP BY goodsgroupseq)
  AS preDiscountMaxMin ON (preDiscountMaxMin.goodsgroupseq = goodsgroup.goodsgroupseq)
INNER JOIN
    goodsgroupdisplay ON( goodsgroup.goodsgroupseq = goodsgroupdisplay.goodsgroupseq )
INNER JOIN
    stock ON( goods.goodsseq = stock.goodsseq )
INNER JOIN
    stocksetting ON( goods.goodsseq = stocksetting.goodsseq )
LEFT OUTER JOIN
    stockStatusDisplay ON( stockStatusDisplay.goodsGroupSeq = goods.goodsGroupSeq )

WHERE
    goods.goodsSeq IN /*goodsSeqList*/(0);
