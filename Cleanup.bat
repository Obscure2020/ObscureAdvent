@echo off
pushd "%~dp0"

pushd 2015
del /s *.class 2>NUL
del /s *.txt 2>NUL
del /s *.bak 2>NUL
del /s *.png 2>NUL
del /s *.csv 2>NUL
popd

pushd 2022
del /s *.class 2>NUL
del /s *.txt 2>NUL
del /s *.bak 2>NUL
del /s *.png 2>NUL
del /s *.csv 2>NUL
popd

pushd 2023
del /s *.class 2>NUL
del /s *.txt 2>NUL
del /s *.bak 2>NUL
del /s *.png 2>NUL
del /s *.csv 2>NUL
popd

pushd 2024
del /s *.class 2>NUL
del /s *.txt 2>NUL
del /s *.bak 2>NUL
del /s *.png 2>NUL
del /s *.csv 2>NUL
popd

popd
pause