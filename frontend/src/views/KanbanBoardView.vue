<script setup lang="ts">
import { FixedBackground, MainSidebar, KanbanColumn, TaskDialog } from '@/components';
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
            <div class="kanban-board">
                <KanbanColumn
                    v-for="status in currentChannelTasksStore.statuses"
                    :key="status.id"
                    :tasks="currentChannelTasksStore.getTasksWithStatus(status)"
                    :title="status.name"
                    :color="status.color"
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
