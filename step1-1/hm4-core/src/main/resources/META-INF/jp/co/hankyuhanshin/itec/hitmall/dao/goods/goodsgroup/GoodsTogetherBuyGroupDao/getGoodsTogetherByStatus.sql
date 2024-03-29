-- 2023-renew No21 from here
select goodstogetherbuy.*,
       goodsgroup.goodsgroupname,
       goodsgroup.goodsgroupcode
from goodstogetherbuy,
     goodsgroup,
     goodsgroup goodsgrouptogetherbuy,
     goods
where goodstogetherbuy.goodsgroupseq = /*goodsGroupSeq*/0
  AND goodstogetherbuy.goodsgroupseq = goodsgroup.goodsgroupseq
  AND goodstogetherbuy.goodsgroupseq = goods.goodsgroupseq
  AND goodstogetherbuy.goodstogetherbuygroupseq = goodsgrouptogetherbuy.goodsgroupseq
  AND goodsgrouptogetherbuy.excludingflag = /*excludingFlag*/0
  AND goodsgroup.goodsopenstatuspc = /*goodsOpenStatusPc*/0
  AND goods.salestatuspc = /*saleStatusPc*/0
order by goodstogetherbuy.orderdisplay asc
-- 2023-renew No21 to here
