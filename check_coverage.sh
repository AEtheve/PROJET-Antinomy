csv_file="coverage.csv"
required_coverage=$1

sed '/,tests,/d' $csv_file > temp.csv
sed '/,Global,/d' temp.csv > temp2.csv
sed '/,Structures,/d' temp2.csv > temp3.csv

coverage=$(tail -n +2 temp3.csv | awk -F',' '{ covered+=$5; missed+=$4 } END { print (covered / (covered + missed)) * 100 }')
covered=$(tail -n +2 temp3.csv | awk -F',' '{ covered+=$5 } END { print covered }')
total_instructions=$(tail -n +2 temp3.csv | awk -F',' '{total+=$5+$4} END {print total}')
coverage_int=$(printf "%.0f" $coverage)
rm temp.csv
rm temp2.csv
rm temp3.csv

if [ $coverage_int -lt $required_coverage ]; then
    echo "Couverture de code insuffisante: $coverage% ($covered/$total_instructions)"
    exit 1
fi

echo "COVERAGE OK: $coverage% ($covered/$total_instructions)"