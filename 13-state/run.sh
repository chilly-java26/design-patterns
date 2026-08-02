#!/bin/bash

echo "==================================="
echo "编译并运行状态模式演示"
echo "==================================="

mvn clean compile exec:java -Dexec.mainClass="com.designpatterns.state.Main"
