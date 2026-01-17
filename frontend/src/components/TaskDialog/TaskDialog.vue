<script setup lang="ts">
import { onMounted, onBeforeUnmount, computed, ref } from 'vue';
import { UniDialog } from '@/layout';
import { PriorityText, TimestampDate } from '@/ui-toolkit';
import type { Task } from '@/shared-types';
import { useCurrentChannelTasksStore } from '@/stores';
import { UnixTimestamp } from '@/utils';
// import { useTasksApiService } from '@/api-services/tasks';

const currentChannelTasksStore = useCurrentChannelTasksStore();
// const tasksApiService = useTasksApiService();
const localEditMode = ref<boolean>(false);

const dateFormatter = computed(
    () =>
        new Intl.DateTimeFormat('pl-PL', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
        }),
);

const props = defineProps<{
    organizationId: string;
    projectId: string;
    task: Task;
    editMode?: boolean;
}>();

const emit = defineEmits<{
    (e: 'close'): void;
}>();

function onKeyDown(e: KeyboardEvent) {
    if (e.key === 'Escape') emit('close');
}

const status = computed(() => currentChannelTasksStore.getStatusWithId(props.task.id));

onMounted(() => window.addEventListener('keydown', onKeyDown));
onBeforeUnmount(() => window.removeEventListener('keydown', onKeyDown));

function handleEditClick() {
    localEditMode.value = true;
}

function handleSaveClick() {
    localEditMode.value = false;
    console.log(props);
    currentChannelTasksStore.updateTask(props.organizationId, props.projectId, props.task);
}

const isInEditMode = computed(() => localEditMode.value || props.editMode);

const beginAtLocal = computed({
    get: () => props.task.beginAt.toInputString(),
    set: (inputStr: string) => {
        /* eslint-disable  vue/no-mutating-props */
        props.task.beginAt = UnixTimestamp.fromInputString(inputStr);
    },
});
const endAtLocal = computed({
    get: () => props.task.endAt.toInputString(),
    set: (inputStr: string) => {
        /* eslint-disable  vue/no-mutating-props */
        props.task.endAt = UnixTimestamp.fromInputString(inputStr);
    },
});
</script>

<template>
    <UniDialog @close="emit('close')">
        <div class="dialog">
            <div class="header">
                <span></span>
                <div class="header-right-container">
                    <button
                        class="btn"
                        type="button"
                        @click="handleEditClick()"
                        v-if="!isInEditMode"
                    >
                        Edit
                    </button>
                    <button
                        class="btn edit-btn"
                        type="button"
                        @click="handleSaveClick()"
                        v-if="isInEditMode"
                    >
                        Save
                    </button>
                    <button class="btn" type="button" @click="emit('close')">Close</button>
                </div>
            </div>

            <div class="content">
                <div class="left">
                    <h1 v-if="!isInEditMode" class="title">
                        {{ task.title }}
                    </h1>
                    <input
                        v-if="isInEditMode"
                        type="text"
                        class="title-input input"
                        v-model="task.title"
                    />

                    <div class="meta-grid">
                        <div class="meta-row">
                            <div class="label">Status</div>
                            <div
                                v-if="!isInEditMode"
                                class="value value-pill"
                                :style="{ color: status?.color }"
                            >
                                {{ status?.name }}
                            </div>
                            <div v-if="isInEditMode" class="value">
                                <select class="input">
                                    <option value="">test</option>
                                </select>
                            </div>
                        </div>

                        <div class="meta-row">
                            <div class="label">Begins at</div>
                            <div v-if="!isInEditMode" class="value value-pill">
                                <TimestampDate
                                    :formatter="dateFormatter"
                                    :timestmap="task.beginAt"
                                />
                            </div>
                            <div v-if="isInEditMode">
                                <input class="input" type="datetime-local" v-model="beginAtLocal" />
                            </div>
                        </div>

                        <div class="meta-row">
                            <div class="label">Ends at</div>
                            <div v-if="!isInEditMode" class="value value-pill">
                                <TimestampDate :formatter="dateFormatter" :timestmap="task.endAt" />
                            </div>
                            <div v-if="isInEditMode">
                                <input class="input" type="datetime-local" v-model="endAtLocal" />
                            </div>
                        </div>

                        <div class="meta-row">
                            <div class="label">Assigned</div>
                            <div class="value value-pill"></div>
                        </div>

                        <div class="meta-row">
                            <div class="label">Priority</div>
                            <div v-if="!isInEditMode" class="value value-pill">
                                <PriorityText :value="task.priority" />
                            </div>
                            <div v-if="isInEditMode">
                                <select class="input" v-model="task.priority">
                                    <option :value="0">Low</option>
                                    <option :value="1">Medium</option>
                                    <option :value="2">High</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="divider" />
                    <p v-if="!isInEditMode" class="description">
                        {{ task.description }}
                    </p>
                    <textarea
                        v-if="isInEditMode"
                        class="input"
                        rows="10"
                        v-model="task.description"
                    ></textarea>
                </div>
            </div>
        </div>
    </UniDialog>
