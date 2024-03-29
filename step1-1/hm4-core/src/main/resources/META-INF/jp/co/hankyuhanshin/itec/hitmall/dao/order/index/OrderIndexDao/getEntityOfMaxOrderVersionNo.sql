select
    *
from
    orderindex
where
    orderseq = /*orderSeq*/0
and orderversionno = (
                      select max(orderversionno) from orderindex where orderseq = /*orderSeq*/0
                     )
