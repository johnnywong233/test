#!/bin/bash
# 定义一个颜色输出字符串函数
function echo_color() {
 if [ $1 == "green" ] ;then
  echo -e "\033[32;40m$2\033[0m"
  elif [ $1 == "red" ]; then
  echo -e "\033[31;40m$2\033[0m"
  fi
}

function echoColor() {
case $1 in
 green)
  echo -e "\033[32;40m$2\033[0m"
  ;;
  red)
  echo -e "\033[31;40m$2\033[0m"
  ;;
  *)
  echo "Usage: echoColor red string"
  esac
}