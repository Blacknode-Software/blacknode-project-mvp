#!/bin/sh

# Ustal katalog export
[ -d "export" ] || mkdir "export"

# Data w formacie YYYY-MM-DD
TODAY=$(date +"%Y-%m-%d")

# Plik wyjściowy z datą
OUTPUT="export/visuals_${TODAY}.mp4"

gource -1920x1080 --seconds-per-day 3 --auto-skip-seconds 1 --logo "logo.png" --background-image "background.png" -o - \
| ffmpeg -y -r 60 -f image2pipe -vcodec ppm -i - -vcodec libx264 -preset ultrafast -pix_fmt yuv420p -crf 1 -threads 0 -bf 0 "$OUTPUT"
