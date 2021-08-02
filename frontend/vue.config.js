module.exports = {
    devServer: {
        https: true,
        proxy: {
            '/dabid': {
                // target: 'http://localhost:8080/'
                target: 'http://localhost:8080/'
            },
            '/webjars': {
                target: 'http://localhost:8443/'
            },
            '/group-call': {
                target: 'http://localhost:8443/'
            },
            '/upload': {
                target: 'http://localhost:8443/'
            }
        },
        historyApiFallback: true,
        hot: true
    },
    css: {
        requireModuleExtension: false
    },
    transpileDependencies: [
        'vuetify',
        'element-plus'
    ],
    lintOnSave: false,
}
