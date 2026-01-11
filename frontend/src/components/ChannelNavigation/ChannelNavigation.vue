<script setup lang="ts"></script>

<template>
    <div class="channel-navigation">
        <router-link to="/list/1/1">List</router-link>
        <router-link to="/kanban/1/1">Board</router-link>
        <router-link to="/settings/1/1">Settings</router-link>
    </div>
</template>

<style scoped>
.channel-navigation {
    display: flex;
    gap: 18px;
    padding: 18px 24px;
    background-color: oklch(0 0 0 / 10%);
}

.channel-navigation a {
    color: white;
    text-decoration: none;
    font-size: 16px;
}
</style>
