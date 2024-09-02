<script setup>
import { ref, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import boardApi from '@/api/boardApi';

const cr = useRoute();
const router = useRouter();

const no = cr.params.no; // 경로변수 가져오기
const article = reactive({});
const attachments = ref([]);
const orgArticle = ref({});
const files = ref(null);

const back = () => {
  router.push({ name: 'board/detail', params: { no } });
};

// 첨부 파일 삭제
const removeFile = async (no, filename) => {
  if (!confirm(filename + '을 삭제할까요?')) return;

  await boardApi.deleteAttachment(no); // 첨부파일 삭제

  // 첨부파일의 no 값을 비교해서 우리가 찾는 첨부파일의 인덱스를 가져온다
  const ix = attachments.value.findIndex((f) => f.no === no);
  attachments.value.splice(ix, 1); // splice(시작 인덱스, 몇개 삭제할지 갯수, (삽입할 아이템))
};

// 수정된 게시글 제출
const submit = async () => {
  if (!confirm('수정할까요?')) return;

  if (files.value.files.length > 0) {
    article.files = files.value.files;
  }

  await boardApi.update(article);
  // 해당 url에서 ? 뒤의 문장을 query로 가져온다
  router.push({ name: 'board/detail', params: { no }, query: cr.query });
};

// 게시글 데이터 초기화
const reset = () => {
  // orgArticle 값에 기존 게시글 데이터 저장해둔후  reset 시 다시 불러온다
  article.no = orgArticle.value.no;
  article.title = orgArticle.value.title;
  article.writer = orgArticle.value.writer;
  article.content = orgArticle.value.content;
  console.log(article);
};

// 로드할때 기존 게시글의 데이터를 미리 채워둔다
const load = async () => {
  const data = await boardApi.get(no);
  orgArticle.value = { ...data };
  attachments.value = data.attachments;
  reset();
};

load();
</script>

<template>
  <h1>게시글 수정 페이지</h1>
</template>
