UPDATE
    cartgoods
SET
    memberInfoSeq = /*changeMemberInfoSeq*/0
WHERE
    memberInfoSeq = /*memberInfoSeq*/0
AND
    accessUid = /*accessUid*/0
/*%if shopSeq != null*/
AND
    shopSeq = /*shopSeq*/0
/*%end*/
