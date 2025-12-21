<script setup lang="ts">
import type { Project as ProjectType } from '@/shared-types/project';
import ProjectsSearch from './ProjectsSearch.vue';
import ExpandableProject from './ExpandableProject.vue';

const projects = [
    {
        uuid: 'asdasdsa',
        name: 'Projekt 1',
        channels: [
            {
                name: 'Kanał 1',
                uuid: 'asddsad',
            },
            {
                name: 'Kanał 2',
                uuid: 'adfgdfgdfgdfgdf',
            },
        ],
    },
    {
        uuid: 'asdasdsa22',
        name: 'Projekt 2',
        channels: [
            {
                name: 'Kanał 1',
                uuid: 'asddsad22',
            },
            {
                name: 'Kanał 2',
                uuid: 'adfgdfgdfgdfgdf',
            },
            {
                name: 'Kanał 3',
                uuid: 'adfgdfgdfgdfgdf',
            },
            {
                name: 'Kanał 4',
                uuid: 'adfgdfgdfgdfgdf',
            },
            {
                name: 'Kanał 5',
                uuid: 'adfgdfgdfgdfgdf',
            },
            {
                name: 'Kanał 6',
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
