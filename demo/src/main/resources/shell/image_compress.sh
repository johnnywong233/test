#!/bin/bash

# 查找目录及子目录的图片文件(jpg,gif,png),将大于某值的图片进行压缩处理

# Config
folderPath='/home/fdipzone/photo'

maxSize='1M'
maxWidth=1280
maxHeight=1280
quality=85

# Param $folderPath 图片目录
function compress(){

    folderPath=$1

    if [ -d "$folderPath" ]; then

        for file in $(find "$folderPath" \( -name "*.jpg" -or -name "*.gif" -or -name "*.png" \) -type f -size +"$maxSize" ); do

            echo $file

            # 调用imagemagick resize图片
            $(convert -resize "$maxWidth"x"$maxHeight" "$file" -quality "$quality" -colorspace sRGB "$file")

        done

    else
        echo "$folderPath not exists"
    fi
}

#execute compress
compress "$folderPath"

exit 0