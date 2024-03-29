UPDATE
    Campaign
SET
    campaignName = /*campaignEntity.campaignName*/0,
    campaignCost = /*campaignEntity.campaignCost*/0,
    note = /*campaignEntity.note*/0,
    redirectUrl = /*campaignEntity.redirectUrl*/0,
    deleteFlag = /*campaignEntity.deleteFlag.value*/0,
    updateTime = /*campaignEntity.updateTime*/0
WHERE
    campaignSeq = /*campaignEntity.campaignSeq*/0
AND
    deleteFlag = '0'
