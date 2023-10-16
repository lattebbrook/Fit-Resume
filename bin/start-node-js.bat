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
echo [2] Start application [Node.js] reserving port 3000 for Front End
cd ./fe-fitresume/src
node app.js
echo [3] Start SpringBoot application [Java] %JAVA_HOME% java version=21.0 
cd ./be-fitresume/
java -jar target/myproject-0.0.1-SNAPSHOT.jar
pause


