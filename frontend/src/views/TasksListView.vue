<script setup lang="ts">
import { FixedBackground, MainSidebar, TasksGroup, TaskDialog } from '@/components';
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
</script>

<template>
    <FixedBackground />
    <MainHeader :sections="['DEPARTMENT OF SECURITY', 'SECTION II']" />
    <div class="view-sections-container">
        <UniSidebar>
            <MainSidebar />
        </UniSidebar>
        <MainView>
            <div class="tasks-list-wrapper">
                <div class="tasks-group">
                    <span class="tasks-group-header">To do</span>
                    <TasksGroup
                        :tasks="[
                            {
                                description: 'Some docs file',
                                id: 'adsdasadsdasdasdas',
                                priority: 0,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 0,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 0,
                            },
                            {
                                description: 'Some docs file',
                                id: 'tww2tweaweweawe',
                                priority: 2,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 0,
                            },
                        ]"
                        @open-task="openTask"
                    />
                </div>
                <div class="tasks-group">
                    <span class="tasks-group-header" style="color: var(--color-main)"
                        >In progress</span
                    >
                    <TasksGroup
                        :tasks="[
                            {
                                description: 'Some docs file',
                                id: 'adsdasadsdasdasdas',
                                priority: 0,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 15,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 50,
                            },
                            {
                                description: 'Some docs file',
                                id: 'tww2tweaweweawe',
                                priority: 2,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 75,
                            },
                        ]"
                        @open-task="openTask"
                    />
                </div>
                <div class="tasks-group">
                    <span class="tasks-group-header" style="color: var(--color-main-green)"
                        >Completed</span
                    >
                    <TasksGroup
                        :tasks="[
                            {
                                description: 'Some docs file',
                                id: 'adsdasadsdasdasdas',
                                priority: 0,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                            {
                                description: 'Some docs file',
                                id: 'dsgagssgsagasd',
                                priority: 1,
                                timestamp: new UnixTimestamp(1766333438),
                                title: 'Some docs file',
                                progress: 100,
                            },
                        ]"
                        @open-task="openTask"
                    />
                </div>
            </div>
        </MainView>
    </div>
    <TaskDialog v-if="selectedTask" :task="selectedTask" @close="closeTask" />
</template>

<style scoped>
.view-sections-container {
    flex-direction: row;
    display: flex;
    flex-grow: 1;
    background-color: transparent;
    height: 0;
}

.tasks-list-wrapper {
    display: flex;
    flex-direction: column;
    padding: 24px;
    gap: 16px;
    overflow-y: auto;
    height: 100%;
}

.tasks-group {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.tasks-group-header {
    color: white;
    font-size: 18px;
}
</style>
