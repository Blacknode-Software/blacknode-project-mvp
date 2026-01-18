<script setup lang="ts">
import { FixedBackground, MainSidebar, KanbanColumn, TaskDialog } from '@/components';
import { MainHeader, UniSidebar, MainView } from '@/layout';
import type { Task } from '@/shared-types';
import { useCurrentChannelTasksStore } from '@/stores';
import { useCurrentOrganizationStore } from '@/stores/current-organization';
import { UnixTimestamp } from '@/utils';
import { onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';

const currentOrganizationStore = useCurrentOrganizationStore();
const currentChannelTasksStore = useCurrentChannelTasksStore();
const selectedTask = ref<Task | null>({
    title: 'test',
    description: 'test',
    beginAt: new UnixTimestamp(123123),
    endAt: new UnixTimestamp(12312321),
    id: 'adasdasdas',
    priority: 0,
    statusId: 'asdasdasd',
});
const taskEditMode = ref<boolean>(false);
const organizationId = ref<string>();
const projectId = ref<string>();

function openTask(task: Task) {
    selectedTask.value = task;
}

function closeTask() {
    selectedTask.value = null;
    taskEditMode.value = false;
}

const route = useRoute();

onMounted(() => {
    const orgId = route.params.organization_id as string;
    const chnlId = route.params.channel_id as string;

    currentChannelTasksStore.requestTasksForChannel(chnlId, orgId);

    organizationId.value = orgId;
    projectId.value = currentOrganizationStore.findProjectWithChannel(chnlId)!;
});

watch(
    () => [route.params.organization_id, route.params.channel_id],
    ([newOrganizatonId, newChannelId]) => {
        const orgId = newOrganizatonId as string;
        const chnlId = newChannelId as string;

        currentChannelTasksStore.requestTasksForChannel(chnlId, orgId);

        organizationId.value = orgId;
        projectId.value = currentOrganizationStore.findProjectWithChannel(chnlId)!;
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
    <TaskDialog
        v-if="selectedTask"
        :edit-mode="taskEditMode"
        :task="selectedTask"
        :organization-id="organizationId!"
        :project-id="projectId!"
        @close="closeTask"
    />
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
