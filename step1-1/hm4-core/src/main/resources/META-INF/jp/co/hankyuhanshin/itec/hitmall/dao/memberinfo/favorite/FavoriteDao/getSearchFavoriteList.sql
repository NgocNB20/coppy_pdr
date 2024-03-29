select
    favorite.*
from
    favorite
left join
    goods
on
    favorite.goodsSeq = goods.goodsSeq
where
    favorite.memberInfoSeq = /*conditionDto.memberInfoSeq*/0
/*%if conditionDto.exclusionGoodsSeqList != null*/
    and favorite.goodsSeq not in /*conditionDto.exclusionGoodsSeqList*/(1,2,3)
/*%end*/

/*%if conditionDto.goodsOpenStatus != null && conditionDto.goodsOpenStatus.value != "none"*/
/*%end*/

     order by
/*%if conditionDto.pageInfo.orderField != null */
     /*%if conditionDto.pageInfo.orderField == "updateTime"*/
         favorite.updateTime
     /*%end*/
     /*%if conditionDto.pageInfo.orderField == "price"*/
         goods.goodsprice
     /*%end*/
     /*%if conditionDto.pageInfo.orderField == "newly"*/
         goods.registTime
     /*%end*/

     /*%if conditionDto.pageInfo.orderAsc */
         asc
     /*%else*/
         desc
     /*%end*/
/*%else*/
         1 asc
/*%end*/
