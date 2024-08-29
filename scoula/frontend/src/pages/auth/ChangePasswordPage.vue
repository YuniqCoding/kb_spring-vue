<script setup>
import { computed, reactive, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';
import authApi from '@/api/authApi';

const router = useRouter();
const auth = useAuthStore();

const changePassword = reactive({
  username: auth.username,
  oldPassword: '',
  newPassword: '',
  newPassword2: '',
});

const disableSubmit = computed(
  () =>
    !changePassword.oldPassword ||
    !changePassword.newPassword ||
    !changePassword.newPassword2
);

const error = ref('');

// 비밀번호 입력 시 오류 메시지 초기화
const inputPassword = () => (error.value = '');
const resetError = () => (error.value = '');
const onSubmit = async () => {
  if (changePassword.newPassword != changePassword.newPassword2) {
    error.value = '새 비밀번호가 일치하지 않습니다.';
    return;
  }

  try {
    // 비밀번호 변경 요청을 백엔드(서버)로 전송
    await authApi.changePassword(changePassword);
    alert('비밀번호를 수정했습니다.');
    router.push({ name: 'profile' }); // 비밀번호 수정 후 프로필 페이지로 이동
  } catch (e) {
    error.value = e.response.data;
  }
};
</script>

<template>
  <div class="mt-5 mx-auto" style="width: 500px">
    <h1 class="my-5">
      <i class="fa-solid fa-lock"></i>
      비밀번호 변경
    </h1>
    <form @submit.prevent="onSubmit">
      <div class="mb-3">
        <!-- 이전 비밀번호 입력 필드 -->
        <label for="password" class="form-label">
          <i class="fa-solid fa-lock"></i>
          이전 비밀번호:
        </label>
        <!-- input 이벤트로 입력할때마다 에러 메시지 초기화 -->
        <input
          type="password"
          class="form-control"
          placeholder="이전 비밀번호"
          v-model="changePassword.oldPassword"
          @input="inputPassword"
        />
      </div>
      <!-- 새 비밀번호 입력 필드 -->
      <div class="mb-3">
        <label for="password" class="form-label">
          <i class="fa-solid fa-lock"></i>
          새 비밀번호:
        </label>
        <input
          type="password"
          class="form-control"
          placeholder="새 비밀번호"
          v-model="changePassword.newPassword"
          @input="resetError"
        />
      </div>
      <!-- 새 비밀번호 확인 입력 필드 -->
      <div class="mb-3">
        <label for="password" class="form-label">
          <i class="fa-solid fa-lock"></i>
          새 비밀번호 확인:
        </label>
        <input
          type="password"
          class="form-control"
          placeholder="새 비밀번호 확인"
          v-model="changePassword.newPassword2"
          @input="resetError"
        />
      </div>
      <!-- 에러 메시지 표시 -->
      <div v-if="error" class="text-danger">{{ error }}</div>

      <!-- 제출 버튼 -->
      <div class="text-center">
        <button type="submit" class="btn btn-primary mt-4" :disabled="disableSubmit">
          <i class="fa-solid fa-check"></i> 확인
        </button>
      </div>
    </form>
  </div>
</template>
