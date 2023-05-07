sh run_tests.sh coverage
if [ $? -ne 0 ]; then
    echo "Tests failed"
    exit 1
fi
sh check_coverage.sh 0