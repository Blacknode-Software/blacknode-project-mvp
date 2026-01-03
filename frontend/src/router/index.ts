import { createRouter, createWebHistory } from 'vue-router';
import KanbanBoardView from '@/views/KanbanBoardView.vue';
import TasksListView from '@/views/TasksListView.vue';
import LoginView from '@/views/LoginView.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/login',
            name: 'Login',
            component: LoginView,
        },
        {
            path: '/list',
            name: 'Tasks list',
            component: TasksListView,
        },
        {
            path: '/kanban',
            name: 'Tasks Kanban board',
            component: KanbanBoardView,
        },
    ],
});

export default router;
