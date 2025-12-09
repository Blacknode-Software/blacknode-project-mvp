<script setup lang="ts">
import type { ButtonSize, ButtonVariant } from './types';

defineProps<{
    size?: ButtonSize;
    variant?: ButtonVariant;
}>();
</script>

<template>
    <button :class="['button', `size-${size ?? 'medium'}`, `variant-${variant ?? 'filled'}`]">
        <slot></slot>
    </button>
</template>

<style lang="css" scoped>
.button {
    display: flex;
    gap: 12px;
    padding: 4px 12px;
    border-radius: 2px;
    cursor: pointer;
    border: none;
}

.size-small {
    font-size: 12px;
}

.size-medium {
    font-size: 14px;
}

.size-big {
    font-size: 16px;
}

.variant-filled {
    background-color: #00000059;
    color: #7a7a90;
}

.variant-filled:hover {
    color: #7a7a904d;
}

.variant-text {
    background-color: transparent;
    color: #7a7a90;
}

.variant-text:hover {
    background-color: #ffffff19;
    color: #cbcbd6;
}
</style>
