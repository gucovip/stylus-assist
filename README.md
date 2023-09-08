# stylus-assist
[![Build](https://github.com/gucovip/stylus-assist/workflows/Build/badge.svg)](https://github.com/gucovip/stylus-assist/actions/workflows/build.yaml)

新仓库：

[stylus-assist-jetbrains](https://github.com/happy-share-forever/stylus-assist-intellij)

[stylus-assist-vscode](https://github.com/happy-share-forever/stylus-assist-vscode)

项目启动时间 2018.2.24
<!-- Plugin description -->
帮助vue.js开发者 编辑 stylus 样式， 适用于 vue.js 的单文件组件。
<!-- Plugin description end -->

#### 获取Plugin
直接从 idea 插件仓库中搜索 Stylus Assist。

#### 技术交流
因为下载量达到30k+，重新组建 Stylus Assist 的交流QQ群：807838852，加群备注 Github 看到。

#### 快捷键
windows: `Alt + insert`, Mac: `control + Enter`

注意：可以在 keymap 中配置自己喜欢的快捷键，后续如果可以将默认自动设置好快捷键（InsertClass: command + \; PasteCss: control + option + v）。

#### 最近更新
* 2022.08.31  InsertClass 插入 class 时不再添加缩进。如果还需要缩进，可以下载 2.0.7 版本。
* 2022.08.4  2.0.7 兼容到 223 版本
* 2022.04.13  2.0.6 兼容到 221 版本
* 2021.12.09  2.0.5 修复新版本 213 不可见的问题。
* 2021.08.23  2.0.4 PasteCss 新增默认快捷键 ctrl(control) + alt(option) + v。
* 2021.08.17  2.0.3 GoCss 修复 非驼峰式 无法跳转的 bug。

##### PS
关于标准 class 名称是 .a-b-c 还是 .aBC 以往就一直存在争议，现在给出一些拙见：css modules 情况下，$style.xxx 驼峰更加符合编程思想同时也更加简洁；非 css modules 情况下，建议用 .a-b-c，另外 驼峰式 的 class 可以直接Ctrl/Command+鼠标左键 跳转到对应的 css class

如果还想保持 驼峰式 可以到下载 [2.0.0](https://plugins.jetbrains.com/plugin/download?rel=true&updateId=131276) 版本

#### 功能介绍

##### 简体中文
* GoCss
  * 跳转到样式
    * .vue 文件
    * .pug 跳转到 .styl 文件
  * 新增样式
    * 没有匹配的样式 创建标准格式class名称，例如：$style.aBC => .aBC
* PasteCss 格式化从其他地方复制的样式（不包含class或id等css选择器）
  * margin:0;  =>  margin 0
* InsertClass 在style标签的最后，插入新的样式class，致力于编写代码时，快速插入以提高编码效率。
  * .newClass
  *     |(光标定位于这里)
  * <\/style>
  

##### English
* GoCss
  * Jump to stylus css
    * .vue file
    * .pug jump to .styl file
  * New class mode
    * If plugin can not find selected class,it will think you want to create a new class,such as：$style.aBC =&gt; .aBC
* PasteCss format some css(don't contains class or other css selectors) you copy from another place, such as chrome or files
  * margin:0;  =&gt;  margin 0
* Insert class into zhe last of style-tag
  *   .newClass
  *     |
  * <\/style>

#### 获取帮助以及建议
QQ:11563928

#### 更新日志
* 2.1.0 InsertClass 插入 class 时不再添加缩进。如果还需要缩进，可以下载 2.0.7 版本。
* 2.0.7 兼容到 223 版本
* 2.0.6 兼容到 221 版本
* 2.0.5 修复新版本 213 不可见的问题。
* 2.0.4 PasteCss 新增默认快捷键 ctrl(control) + alt(option) + v。
* 2.0.3 GoCss 修复 非驼峰式 无法跳转的 bug。
* 2.0.2 GoCss 也将生成 驼峰式 的 class
* 2.0.1 InsertClass 不再格式化为短横线间隔，而是驼峰式。GoCss 依然支持查找短横线间隔的class。
* 2.0.0 调整整体代码架构兼容最新版的 IDE

##### 归档的日志
[CHANGELOG_ARCHIVED.md](./CHANGELOG_ARCHIVED.md)

#### 给我买杯咖啡 Buy me a coffee
![image](https://user-images.githubusercontent.com/13230237/128452500-292addd6-8bd6-42f1-aa9f-547341b0cb1e.png)

