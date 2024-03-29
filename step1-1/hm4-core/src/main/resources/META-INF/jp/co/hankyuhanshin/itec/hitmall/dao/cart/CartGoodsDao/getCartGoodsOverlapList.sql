SELECT
    *
FROM
    cartgoods
WHERE
    goodsSeq = /*cartGoodsEntity.goodsSeq*/0
/*%if cartGoodsEntity.memberInfoSeq != null && cartGoodsEntity.memberInfoSeq != 0*/
    AND memberInfoSeq = /*cartGoodsEntity.memberInfoSeq*/0
/*%else*/
    AND memberInfoSeq = 0
    AND accessUid = /*cartGoodsEntity.accessUid*/0
/*%end*/
/*%if cartGoodsEntity.shopSeq != null*/
    AND shopSeq = /*cartGoodsEntity.shopSeq*/0
/*%end*/
-- 2023-renew No14 from here
    AND reserveFlag != '1'
-- 2023-renew No14 to here
