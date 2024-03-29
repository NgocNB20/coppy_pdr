select
    count(addressbook.addressbookseq)
from
    addressbook
where
    addressbook.memberInfoSeq = /*memberInfoSeq*/0
