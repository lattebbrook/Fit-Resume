@echo off
setlocal enabledelayedexpansion

for %%a in ("%~dp0\..") do set "PATH_ROOT=%%~fa"
echo ROOT PATH SET TO = %PATH_ROOT%

set "config_file=%PATH_ROOT%\conf\envconfig.json"

set "SEARCH_PATH_FROM=$PATH_FROM"
set "SEARCH_PATH_TO=$PATH_TO"
set "SEARCH_PATH_ERROR=$PATH_ERROR"
set "SEARCH_PATH_BACKUP=$PATH_BACKUP"
set "SEARCH_PATH_RESOURCE_PYTHON=$PATH_RESOURCE_PYTHON"
set "INSTALL_STATUS=$FIRST_TIME_INSTALL"

set "PATH_FROM=%PATH_ROOT%\resources\dir\main"
set "PATH_TO=%PATH_ROOT%\resources\dir\tmp"
set "PATH_ERROR=%PATH_ROOT%\resources\dir\error"
set "PATH_BACKUP=%PATH_ROOT%\resources\dir\backup"
set "TRUE=true"

REM Create a temporary file
set "temp_file=%temp%\tempfile.txt"
copy "%config_file%" "%temp_file%"

REM Clear the original file
type nul > "%config_file%"

for /f "delims=" %%i in (%temp_file%) do (
    set "line=%%i"
    setlocal enabledelayedexpansion
    set "line=!line:%SEARCH_PATH_FROM%=%PATH_FROM%!"
    set "line=!line:%SEARCH_PATH_TO%=%PATH_TO%!"
    set "line=!line:%SEARCH_PATH_ERROR%=%PATH_ERROR%!"
    set "line=!line:%SEARCH_PATH_BACKUP%=%PATH_BACKUP%!"
	set "line=!line:%SEARCH_PATH_RESOURCE_PYTHON%=%PATH_RESOURCE_PYTHON%!"
    set "line=!line:%INSTALL_STATUS%=%TRUE%!"
    echo(!line!>>"%config_file%"
    endlocal
)

REM Delete the temporary file
del "%temp_file%"
echo [DONE]
CALL %PATH_ROOT%\bin\configure-path.bat
