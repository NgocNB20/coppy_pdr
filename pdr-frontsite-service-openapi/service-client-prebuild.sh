#!/bin/sh
PATH_REPLACE="/service-client/"
IMPORT_LINE_1='import com.fasterxml.jackson.databind.SerializationFeature;'
IMPORT_LINE_2='import com.fasterxml.jackson.databind.ObjectMapper;'
CONVERT_TO_MAP='new ObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).convertValue(request, Map.class))'

MY_FILES=$(find out/ -type f -iname "*Api.java")
for file in $MY_FILES
do
  if [[ "$file" == *"$PATH_REPLACE"* ]]
  then
    echo "Update file $file"
    sed -i "2 a $IMPORT_LINE_1" "$file"
    sed -i "3 a $IMPORT_LINE_2" "$file"
    while IFS= read -r line
    do
      if [[ "$line" == *".parameterToMultiValueMap"* ]];
      then
        request=$(echo $line | cut -d '"' -f2)
        replace=$(echo $CONVERT_TO_MAP | sed -e "s|request|$request|g")
        new_data=$(echo $line | sed -e "s|$request)|$replace|g")
        echo $new_data
        sed -i "s|$line|        $new_data|g" $file
      fi
    done <"$file"
  fi
done
