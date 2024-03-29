UPDATE memberInfo
SET
    loginFailureCount = loginFailureCount + 1
/*%if accountLockTime != null*/
  , accountLockTime = /*accountLockTime*/0
/*%end*/
WHERE
        memberInfoSeq = /*memberInfoSeq*/0