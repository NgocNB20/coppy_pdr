-- 『フリーエリア』
--   freearea
-- 
-- ■更新内容
-- ・使用する環境に合わせてドメインを更新する
-- 
UPDATE freearea
SET
    freeareabodypc =
        CASE
            WHEN freearea.freeareabodypc LIKE '%shop.pdr.co.jp%' THEN REPLACE(freeareadata.freeareabodypc, 'shop.pdr.co.jp', 'e4-pdr-dev.itechh.ne.jp')
            ELSE freearea.freeareabodypc
            END,
    freeareabodysp =
        CASE
            WHEN freearea.freeareabodysp LIKE '%shop.pdr.co.jp%' THEN REPLACE(freeareadata.freeareabodysp, 'shop.pdr.co.jp', 'e4-pdr-dev.itechh.ne.jp')
            ELSE freearea.freeareabodysp
            END,
    freeareabodymb =
        CASE
            WHEN freearea.freeareabodymb LIKE '%shop.pdr.co.jp%' THEN REPLACE(freeareadata.freeareabodymb, 'shop.pdr.co.jp', 'e4-pdr-dev.itechh.ne.jp')
            ELSE freearea.freeareabodymb
            END
    FROM
  (SELECT freeareakey, freeareabodypc, freeareabodysp, freeareabodymb
   FROM freearea
   WHERE freeareabodypc LIKE '%shop.pdr.co.jp%' OR freeareabodysp LIKE '%shop.pdr.co.jp%' OR freeareabodymb LIKE '%shop.pdr.co.jp%') freeareadata
WHERE
    freearea.freeareakey = freeareadata.freeareakey
