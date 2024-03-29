-- 『キャンペーン』
--   campaign
-- 
-- ■更新内容
-- ・使用する環境に合わせてドメインを更新する
-- 
UPDATE
    campaign
SET
    redirecturl =
        CASE
            WHEN campaign.redirecturl LIKE '%shop.pdr.co.jp%' THEN REPLACE(campaigndata.redirecturl, 'shop.pdr.co.jp', 'e4-pdr-dev.itechh.ne.jp')
            WHEN campaign.redirecturl LIKE '%test.pdr.co.jp%' THEN REPLACE(campaigndata.redirecturl, 'test.pdr.co.jp', 'e4-pdr-dev.itechh.ne.jp')
            ELSE campaign.redirecturl
            END
    FROM
  (SELECT redirecturl, campaignseq
   FROM campaign
   WHERE redirecturl LIKE '%shop.pdr.co.jp%' OR redirecturl LIKE '%test.pdr.co.jp%') campaigndata
WHERE
    campaign.campaignseq = campaigndata.campaignseq
