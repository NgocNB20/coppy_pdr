-- 2023-renew No60 from here
ALTER TABLE SHOP ADD creditErrorCount NUMERIC(4) DEFAULT 10 NOT NULL;
COMMENT ON COLUMN SHOP.creditErrorCount IS 'クレジットカードエラー回数上限';
-- 2023-renew No60 to here