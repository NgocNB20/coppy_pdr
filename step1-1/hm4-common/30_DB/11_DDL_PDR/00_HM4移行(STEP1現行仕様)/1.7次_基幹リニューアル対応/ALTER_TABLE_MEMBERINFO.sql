ALTER TABLE memberinfo ADD COLUMN medicalTreatmentFlag VARCHAR(10) DEFAULT '0000000000' NOT NULL;
COMMENT ON COLUMN memberinfo.medicalTreatmentFlag IS '診療内容';

ALTER TABLE memberinfo ADD COLUMN medicalTreatmentMemo VARCHAR(30);
COMMENT ON COLUMN memberinfo.medicalTreatmentMemo IS 'その他診療内容';

ALTER TABLE memberinfo ADD COLUMN metalPermitFlag VARCHAR(1) DEFAULT 0 NOT NULL;
COMMENT ON COLUMN memberinfo.metalPermitFlag IS '金属商品価格お知らせメール';

ALTER TABLE MEMBERINFO ADD COLUMN confDocumentType VARCHAR(2) DEFAULT 0 NOT NULL;
COMMENT ON COLUMN MEMBERINFO.confDocumentType IS '確認書類';

ALTER TABLE MEMBERINFO ADD COLUMN accountingType VARCHAR(2) DEFAULT 0 NOT NULL;
COMMENT ON COLUMN MEMBERINFO.accountingType IS '経理区分';

ALTER TABLE MEMBERINFO ADD COLUMN onlineLoginAdvisability VARCHAR(1) DEFAULT 0 NOT NULL;
COMMENT ON COLUMN MEMBERINFO.onlineLoginAdvisability IS 'オンラインログイン可否';

ALTER TABLE MEMBERINFO ALTER COLUMN memberInfoAddress1 TYPE VARCHAR(40);

ALTER TABLE MEMBERINFO ALTER COLUMN memberInfoAddress2 TYPE VARCHAR(40);

ALTER TABLE MEMBERINFO ALTER COLUMN memberInfoAddress3 TYPE VARCHAR(40);

ALTER TABLE MEMBERINFO ALTER COLUMN memberinfoaddress4 TYPE VARCHAR(30);

ALTER TABLE MEMBERINFO ALTER COLUMN memberinfoaddress5 TYPE VARCHAR(30);
