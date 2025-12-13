<script lang="ts" setup>
import { computed } from 'vue';
import { valueToText } from './utils';

const props = defineProps<{
    value: number;
}>();

const text = computed(() => valueToText(props.value));
</script>

<template>
    <div :class="['container', `color-${text?.toLowerCase()}`]">
        <!-- TODO: Add flag icon -->
        {{ text }}
    </div>
</template>

<style lang="css" scoped>
.container {
    display: flex;
    align-items: center;
    gap: 8px;
}

.color-low {
    color: #2fefc9cc;
}

.color-medium {
    color: #ffbe74e5;
}

.color-high {
    color: #ef2f7ccc;
}
</style>
