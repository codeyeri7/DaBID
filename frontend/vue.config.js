module.exports = {
    devServer: {
        https: true,
        proxy: {
            '/dabid': {
                // target: 'http://localhost:8080/'
                target: 'http://i5a506.p.ssafy.io:8080/'
            },
            '/webjars': {
                target: 'http://i5a506.p.ssafy.io:8443/'
            },
            '/group-call': {
                target: 'http://i5a506.p.ssafy.io:8443/'
            },
            '/upload': {
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
