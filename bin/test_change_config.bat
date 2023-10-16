@echo off
setlocal enabledelayedexpansion

for %%a in ("%~dp0\..") do set "PATH_ROOT=%%~fa"
echo ROOT PATH SET TO = %PATH_ROOT%

set "config_file=%PATH_ROOT%\conf\testconfig.json"

set "SEARCH_PATH_FROM=$PATH_FROM"
set "SEARCH_PATH_TO=$PATH_TO"

set "PATH_FROM=%PATH_ROOT%\fit-resume\filedata\java\main"
set "PATH_TO=%PATH_ROOT%\fit-resume\filedata\java\tmp"

REM Create a temporary file
set "temp_file=%temp%\tempfile.txt"
copy "%config_file%" "%temp_file%"

REM Clear the original file
type nul > "%config_file%"

for /f "delims=" %%i in ('type "%temp_file%"') do (
    set "line=%%i"
    setlocal enabledelayedexpansion
    set "line=!line:%SEARCH_PATH_FROM%=%PATH_FROM%!"
    set "line=!line:%SEARCH_PATH_TO%=%PATH_TO%!"
    echo(!line!>>"%config_file%"
    endlocal
)

REM Delete the temporary file
del "%temp_file%"

CALL %PATH_ROOT%\bin\Configure-path.bat