google 官方文档地址：https://developers.google.com/identity/sms-retriever/request?hl=zh-cn

使用之前请详细阅读使用说明
如果您的应用程序需要提交到google应用市场并且有短信验证码自动填充的功能 那么这个插件可以帮你完成Android手机的短信验证自动填充功能并且不会因为
短信权限的问题被google审核拒绝，官方方案值的你的选择。（注：此插件使用的SDK用到了google服务，所以国内因xxx等问题不推荐使用，仅限使用目标是海外项目）

应用要满足的前提条件
确保您应用的 build 文件使用以下值：

minSdkVersion 为 19 或更高版本
28 或更高版本的 compileSdkVersion

插件使用的依赖项
'''
    implementation 'com.google.android.gms:play-services-auth:20.4.1'
    implementation 'com.google.android.gms:play-services-auth-api-phone:18.0.1'
'''

插件使用方式：

插件名称：
1.引用原生插件
const annaAutomaticOtp = uni.requireNativePlugin('Anna-AutomaticOtp');
2.页面创建后开始监听
annaAutomaticOtp.registerOtpObserver(
					(ret) => {
						if (JSON.stringify(ret).result == 'S') {
							//成功获取短信-->JSON.stringify(ret).sms
							console.log(JSON.stringify(ret).sms);
						}else{
							//失败
							console.log(JSON.stringify(ret).error);
						}
					});
3.页面销毁前注销监听
annaAutomaticOtp.unRegisterOtpObserver();
4.调用接口发送验证码成功后
annaAutomaticOtp.startSmsRetriever(
					(ret) => {
						if (JSON.stringify(ret).result == 'F') {
							//失败
                            console.log(JSON.stringify(ret).error);
						}
					});


