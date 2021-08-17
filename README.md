# stylus-assist
项目启动时间 2018.2.24
<!-- Plugin description -->
帮助vue.js开发者 编辑 stylus 样式， 适用于 vue.js 的单文件组件。
<!-- Plugin description end -->

#### 获取Plugin
直接从idea插件仓库中搜索 Stylus Assist

#### 技术交流
因为下载量达到10k+，重新组建 Stylus Assist 的交流QQ群：807838852，加群备注 Github 看到。

#### 快捷键
windows: `Alt + insert`, Mac: `control + Enter`

#### 最近更新
* 2021.08.14  * 2.0.1 InsertClass 不再格式化为短横线间隔，而是驼峰式。GoCss 依然支持查找短横线间隔的class。

#### 功能介绍

##### 简体中文
* GoCss
  * 跳转到样式
    * .vue 文件
    * .pug 跳转到 .styl 文件
  * 新增样式
    * 没有匹配的样式 创建标准格式class名称，例如：$style.aBC => .a-b-c
* PasteCss 格式化从其他地方复制的样式（不包含class或id等css选择器）
  * margin:0;  =>  margin 0
* InsertClass 在style标签的最后，插入新的样式class，致力于编写代码时，快速插入以提高编码效率。
  * .newClass
  *     |(光标定位于这里)
  * <\/style>

##### PS
关于标准 class 名称是 .a-b-c 还是 .aBC 以往就一直存在争议，现在给出一些拙见：css modules 情况下，$style.xxx 驼峰更加符合编程思想同时也更加简洁；非 css modules 情况下，建议用 .a-b-c，

##### English
* GoCss
  * Jump to stylus css
    * .vue file
    * .pug jump to .styl file
  * New class mode
    * If plugin can not find selected class,it will think you want to create a new class,such as：$style.aBC =&gt; .a-b-c
* PasteCss format some css(don't contains class or other css selectors) you copy from another place, such as chrome or files
  * margin:0;  =&gt;  margin 0
* Insert class into zhe last of style-tag
  *   .newClass
  *     |
  * <\/style>

#### 获取帮助以及建议
QQ:11563928

#### 更新日志
* 2.0.1 InsertClass 不再格式化为短横线间隔，而是驼峰式。GoCss 依然支持查找短横线间隔的class。
* 2.0.0 调整整体代码架构兼容最新版的 IDE
* 1.4.4 优化代码
* 1.4.3 优化了InsertClass的首次插入操作。
* 1.4.2 修改开源项目地址。
* 1.4.1 修复没有style标签异常的情况，优化了实现缩小了插件大小。
* 1.4.0 新增InsertClass功能，该功能处于公共测试阶段，如有问题，欢迎在github上提出。
* 1.3.0 GoCss加入一直期待支持的 Navigate Back & Forward 快捷键来回切换。
* 1.2.7 优化PasteCss格式化代码的逻辑，区别处理混合代码的格式化效果；优化GoCss跳转后光标位置。
* 1.2.6 继续优化PasteCss将插入操作合并成一步，进一步方便使用。
* 1.2.5 优化PasteCss插入方式，现在会正确插入到指定位置。新增选中部分样式，可以直接替换插入，注意替换插入包含两步：删除和插入，因此撤销时需要两次才能撤销完善。
* 1.2.4 changes: 应平台要求，添加新的英文描述，需要中文描述的，可以去github查看。
* 1.2.1~1.2.3 changes: 支持多平台，修复一些小问题
* 1.2.0 changes: 新增功能PasteCss,该功能可以将从chrome中调试的css样式,直接按照标准格式粘贴入stylus
* 1.1.5 changes: 修复了一个匹配bug，之前container有可能会误匹配到.container-any，现在将正确匹配到.container
* 1.1.4 changes: 优化了选择逻辑，手动选择和自动选择将更加流畅，不光适用于.~"
* 1.1.3 changes: 新增光标位置自动选择，适用于：.~"的class
* 1.1.2 changes: 打包问题
* 1.1.1 changes: 修复bug
* 1.1.0 changes: 新增pug文件跳转styl文件的支持

#### 给我买杯咖啡 Buy me a coffee
![image](https://user-images.githubusercontent.com/13230237/128452500-292addd6-8bd6-42f1-aa9f-547341b0cb1e.png)