</template>

<style scoped>
.overlay {
    position: fixed;
    inset: 0;
    z-index: 1000;
}

.dialog {
    width: min(1180px, calc(100vw - 40px));
    display: flex;
    flex-direction: column;
}

.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px 18px;
    border-bottom: 1px solid oklch(from var(--color-second-gray) l c h / 20%);
}

.header {
    display: flex;
    gap: 10px;
    color: rgba(255, 255, 255, 0.75);
    font-size: 14px;
}

.header-right-container {
    display: flex;
    align-items: center;
    gap: 12px;
}

.creation-date {
    color: rgba(255, 255, 255, 0.65);
    font-size: 14px;
}

.content {
    min-height: 520px;
}

.left {
    padding: 22px;
    border-right: 1px solid rgba(255, 255, 255, 0.08);
    overflow: auto;
    display: flex;
    flex-direction: column;
}

.right {
    position: relative;
    overflow: auto;
    display: flex;
    flex-direction: column;
}

.title {
    margin: 0 0 14px;
    font-size: 36px;
    font-weight: 700;
    color: rgba(255, 255, 255, 0.92);
}

.meta-grid {
    width: fit-content;
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 14px 22px;
}

.meta-row {
    display: flex;
    gap: 10px;
    align-items: center;
}

.label {
    color: rgba(255, 255, 255, 0.7);
    font-size: 14px;
}

.value {
    color: rgba(255, 255, 255, 0.88);
    font-size: 14px;
}

.muted {
    opacity: 0.6;
}
.arrow {
    margin: 0 8px;
    opacity: 0.6;
}

.pill {
    display: inline-flex;
    align-items: center;
    padding: 6px 10px;
    border-radius: 6px;
    background: rgba(255, 255, 255, 0.08);
    border: 1px solid rgba(255, 255, 255, 0.1);
    font-weight: 600;
    letter-spacing: 0.2px;
}

.assignees {
    display: inline-flex;
    gap: 6px;
}
.dot {
    width: 14px;
    height: 14px;
    border-radius: 999px;
    border: 1px solid rgba(0, 0, 0, 0.25);
}

.divider {
    height: 1px;
    background: rgba(255, 255, 255, 0.08);
    margin: 18px 0;
}

.description {
    margin: 0;
    color: rgba(255, 255, 255, 0.7);
    line-height: 1.55;
    flex-grow: 1;
    overflow-y: auto;
}

.dropzone {
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 6px;
    padding: 14px;
    background: rgba(0, 0, 0, 0.18);
}

.link {
    text-decoration: underline;
    cursor: pointer;
    opacity: 0.8;
}

.right-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px 10px;
    border-bottom: 1px solid oklch(from var(--color-second-gray) l c h / 20%);
}

.right-title {
    font-weight: 700;
    font-size: 18px;
    color: rgba(255, 255, 255, 0.86);
}

.right-actions {
    display: flex;
    gap: 8px;
}

.activity {
    position: relative;
    display: flex;
    flex-direction: column;
    gap: 12px;
    overflow-y: auto;
    padding: 12px;
}

.activity-body {
    font-size: 14px;
    line-height: 1.4;
}

.commentbar {
    bottom: 0;
    display: grid;
    grid-template-columns: 1fr auto;
    align-items: center;
    border-top: 1px solid rgba(255, 255, 255, 0.08);
}

.comment-input {
    width: 100%;
    padding: 10px 12px;
    border: none;
    color: var(--color-second-gray);
    outline: none;
    background: transparent;
}

.btn {
    border-radius: 6px;
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(255, 255, 255, 0.06);
    color: rgba(255, 255, 255, 0.82);
    padding: 8px 12px;
    cursor: pointer;
}
.btn:hover .send-button:hover {
    background: rgba(255, 255, 255, 0.09);
}
.btn.ghost {
    background: transparent;
}

.edit-btn {
    background-color: oklch(69.137% 0.14992 158.536);
}

.send-button {
    background: rgba(255, 255, 255, 0.06);
    color: white;
    border: none;
    display: block;
    cursor: pointer;
    height: 100%;
    width: 100%;
    padding: 0 10px;
}

.value-pill,
.input {
    padding: 5px 10px;
    border-radius: 8px;
    min-height: 32px;
    min-width: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-family: inherit;
}

.title-input {
    font-size: 36px;
    margin-bottom: 14px;
    font-weight: 700;
}

.input {
    border: none;
    color: white;
    background-color: oklch(0 0 0 / 30%);
}

textarea.input {
    resize: vertical;
}
</style>
