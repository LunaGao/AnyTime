# Anytime介绍
[![Bless](https://cdn.rawgit.com/LunaGao/BlessYourCodeTag/master/tags/god.svg)](http://lunagao.github.io/BlessYourCodeTag/)

# 效果图&说明

![img](https://raw.githubusercontent.com/lzwjava/plan/master/anytime360.png)

这是从开发者征集到的一个应用，演示了：

* 用户注册、登陆、登出和忘记密码等用户系统相关的功能。
* 相对复杂一些的数据增删改查操作。
* 消息推送

项目开发记录和介绍在这里

http://www.cnblogs.com/maomishen/p/3577480.html

将学会

* 配置消息推送
* AVQuery
* 邮箱验证重设密码
* 用户登录注册
* 调用云函数
* 创建 AVObject

# 如何运行

## With Gradle

The easiest way to build is to install [Android Studio](https://developer.android.com/sdk/index.html) v1.+
with [Gradle](https://www.gradle.org/) v2.2.1.
Once installed, then you can import the project into Android Studio:

1. Open `File`
2. Import Project
3. Select `build.gradle` under the project directory
4. Click `OK`

Then, Gradle will do everything for you.

## With Eclipse

* 导入本工程到 Eclipse
* 右键点击项目，运行 `Run As -> Android Application`即可看到。

# 替换 App 信息

Demo 使用的是公共的 app id 和 app key，您可以在`com.luna.anytime.AnyTimeApplication`修改成您自己的应用 id 和 key。

您还需要导入 [data](./data) 目录下的数据，否则无法正常运行，可以在数据管理平台做数据导入。

# 协议

[Apache License 2](http://www.apache.org/licenses/LICENSE-2.0.html)

# 核心代码

```java
public class AVService {
  public static void countDoing(String doingObjectId, CountCallback countCallback) {
    AVQuery<AVObject> query = new AVQuery<AVObject>("DoingList");
    query.whereEqualTo("doingListChildObjectId", doingObjectId);
    Calendar c = Calendar.getInstance();
    c.add(Calendar.MINUTE, -10);
    // query.whereNotEqualTo("userObjectId", userId);
    query.whereGreaterThan("createdAt", c.getTime());
    query.countInBackground(countCallback);
  }

  //Use callFunctionMethod
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static void getAchievement(String userObjectId) {
    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("userObjectId", userObjectId);
    AVCloud.callFunctionInBackground("hello", parameters,
        new FunctionCallback() {
          @Override
          public void done(Object object, AVException e) {
            if (e == null) {
              Log.e("at", object.toString());// processResponse(object);
            } else {
              // handleError();
            }
          }
        });
  }

  public static void createDoing(String userId, String doingObjectId) {
    AVObject doing = new AVObject("DoingList");
    doing.put("userObjectId", userId);
    doing.put("doingListChildObjectId", doingObjectId);
    doing.saveInBackground();
  }

  public static void requestPasswordReset(String email, RequestPasswordResetCallback callback) {
    AVUser.requestPasswordResetInBackground(email, callback);
  }

  public static void findDoingListGroup(FindCallback<AVObject> findCallback) {
    AVQuery<AVObject> query = new AVQuery<AVObject>("DoingListGroup");
    query.orderByAscending("Index");
    query.findInBackground(findCallback);
  }

  public static void findChildrenList(String groupObjectId, FindCallback<AVObject> findCallback) {
    AVQuery<AVObject> query = new AVQuery<AVObject>("DoingListChild");
    query.orderByAscending("Index");
    query.whereEqualTo("parentObjectId", groupObjectId);
    query.findInBackground(findCallback);
  }

  public static void initPushService(Context ctx) {
    PushService.setDefaultPushCallback(ctx, LoginActivity.class);
    PushService.subscribe(ctx, "public", LoginActivity.class);
    AVInstallation.getCurrentInstallation().saveInBackground();
  }

  public static void signUp(String username, String password, String email, SignUpCallback signUpCallback) {
    AVUser user = new AVUser();
    user.setUsername(username);
    user.setPassword(password);
    user.setEmail(email);
    user.signUpInBackground(signUpCallback);
  }

  public static void logout() {
    AVUser.logOut();
  }

  public static void createAdvice(String userId, String advice, SaveCallback saveCallback) {
    AVObject doing = new AVObject("SuggestionByUser");
    doing.put("UserObjectId", userId);
    doing.put("UserSuggestion", advice);
    doing.saveInBackground(saveCallback);
  }
}

```
