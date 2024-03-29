delete from
    cartgoods
where
    cartSeq in /*cartSeqList*/(0,1,2)
and
/*%if memberInfoSeq != null && memberInfoSeq != 0*/
    memberInfoSeq = /*memberInfoSeq*/0
  /*%else*/
  memberInfoSeq = 0 and accessUid = /*accessUid*/0
/*%end*/
