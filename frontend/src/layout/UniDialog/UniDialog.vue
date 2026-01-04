<script setup lang="ts">
const emit = defineEmits<{
    (e: 'close'): void;
}>();
</script>

<template>
    <div class="dialog-background" @click.self="emit('close')">
        <div class="dialog-container">
            <slot />
        </div>
    </div>
</template>

<style lang="css" scoped>
.dialog-background {
    background-color: #00000059;
    backdrop-filter: blur(2px);
    position: fixed;
    height: 100vh;
    width: 100vw;
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
}

.dialog-container {
    border-radius: 4px;
    background-image: url('@/assets/background.svg');
    border: 1px solid oklch(from var(--color-second-gray) l c h / 20%);
    backdrop-filter: blur(5px);
}
</style>
