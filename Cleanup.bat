@echo off
pushd "%~dp0"
javac Cleanup.java
java -ea Cleanup
del *.class
popd
pause