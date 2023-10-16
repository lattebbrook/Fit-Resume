@echo off
setlocal enabledelayedexpansion

for %%a in ("%~dp0\..") do set "PATH_ROOT=%%~fa"
echo =========================================
echo         Fit Resume Application
echo =========================================
echo [1.] Check installation status

set "config_file=%PATH_ROOT%\conf\envconfig.json"
set "CHECK_INSTALL_STATUS=$FIRST_TIME_INSTALL"

REM Read the JSON file and extract the value associated with CHECK_INSTALL_STATUS key
for /f "tokens=2 delims=:, " %%i in ('type "%config_file%" ^| find /i "%CHECK_INSTALL_STATUS%"') do (
    set "INSTALL_STATUS=%%~i"
)

if "%INSTALL_STATUS%"=="$FIRST_TIME_INSTALL" (
    echo [2.] This is the first-time installation, proceed to environment-variable installation and configuring paths. Please wait patiently.
	call %PATH_ROOT%\bin\config-env-variable.bat
    call %PATH_ROOT%\bin\test_run_server.bat
) else (
    echo [2.] Application already initialized on this machine. Proceed to run the application directly.
    call %PATH_ROOT%\bin\test_run_server.bat
)
