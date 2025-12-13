<script setup lang="ts">
const props = defineProps<{
    isSelected: boolean;
}>();
</script>

<template>
    <div
        :class="[
            'navigation-select',
            props.isSelected === true ? 'navigation-select-selected' : '',
        ]"
    >
        <slot />
    </div>
</template>

<style scoped>
.navigation-select {
    padding: 3px 8px;
    color: white;
    font-size: 16px;
    cursor: pointer;
    border-radius: 6px;
    user-select: none;
}

.navigation-select:hover {
    background-color: #00000021;
}

.navigation-select-selected {
    color: var(--color-main-green);
}
</style>
