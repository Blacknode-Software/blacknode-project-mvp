<template>
    <div class="fixed-background"></div>
</template>

<style>
.fixed-background {
    position: fixed;
    inset: 0;
    z-index: -1;
    background-image: url('@/assets/background.svg');
    background-repeat: no-repeat;
    background-size: cover;
    height: 100%;
    width: 100%;
}
</style>
