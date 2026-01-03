<script setup lang="ts">
import type { Task } from '@/shared-types';
import KanbanCard from './KanbanCard.vue';

defineProps<{
    title: string;
    color: string;
    tasks: Task[];
}>();

const emit = defineEmits<{
    (e: 'open-task', task: Task): void;
}>();
</script>

<template>
    <section class="kanban-column">
        <header class="kanban-column-header">
            <span class="kanban-column-title" :style="{ color }">
                {{ title }}
            </span>
            <span class="kanban-column-count">
                {{ tasks.length }}
            </span>
        </header>

        <div class="kanban-column-body">
            <KanbanCard
                v-for="task in tasks"
                :key="task.id"
                :task="task"
                @open="emit('open-task', $event)"
            />
        </div>
    </section>
</template>

<style scoped>
.kanban-column {
    display: flex;
    flex-direction: column;
    gap: 14px;
    min-width: 340px;
}

.kanban-column-header {
    display: flex;
    align-items: center;
    gap: 10px;
    font-size: 18px;
}

.kanban-column-count {
    color: var(--color-second-gray);
}

.kanban-column-body {
    display: flex;
    flex-direction: column;
    gap: 14px;
}
</style>
