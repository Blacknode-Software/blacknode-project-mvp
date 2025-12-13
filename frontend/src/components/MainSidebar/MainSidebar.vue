<script setup lang="ts">
import GeneralNavigation from './GeneralNavigation/GeneralNavigation.vue';
import ProjectsList from './ProjectsList/ProjectsList.vue';
</script>

<template>
    <div class="main-sidebar-container">
        <GeneralNavigation />
        <ProjectsList />
    </div>
</template>

<style scoped>
.main-sidebar-container {
    position: absolute;
    inset: 0;
    background-color: #0000001a;
    padding: 20px 16px;
    display: flex;
    flex-direction: column;
    gap: 16px;
}
</style>
