SELECT a.shopseq,
a.campaigncode AS campaignCode,
a.campaignname AS campaignName,
a.note AS note,
C001 AS clickCount,

G003 AS orderCount,
CASE 
WHEN C001 = 0 THEN '-'
WHEN G003 = 0 THEN '0.00' ELSE round(G003/C001*100,2)||'' END AS orderCountRatio,

M001 AS memberRegistCount,
CASE 
WHEN C001 = 0 THEN '-'
WHEN M001 = 0 THEN '0.00' ELSE round(M001/C001*100,2)||'' END AS memberRegistCountRatio,

M002 AS memberDeleteCount,
CASE 
WHEN C001 = 0 THEN '-'
WHEN M002 = 0 THEN '0.00' ELSE round(M002/C001*100,2)||'' END AS memberDeleteCountRatio,

a.campaigncost AS advertiseCost,
G005 AS orderPrice,

CASE 
WHEN a.campaigncost = 0 OR a.campaigncost IS NULL THEN '-'
WHEN G005 = 0 THEN '0.00' ELSE round(G005/a.campaigncost*100,2)||'' END AS advertiseCostRatio

FROM

(SELECT campaign.shopseq,campaign.campaigncode,campaign.campaignname,campaign.note,campaign.campaigncost,campaign.registtime,
  SUM(CASE WHEN accessinfo.accesstype = 'C001' THEN accessinfo.accesscount ELSE 0 END) AS C001,
  SUM(CASE WHEN accessinfo.accesstype = 'G003' THEN accessinfo.accesscount ELSE 0 END) AS G003,
  SUM(CASE WHEN accessinfo.accesstype = 'G005' THEN accessinfo.accesscount ELSE 0 END) AS G005,
  SUM(CASE WHEN accessinfo.accesstype = 'M001' THEN accessinfo.accesscount ELSE 0 END) AS M001,
  SUM(CASE WHEN accessinfo.accesstype = 'M002' THEN accessinfo.accesscount ELSE 0 END) AS M002
  FROM campaign LEFT OUTER JOIN accessinfo ON
campaign.shopseq = accessinfo.shopseq
AND campaign.campaigncode = accessinfo.campaigncode
/*%if accessInfoSearchForDaoConditionDto.campaignFromDate != null*/
AND accessinfo.accessdate >= /*accessInfoSearchForDaoConditionDto.campaignFromDate*/0
/*%end*/
/*%if accessInfoSearchForDaoConditionDto.campaignToDate != null*/
AND accessinfo.accessdate <= /*accessInfoSearchForDaoConditionDto.campaignToDate*/0
/*%end*/
WHERE

campaign.shopseq = /*accessInfoSearchForDaoConditionDto.shopSeq*/0
/*%if accessInfoSearchForDaoConditionDto.campaignCode != null*/
AND campaign.campaigncode LIKE '%' || /*accessInfoSearchForDaoConditionDto.campaignCode*/0 || '%'
/*%end*/
/*%if accessInfoSearchForDaoConditionDto.campaignName != null*/
AND campaign.campaignname LIKE '%' || /*accessInfoSearchForDaoConditionDto.campaignName*/0 || '%'
/*%end*/

/*%if accessInfoSearchForDaoConditionDto.note != null*/
AND campaign.note LIKE '%' || /*accessInfoSearchForDaoConditionDto.note*/0 || '%'
/*%end*/
GROUP BY campaign.shopseq,campaign.campaigncode,campaign.campaignname,campaign.note,campaign.campaigncost,campaign.registtime) a

order by campaigncode ASC