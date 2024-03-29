select
 goodsrelation.*,
 -- 2023-renew No64 from here
 goodsgroup.goodsgroupnameadmin,
 -- 2023-renew No64 to here
 goodsgroup.goodsgroupcode,
 goodsgroup.goodsopenstatuspc
from
 goodsrelation,
 goodsgroup
where
 goodsrelation.goodsgroupseq = /*goodsGroupSeq*/0
 AND goodsrelation.goodsrelationgroupseq = goodsgroup.goodsgroupseq
order by goodsrelation.orderdisplay asc
