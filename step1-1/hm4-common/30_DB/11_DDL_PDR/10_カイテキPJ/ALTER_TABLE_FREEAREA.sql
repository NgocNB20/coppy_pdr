-- 2023-renew No36-1, No61,67,95 from here
ALTER TABLE public.freearea ADD ukfeedinfosendflag VARCHAR(1) DEFAULT 0 NOT NULL;
ALTER TABLE public.freearea ADD uktransitionurl TEXT;
ALTER TABLE public.freearea ADD ukcontentimageurl VARCHAR(100);
ALTER TABLE public.freearea ADD ukSearchKeyword VARCHAR(1000);

COMMENT ON COLUMN public.freearea.ukfeedInfosendflag IS 'UK-フィード情報送信フラグ';
COMMENT ON COLUMN public.freearea.uktransitionurl IS 'UK-遷移先URL';
COMMENT ON COLUMN public.freearea.ukcontentimageurl IS 'UK-コンテンツ画像URL';
COMMENT ON COLUMN public.freearea.ukSearchKeyword IS 'UK-検索キーワード';
-- 2023-renew No36-1, No61,67,95 to here
