import axios from 'axios';

// 전역 Axios 설정
axios.defaults.baseURL = '//localhost:8080';

// 모든 요청에 JWT 토큰을 추가하는 인터셉터
axios.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, error => {
  if (error.response && error.response.status === 401) {
    localStorage.removeItem('token'); // 만료된 토큰 제거
    // 로그인 페이지로 리다이렉트
    window.location.href = '/login';
  }
  return Promise.reject(error);
});

// 토큰 만료를 처리하는 인터셉터
axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('token'); // 만료된 토큰 제거
    }
    return Promise.reject(error);
});

export default axios;
