UPDATE memberInfo
SET
    loginFailureCount = 0
  , accountLockTime = null
  , updateTime = CURRENT_TIMESTAMP
WHERE
        memberInfoSeq = /*memberInfoSeq*/0