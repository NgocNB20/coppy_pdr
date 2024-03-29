select
    cartgoods.*
from
    cartgoods
where
 1 = 1
/*%if cartGoodsConditionDto.memberInfoSeq != null && cartGoodsConditionDto.memberInfoSeq != 0 */
    and cartGoods.memberInfoSeq = /*cartGoodsConditionDto.memberInfoSeq*/0
/*%else*/
    and cartGoods.memberInfoSeq = 0
    and cartGoods.accessUid = /*cartGoodsConditionDto.accessUid*/0
/*%end*/
/*%if cartGoodsConditionDto.shopSeq != null*/
    and cartGoods.shopSeq = /*cartGoodsConditionDto.shopSeq*/0
/*%end*/
-- 2023-renew No14 from here
/*%if cartGoodsConditionDto.goodsSeq != null*/
    and cartGoods.goodsSeq = /*cartGoodsConditionDto.goodsSeq*/0
/*%end*/
/*%if cartGoodsConditionDto.reserveFlag != null*/
    and cartGoods.reserveFlag = /*cartGoodsConditionDto.reserveFlag.value*/0
/*%end*/
-- 2023-renew No14 to here
    ORDER BY
/*%if cartGoodsConditionDto.orderField == "registTime"*/
    CARTGOODS.REGISTTIME DESC, CARTGOODS.CARTSEQ DESC
-- 2023-renew No14 from here
/*%elseif cartGoodsConditionDto.orderField == "reserveDeliveryDate"*/
    CARTGOODS.RESERVEDELIVERYDATE ASC
-- 2023-renew No14 to here
/*%else*/
    1 asc
/*%end*/
