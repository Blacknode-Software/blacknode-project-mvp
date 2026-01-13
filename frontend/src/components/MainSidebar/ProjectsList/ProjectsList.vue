<script setup lang="ts">
import ProjectsSearch from './ProjectsSearch.vue';
import ExpandableProject from './ExpandableProject.vue';
import { onMounted, watch } from 'vue';
import { useCurrentOrganizationStore } from '@/stores/current-organization';
import { useRoute } from 'vue-router';
import type { Project } from '@/shared-types';

const currentOrganizationStore = useCurrentOrganizationStore();

const route = useRoute();

onMounted(() => {
    currentOrganizationStore.requestWholeOrganization(route.params.organization_id as string);
});

watch(
    () => [route.params.organization_id],
    ([newOrganizatonId]) => {
        currentOrganizationStore.requestWholeOrganization(newOrganizatonId as string);
    },
    { immediate: true },
);

function getChannelsForProject(project: Project) {
    return currentOrganizationStore.channels[project.id]!;
}
</script>

<template>
    <div class="projects-list-container">
        <ProjectsSearch />
        <hr class="projects-list-divide" />
        <ul class="projects-list">
            <ExpandableProject
                v-for="project in currentOrganizationStore.projects"
                :project="project"
                :channels="getChannelsForProject(project)"
                :key="project.id"
            />
        </ul>
    </div>
</template>

<style scoped>
.projects-list-container {
    display: flex;
    flex-direction: column;
    gap: 13px;
}

.projects-list-divide {
    margin: 0 8px;
    border-color: var(--color-second-gray);
}

.projects-list {
    list-style-type: none;
    padding: 0;
}
</style>
