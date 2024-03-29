-- PDR Migrate Customization from here
SELECT
    addressBook.*
FROM
    addressBook
WHERE
    addressBook.customerNo = /*customerNo*/0
-- PDR Migrate Customization to here