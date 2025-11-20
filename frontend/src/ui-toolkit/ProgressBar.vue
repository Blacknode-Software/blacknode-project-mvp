<script setup lang="ts">
defineProps({
    value: Number,
    maxValue: Number,
});

const startColor = '#2A7B9B';
const endColor = '#EDDD53';
</script>

<template>
    <div class="container">
        <slot name="left" class="content" />
        <div class="progress-container">
            <div
                class="progress-value"
                :style="{
                    width: `${((value || 0) / (maxValue || 1)) * 100}%`,
                    background: `linear-gradient(90deg, ${startColor} 0%, ${endColor} 100%)`,
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
