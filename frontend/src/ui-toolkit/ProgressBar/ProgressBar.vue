<script setup lang="ts">
import { lerpColor } from '@/utils';
import { computed } from 'vue';

const props = defineProps({
    value: Number,
    maxValue: Number,
});

const colors = computed(() => ({
    start:
        '#' +
        lerpColor(0x238dde, 0x23dea0, (props.value || 0) / (props.maxValue || 1)).toString(16),
    end:
        '#' +
        lerpColor(0x6123de, 0x23b9de, (props.value || 0) / (props.maxValue || 1)).toString(16),
}));
</script>

<template>
    <div class="container">
        <slot name="left" class="content" />
        <div class="progress-container">
            <div
                class="progress-value"
                :style="{
                    width: `${((value || 0) / (maxValue || 1)) * 100}%`,
                    background: `linear-gradient(90deg, ${colors.start} 0%, ${colors.end} 100%)`,
                }"
            ></div>
        </div>
        <slot name="right" class="content" />
    </div>
</template>

<style lang="css" scoped>
.container {
    position: relative;
    display: flex;
    align-items: center;
    gap: 17px;
    width: 100%;
}

.progress-container {
    overflow: hidden;
    flex-grow: 1;
    border-radius: 2px;
    height: 6px;
    position: relative;
    background-color: rgba(0, 0, 0, 0.2);
}

.progress-value {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
}
</style>
