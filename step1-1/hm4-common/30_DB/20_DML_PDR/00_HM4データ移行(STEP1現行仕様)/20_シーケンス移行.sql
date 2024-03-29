-- *****************************
-- ** シーケンス移行用SQL     **
-- *****************************

-- 『権限グループSEQ』
--   adminauthgroupseq
SELECT setval('public.adminauthgroupseq', (SELECT last_value FROM hm3.adminauthgroupseq));

-- 『管理者SEQ』
--   administratorseq
SELECT setval('public.administratorseq', (SELECT last_value FROM hm3.administratorseq));

-- 『配送方法SEQ』
--   deliverymethodseq
SELECT setval('public.deliverymethodseq', (SELECT last_value FROM hm3.deliverymethodseq));

-- 『決済方法SEQ』
--   settlementmethodseq
SELECT setval('public.settlementmethodseq', (SELECT last_value FROM hm3.settlementmethodseq));

-- 『メールテンプレートSEQ』
--   mailtemplateseq
SELECT setval('public.mailtemplateseq', (SELECT last_value FROM hm3.mailtemplateseq));

-- 『バッチタスクSEQ』
--   batchtask_taskid_seq
SELECT setval('public.batchtask_taskid_seq', (SELECT last_value FROM hm3.batchtask_taskid_seq));

-- 『カスタムアラートSEQ』
--   customalertseq
SELECT setval('public.customalertseq', (SELECT last_value FROM hm3.customalertseq));

-- 『サイトマップSEQ』
--   sitemapseq
SELECT setval('public.sitemapseq', (SELECT last_value FROM hm3.sitemapseq));

-- 『フリーエリアFEQ』
--   freeareaseq
SELECT setval('public.freeareaseq', (SELECT last_value FROM hm3.freeareaseq));

-- 『ニュースSEQ』
--   newsseq
SELECT setval('public.newsseq', (SELECT last_value FROM hm3.newsseq));

-- 『アンケートSEQ』
--   questionnaireseq
SELECT setval('public.questionnaireseq', (SELECT last_value FROM hm3.questionnaireseq));

-- 『アンケート設問SEQ』
--   questionseq
SELECT setval('public.questionseq', (SELECT last_value FROM hm3.questionseq));

-- 『アンケート回答SEQ』
--   questionnairereplyseq
SELECT setval('public.questionnairereplyseq', (SELECT last_value FROM hm3.questionnairereplyseq));

-- 『キャンペーンSEQ』
--   campaignseq
SELECT setval('public.campaignseq', (SELECT last_value FROM hm3.campaignseq));

-- 『カテゴリSEQ』
--   categoryseq
SELECT setval('public.categoryseq', (SELECT last_value FROM hm3.categoryseq));

-- 『会員SEQ』
--   memberinfoseq
SELECT setval('public.memberinfoseq', (SELECT last_value FROM hm3.memberinfoseq));

-- 『住所録SEQ』
--   addressbookseq
SELECT setval('public.addressbookseq', (SELECT last_value FROM hm3.addressbookseq));

-- 『確認メールSEQ』
--   confirmmailseq
SELECT setval('public.confirmmailseq', (SELECT last_value FROM hm3.confirmmailseq));

-- 『受注SEQ』
--   orderseq
SELECT setval('public.orderseq', (SELECT last_value FROM hm3.orderseq));

-- 『マルチペイメント請求SEQ』
--   mulpaybillseq
SELECT setval('public.mulpaybillseq', (SELECT last_value FROM hm3.mulpaybillseq));

-- 『マルチペイメント決済結果SEQ』
--   mulpayresultseq
SELECT setval('public.mulpayresultseq', (SELECT last_value FROM hm3.mulpayresultseq));

-- 『端末識別情報SEQ』
--   accessuidseq
SELECT setval('public.accessuidseq', (SELECT last_value FROM hm3.accessuidseq));

-- 『顧客番号SEQ』
--   customerseq
SELECT setval('public.customerseq', (SELECT last_value FROM hm3.customerseq));

-- 『受注番号SEQ』
--   ordernoseq
SELECT setval('public.ordernoseq', (SELECT last_value FROM hm3.ordernoseq));
