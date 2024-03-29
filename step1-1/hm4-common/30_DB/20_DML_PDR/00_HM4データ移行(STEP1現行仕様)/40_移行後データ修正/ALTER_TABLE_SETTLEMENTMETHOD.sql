-- 『決済方法』
--   settlementmethod
-- 
-- ■更新内容
-- ・PDRでは決済名称を決済方法表示名PCへ上書きしておく
--   
-- ★★★当SQLは「10_データ移行.sql」にも反映済なので、新しい「hm4.backup」を作成したら削除する
-- 
UPDATE settlementmethod 
SET
    settlementmethodname = settlementmethoddisplaynamepc

