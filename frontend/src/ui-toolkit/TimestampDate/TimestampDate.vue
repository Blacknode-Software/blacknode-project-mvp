<script lang="ts" setup>
import { computed } from 'vue';
import { UnixTimestamp } from '@/utils';

const props = defineProps<{
    timestmap: UnixTimestamp;
    formatter: Intl.DateTimeFormat;
}>();

const date = computed(() => props.formatter.format(props.timestmap.getDate()));
</script>

<template>{{ date }}</template>
