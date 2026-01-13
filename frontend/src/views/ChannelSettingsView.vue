<script setup lang="ts">
import { MainHeader, UniSidebar, MainView } from '@/layout';
import { FixedBackground, LocalNavigation, MainSidebar } from '@/components';
import { ref } from 'vue';

const channelName = ref('Section II');
const iconColor = ref('#5882DD');

function onSave() {
    // TODO: Send changes request
}

function onCancel() {}

function onDelete() {}
</script>

<template>
    <FixedBackground />
    <MainHeader :sections="['DEPARTMENT OF SECURITY', 'SECTION II']" />
    <div class="view-sections-container">
        <UniSidebar>
            <MainSidebar />
        </UniSidebar>
        <MainView>
            <div class="cs-root">
                <div class="cs-topbar">
                    <div class="cs-title">Channel Settings</div>
                </div>

                <LocalNavigation :options="['Channel Profile', 'Members']" />

                <div class="cs-panel">
                    <button class="cs-danger" type="button" @click="onDelete">
                        Delete Channel
                    </button>

                    <div class="cs-form">
                        <div class="cs-row">
                            <div class="cs-label">
                                <div class="cs-label-title">Name</div>
                                <div class="cs-label-sub">How this place should be called</div>
                            </div>

                            <div class="cs-field">
                                <input v-model="channelName" class="cs-input" type="text" />
                            </div>
                        </div>

                        <div class="cs-row">
                            <div class="cs-label">
                                <div class="cs-label-title">Icon Color</div>
                                <div class="cs-label-sub">
                                    The accent color used for this channel
                                </div>
                            </div>

                            <div class="cs-field cs-color-field">
                                <label
                                    class="cs-color-swatch"
                                    :style="{ backgroundColor: iconColor }"
                                >
                                    <input
                                        v-model="iconColor"
                                        class="cs-color-native"
                                        type="color"
                                    />
                                </label>

                                <div class="cs-color-hex">
                                    <span class="cs-hash">#</span>
                                    <input
                                        class="cs-hex-input"
                                        :value="iconColor.replace('#', '').toUpperCase()"
                                        @input="
                                            iconColor =
                                                '#' +
                                                ($event.target as HTMLInputElement).value
                                                    .replace(/[^0-9a-fA-F]/g, '')
                                                    .slice(0, 6)
                                        "
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cs-actions">
                    <button class="cs-btn ghost" type="button" @click="onCancel">Cancel</button>
                    <button class="cs-btn primary" type="button" @click="onSave">
                        Save changes
                    </button>
                </div>
            </div>
        </MainView>
    </div>
</template>

<style scoped>
.cs-root {
    background: transparent;
    padding: 26px 28px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    gap: 14px;
}

.cs-topbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 16px;
}

.cs-title {
    display: inline-flex;
    align-items: center;
    gap: 10px;
    letter-spacing: 0.2px;
    font-size: 18px;
    color: white;
}

.cs-gear {
    opacity: 0.9;
    transform: translateY(-1px);
}

.cs-search {
    position: relative;
    width: 360px;
    max-width: 46vw;
}

.cs-search-input {
    width: 100%;
    background: rgba(15, 17, 24, 0.45);
    border: 1px solid rgba(255, 255, 255, 0.08);
    border-radius: 10px;
    padding: 10px 38px 10px 14px;
    color: rgba(255, 255, 255, 0.88);
    outline: none;
}

.cs-search-input::placeholder {
    color: rgba(255, 255, 255, 0.38);
}

.cs-search-icon {
    position: absolute;
    right: 12px;
    top: 50%;
    transform: translateY(-50%);
    opacity: 0.55;
    pointer-events: none;
}

.cs-tabs {
    display: flex;
    gap: 18px;
    padding-left: 4px;
}

.cs-tab {
    background: transparent;
    border: none;
    color: rgba(255, 255, 255, 0.55);
    padding: 8px 0;
    font-size: 14px;
    cursor: pointer;
}

.cs-tab.is-active {
    color: white;
    position: relative;
}

.cs-tab.is-active::after {
    content: '';
    position: absolute;
    left: 0;
    bottom: -1px;
    height: 2px;
    width: 100%;
    background: white;
}

.cs-panel {
    position: relative;
    flex: 1;
    min-height: 0;
}

.cs-danger {
    position: absolute;
    right: 0;
    top: 0;
    display: inline-flex;
    align-items: center;
    gap: 10px;
    padding: 10px 14px;
    border-radius: 10px;
    background: rgba(255, 63, 110, 0.07);
    border: 1px solid rgba(255, 63, 110, 0.55);
    color: rgba(255, 120, 150, 0.95);
    cursor: pointer;
}

.cs-shield {
    opacity: 0.9;
}

.cs-form {
    display: flex;
    flex-direction: column;
    gap: 22px;
    max-width: 820px;
}

.cs-row {
    display: grid;
    grid-template-columns: 360px 1fr;
    gap: 18px;
    align-items: center;
}

.cs-label-title {
    font-size: 16px;
    color: white;
}

.cs-label-sub {
    font-size: 14px;
    color: var(--color-second-gray);
}

.cs-field {
    display: flex;
    align-items: center;
    gap: 12px;
}

.cs-input {
    width: min(520px, 100%);
    background: rgba(15, 17, 24, 0.42);
    border: 1px solid rgba(255, 255, 255, 0.08);
    border-radius: 10px;
    padding: 10px 12px;
    color: rgba(255, 255, 255, 0.88);
    outline: none;
}

.cs-input:focus,
.cs-search-input:focus,
.cs-hex-input:focus {
    border-color: rgba(120, 130, 255, 0.55);
}

.cs-color-field {
    gap: 14px;
}

.cs-color-swatch {
    width: 34px;
    height: 34px;
    border-radius: 8px;
    border: 1px solid rgba(255, 255, 255, 0.14);
    overflow: hidden;
    cursor: pointer;
    position: relative;
}

.cs-color-native {
    position: absolute;
    inset: 0;
    opacity: 0;
    cursor: pointer;
}

.cs-color-hex {
    display: flex;
    align-items: center;
    gap: 8px;
    background: rgba(15, 17, 24, 0.42);
    border: 1px solid rgba(255, 255, 255, 0.08);
    border-radius: 10px;
    padding: 8px 10px;
}

.cs-hash {
    color: white;
}

.cs-hex-input {
    width: 90px;
    background: transparent;
    border: none;
    outline: none;
    color: white;
    height: fit-content;
}

.cs-actions {
    display: flex;
    gap: 14px;
    justify-content: end;
}

.cs-btn {
    padding: 10px 16px;
    border-radius: 10px;
    font-weight: 700;
    cursor: pointer;
    border: 1px solid transparent;
}

.cs-btn.ghost {
    background: rgba(15, 17, 24, 0.22);
    border-color: rgba(255, 255, 255, 0.08);
    color: rgba(255, 255, 255, 0.65);
}

.cs-btn.primary {
    background: rgba(90, 110, 255, 0.92);
    color: rgba(255, 255, 255, 0.95);
}

/* TODO: Move to App.vue */
.view-sections-container {
    flex-direction: row;
    display: flex;
    flex-grow: 1;
    background-color: transparent;
    height: 0;
}
</style>
