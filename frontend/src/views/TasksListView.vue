<script setup lang="ts">
import { FixedBackground, MainSidebar, TasksGroup, TaskDialog } from '@/components';
import { MainHeader, UniSidebar, MainView } from '@/layout';
import type { Task } from '@/shared-types';
import { useCurrentChannelTasksStore } from '@/stores';
import { useCurrentOrganizationStore } from '@/stores/current-organization';
import { onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';

const currentOrganizationStore = useCurrentOrganizationStore();
const currentChannelTasksStore = useCurrentChannelTasksStore();
const selectedTask = ref<Task | null>(null);
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
    currentChannelTasksStore.requestTasksForChannel(
        route.params.channel_id as string,
        route.params.organization_id as string,
    );
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
            <div class="tasks-list-wrapper">
                <div
                    class="tasks-group"
                    v-for="status in currentChannelTasksStore.statuses"
                    :key="status.id"
                >
                    <span class="tasks-group-header" :style="{ color: status.color }">
                        {{ status.name }}
                    </span>
                    <TasksGroup
                        :tasks="currentChannelTasksStore.getTasksWithStatus(status)"
                        @open-task="openTask"
                    />
                </div>
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
