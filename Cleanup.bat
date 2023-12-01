@echo off
pushd 2022
del /s *.class 2>NUL
del /s *.txt 2>NUL
del /s *.bak 2>NUL
popd
pushd 2023
del /s *.class 2>NUL
del /s *.txt 2>NUL
del /s *.bak 2>NUL
popd
pause