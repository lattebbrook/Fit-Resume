@echo off
setlocal enabledelayedexpansion

set "batch_file_dir=%~dp0"
for %%a in ("%~dp0\..") do set "PATH_ROOT=%%~fa"

rem run python initialization first 
CALL %PATH_ROOT%\bin\start-python-app.bat 
echo [3.] Run node js test-- [OK]
echo [4.] Run Spring Boot Application [OK]
echo [5.] Preparing python dependencies services [OK]

endlocal
pause