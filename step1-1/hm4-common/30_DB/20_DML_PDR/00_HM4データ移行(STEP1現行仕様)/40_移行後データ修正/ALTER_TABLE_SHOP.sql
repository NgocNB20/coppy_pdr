-- 『ショップ』
--   shop
-- 
-- ■更新内容
-- ・使用する環境に合わせてドメインを更新する
-- 
UPDATE
    shop
SET
    urlpc = REPLACE(urlpc, 'shop.pdr.co.jp', 'e4-pdr-dev.itechh.ne.jp'),
    urlmb = REPLACE(urlmb, 'shop.pdr.co.jp', 'e4-pdr-dev.itechh.ne.jp')
WHERE
    urlpc LIKE '%shop.pdr.co.jp%'
