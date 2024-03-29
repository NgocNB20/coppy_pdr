-- 2023-renew No85-1 from here
ALTER TABLE public.memberinfo ALTER COLUMN memberinfouniqueid TYPE varchar(260) USING memberinfouniqueid::varchar(260);
ALTER TABLE public.memberinfo ALTER COLUMN memberinfoid TYPE varchar(256) USING memberinfoid::varchar(256);
ALTER TABLE public.memberinfo ALTER COLUMN memberinfolastkana TYPE varchar(30) USING memberinfolastkana::varchar(30);
ALTER TABLE public.memberinfo ALTER COLUMN memberInfoTel TYPE varchar(14) USING memberInfoTel::varchar(14);
ALTER TABLE public.memberinfo ALTER COLUMN memberinfomail TYPE varchar(256) USING memberinfomail::varchar(256);
ALTER TABLE public.memberinfo ALTER COLUMN memberinfofax TYPE varchar(14) USING memberinfofax::varchar(14);

UPDATE public.memberinfo
SET noantisocialflag = 1
WHERE noantisocialflag IS NULL;
ALTER TABLE public.memberinfo ALTER COLUMN noantisocialflag SET NOT NULL;
ALTER TABLE public.memberinfo ALTER COLUMN noantisocialflag SET DEFAULT 1;
-- 2023-renew No85-1 to here

-- 2023-renew No71 from here
ALTER TABLE public.memberinfo ADD topSaleAnnounceFlg VARCHAR(1) DEFAULT 0 NOT NULL;
COMMENT ON COLUMN public.memberinfo.topSaleAnnounceFlg IS 'トップセール通知フラグ';

ALTER TABLE public.memberinfo ADD saleAnnounceWatchFlg VARCHAR(1) DEFAULT 0 NOT NULL;
COMMENT ON COLUMN public.memberinfo.saleAnnounceWatchFlg IS 'セール通知既読フラグ';

ALTER TABLE public.memberinfo ADD topStockAnnounceFlg VARCHAR(1) DEFAULT 0 NOT NULL;
COMMENT ON COLUMN public.memberinfo.topStockAnnounceFlg IS 'トップ入荷通知フラグ';

ALTER TABLE public.memberinfo ADD stockAnnounceWatchFlg VARCHAR(1) DEFAULT 0 NOT NULL;
COMMENT ON COLUMN public.memberinfo.stockAnnounceWatchFlg IS '入荷通知既読フラグ';
-- 2023-renew No71 to here


-- 2023-renew No79 from here
ALTER TABLE public.memberinfo ADD ordercompletepermitflag varchar(1) NOT NULL DEFAULT 1;
ALTER TABLE public.memberinfo ADD deliverycompletepermitflag varchar(1) NOT NULL DEFAULT 1;
COMMENT ON COLUMN public.memberinfo.ordercompletepermitflag IS '注文完了メール';
COMMENT ON COLUMN public.memberinfo.deliverycompletepermitflag IS '発送完了メール';
-- 2023-renew No79 from here
