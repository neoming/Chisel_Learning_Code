#!/bin/bash
scala_root="./src/main/scala"
module_root="./src/module"
echo "searching package in ${scala_root}"

function move_file(){
  if [ -f $1 ];then
    mv $1 $2
  fi
  echo "      moving $1 to $2"
}

for package in `ls ${scala_root}` ;do

    # 进入路径下面的scala包
    echo "  now is at package: ${package}"

    for scala_class in `ls ${scala_root}/${package}`;do
        # 在包中找到对应的.scala文件,并且去除.scala后缀
        module_name=${scala_class%.scala}
        echo "    module name is ${module_name}"

        # 在目标目录下面根据class名称创建文件夹
        targetPath=${module_root}/${module_name}/
        echo "      make dir ${targetPath}"
        if [ ! -d ${targetPath} ];then
          mkdir ${targetPath}
        fi

        # 将主目录下面的一些输出文件移动到目标文件夹下面
        move_file ${module_name}.v ${targetPath}
        move_file ${module_name}.fir ${targetPath}
        move_file ${module_name}.anno.json ${targetPath}
    done
done

