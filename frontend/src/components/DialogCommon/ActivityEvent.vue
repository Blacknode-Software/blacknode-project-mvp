<script setup lang="ts">
import { TimestampDate } from '@/ui-toolkit';
import type { UnixTimestamp } from '@/utils';
import { activityDateFormatter } from './activity-date-formatter';

defineProps<{
    timestamp: UnixTimestamp;
}>();
</script>

<template>
    <div class="activity-event-container">
        <span><slot></slot></span>
        <TimestampDate :timestmap="timestamp" :formatter="activityDateFormatter" />
    </div>
</template>

<style scoped>
.activity-event-container {
    display: flex;
    gap: 10px;
    color: var(--color-second-gray);
    font-size: 12px;
    padding: 0 9px;
}
</style>
