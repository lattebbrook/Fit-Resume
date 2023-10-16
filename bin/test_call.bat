@echo off
setlocal enabledelayedexpansion

set "batch_file_dir=%~dp0"
for %%a in ("%~dp0\..") do set "PATH_ROOT=%%~fa"
echo %PATH_ROOT%


CALL %PATH_ROOT%\test_target.bat

endlocal
pause

rem https://stackoverflow.com/questions/26055421/get-path-two-levels-up-in-batch-file