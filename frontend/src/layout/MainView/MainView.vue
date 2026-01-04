<script setup lang="ts">
import { ChannelNavigation } from '@/components';
</script>

<template>
    <div class="main-view-container">
        <ChannelNavigation />
        <slot></slot>
    </div>
</template>

<style scoped>
.main-view-container {
    background-color: transparent;
    flex-grow: 1;
    overflow-y: hidden;
}
</style>
