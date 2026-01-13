<script lang="ts" setup>
import UniDialog from '@/layout/UniDialog/UniDialog.vue';
</script>

<template>
    <UniDialog>
        <div class="header">Invitation</div>
        <div class="dialog-internal-container">
            <div class="email-title">Email</div>
            <div class="email-description">Who should be invited</div>
            <input class="email-input" type="text" placeholder="some.fancy@email.com" />
            <div class="buttons-container">
                <button class="button button-cancel">Nevermind</button>
                <button class="button button-send">Send invitation</button>
            </div>
        </div>
    </UniDialog>
</template>

<style scoped>
.header {
    padding: 10px;
    background-color: oklch(0 0 0 / 10%);
    color: var(--color-second-gray);
}

.dialog-internal-container {
    padding: 32px;
    width: 500px;
}

.email-title {
    color: white;
    font-size: 16px;
}

.email-description {
    color: var(--color-second-gray);
    font-size: 14px;
}

.buttons-container {
    margin-top: 32px;
    display: flex;
    gap: 10px;
    align-items: center;
    justify-content: center;
}

.button {
    padding: 8px 16px;
    border-radius: 8px;
    cursor: pointer;
}

.email-input {
    margin-top: 16px;
    border: 1px solid var(--color-second-gray);
    padding: 8px 16px;
    background-color: transparent;
    border-radius: 8px;
    width: 100%;
    color: white;
}

.email-input::placeholder {
    color: var(--color-second-gray);
}

.button-cancel {
    background-color: transparent;
    border: 1px solid var(--color-second-gray);
    color: var(--color-second-gray);
}

.button-send {
    background-color: #27b778;
    border: none;
    color: white;
}
</style>
