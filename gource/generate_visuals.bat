@echo off

REM Ustal katalog export
if not exist "export" (
    mkdir "export"
)

REM Data w formacie YYYY-MM-DD
for /f %%i in ('powershell -NoProfile -Command "Get-Date -Format yyyy-MM-dd"') do set TODAY=%%i

REM Plik wyjściowy z datą
set OUTPUT=export\visuals_%TODAY%.mp4

gource -1920x1080 --seconds-per-day 3 --auto-skip-seconds 1 --logo "logo.png" --background-image "background.png" -o - ^
| ffmpeg -y -r 60 -f image2pipe -vcodec ppm -i - -vcodec libx264 -preset ultrafast -pix_fmt yuv420p -crf 1 -threads 0 -bf 0 "%OUTPUT%"
