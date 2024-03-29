-- PDR Migrate Customization from here
SELECT
  salesAdvisabilitySeq
FROM
  SalesAdvisability
WHERE
  SalesAdvisability.loginType = '2'
AND
  SalesAdvisability.businessType = /*businessType*/0
AND
  SalesAdvisability.confDocumentType = /*confDocumentType*/0
AND
  SalesAdvisability.salableGoodsType = '3'
-- PDR Migrate Customization to here