<template>
    <div class="dialog-background">
        <div class="dialog-container">
            <slot />
        </div>
    </div>
</template>

<style lang="css" scoped>
.dialog-background {
    background-color: #00000059;
    backdrop-filter: blur(10px);
    position: fixed;
    height: 100vh;
    width: 100vw;
    display: flex;
    align-items: center;
    justify-content: center;
}

.dialog-container {
    border-radius: 4px;
    background-color: #00000040;
    border: 1px solid var(--color-second-gray);
    backdrop-filter: blur(10px);
}
</style>
