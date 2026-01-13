<script lang="ts" setup>
import router from '@/router';
import { useAuthUserStore } from '@/stores/auth-user';

const authStore = useAuthUserStore();

if (!authStore.accessToken) {
    router.push('/login');
}
</script>
