
const port = process.env.port || process.env.npm_config_port || 8888

const path = require('path')

function resolve (dir) {
  return path.join(__dirname, './', dir)
}

module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  publicPath: process.env.NODE_ENV === 'production'
    ? './'
    : '/',
  chainWebpack: config => {
    config.plugin('define').tap(args => {
      const argv = process.argv
      const icourt = argv[argv.indexOf('--icourt-mode') + 1]

      args[0]['process.env'].MODE = `"${icourt}"`

      return args
    })
    // svg rule loader
    const svgRule = config.module.rule('svg') // 找到svg-loader
    svgRule.uses.clear() // 清除已有的loader, 如果不这样做会添加在此loader之后
    svgRule.exclude.add(/node_modules/) // 正则匹配排除node_modules目录
    svgRule // 添加svg新的loader处理
      .test(/\.svg$/)
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })

    // 修改images loader 添加svg处理
    const imagesRule = config.module.rule('images')
    imagesRule.exclude.add(resolve('src/icons'))
    config.module
      .rule('images')
      .test(/\.(png|jpe?g|gif|svg)(\?.*)?$/)
  },

  configureWebpack: {
    devServer: {
      port: port,
      proxy: {
        '/api': {
          target: 'http://localhost:8080',
          // 在本地会创建一个虚拟服务端，然后发送请求的数据，并同时接收请求的数据，这样服务端和服务端进行数据的交互就不会有跨域问题
          changeOrigin: true,
          ws: true
          // ,
          // pathRewrite: {
          //   '^/api': '/api'
          //   // 替换target中的请求地址，也就是说以后你在请求http://api.jisuapi.com/XXXXX这个地址的时候直接写成/api即可
          // }
        }

      }
    }
  }
}
