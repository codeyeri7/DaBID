module.exports = {
    devServer: {
        // 3000 포트에서 vue 개발 서버를 실행합니다.(chat)
        // port:3000, // ---> 혹시 충돌날까봐 일단 주석처리합니다!
        https: true,
        proxy: {
            '/dabid': {
                target: 'http://i5a506.p.ssafy.io/'
                // target: 'http://localhost:8080/'
                // target: 'http://localhost:8080/'
                // target: 'http://i5a506.p.ssafy.io:8080/'
            },
            '/webjars': {
                // target: 'http://localhost:8080/'
                target: 'http://i5a506.p.ssafy.io:8443/'
            },
            '/group-call': {
                // target: 'http://localhost:8080/'
                target: 'http://i5a506.p.ssafy.io:8443/'
            },
            '/upload': {
                // target: 'http://localhost:8080/'
                target: 'http://i5a506.p.ssafy.io:8443/'
            }
        },
        historyApiFallback: true,
        hot: true
    },
    css: {
        requireModuleExtension: true
    },
    transpileDependencies: [
        'vuetify',
        'element-plus'
    ],
    lintOnSave: false,
    outputDir: '../backend/src/main/resources/dist'
}