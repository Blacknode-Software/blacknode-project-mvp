<script setup lang="ts">
import type { UnixTimestamp } from '@/utils';
import { activityDateFormatter } from './activity-date-formatter';
import { TimestampDate } from '@/ui-toolkit';

defineProps<{
    authorName: string;
    content: string;
    timestamp: UnixTimestamp;
}>();
</script>

<template>
    <div class="activity-comment">
        <div class="comment-header">
            {{ authorName }}
            <span class="comment-date">
                <TimestampDate :timestmap="timestamp" :formatter="activityDateFormatter" />
            </span>
        </div>
        <div class="comment-content">{{ content }}</div>
    </div>
</template>

<style scoped>
.activity-comment {
    border: 1px solid oklch(from var(--color-second-gray) l c h / 20%);
}

.comment-header {
    display: flex;
    gap: 10px;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    background-color: oklch(from var(--color-second-gray) l c h / 5%);
    font-size: 12px;
    color: white;
}

.comment-content {
    padding: 8px;
    font-size: 12px;
    color: var(--color-second-gray);
}

.comment-date {
    color: var(--color-second-gray);
}
</style>
