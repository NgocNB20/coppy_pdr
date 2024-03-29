select
    ordersettlement.*
from
    ordersettlement
where
        ordersettlement.orderseq = /*orderSeq*/0
    and ordersettlement.ordersettlementversionno = /*orderSettlementVersionNo*/0
