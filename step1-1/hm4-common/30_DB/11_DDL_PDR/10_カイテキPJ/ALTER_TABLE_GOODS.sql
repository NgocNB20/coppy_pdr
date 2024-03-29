-- 2023-renew No76 from here
ALTER TABLE public.goods ADD unitimageflag VARCHAR(1) DEFAULT 0 NOT NULL;
COMMENT ON COLUMN public.goods.unitimageflag IS '規格画像有無';
-- 2023-renew No76 to here

-- 2023-renew No65 from here
ALTER TABLE goods ADD COLUMN goodsPriceInTaxLow NUMERIC(10) NOT NULL DEFAULT 0;
ALTER TABLE goods ADD COLUMN goodsPriceInTaxHight NUMERIC(10) NOT NULL DEFAULT 0;
ALTER TABLE goods ADD COLUMN preDiscountPriceLow NUMERIC(10) DEFAULT 0;
ALTER TABLE goods ADD COLUMN preDiscountPriceHight NUMERIC(10) DEFAULT 0;

COMMENT ON COLUMN goods.goodsPriceInTaxLow IS '価格(最低)';
COMMENT ON COLUMN goods.goodsPriceInTaxHight IS '価格（最高）';
COMMENT ON COLUMN goods.preDiscountPriceLow IS 'セール価格（最低）';
COMMENT ON COLUMN goods.preDiscountPriceHight IS 'セール価格（最高）';
-- 2023-renew No65 to here

-- 2023-renew No92 from here
ALTER TABLE goods ADD COLUMN saleControl VARCHAR(2) NOT NULL DEFAULT 0;
COMMENT ON COLUMN goods.saleControl IS '販売制御区分';
-- 2023-renew No92 to here
