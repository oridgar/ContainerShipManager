#!/bin/sh

version=0.0.1
module=csa
arch=all
filename=${module}_${version}_${arch}
projectname=agent

#echo "Prepare DEBIAN configuration files..."
rm -R /tmp/${filename}/
cp -R ./${filename} /tmp/
chmod -R 0755 /tmp/${filename}

echo "installing ContainerShip libraries into maven local repository..."
cd ..
cd libs
mvn install

echo "Building java package for ${module}..."
cd ..
cd server-agent
#mvn compile
mvn package

echo "Copy binary files..."
cp ./target/${projectname}-${version}-SNAPSHOT.jar /tmp/${filename}/usr/share/${module}/lib/${module}.jar
echo "Build package..."
cd /tmp
dpkg -b ${filename}
