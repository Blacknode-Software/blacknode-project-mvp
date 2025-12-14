<script setup lang="ts">
import type { Project } from '@/shared-types/project';

defineProps<{ project: Project }>();
</script>

<template>
    <li class="project-select">{{ project.name }}</li>
</template>

<style scoped>
.project-select {
    padding: 3px 8px;
    color: white;
    font-size: 16px;
    cursor: pointer;
    border-radius: 6px;
    user-select: none;
}

.project-select:hover {
    background-color: #00000021;
}
</style>
