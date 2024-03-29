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

/*************** sort ***************/
order by campaigncode ASC