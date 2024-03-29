delete from
    addressbook
where
    addressbook.memberInfoSeq = /*memberInfoSeq*/0
and
    addressbook.addressBookSeq in /*addressBookSeqList*/(1,2,3)
