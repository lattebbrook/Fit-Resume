@echo off

echo ----- Starting Application -----
echo This software is distributed under the GNU General Public License (GPL). 
echo [1] Check port availability: 3000, 8080
setlocal enabledelayedexpansion
set "ports=8080 3000"
for %%p in (%ports%) do (
    netstat -ano | findstr /i "%%p" >nul
    if !errorlevel! equ 0 (
        echo -- [ERROR] Port %%p is in use.
		break
    ) else (
        echo -- [OK] Port %%p is available.
    )
)
endlocal 
echo [2] Start ExpressJs Server Side Application [NodeJs] source location: %NVM_HOME% node version=18.13.0
cd ../data/app-fit-resume-FE/
npm run dev
echo [3] Start SpringBoot Server Side Application [Java] source location: %JAVA_HOME% jvm version=21.0 LTS
cd ../data/app-java-springboot-fit-resume-BE/target REM <--- change here
java -jar target/myproject-0.0.1-SNAPSHOT.jar  REM <--- change here
pause


