SELECT *
FROM campaign
WHERE
shopseq = /*campaignSearchForDaoConditionDto.shopSeq*/'1001'
/*%if campaignSearchForDaoConditionDto.campaignCode != null*/
AND campaign.campaigncode LIKE '%' || /*campaignSearchForDaoConditionDto.campaignCode*/'' || '%'
/*%end*/
/*%if campaignSearchForDaoConditionDto.campaignName != null*/
AND campaign.campaignname LIKE '%' || /*campaignSearchForDaoConditionDto.campaignName*/'' || '%'
/*%end*/
/*%if campaignSearchForDaoConditionDto.note != null*/
AND campaign.note LIKE '%' || /*campaignSearchForDaoConditionDto.note*/'' || '%'
/*%end*/
AND deleteflag = '0'

ORDER BY
/*%if campaignSearchForDaoConditionDto.pageInfo.orderField == null*/
campaigncode ASC
/*%end*/

/*%if campaignSearchForDaoConditionDto.pageInfo.orderField == "campaignCode"*/
campaigncode
/*%if campaignSearchForDaoConditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if campaignSearchForDaoConditionDto.pageInfo.orderField == "campaignName"*/
campaignname
/*%if campaignSearchForDaoConditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/
/*%end*/

/*%if campaignSearchForDaoConditionDto.pageInfo.orderField == "note"*/
note
/*%if campaignSearchForDaoConditionDto.pageInfo.orderAsc == true */ ASC /*%else*/ DESC /*%end*/
/*%end*/
