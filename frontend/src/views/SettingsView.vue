<script setup lang="ts">
import { FixedBackground, MainSidebar, LocalNavigation, InvitesView } from '@/components';
import { MainHeader, UniSidebar } from '@/layout';
</script>

<template>
    <FixedBackground />
    <MainHeader :sections="['DEPARTMENT OF SECURITY', 'SECTION II']" />
    <div class="view-sections-container">
        <UniSidebar>
            <MainSidebar />
        </UniSidebar>
        <div class="settings-container">
            <h1 class="setting-heading">Settings</h1>
            <LocalNavigation :options="['Invites']" />
            <InvitesView />
        </div>
    </div>
</template>

<style scoped>
.view-sections-container {
    flex-direction: row;
    display: flex;
    flex-grow: 1;
    background-color: transparent;
    height: 0;
}

.setting-heading {
    font-size: 18px;
}

.settings-container {
    color: white;
    padding: 24px;
    width: 100%;
}
</style>
