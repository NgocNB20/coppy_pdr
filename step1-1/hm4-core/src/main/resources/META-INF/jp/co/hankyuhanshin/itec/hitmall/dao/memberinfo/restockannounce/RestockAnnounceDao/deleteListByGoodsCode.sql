-- 2023-renew No65 from here
delete
from
  restockannounce
where
  restockannounce.memberInfoSeq = /*memberInfoSeq*/0
  and restockannounce.goodsSeq in (
    select
      goodsSeq
    from
      goods
    where
      goodsCode in /*goodsCodes*/(1,2,3)
  )
-- 2023-renew No65 to here