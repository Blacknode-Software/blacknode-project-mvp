<script lang="ts" setup>
import { ref } from 'vue';

const activeOptionIndex = ref(0);

defineProps<{
    options: string[];
}>();

const emit = defineEmits(['click']);
</script>

<template>
    <div class="tabs">
        <button
            v-for="(option, i) in options"
            :key="i"
            :class="['tab', activeOptionIndex === i ? 'is-active' : '']"
            type="button"
            @click="(emit('click', i), (activeOptionIndex = i))"
        >
            {{ option }}
        </button>
    </div>
</template>

<style scoped>
.tabs {
    display: flex;
    gap: 18px;
    padding-left: 4px;
}

.tab {
    background: transparent;
    border: none;
    color: var(--color-second-gray);
    padding: 8px 0;
    font-size: 14px;
    cursor: pointer;
}

.tab.is-active {
    color: white;
    position: relative;
}

.tab.is-active::after {
    content: '';
    position: absolute;
    left: 0;
    bottom: -1px;
    height: 2px;
    width: 100%;
    background: white;
}
</style>
