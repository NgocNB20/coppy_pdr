CREATE TABLE LOGINADVISABILITY
(
    loginAdvisabilitySeq           NUMERIC(8) NOT NULL,
    memberInfoStatus           VARCHAR(1) NOT NULL,
    approveStatus           VARCHAR(1) NOT NULL,
    onlineLoginAdvisability           VARCHAR(2) NOT NULL,
    memberListType           VARCHAR(2) NOT NULL,
    accountingType           VARCHAR(2) NOT NULL,
    loginAdvisabilityType           VARCHAR(1) NOT NULL
)
;

ALTER TABLE LOGINADVISABILITY
    ADD CONSTRAINT LOGINADVISABILITY_PKEY PRIMARY KEY (loginAdvisabilitySeq)
;

COMMENT ON TABLE LOGINADVISABILITY IS 'ログイン可否判定';
COMMENT ON COLUMN LOGINADVISABILITY.loginAdvisabilitySeq IS 'ログイン可否判定SEQ';
COMMENT ON COLUMN LOGINADVISABILITY.memberInfoStatus IS '会員状態';
COMMENT ON COLUMN LOGINADVISABILITY.approveStatus IS '承認状態';
COMMENT ON COLUMN LOGINADVISABILITY.onlineLoginAdvisability IS 'オンラインログイン可否';
COMMENT ON COLUMN LOGINADVISABILITY.memberListType IS '名簿区分';
COMMENT ON COLUMN LOGINADVISABILITY.accountingType IS '経理区分';
COMMENT ON COLUMN LOGINADVISABILITY.loginAdvisabilityType IS 'ログイン可否区分';

CREATE UNIQUE INDEX idx_loginAdvisability ON LOGINADVISABILITY (memberInfoStatus,approveStatus,onlineLoginAdvisability,memberListType,accountingType);
