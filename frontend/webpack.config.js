const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    mainPage: path.resolve(__dirname, 'src', 'pages', 'mainPage.js'),
    loginPage: path.resolve(__dirname, 'src', 'pages', 'loginPage.js'),
    portfolioPage: path.resolve(__dirname, 'src', 'pages', 'portfolioPage.js'),
    portalPage: path.resolve(__dirname, 'src', 'pages', 'portalPage.js'),
    makeMoneyPage: path.resolve(__dirname, 'src', 'pages', 'makeMoneyPage.js'),

  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8080,
    open: true,
    openPage: 'http://localhost:8080/main.html',
    // diableHostChecks, otherwise we get an error about headers and the page won't render
    disableHostCheck: true,
    contentBase: 'packaging_additional_published_artifacts',
    // overlay shows a full-screen overlay in the browser when there are compiler errors or warnings
    overlay: true,
    proxy: [
      {
        context: [
          '/stocks',
          '/login',
          '/makeMoney'
        ],
        target: 'http://localhost:5001'
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/main.html',
      filename: 'main.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
          template: './src/login.html',
          filename: 'login.html',
          inject: false
        }),
    new HtmlWebpackPlugin({
              template: './src/portfolio.html',
              filename: 'portfolio.html',
              inject: false
            }),
    new HtmlWebpackPlugin({
                  template: './src/portal.html',
                  filename: 'portal.html',
                  inject: false
                }),
    new HtmlWebpackPlugin({
                      template: './src/makeMoney.html',
                      filename: 'makeMoney.html',
                      inject: false
                    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        },
      ]
    }),
    new CleanWebpackPlugin()
  ]
}
