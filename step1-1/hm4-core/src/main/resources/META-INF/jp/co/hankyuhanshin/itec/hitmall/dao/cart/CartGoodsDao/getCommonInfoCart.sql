select
	coalesce(sum(c.cartgoodscount), 0) as cartGoodsSumCount,
    coalesce(sum(g.goodsprice * c.cartgoodscount), 0) as cartGoodsSumPrice
from
    goods g, cartGoods c
where
        g.goodsseq = c.goodsseq
  and
        c.goodsseq = g.goodsseq
/*%if memberInfoSeq != null && memberInfoSeq != 0*/
    and c.memberInfoSeq = /*memberInfoSeq*/1
/*%else*/
  	and memberInfoSeq = 0
  	and c.accessuid = /*accessUid*/'accessUid'
/*%end*/
