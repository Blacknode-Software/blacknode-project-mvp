#!/usr/bin/env bash

set -e

# ============================
# User input
# ============================
read -p "Enter START date (YYYY-MM-DD) [leave empty = from beginning]: " START_DATE
read -p "Enter END date (YYYY-MM-DD) [leave empty = no end limit]: " END_DATE
read -p "Enter MODE (recording / r / leave empty = preview): " MODE

if [[ "$MODE" == "r" ]]; then
    MODE="recording"
fi

# ============================
# Resolve stop-date (optional)
# ============================
STOP_DATE_ARG=""

if [[ -n "$END_DATE" ]]; then
    # gource stop-date is exclusive → add +1 day
    STOP_DATE=$(date -d "$END_DATE +1 day" +"%Y-%m-%dT%H:%M:%S")
    STOP_DATE_ARG="--stop-date $STOP_DATE"
fi

# ============================
# Prepare export
# ============================
mkdir -p export
NOW=$(date +"%Y-%m-%d@%H-%M-%S")
OUTPUT="export/visual-exports-$NOW.mp4"

# ============================
# Base Gource arguments
# ============================
GOURCE_ARGS=(
    -1920x1080
    --seconds-per-day 3
    --auto-skip-seconds 1
    --logo "media/logo.png"
    --background-image "media/background.png"
)

if [[ -n "$START_DATE" ]]; then
    GOURCE_ARGS+=(--start-date "$START_DATE")
fi

if [[ -n "$STOP_DATE_ARG" ]]; then
    GOURCE_ARGS+=($STOP_DATE_ARG)
fi

# ============================
# Mode dispatch
# ============================
if [[ "$MODE" == "recording" ]]; then
    echo
    echo "Select OUTPUT PRESET:"
    echo "1 = High quality   (~80–150 MB/min)  CRF 18"
    echo "2 = Balanced       (~40–80 MB/min)   CRF 21"
    echo "3 = Small          (~20–40 MB/min)   CRF 23"
    echo "4 = Preview        (~8–15 MB/min)    CRF 26"
    echo

    read -p "Preset number: " PRESET

    # Default preset
    FFMPEG_OPTS=(-c:v libx264 -preset slow -crf 26 -profile:v high -level 4.2 -bf 3)

    case "$PRESET" in
        1) FFMPEG_OPTS=(-c:v libx264 -preset medium -crf 18 -profile:v high -level 4.2 -bf 2) ;;
        2) FFMPEG_OPTS=(-c:v libx264 -preset slow   -crf 21 -profile:v high -level 4.2 -bf 3) ;;
        3) FFMPEG_OPTS=(-c:v libx264 -preset slow   -crf 23 -profile:v high -level 4.2 -bf 3) ;;
    esac

    # ============================
    # Execute recording
    # ============================
    gource "${GOURCE_ARGS[@]}" -o - | \
    ffmpeg -y -r 60 -f image2pipe -vcodec ppm -i - \
           "${FFMPEG_OPTS[@]}" \
           -pix_fmt yuv420p \
           -movflags +faststart \
           "$OUTPUT"

else
    # ============================
    # Preview mode
    # ============================
    gource "${GOURCE_ARGS[@]}"
fi
