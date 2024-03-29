select
    goods.*,
    goodsgroup.taxrate,
    goodsgroup.goodsgroupcode,
    goodsgroup.whatsnewdate,
    goodsgroup.goodsopenstatuspc,
    goodsgroup.openstarttimepc,
    goodsgroup.openendtimepc,
    goodsgroup.goodsgroupname,
    -- PDR Migrate Customization from here
    goodsgroup.goodsClassType,
    goodsgroup.dentalmonopolysalesflg,
    -- PDR Migrate Customization to here
    goodsgroupdisplay.unittitle1,
    goodsgroupdisplay.unittitle2,
    goodsgroup.goodsprediscountprice
from
        goods
 left join goodsgroup on( goods.goodsgroupseq = goodsgroup.goodsgroupseq )
 left join goodsgroupdisplay on( goods.goodsgroupseq = goodsgroupdisplay.goodsgroupseq )
where
        goodscode=/*goodsCode*/0
        and goodsgroup.shopseq=/*shopSeq*/0
/*%if goodsOpenStatus != null && siteType != null */
  /*%if siteType.value == "0" || siteType.value == "1" */
    AND (goodsgroup.openstarttimepc <= CURRENT_TIMESTAMP OR goodsgroup.openstarttimepc is null)
    AND (goodsgroup.openendtimepc >= CURRENT_TIMESTAMP OR goodsgroup.openendtimepc is null)
    AND goodsgroup.goodsopenstatuspc = /*goodsOpenStatus.value*/0
  /*%end*/
/*%end*/
