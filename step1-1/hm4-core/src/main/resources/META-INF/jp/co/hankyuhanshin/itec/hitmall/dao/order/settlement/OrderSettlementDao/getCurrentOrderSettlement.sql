select
    ordersettlement.*
from
    ordersettlement inner join orderindex
    on
            ordersettlement.orderseq = orderindex.orderseq
        and ordersettlement.ordersettlementversionno = orderindex.ordersettlementversionno
where
        ordersettlement.orderseq = /*orderSeq*/0
    and orderindex.orderversionno = /*orderVersionNo*/0;
