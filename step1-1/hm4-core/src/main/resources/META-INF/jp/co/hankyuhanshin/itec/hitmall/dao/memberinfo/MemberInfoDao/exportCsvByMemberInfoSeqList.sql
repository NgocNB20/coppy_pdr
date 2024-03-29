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
    (SELECT * FROM memberInfo WHERE memberInfoSeq IN /*memberInfoSeqList*/(1,2,3)) AS memberInfo

ORDER BY
    memberInfo.memberInfoSeq
