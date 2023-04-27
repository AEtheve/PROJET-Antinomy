javac -d out/tests -cp src:./lib/junit-4.13.2.jar tests/*.java 

result=0
nb_failed=0
nb_tests=0

for test in tests/*.java; do
    nb_tests=$((nb_tests+1))
    test_name=$(basename $test .java)
    java -cp out/tests/:./lib/hamcrest-core-1.3.jar:./lib/junit-4.13.2.jar org.junit.runner.JUnitCore tests.$test_name
    if [ $? -ne 0 ]; then
        result=1
        nb_failed=$((nb_failed+1))
    fi
done
echo "Tests réussis: \033[32m$((nb_tests-nb_failed))\033[0m | Tests échoués: \033[31m$nb_failed\033[0m"
if [ $result -ne 0 ]; then
    exit 1
fi