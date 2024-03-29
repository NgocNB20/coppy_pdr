-- 2023-renew No21 from here
select distinct
 gg.goodsGroupSeq
from
 goodsgroup gg,
 goods g,
 ordersummary os,
 ordergoods og
where
    os.orderseq = og.orderseq
and og.goodsseq = g.goodsseq
and g.goodsgroupseq = gg.goodsgroupseq
-- 受注サマリ．受注日時 >= 現在日時より6か月前
and os.ordertime >= (CURRENT_TIMESTAMP - INTERVAL '6 months')
-- 商品．販売状態 = 1：販売中
and g.salestatuspc = '1'
-- 商品．販売開始日時 <= 現在日時　（値が設定されている場合のみ）
and (g.salestarttimepc is null or g.salestarttimepc <= CURRENT_TIMESTAMP)
-- 商品．販売終了日時 >= 現在日時　（値が設定されている場合のみ）
and (g.saleendtimepc is null or g.saleendtimepc >= CURRENT_TIMESTAMP)
-- 商品グループ．公開状態 = 1：公開中
and gg.goodsopenstatuspc = '1'
-- 商品グループ．公開開始日時 <= 現在日時　（値が設定されている場合のみ）
and (gg.openstarttimepc is null or gg.openstarttimepc <= CURRENT_TIMESTAMP)
-- 商品グループ．公開終了日時 >= 現在日時　（値が設定されている場合のみ）
and (gg.openendtimepc is null or gg.openendtimepc >= CURRENT_TIMESTAMP)
order by gg.goodsGroupSeq asc
-- 2023-renew No21 to here