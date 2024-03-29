SELECT
    memberInfo.*
FROM
    memberInfo
WHERE
    memberInfo.shopSeq = /*shopSeq*/0
AND
    memberInfo.accessUid = /*accessUid*/0
AND
    memberInfo.memberInfoStatus = '0'
