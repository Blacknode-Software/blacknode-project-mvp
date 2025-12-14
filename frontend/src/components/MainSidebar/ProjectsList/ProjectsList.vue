<script setup lang="ts">
import type { Project as ProjectType } from '@/shared-types/project';
import ProjectsSearch from './ProjectsSearch.vue';
import ExpandableProject from './ExpandableProject.vue';

const projects = [
    {
        uuid: 'asdasdsa',
        name: 'asdasd',
        channels: [
            {
                name: 'asdasdasasdasd',
                uuid: 'asddsad',
            },
            {
                name: 'dfgdfgdfgdf',
                uuid: 'adfgdfgdfgdfgdf',
            },
        ],
    },
    {
        uuid: 'asdasdsa22',
        name: 'asdasd22',
        channels: [
            {
                name: 'asdasdasasdasd',
                uuid: 'asddsad',
            },
            {
                name: 'dfgdfgdfgdf',
                uuid: 'adfgdfgdfgdfgdf',
            },
        ],
    },
] as ProjectType[];
</script>

<template>
    <div class="projects-list-container">
        <ProjectsSearch />
        <hr class="projects-list-divide" />
        <ul class="projects-list">
            <ExpandableProject v-for="project in projects" :project="project" :key="project.uuid" />
        </ul>
    </div>
</template>

<style scoped>
.projects-list-container {
    display: flex;
    flex-direction: column;
    gap: 16px;
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
