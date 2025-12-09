<script setup lang="ts">
import { MainHeader, UniSidebar, MainView } from '@/layout';
</script>

<template>
    <MainHeader :sections="['DEPARTMENT OF SECURITY', 'SECTION II']" />
    <div class="view-sections-container">
        <UniSidebar></UniSidebar>
        <MainView></MainView>
    </div>
</template>

<style scoped>
.view-sections-container {
    flex-direction: row;
    display: flex;
    flex-grow: 1;
    background-color: transparent;
}
</style>
