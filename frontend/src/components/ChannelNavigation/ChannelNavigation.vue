<script setup lang="ts">
import { useRouter } from 'vue-router';

const router = useRouter();

const routerUrl = router.currentRoute.value;

function applyStyle(expectedRoute: string) {
    console.log(routerUrl.path.includes(expectedRoute));
    return [routerUrl.path.includes(expectedRoute) ? 'link-selected' : ''];
}
</script>

<template>
    <div class="channel-navigation">
        <router-link to="/list/1/1" :class="applyStyle('list')">List</router-link>
        <router-link to="/kanban/1/1" :class="applyStyle('kanban')">Board</router-link>
        <router-link to="/settings/1/1" :class="applyStyle('settings')">Settings</router-link>
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

.channel-navigation a.link-selected {
    color: var(--color-main-green);
}
</style>
