-- PDR Migrate Customization from here
SELECT
  addressbook.customerno
FROM
  addressbook
WHERE
  addressbook.memberInfoSeq = /*conditionDto.memberInfoSeq*/0
ORDER BY
  addressbook.customerno DESC
;
-- PDR Migrate Customization to here