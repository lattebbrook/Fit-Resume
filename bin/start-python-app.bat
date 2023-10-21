@echo off
setlocal enabledelayedexpansion

set "batch_file_dir=%~dp0"
for %%a in ("%~dp0\..") do set "PATH_ROOT=%%~fa"
echo %PATH_ROOT%

cd %PATH_ROOT%\data\app-python-BE\fit-resume-python-app\
python .\initialization.py
python -m pip install --upgrade pip
pip install -r requirements.txt
pip install --upgrade pythainlp
pause
endlocal
