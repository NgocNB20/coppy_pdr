SELECT
    CAST(saleannouncemail.goodsSeq AS VARCHAR) || CAST(saleannouncemail.memberInfoSeq AS VARCHAR) AS key
    , saleannouncemail.goodsSeq
    , saleannouncemail.memberInfoSeq
    , saleannouncemail.deliveryId
    , saleannouncemail.deliveryStatus
    , saleannouncemail.deliveryTime
    , saleannouncemail.registTime
    , saleannouncemail.updateTime
FROM
saleannouncemail
    LEFT JOIN favorite
    ON saleannouncemail.goodsseq = favorite.goodsseq AND saleannouncemail.memberinfoseq = favorite.memberinfoseq
WHERE
    saleannouncemail.deliverystatus = '0'
    OR saleannouncemail.deliverystatus = '1'
ORDER BY favorite.registTime DESC
