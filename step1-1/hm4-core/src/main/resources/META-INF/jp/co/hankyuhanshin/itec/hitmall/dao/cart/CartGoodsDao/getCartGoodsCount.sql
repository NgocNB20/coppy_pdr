select
    count(cartSeq)
from
    cartgoods
where
  1 = 1
/*%if memberInfoSeq != null*/
  and memberInfoSeq = /*memberInfoSeq*/0
/*%else*/ 
  and memberInfoSeq = 0 and accessUid = /*accessUid*/0
/*%end*/
/*%if shopSeq != null*/
  and shopSeq = /*shopSeq*/0
/*%end*/
