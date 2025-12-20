<script setup lang="ts">
import type { Project } from '@/shared-types/project';
import { ref } from 'vue';

const props = defineProps<{ project: Project; forceExpand?: boolean }>();

const isExpanded = ref(props.forceExpand);

const handleExpand = () => {
    isExpanded.value = !isExpanded.value;
    console.log(isExpanded.value);
};
</script>

<template>
    <li>
        <div class="project-button" :onclick="handleExpand">
            {{ project.name }}
        </div>
        <ul :class="[isExpanded! ? 'project-drawer-closed' : '', 'project-drawer']">
            <li v-for="channel in project.channels" :key="channel.uuid">
                <div class="channel-button">
                    {{ channel.name }}
                </div>
            </li>
        </ul>
    </li>
</template>

<style scoped>
.project-button {
    padding: 3px 8px;
    cursor: pointer;
    color: white;
    font-size: 16px;
    border-radius: 6px;
    user-select: none;
}

.project-button:hover {
    background-color: #00000021;
}

.project-drawer {
    interpolate-size: allow-keywords;
    list-style-type: none;
    padding: 0;
    padding-left: 10px;
    overflow: hidden;
    transition: height 100ms ease-in-out;
}

.project-drawer-closed {
    height: 0px;
}

.channel-button {
    padding: 3px 8px;
    cursor: pointer;
    color: white;
    font-size: 16px;
    border-radius: 6px;
    user-select: none;
}

.channel-button:hover {
    background-color: #00000021;
}
</style>
