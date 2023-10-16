@echo off
setlocal enabledelayedexpansion

set "jsonFile=%~dp0..\conf\envconfig.json"

(
    for /f "usebackq delims=" %%a in ("%jsonFile%") do (
        set "line=%%a"
        set "line=!line:\=\\!"
        echo !line!
    )
) > "%jsonFile%.temp"

move /y "%jsonFile%.temp" "%jsonFile%"

endlocal
