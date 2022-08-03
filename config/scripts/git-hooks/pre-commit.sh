#!/bin/sh
echo "Running static analysis..."

OUTPUT="/tmp/analysis-result"
./gradlew checkFormat --daemon > ${OUTPUT}
EXIT_CODE=$?
if [ ${EXIT_CODE} -ne 0 ]; then
    cat ${OUTPUT}
    rm ${OUTPUT}
    echo "***********************************************"
    echo "             Static Analysis Failed            "
    echo " Please fix the above issues before committing "
    echo "          Hint: use './gradlew reformat'       "
    echo "If you really really need this, use --no-verify"
    echo "***********************************************"
    exit ${EXIT_CODE}
else
    rm ${OUTPUT}
    echo "***********************************************"
    echo "       Static analysis no problems found       "
    echo "***********************************************"
fi
