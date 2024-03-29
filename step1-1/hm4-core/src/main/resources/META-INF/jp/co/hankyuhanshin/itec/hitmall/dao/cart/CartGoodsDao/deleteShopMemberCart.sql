delete from
    cartgoods
where
/*%if memberInfoSeq != null*/ memberInfoSeq = /*memberInfoSeq*/0
/*%else*/ memberInfoSeq = 0 and accessUid = /*accessUid*/0
/*%end*/
/*%if shopSeq != null*/
    and shopSeq = /*shopSeq*/0
/*%end*/
-- 2023-renew No14 from here
    and reserveFlag != '1'
-- 2023-renew No14 to here
