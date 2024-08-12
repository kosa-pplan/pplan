const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',  // 백엔드 서버가 실행 중인 포트
        changeOrigin: true,
        pathRewrite: { '^/api': '' },
      }
    }
  }
})
