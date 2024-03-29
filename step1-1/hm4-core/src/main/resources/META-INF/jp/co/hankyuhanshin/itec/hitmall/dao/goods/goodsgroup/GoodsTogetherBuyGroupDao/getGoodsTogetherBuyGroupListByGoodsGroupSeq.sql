-- 2023-renew No21 from here
select goodstogetherbuy.*,
       goodsgroup.goodsgroupnameAdmin as goodsgroupname,
       goodsgroup.goodsgroupcode
from goodstogetherbuy,
     goodsgroup
where goodstogetherbuy.goodsgroupseq = /*goodsGroupSeq*/0
  AND goodstogetherbuy.goodstogetherbuygroupseq = goodsgroup.goodsgroupseq
order by goodstogetherbuy.orderdisplay asc
-- 2023-renew No21 to here
