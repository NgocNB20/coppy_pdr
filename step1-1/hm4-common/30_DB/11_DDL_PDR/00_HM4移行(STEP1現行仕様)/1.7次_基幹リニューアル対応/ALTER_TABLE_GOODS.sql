ALTER TABLE GOODS ADD COLUMN emotionPriceType VARCHAR(1) DEFAULT 0 NOT NULL;
COMMENT ON COLUMN GOODS.emotionPriceType IS '心意気価格保持区分';