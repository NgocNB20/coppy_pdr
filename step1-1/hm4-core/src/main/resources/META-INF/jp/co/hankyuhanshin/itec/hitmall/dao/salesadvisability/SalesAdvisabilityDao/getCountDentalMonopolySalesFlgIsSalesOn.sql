-- PDR Migrate Customization from here
SELECT
  count(*)
FROM
  SalesAdvisability
WHERE
  SalesAdvisability.businessType = /*businessType*/0
AND
  SalesAdvisability.confDocumentType = /*confDocumentType*/0
AND
  SalesAdvisability.dentalMonopolySalesFlg = '1'
AND
  SalesAdvisability.salableGoodsType = '3'
;
-- PDR Migrate Customization to here