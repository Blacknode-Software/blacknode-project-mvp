<script setup lang="ts">
defineProps<{
    sections: string[];
}>();
</script>

<template>
    <div class="container">
        <template v-for="(name, i) in sections" :key="i">
            <span class="text">{{ name }}</span>
            <span v-if="i !== sections.length - 1" class="separator">#</span>
        </template>
    </div>
</template>

<style lang="css" scoped>
.container {
    padding: 24px;
    font-size: 26px;
    display: flex;
    gap: 17px;
    letter-spacing: 16%;
    background-color: transparent;
}

.text {
    color: white;
}

.separator {
    color: var(--color-main-yellow);
}
</style>
