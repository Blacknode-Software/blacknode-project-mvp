import { createRouter, createWebHistory } from 'vue-router';
import TasksListView from '@/views/TasksListView.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Tasks list',
            component: TasksListView,
        },
    ],
});

export default router;
