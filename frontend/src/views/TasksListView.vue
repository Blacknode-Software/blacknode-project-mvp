<script setup lang="ts">
import { FixedBackground, MainSidebar, TasksGroup, TaskDialog } from '@/components';
import { MainHeader, UniSidebar, MainView } from '@/layout';
import type { Task } from '@/shared-types';
import { useCurrentChannelTasksStore } from '@/stores';
import { onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';

const currentChannelTasksStore = useCurrentChannelTasksStore();
const selectedTask = ref<Task | null>(null);

function openTask(task: Task) {
    selectedTask.value = task;
}

function closeTask() {
    selectedTask.value = null;
}

const route = useRoute();

onMounted(() => {
    currentChannelTasksStore.requestTasksForChannel(
        route.params.channel_id as string,
        route.params.organization_id as string,
    );
});

watch(
    () => [route.params.organization_id, route.params.channel_id],
    ([newOrganizatonId, newChannelId]) => {
        currentChannelTasksStore.requestTasksForChannel(
            newChannelId as string,
            newOrganizatonId as string,
        );
    },
    { immediate: true },
);
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
                <div
                    class="tasks-group"
                    v-for="status in currentChannelTasksStore.statuses"
                    :key="status.id"
                >
                    <span class="tasks-group-header" :style="{ color: status.color }">{{
                        status.id
                    }}</span>
                    <TasksGroup
                        :tasks="currentChannelTasksStore.getTasksWithStatus(status)"
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
