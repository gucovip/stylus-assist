# CHANGE LOG

## ARCHIVED
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
