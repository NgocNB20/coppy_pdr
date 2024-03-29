-- 『サイトマップ』
--   sitemap
-- 
-- ■更新内容
-- ・使用する環境に合わせてドメインを更新する
-- 
UPDATE
    sitemap
SET
    loc = REPLACE(loc, 'shop.pdr.co.jp', 'e4-pdr-dev.itechh.ne.jp')
WHERE
    loc LIKE '%shop.pdr.co.jp%'
