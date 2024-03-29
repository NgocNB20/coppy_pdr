SELECT
    memberInfo.*
FROM
    memberInfo
WHERE
    memberinfo.shopSeq = /*shopSeq*/0
AND
    memberinfo.memberInfoId = /*memberInfoId*/0
AND
    memberinfo.memberInfoStatus = '0'
AND
    memberinfo.memberInfoBirthDay = /*memberInfoBirthDay*/0
