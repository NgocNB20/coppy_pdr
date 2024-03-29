SELECT
  memberInfo.*
FROM
  memberInfo memberInfo
WHERE
  memberInfo.shopseq = /*conditionDto.shopSeq*/0

/*%if conditionDto.memberInfoStatus != null*/
  AND
    memberInfo.memberinfostatus = /*conditionDto.memberInfoStatus.value*/0
/*%end*/

/*%if conditionDto.lastLoginDeviceType != null */
  AND
    memberInfo.lastLoginDeviceType IN /*conditionDto.lastLoginDeviceType*/(0)
/*%end*/

/*%if conditionDto.lastLoginUserAgent != null*/
  AND
    memberInfo.lastLoginUserAgent LIKE '%' || /*conditionDto.lastLoginUserAgent*/0 || '%'
/*%end*/

/*%if conditionDto.memberInfoId != null*/
  AND
    memberInfo.memberinfoid LIKE '%' || /*conditionDto.memberInfoId*/0 || '%'
/*%end*/

/*%if conditionDto.memberInfoSendMailPermitFlag != null*/
  AND
    memberInfo.sendMailPermitFlag = /*conditionDto.memberInfoSendMailPermitFlag.value*/0
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

-- PDR Migrate Customization from here
/*%if conditionDto.memberInfoName != null*/
  AND
    (
    memberInfo.memberInfoLastName || coalesce(memberInfo.memberInfoFirstName,'') LIKE '%' || /*conditionDto.memberInfoName*/0 || '%' or
    memberInfo.memberInfoLastKana || coalesce(memberInfo.memberInfoFirstKana,'') LIKE '%' || /*conditionDto.memberInfoName*/0 || '%'
    )
/*%end*/

/*%if conditionDto.memberInfoAddress != null*/
  AND
    coalesce(memberInfo.memberInfoPreFecture,'') || memberInfo.memberinfoAddress1 || memberInfo.memberinfoAddress2 || coalesce(memberInfo.memberinfoAddress3,'') || coalesce(memberInfo.memberinfoAddress4,'') || coalesce(memberInfo.memberinfoAddress5,'') LIKE '%' || /*conditionDto.memberInfoAddress*/0 || '%'
/*%end*/
-- PDR Migrate Customization to here

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
      NOT (memberInfo.memberInfoUniqueId IS NULL AND memberInfo.memberinfostatus = '0')
    /*%end*/
/*%end*/
-- PDR Migrate Customization from here
/*%if conditionDto.customerNo != null*/
  AND
    memberInfo.customerNo = /*conditionDto.customerNo*/0
/*%end*/

/*%if conditionDto.approveStatus != null*/
  AND
    memberInfo.approveStatus = /*conditionDto.approveStatus.value*/0
/*%end*/

/*%if conditionDto.businessType != null*/
  AND
    memberInfo.businessType IN /*conditionDto.businessType*/(1)
/*%end*/

/*%if conditionDto.sendFaxPermitFlag != null*/
  AND
    memberInfo.sendFaxPermitFlag = /*conditionDto.sendFaxPermitFlag.value*/0
/*%end*/

/*%if conditionDto.sendDirectMailFlag != null*/
  AND
    memberInfo.sendDirectMailFlag = /*conditionDto.sendDirectMailFlag.value*/0
/*%end*/

/*%if conditionDto.onlineRegistFlag != null*/
  AND
    memberInfo.onlineRegistFlag = /*conditionDto.onlineRegistFlag.value*/0
/*%end*/
-- PDR Migrate Customization to here
/*************** sort ***************/

ORDER BY 
/*%if conditionDto.pageInfo.orderField != null*/

/*%if conditionDto.pageInfo.orderField == "memberInfoSeq"*/
memberInfo.memberInfoSeq /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "memberInfoId"*/
memberInfo.memberInfoId /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "memberInfoStatus"*/
memberInfo.memberInfoStatus /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
       , memberInfo.memberinfoseq
/*%end*/

/*%if conditionDto.pageInfo.orderField == "memberInfoName"*/
memberInfo.memberInfoLastName /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
       , memberInfo.memberInfoFirstName
       , memberInfo.memberinfoseq
/*%end*/

/*%if conditionDto.pageInfo.orderField == "memberInfoTel"*/
memberInfo.memberInfoTel /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
       , memberInfo.memberinfoseq
/*%end*/

/*%if conditionDto.pageInfo.orderField == "memberInfoZipCode"*/
memberInfo.memberInfoZipCode /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
       , memberInfo.memberinfoseq
/*%end*/

/*%if conditionDto.pageInfo.orderField == "memberInfoAddress"*/
memberInfo.memberInfoPrefecture /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
       , memberInfo.memberInfoAddress1, memberInfo.memberInfoAddress2, memberInfo.memberInfoAddress3
       , memberInfo.memberinfoseq
/*%end*/

/*%if conditionDto.pageInfo.orderField == "memberInfoRegistTime"*/
memberInfo.registTime /*%if conditionDto.pageInfo.orderAsc*/ ASC /*%else*/ DESC /*%end*/
/*%end*/

-- PDR Migrate Customization from here
/*%if conditionDto.pageInfo.orderField == "customerNo"*/
memberInfo.customerNo /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "businessType"*/
memberInfo.businessType /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if conditionDto.pageInfo.orderField == "approveStatus"*/
memberInfo.approveStatus /*%if conditionDto.pageInfo.orderAsc */ ASC /*%else*/ DESC /*%end*/
/*%end*/
-- PDR Migrate Customization to here

/*%else*/
    1 ASC
/*%end*/
