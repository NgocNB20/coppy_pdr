select
    goods.*,
    goodsgroup.goodsgroupcode,
    goodsgroup.whatsnewdate,
    goodsgroup.goodsopenstatuspc,
    goodsgroup.openstarttimepc,
    goodsgroup.openendtimepc,
    goodsgroup.goodsgroupname,
    goodsgroup.snsLinkFlag,
    goodsgroup.goodstaxtype,
    goodsgroup.taxrate,
    goodsgroup.alcoholFlag,
    -- PDR Migrate Customization from here
    goodsgroup.goodsClassType,
    -- PDR Migrate Customization to here
    goodsgroupdisplay.deliverytype,
    goodsgroupdisplay.goodsNote1,
    goodsgroupdisplay.goodsNote2,
    goodsgroupdisplay.goodsNote3,
    goodsgroupdisplay.goodsNote4,
    goodsgroupdisplay.goodsNote5,
    goodsgroupdisplay.goodsNote6,
    goodsgroupdisplay.goodsNote7,
    goodsgroupdisplay.goodsNote8,
    goodsgroupdisplay.goodsNote9,
    goodsgroupdisplay.goodsNote10,
    goodsgroupdisplay.orderSetting1,
    goodsgroupdisplay.orderSetting2,
    goodsgroupdisplay.orderSetting3,
    goodsgroupdisplay.orderSetting4,
    goodsgroupdisplay.orderSetting5,
    goodsgroupdisplay.orderSetting6,
    goodsgroupdisplay.orderSetting7,
    goodsgroupdisplay.orderSetting8,
    goodsgroupdisplay.orderSetting9,
    goodsgroupdisplay.orderSetting10,
    -- PDR Migrate Customization from here
    goodsgroupdisplay.saleIconFlag,
    goodsgroupdisplay.reserveIconFlag,
    goodsgroupdisplay.newIconFlag,
    -- 2023-renew No92 from here
    goodsgroupdisplay.outletIconFlag,
    -- 2023-renew No92 to here
    goodsgroup.dentalmonopolysalesflg,
    -- PDR Migrate Customization to here
    goodsgroupdisplay.unittitle1,
    goodsgroupdisplay.unittitle2,
    goodsgroup.goodsprediscountprice,
    goodsgroupdisplay.metaDescription,
    stock.realstock,
    stock.realStock - stock.orderreservestock - stocksetting.safetystock as salesPossibleStock,
    stock.orderreservestock,
    stocksetting.remainderfewstock,
    stocksetting.orderpointstock,
    stocksetting.safetystock,
    stockStatusDisplay.stockStatusPc
from
    goods
inner join
    goodsgroup on( goods.goodsgroupseq = goodsgroup.goodsgroupseq )
inner join
    goodsgroupdisplay on( goodsgroup.goodsgroupseq = goodsgroupdisplay.goodsgroupseq )
inner join
    stock on( goods.goodsseq = stock.goodsseq )
inner join
    stocksetting on( goods.goodsseq = stocksetting.goodsseq )
left outer join
    stockStatusDisplay on( stockStatusDisplay.goodsGroupSeq = goods.goodsGroupSeq )
where
    goods.goodsCode in /*goodsCodeList*/(0);
