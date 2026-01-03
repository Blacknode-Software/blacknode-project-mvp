import { createRouter, createWebHistory } from 'vue-router';
import KanbanBoardView from '@/views/KanbanBoardView.vue';
import TasksListView from '@/views/TasksListView.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
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
