<script setup lang="ts">
import SelectOption from './SelectOption.vue';
</script>

<template>
    <div>
        <SelectOption :is-selected="false">Dashboard</SelectOption>
        <SelectOption :is-selected="false">Notifications</SelectOption>
        <SelectOption :is-selected="true">Settings</SelectOption>
    </div>
</template>
