// 김동혁

/**
 * 로컬 스토리지에서 저장된 JWT 토큰을 가져오는 함수
 * @returns {string | null} JWT 토큰 문자열 또는 null
 */
export function getToken() {
  return localStorage.getItem('token');
}

/**
 * JWT 토큰을 디코딩하여 페이로드 데이터를 추출하는 함수
 * @param {string} token - 디코딩할 JWT 토큰
 * @returns {object} 디코딩된 페이로드 데이터 (JSON 객체 형태)
 */
export function decodeJWT(token) {
  // JWT 토큰의 두 번째 부분(페이로드)을 가져옴
  const base64Url = token.split('.')[1];
  // URL 안전한 문자열을 일반적인 Base64 형식으로 변환
  const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  // Base64 형식의 문자열을 디코딩하고, UTF-8 형식으로 변환
  const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));
  // 디코딩된 JSON 문자열을 객체로 변환하여 반환
  return JSON.parse(jsonPayload);
}
