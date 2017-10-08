#!/usr/bin/env bash
if [ $# -eq 0 ]; then
    echo "please enter classpath dir"
    exit -1
fi

if [ ! -d "$1" ]; then
    echo "not a directory"
    exit -2
fi

tmpFile="/tmp/.cp$(date +%s)"
tmpHash="/tmp/.hash$(date +%s)"
verbose="/tmp/cp-verbose.log"
declare -a files=(`find "$1" -name "*.jar"`)
for ((i=0; i<${#files[@]}; i++)); do
    jarName=`basename ${files[$i]}}`
    list=`unzip -l ${files[$i]} | awk -v fn=${jarName} '/\.class$/{print $NF, fn}'`
    size=`echo "$list" | wc -l`
    echo ${jarName} ${size} >> ${tmpHash}
    echo "$list"
done | sort | awk 'NF{
    a[$1]++;m[$1]=m[$1]","$2}END{for (i in a) if (a[i] > 1) print i,substr(m[i],2)}' > ${tmpFile}
    awk '{print $2}' ${tmpFile} |
    awk -F ',' '{i=1; for(; i<=NF;i++) for(j=i+1;j<=NF;j++) print $i,$j}' |
    sort | uniq -c | sort -nrkl | while read line; do
        dup=${line%% *}
        jars=${line#* }
        jar1=${jars% *}
        jar2=${jars#* }
        len_jar1=`grep -F "$jar1" ${tmpHash} | grep ^"$jar1" | awk '{print $2}'`
        len_jar2=`grep -F "$jar2" ${tmpHash} | grep ^"$jar2" | awk '{print $2}'`
        len_jar1=`echo ${len_jar1} | awk -F ' ' ' {print $1}'`
        len_jar2=`echo ${len_jar2} | awk -F ' ' ' {print $1}'`
        if [ ${len_jar1} -gt ${len_jar2} ]
        then
            len=${len_jar1}
        else
            len=${len_jar2}
        fi
        per=$(echo "scale=2; $dup/$len" | bc -l)
        echo ${per/./} ${dup} ${jar1} ${jar2}
done | sort -nr -k1 -k2 |
awk 'NR==1{print "Similarity Duplication Classes File1 File2"}{print "%"$0}' |
column -t
sort ${tmpFile} | awk '{print $1, "\n\t\t",$2}' > ${verbose}
echo "See $verbose for more details."
rm -f ${tmpFile}
rm -f ${tmpHash}



















