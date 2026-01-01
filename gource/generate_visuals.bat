@echo off
setlocal EnableDelayedExpansion

REM ============================
REM User input
REM ============================
echo Enter START date (YYYY-MM-DD) [leave empty = from beginning]:
set /p START_DATE=

echo Enter END date (YYYY-MM-DD) [leave empty = no end limit]:
set /p END_DATE=

echo Enter MODE (recording / r / leave empty = preview):
set /p MODE=

if /I "%MODE%"=="r" set MODE=recording

REM ============================
REM Resolve stop-date (optional)
REM ============================
set STOP_DATE_ARG=

if not "%END_DATE%"=="" (
    for /f %%i in ('
        powershell -NoProfile -Command ^
        "[datetime]::Parse(''%END_DATE%'').AddDays(1).ToString(''yyyy-MM-ddTHH:mm:ss'')"
    ') do set STOP_DATE_ARG=--stop-date %%i
)

REM ============================
REM Prepare export
REM ============================
if not exist export mkdir export

for /f %%i in ('powershell -NoProfile -Command "Get-Date -Format yyyy-MM-dd@HH-mm-ss"') do set NOW=%%i
set OUTPUT=export\visual-exports-%NOW%.mp4

REM ============================
REM Base Gource arguments
REM ============================
set GOURCE_ARGS=-1920x1080 --seconds-per-day 3 --auto-skip-seconds 1 --logo "media/logo.png" --background-image "media/background.png"

if not "%START_DATE%"=="" (
    set GOURCE_ARGS=%GOURCE_ARGS% --start-date "%START_DATE%"
)

if not "%STOP_DATE_ARG%"=="" (
    set GOURCE_ARGS=%GOURCE_ARGS% %STOP_DATE_ARG%
)

REM ============================
REM Mode dispatch
REM ============================
if /I "%MODE%"=="recording" goto RECORD
goto PREVIEW

REM ============================
REM Recording mode
REM ============================
:RECORD
echo.
echo Select OUTPUT PRESET:
echo 1 = High quality   (~80–150 MB/min)  CRF 18
echo 2 = Balanced       (~40–80 MB/min)   CRF 21
echo 3 = Small          (~20–40 MB/min)   CRF 23
echo 4 = Preview        (~8–15 MB/min)    CRF 26
echo.

set /p PRESET=Preset number:

REM Default preset
set FFMPEG_OPTS=-c:v libx264 -preset slow -crf 26 -profile:v high -level 4.2 -bf 3

if "%PRESET%"=="1" set FFMPEG_OPTS=-c:v libx264 -preset medium -crf 18 -profile:v high -level 4.2 -bf 2
if "%PRESET%"=="2" set FFMPEG_OPTS=-c:v libx264 -preset slow   -crf 21 -profile:v high -level 4.2 -bf 3
if "%PRESET%"=="3" set FFMPEG_OPTS=-c:v libx264 -preset slow   -crf 23 -profile:v high -level 4.2 -bf 3

REM ===== Execute recording =====
gource %GOURCE_ARGS% -o - | ffmpeg -y -r 60 -f image2pipe -vcodec ppm -i - %FFMPEG_OPTS% -pix_fmt yuv420p -movflags +faststart "%OUTPUT%"
goto END

REM ============================
REM Preview mode
REM ============================
:PREVIEW
gource %GOURCE_ARGS%
goto END

REM ============================
REM Cleanup
REM ============================
:END
endlocal
