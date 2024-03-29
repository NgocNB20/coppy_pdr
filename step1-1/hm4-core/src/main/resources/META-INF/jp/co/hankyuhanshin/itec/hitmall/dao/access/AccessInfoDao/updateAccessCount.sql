UPDATE
	accessinfo
SET
	accesscount = accesscount + /*accessInfoEntity.accessCount*/1
	,updatetime = /*accessInfoEntity.updateTime*/'2010-10-15 00:00:00'
WHERE
		shopSeq = /*accessInfoEntity.shopSeq*/1001
	AND accessType = /*accessInfoEntity.accessType.value*/'A001'
	AND accessDate = /*accessInfoEntity.accessDate*/'2010-10-15 00:00:00'
	AND siteType = /*accessInfoEntity.siteType.value*/'0'
	AND deviceType = /*accessInfoEntity.deviceType.value*/'0'
	AND goodsgroupseq = /*accessInfoEntity.goodsGroupSeq*/123456
	AND accessUid = /*accessInfoEntity.accessUid*/'123456'
	AND campaigncode = /*accessInfoEntity.campaignCode*/'test'
