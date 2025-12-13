<script setup lang="ts"></script>

<template>
    <div class="view-container">
        <RouterView />
    </div>
</template>

<style scoped>
.view-container {
    position: relative;
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 100%;
}
</style>
