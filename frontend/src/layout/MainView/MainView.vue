<template>
    <div class="main-view-container">
        <slot></slot>
    </div>
</template>

<style scoped>
.main-view-container {
    background-color: transparent;
    flex-grow: 1;
    overflow-y: hidden;
}
</style>
