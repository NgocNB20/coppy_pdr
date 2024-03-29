-- 2023-renew No21 from here
select
 gg.goodsgroupseq goodsTogetherBuyGroupSeq,
 count(1) as boughtnumber
from
 ordersummary osa,
 ordergoods og,
 goods g,
 goodsgroup gg,
 (select
 os.orderseq orderseqb,
 os.ordertime ordertimeb
from
 goods g,
 ordersummary os,
 ordergoods og
where
    os.orderseq = og.orderseq
and og.goodsseq = g.goodsseq
and g.goodsgroupseq = /*goodsGroupSeq*/0) osb
where
    osa.orderseq = og.orderseq
and og.goodsseq = g.goodsseq
and g.goodsgroupseq = gg.goodsgroupseq
-- 商品．販売状態 = 1：販売中
and g.salestatuspc = '1'
-- 商品．販売開始日時 <= 現在日時　（値が設定されている場合のみ）
and (g.salestarttimepc is null or g.salestarttimepc <= CURRENT_TIMESTAMP)
-- 商品．販売終了日時 >= 現在日時　（値が設定されている場合のみ）
and (g.saleendtimepc is null or g.saleendtimepc >= CURRENT_TIMESTAMP)
-- 商品グループ．除外フラグ = 0：OFF
and gg.excludingflag = '0'
-- 商品グループ．公開状態 = 1：公開中
and gg.goodsopenstatuspc = '1'
-- 商品グループ．公開開始日時 <= 現在日時　（値が設定されている場合のみ）
and (gg.openstarttimepc is null or gg.openstarttimepc <= CURRENT_TIMESTAMP)
-- 商品グループ．公開終了日時 >= 現在日時　（値が設定されている場合のみ）
and (gg.openendtimepc is null or gg.openendtimepc >= CURRENT_TIMESTAMP)
-- 商品グループ．商品グループSEQ ≠ 引数．商品グループSEQ
and gg.goodsgroupseq != /*goodsGroupSeq*/0
-- 受注サマリ．受注SEQ が同じ
-- 受注サマリ．受注日時 が同じ
-- No1,2の条件どちらかを満たせば同じ受注とみなす。
and (osa.orderseq = osb.orderseqb or osa.ordertime = osb.ordertimeb)

group by gg.goodsgroupseq
order by boughtnumber desc, gg.goodsgroupseq asc
limit 20
-- 2023-renew No21 to here