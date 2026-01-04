<script setup lang="ts">
import { FixedBackground, MainSidebar, KanbanColumn, TaskDialog } from '@/components';
import { MainHeader, UniSidebar, MainView } from '@/layout';
import type { Task } from '@/shared-types';
import { UnixTimestamp } from '@/utils';
import { ref } from 'vue';

const selectedTask = ref<Task | null>(null);

function openTask(task: Task) {
    selectedTask.value = task;
}

function closeTask() {
    selectedTask.value = null;
}

const tasks = {
    todo: [
        {
            id: '1',
            title: 'Lorem ipsum dolor sit amet',
            description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit.',
            priority: 0,
            timestamp: new UnixTimestamp(1766333438),
        },
        {
            id: '2',
            title: 'Lorem ipsum dolor sit amet',
            description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit.',
            priority: 1,
            timestamp: new UnixTimestamp(1766333438),
        },
        {
            id: '3',
            title: 'Lorem ipsum dolor sit amet',
            description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit.',
            priority: 2,
            timestamp: new UnixTimestamp(1766333438),
        },
        {
            id: '4',
            title: 'Lorem ipsum dolor sit amet',
            description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit.',
            priority: 2,
            timestamp: new UnixTimestamp(1766333438),
        },
    ],
    progress: [],
    review: [],
    done: [
        {
            id: '1',
            title: 'Lorem ipsum dolor sit amet',
            description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit.',
            priority: 2,
            timestamp: new UnixTimestamp(1766333438),
        },
    ],
} as Record<string, Task[]>;
</script>

<template>
    <FixedBackground />
    <MainHeader :sections="['DEPARTMENT OF SECURITY', 'SECTION II']" />
    <div class="view-sections-container">
        <UniSidebar>
            <MainSidebar />
        </UniSidebar>
        <MainView>
            <div class="kanban-board">
                <KanbanColumn
                    title="To do"
                    :tasks="tasks.todo!"
                    color="white"
                    @open-task="openTask"
                />
                <KanbanColumn
                    title="In progress"
                    color="var(--color-main-yellow)"
                    :tasks="tasks.progress!"
                    @open-task="openTask"
                />
                <KanbanColumn
                    title="In review"
                    color="var(--color-magenta)"
                    :tasks="tasks.review!"
                    @open-task="openTask"
                />
                <KanbanColumn
                    title="Completed"
                    color="var(--color-main-green)"
                    :tasks="tasks.done!"
                    @open-task="openTask"
                />
            </div>
        </MainView>
    </div>
    <TaskDialog v-if="selectedTask" :task="selectedTask" @close="closeTask" />
</template>

<style scoped>
.kanban-board {
    display: grid;
    grid-auto-flow: column;
    grid-auto-columns: minmax(340px, 380px);
    gap: 20px;
    padding: 24px;
    height: 100%;
    overflow: auto;
}

/* TODO: Move to App.vue */
.view-sections-container {
    flex-direction: row;
    display: flex;
    flex-grow: 1;
    background-color: transparent;
    height: 0;
}
</style>
