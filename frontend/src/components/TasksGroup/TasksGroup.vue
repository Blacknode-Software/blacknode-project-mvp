<script setup lang="ts">
import { computed } from 'vue';
import { ProgressBar, PriorityText, TimestampDate } from '@/ui-toolkit';
import type { Task } from '@/shared-types';

defineProps<{
    tasks: Task[];
}>();

const dateFormatter = computed(
    () =>
        new Intl.DateTimeFormat('en-US', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
        }),
);

const emit = defineEmits<{
    (e: 'open-task', task: Task): void;
}>();
</script>

<template>
    <table class="group-table">
        <tbody>
            <tr class="group-header">
                <th>Task name</th>
                <th>Description</th>
                <th>Priority</th>
                <th>Timeline date</th>
                <th>Progress</th>
            </tr>
            <tr
                v-for="task in tasks"
                :key="task.id"
                class="task-row"
                @click="emit('open-task', task)"
            >
                <td>{{ task.title }}</td>
                <td>{{ task.description }}</td>
                <td><PriorityText :value="task.priority" /></td>
                <td>
                    <TimestampDate :formatter="dateFormatter" :timestmap="task.beginAt" />
                </td>
                <td>
                    <ProgressBar :value="task.progress" :max-value="100">
                        <template #left>{{ task.progress }}%</template>
                    </ProgressBar>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<style scoped>
.group-table {
    width: 100%;
    font-size: 14px;
    color: white;
    border-collapse: collapse;
}

.group-header {
    color: var(--color-second-gray);
    font-size: 12px;
}

.group-table th {
    padding: 10px 12px;
    text-align: left;
}

.group-table td {
    padding: 14px 12px;
}

.group-table tr {
    border-bottom: 1px solid #7a7a9075;
}

.task-row {
    cursor: pointer;
}

.task-row:hover {
    background-color: #00000021;
}
</style>
