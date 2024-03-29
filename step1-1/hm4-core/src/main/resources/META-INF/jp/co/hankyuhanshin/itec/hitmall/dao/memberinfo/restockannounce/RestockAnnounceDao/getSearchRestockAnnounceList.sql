--	2023-renew No65 from here	--
select
    restockannounce.*,restockannouncegoods.restockstatus
from
    restockannounce
left join
    restockannouncegoods
on
    restockannounce.goodsSeq = restockannouncegoods.goodsSeq
where
/*%if conditionDto.memberInfoSeq != null*/
    restockannounce.memberInfoSeq = /*conditionDto.memberInfoSeq*/0
/*%end*/
/*%if conditionDto.restockstatus != null*/
    and restockannouncegoods.reStockStatus = /*conditionDto.restockstatus*/0
/*%end*/
/*%if conditionDto.exclusionGoodsSeqList != null*/
    and restockannounce.goodsSeq not in /*conditionDto.exclusionGoodsSeqList*/(1,2,3)
/*%end*/
order by
/*%if conditionDto.pageInfo.orderField != null */
     /*%if conditionDto.pageInfo.orderField == "updateTime"*/
         restockannounce.updateTime
     /*%end*/
     /*%if conditionDto.pageInfo.orderAsc */
         asc
     /*%else*/
         desc
     /*%end*/
/*%else*/
         1 asc
/*%end*/

--	2023-renew No65 to here	--
