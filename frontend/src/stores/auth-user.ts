import { useAuthenticationApiService } from '@/api-services/authentication';
import { UnixTimestamp } from '@/utils';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useAuthUserStore = defineStore('authUser', () => {
    const apiService = useAuthenticationApiService();
    const isAuthenticated = ref(false);
    const errorMessage = ref<string | undefined>();
    const accessToken = ref<string | undefined>();
    const accessTokenExpiresAt = ref<string | UnixTimestamp>();

    async function requestAuthentication(email: string, password: string) {
        const res = await apiService.requestAuthentication({ email, password });

        if (res.isOk()) {
            accessToken.value = res.value.accessToken;
            accessTokenExpiresAt.value = new UnixTimestamp(res.value.accessTokenExpiresAt);
            isAuthenticated.value = true;
        } else {
            errorMessage.value = 'Something went wrong';
        }
    }

    return { requestAuthentication };
});
