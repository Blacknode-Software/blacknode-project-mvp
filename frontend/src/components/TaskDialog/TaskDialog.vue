<script setup lang="ts">
import { onMounted, onBeforeUnmount, computed } from 'vue';
import { UniDialog } from '@/layout';
import { UnixTimestamp } from '@/utils';
import ActivityComment from '../DialogCommon/ActivityComment.vue';
import ActivityEvent from '../DialogCommon/ActivityEvent.vue';
import { PriorityText } from '@/ui-toolkit';
import type { Task } from '@/shared-types';
import { useCurrentChannelTasksStore } from '@/stores';

const currentChannelTasksStore = useCurrentChannelTasksStore();

const props = defineProps<{
    task: Task;
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
</script>

<template>
    <UniDialog @close="emit('close')">
        <div class="dialog">
            <div class="header">
                <span></span>
                <div class="header-right-container">
                    <span class="creation-date">Dec 22 2025</span>
                    <button class="btn" type="button" @click="emit('close')">Close</button>
                </div>
            </div>

            <div class="content">
                <div class="left">
                    <h1 class="title">{{ task.title }}</h1>

                    <div class="meta-grid">
                        <div class="meta-row">
                            <div class="label">Status</div>
                            <div class="value" :style="{ color: status?.color }">
                                {{ status?.name }}
                            </div>
                        </div>

                        <div class="meta-row">
                            <div class="label">Dates</div>
                            <div class="value muted"></div>
                        </div>

                        <div class="meta-row">
                            <div class="label">Assigned</div>
                            <div class="value">
                                <div class="assignees">
                                    <span class="dot a1" />
                                    <span class="dot a2" />
                                    <span class="dot a3" />
                                    <span class="dot a4" />
                                </div>
                            </div>
                        </div>

                        <div class="meta-row">
                            <div class="label">Priority</div>
                            <div class="value">
                                <PriorityText :value="task.priority" />
                            </div>
                        </div>
                    </div>
                    <div class="divider" />
                    <p class="description">
                        {{ task.description ?? 'No description.' }}
                    </p>
                </div>

                <div class="right">
                    <div class="right-header">
                        <div class="right-title">Activity</div>
                    </div>

                    <div class="activity">
                        <ActivityEvent :timestamp="new UnixTimestamp(12312321)"
                            >You changed title: Some docs file</ActivityEvent
                        >
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                        <ActivityComment
                            author-name="Nikodem Cyrzan"
                            content="Test"
                            :timestamp="new UnixTimestamp(12312321)"
                        />
                    </div>
                    <div class="commentbar">
                        <input class="comment-input" placeholder="Write down your comment..." />
                        <button class="send-button" type="button">Send</button>
                    </div>
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
    max-width: 1180px;
    width: 100%;
    max-height: 800px;
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
    display: grid;
    grid-template-columns: 1.35fr 0.65fr;
    min-height: 520px;
}

.left {
    padding: 22px 22px 18px;
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
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 14px 22px;
}

.meta-row {
    display: grid;
    grid-template-columns: 140px 1fr;
    align-items: center;
    gap: 12px;
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
</style>
