<script setup lang="ts">
import type { Task } from '@/shared-types';
import { PriorityText, TimestampDate } from '@/ui-toolkit';

defineProps<{
    task: Task;
}>();

const dateFormatter = new Intl.DateTimeFormat('en-US', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
});

const emit = defineEmits<{
    (e: 'open', task: Task): void;
}>();
</script>

<template>
    <div
        class="kanban-card"
        role="button"
        @click="emit('open', task)"
        @keydown.enter="emit('open', task)"
        @keydown.space.prevent="emit('open', task)"
    >
        <div class="kanban-card-top">
            <div class="kanban-timestamp-pill">
                <TimestampDate :timestmap="task.timestamp" :formatter="dateFormatter" />
            </div>
        </div>

        <div class="kanban-card-title">
            {{ task.title }}
        </div>

        <div v-if="task.description" class="kanban-card-description">
            {{ task.description }}
        </div>

        <div class="kanban-card-footer">
            <span class="priority">
                <PriorityText :value="task.priority" />
            </span>
        </div>
    </div>
</template>

<style scoped>
.kanban-card {
    background-color: oklab(0% 0 0 / 0.2);
    padding: 14px;
    display: flex;
    flex-direction: column;
    gap: 10px;
    cursor: pointer;
    user-select: none;
}

.kanban-card:hover {
    background-color: oklab(0% 0 0 / 0.3);
}

.kanban-card-top {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.kanban-card-title {
    font-weight: 600;
    color: white;
}

.kanban-card-description {
    line-height: 1.4;
    color: var(--color-second-gray);
}

.kanban-card-footer {
    display: flex;
    justify-content: space-between;
}

.kanban-timestamp-pill {
    border-radius: 2px;
    padding: 2px 6px;
    color: var(--color-main);
    background-color: oklch(from var(--color-main) l c h / 8%);
}
</style>
