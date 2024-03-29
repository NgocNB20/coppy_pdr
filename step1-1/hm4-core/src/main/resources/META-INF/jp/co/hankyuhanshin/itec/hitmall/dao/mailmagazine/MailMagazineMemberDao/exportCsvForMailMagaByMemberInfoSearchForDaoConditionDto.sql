SELECT
    memberInfo.memberInfoMail AS mail
FROM
    memberInfo

WHERE     
    /*%if conditionDto.shopSeq != null*/
    memberInfo.shopseq = /*conditionDto.shopSeq*/0
    /*%end*/

    /*%if conditionDto.memberInfoStatus != null*/
    AND
        memberInfo.memberinfostatus = /*conditionDto.memberInfoStatus.value*/0
    /*%end*/

    /*%if conditionDto.lastLoginUserAgent != null*/
    AND
        memberInfo.lastLoginUserAgent LIKE '%' || /*conditionDto.lastLoginUserAgent*/0 || '%'
    /*%end*/

    /*%if conditionDto.memberInfoId != null*/
    AND
        memberInfo.memberinfoid LIKE '%' || /*conditionDto.memberInfoId*/0 || '%'
    /*%end*/

    /*%if conditionDto.memberInfoSeq != null*/
    AND
        memberInfo.memberInfoSeq = /*conditionDto.memberInfoSeq*/0
    /*%end*/

    /*%if conditionDto.memberInfoSex != null*/
    AND
        memberInfo.memberInfoSex = /*conditionDto.memberInfoSex.value*/0
    /*%end*/

    /*%if conditionDto.memberInfoBirthday != null*/
    AND
        memberInfo.memberInfoBirthday = /*conditionDto.memberInfoBirthday*/0
    /*%end*/

    /*%if conditionDto.memberInfoTel != null*/
    AND
        (
        memberInfo.memberInfoTel LIKE '%' || /*conditionDto.memberInfoTel*/0 || '%' or
        memberInfo.memberInfoContactTel LIKE '%' || /*conditionDto.memberInfoTel*/0 || '%' or
        memberInfo.memberInfoFax LIKE '%' || /*conditionDto.memberInfoTel*/0 || '%'
        )
    /*%end*/

    /*%if conditionDto.memberInfoZipCode != null*/
    AND
        memberInfo.memberInfoZipCode LIKE '%' || /*conditionDto.memberInfoZipCode*/0 || '%'
    /*%end*/

    /*%if conditionDto.memberInfoPrefecture != null*/
    AND
        memberInfoPrefecture = /*conditionDto.memberInfoPrefecture*/0
    /*%end*/

    /*%if conditionDto.memberInfoName != null*/
    AND
        (
        memberInfo.memberInfoLastName || memberInfo.memberInfoFirstName LIKE '%' || /*conditionDto.memberInfoName*/0 || '%' or
        memberInfo.memberInfoLastKana || memberInfo.memberInfoFirstKana LIKE '%' || /*conditionDto.memberInfoName*/0 || '%'
        )
    /*%end*/

    /*%if conditionDto.memberInfoAddress != null*/
    AND
        memberInfo.memberInfoPreFecture || memberInfo.memberinfoAddress1 || memberInfo.memberinfoAddress2 || coalesce(memberInfo.memberinfoAddress3,'') LIKE '%' || /*conditionDto.memberInfoAddress*/0 || '%'
    /*%end*/

    /*%if conditionDto.periodType != null*/
        /*%if conditionDto.periodType == "0"*/
            /*%if conditionDto.startDate != null*/
                AND
                    TO_DATE(memberInfo.admissionymd, 'yyyyMMdd') >= TO_DATE(/*conditionDto.startDate*/0, 'yyyy/MM/dd')
            /*%end*/
            /*%if conditionDto.endDate != null*/
                AND
                    TO_DATE(memberInfo.admissionymd, 'yyyyMMdd') <= TO_DATE(/*conditionDto.endDate*/0, 'yyyy/MM/dd')
            /*%end*/
        /*%end*/

        /*%if conditionDto.periodType == "1"*/
            /*%if conditionDto.startDate != null*/
                AND
                    TO_DATE(memberInfo.secessionymd, 'yyyyMMdd') >= TO_DATE(/*conditionDto.startDate*/0, 'yyyy/MM/dd')
            /*%end*/
            /*%if conditionDto.endDate != null*/
                AND
                    TO_DATE(memberInfo.secessionymd, 'yyyyMMdd') <= TO_DATE(/*conditionDto.endDate*/0, 'yyyy/MM/dd')
            /*%end*/
        /*%end*/
    /*%end*/

    /*%if conditionDto.mainMemberFlag != null*/
        /*%if conditionDto.mainMemberFlag.value == "0"*/
          AND
            memberInfo.memberInfoUniqueId != ''
        /*%end*/
    /*%end*/

ORDER BY
    memberInfo.memberinfomail
