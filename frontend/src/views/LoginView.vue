<script setup lang="ts">
import { useAuthUserStore } from '@/stores/auth-user';
import { reactive, ref } from 'vue';

const authUserStorage = useAuthUserStore();

const loading = ref(false);

const form = reactive({
    email: '',
    password: '',
});

async function onSubmit() {
    loading.value = true;
    authUserStorage.requestAuthentication(form.email, form.password);
}
</script>

<template>
    <div class="page">
        <div class="card">
            <section class="left">
                <h1 class="title">LOGIN</h1>

                <form class="form" @submit.prevent="onSubmit">
                    <label class="label" for="username">Username</label>
                    <input
                        id="emial"
                        v-model.trim="form.email"
                        class="input"
                        type="text"
                        placeholder="Enter your emial"
                        autocomplete="email"
                    />

                    <label class="label" for="password">Password</label>
                    <input
                        id="password"
                        v-model="form.password"
                        class="input"
                        type="password"
                        placeholder="Enter your password"
                        autocomplete="current-password"
                    />

                    <button class="btn" type="submit" :disabled="loading">
                        {{ loading ? 'Logging in...' : 'Login' }}
                    </button>
                </form>
            </section>

            <section class="right">
                <div class="brand">BLACKNODE</div>

                <h2 class="headline">
                    DESIGNED WITH YOUR<br />
                    PROJECT IN MIND
                </h2>

                <div class="footer">BLACKNODE SOFTWARE</div>
            </section>
        </div>
    </div>
</template>

<style scoped>
.page {
    min-height: 100vh;
    display: grid;
    place-items: center;
    padding: 28px;
    background: url('@/assets/background.svg');
    background-size: cover;
}

.card {
    width: min(1100px, 100%);
    height: min(520px, 80vh);
    display: grid;
    grid-template-columns: 1.05fr 1.95fr;
    border-radius: 8px;
    overflow: hidden;
    background: rgba(255, 255, 255, 0.06);
    box-shadow: 0 30px 80px rgba(0, 0, 0, 0.55);
}

.left {
    background: #f7f8fb;
    padding: 64px 56px;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.title {
    text-align: center;
    letter-spacing: 5px;
    font-size: 24px;
    color: #141821;
}

.form {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.label {
    font-size: 12px;
    color: #6b7280;
    letter-spacing: 0.02em;
    margin-top: 12px;
}

.input {
    height: 44px;
    padding: 10px 2px;
    border: none;
    outline: none;
    background: transparent;
    border-bottom: 2px solid rgba(0, 0, 0, 0.12);
    font-size: 14px;
    color: #111827;
}

.input::placeholder {
    color: rgba(17, 24, 39, 0.35);
}

.btn {
    margin-top: 28px;
    height: 46px;
    border: none;
    border-radius: 8px;
    color: #fff;
    font-weight: 600;
    cursor: pointer;
    background: linear-gradient(90deg, #c7a8ff, #5b6cff);
    transition:
        transform 0.08s ease,
        opacity 0.12s ease;
}

.btn:active {
    transform: translateY(1px);
}

.btn:disabled {
    opacity: 0.65;
    cursor: not-allowed;
}

.forgot {
    margin-top: 18px;
    text-align: center;
    font-size: 13px;
    color: rgba(17, 24, 39, 0.45);
    text-decoration: none;
}

.forgot:hover {
    color: rgba(17, 24, 39, 0.7);
    text-decoration: underline;
}

.right {
    position: relative;
    padding: 20px;
    color: rgba(255, 255, 255, 0.92);
    background:
        radial-gradient(800px 520px at 25% 30%, rgba(30, 40, 120, 0.85), transparent 60%),
        radial-gradient(850px 600px at 75% 70%, rgba(140, 60, 170, 0.8), transparent 62%),
        linear-gradient(135deg, #12163a, #2a1b49 55%, #3a1f4f);
}

.brand {
    font-size: 14px;
    letter-spacing: 0.22em;
    opacity: 0.9;
    font-weight: 600;
}

.headline {
    margin: 10px 0 0;
    font-size: 32px;
    line-height: 1.05;
    letter-spacing: 16%;
}

.footer {
    position: absolute;
    right: 42px;
    bottom: 34px;
    font-size: 13px;
    letter-spacing: 0.22em;
    opacity: 0.85;
}

@media (max-width: 900px) {
    .card {
        grid-template-columns: 1fr;
        height: auto;
    }
    .right {
        min-height: 260px;
    }
    .headline {
        font-size: 34px;
    }
}

@media (max-width: 520px) {
    .left,
    .right {
        padding: 42px 26px;
    }
    .headline {
        font-size: 28px;
    }
}
</style>
