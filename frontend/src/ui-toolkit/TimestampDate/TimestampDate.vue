<script lang="ts" setup>
import { computed } from 'vue';
import { UnixTimestamp } from '@/utils';

const props = defineProps<{
    timestmap: number;
    formatter: Intl.DateTimeFormat;
}>();

const date = computed(() =>
    props.formatter.format(new UnixTimestamp(props.timestmap ?? 0).getDate()),
);
</script>

<template>{{ date }}</template>
