SELECT
    memberInfo.memberinfoseq,
    memberInfo.memberinfostatus,
    memberInfo.memberinfoid,
    memberInfo.memberinfolastname,
    memberInfo.memberinfofirstname,
    memberInfo.memberinfolastkana,
    memberInfo.memberinfofirstkana,
    memberInfo.memberinfosex,
    memberInfo.memberinfobirthday,
    memberInfo.memberinfotel,
    memberInfo.memberinfocontacttel,
    memberInfo.memberinfofax,
    memberInfo.memberinfozipcode,
    memberInfo.memberinfoprefecture,
    memberInfo.memberinfoaddress1,
    memberInfo.memberinfoaddress2,
    memberInfo.memberinfoaddress3,
    memberInfo.admissionymd,
    memberInfo.secessionymd,
    memberInfo.lastlogintime,
    memberInfo.lastloginuseragent,
    memberInfo.paymentmemberid,
    memberInfo.paymentcardregisttype,
    memberInfo.registtime,
    memberInfo.updatetime,
    -- PDR Migrate Customization from here
    memberInfo.customerNo,
    memberInfo.representativeName,
    memberInfo.memberInfoAddress4,
    memberInfo.memberInfoAddress5,
    memberInfo.businessType,
    memberInfo.sendFaxPermitFlag,
    memberInfo.sendDirectMailFlag,
    memberInfo.nonConsultationDay,
    memberInfo.approveStatus,
    memberInfo.drugSalesType,
    memberInfo.medicalEquipmentSalesType,
    memberInfo.dentalMonopolySalesType,
    memberInfo.creditPaymentUseFlag,
    memberInfo.transferPaymentUseFlag,
    memberInfo.cashDeliveryUseFlag,
    memberInfo.directDebitUseFlag,
    memberInfo.monthlyPayUseFlag,
    memberInfo.memberListType,
    memberInfo.onlineRegistFlag
    /** 診療内容１ */
    ,SUBSTRING(memberInfo.medicaltreatmentflag,1,1) AS medicaltreatment1
    /** 診療内容２ */
    ,SUBSTRING(memberInfo.medicaltreatmentflag,2,1) AS medicaltreatment2
    /** 診療内容３ */
    ,SUBSTRING(memberInfo.medicaltreatmentflag,3,1) AS medicaltreatment3
    /** 診療内容４ */
    ,SUBSTRING(memberInfo.medicaltreatmentflag,4,1) AS medicaltreatment4
    /** 診療内容５ */
    ,SUBSTRING(memberInfo.medicaltreatmentflag,5,1) AS medicaltreatment5
    /** 診療内容６ */
    ,SUBSTRING(memberInfo.medicaltreatmentflag,6,1) AS medicaltreatment6
    /** 診療内容７ */
    ,SUBSTRING(memberInfo.medicaltreatmentflag,7,1) AS medicaltreatment7
    /** 診療内容８ */
    ,SUBSTRING(memberInfo.medicaltreatmentflag,8,1) AS medicaltreatment8
    /** 診療内容９ */
    ,SUBSTRING(memberInfo.medicaltreatmentflag,9,1) AS medicaltreatment9
    /** 診療内容１０ */
    ,SUBSTRING(memberInfo.medicaltreatmentflag,10,1) AS medicaltreatment10
    /** その他診療内容 */
    ,memberInfo.medicaltreatmentmemo
    /** 金属商品価格お知らせメール */
    ,memberInfo.metalpermitflag
    /** 確認書類 */
    ,memberInfo.confdocumenttype
    /** 経理区分 */
    ,memberInfo.accountingtype
    /** オンラインログイン可否 */
    ,memberInfo.onlineloginadvisability
    -- PDR Migrate Customization to here
FROM
    memberInfo

WHERE
    memberInfo.shopseq = /*conditionDto.shopSeq*/0

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
    /*%if conditionDto.customerNo != null*/
    AND
        memberInfo.customerNo = /*conditionDto.customerNo*/0
    /*%end*/

    /*%if conditionDto.approveStatus != null*/
    AND
        memberInfo.approveStatus = /*conditionDto.approveStatus.value*/0
    /*%end*/

    /*%if conditionDto.onlineRegistFlag != null*/
    AND
        memberInfo.onlineRegistFlag = /*conditionDto.onlineRegistFlag.value*/0
    /*%end*/

    /*%if conditionDto.businessType != null*/
    AND
        memberInfo.businessType IN /*conditionDto.businessType*/()
    /*%end*/

    /*%if conditionDto.sendFaxPermitFlag != null*/
    AND
        memberInfo.sendFaxPermitFlag = /*conditionDto.sendFaxPermitFlag.value*/0
    /*%end*/

    /*%if conditionDto.sendDirectMailFlag != null*/
    AND
        memberInfo.sendDirectMailFlag = /*conditionDto.sendDirectMailFlag.value*/0
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

ORDER BY
    memberInfo.memberInfoSeq
