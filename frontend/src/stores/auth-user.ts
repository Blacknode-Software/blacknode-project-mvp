import { useAuthenticationApiService } from '@/api-services/authentication';
import { UnixTimestamp } from '@/utils';
import { defineStore } from 'pinia';
import { ref, watch } from 'vue';
// import { useRouter } from 'vue-router';

export const useAuthUserStore = defineStore('authUser', () => {
    const apiService = useAuthenticationApiService();
    const errorMessage = ref<string | undefined>();
    const accessToken = ref(localStorage.getItem('accessToken'));
    const accessTokenExpiresAt = ref<string | UnixTimestamp>();

    // const router = useRouter();

    if (typeof accessToken.value !== 'string') {
        // router.push('/login');
    }

    watch(accessToken, (newAccessToken) => {
        if (newAccessToken) {
            localStorage.setItem('accessToken', newAccessToken);
        } else {
            localStorage.removeItem('accessToken');
        }
    });

    async function requestAuthentication(email: string, password: string) {
        const res = await apiService.requestAuthentication({ email, password });

        if (res.isOk()) {
            accessToken.value = res.value.accessToken;
            accessTokenExpiresAt.value = new UnixTimestamp(res.value.accessTokenExpiresAt);
        } else {
            errorMessage.value = 'Something went wrong';
        }
    }

    return { requestAuthentication, accessToken };
});
