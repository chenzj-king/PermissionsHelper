# PermissionsHelper
-----

Android6.0也出来很久了.运行时权限也是时候做一些适配了.刚好公司需要做一下兼容.看了一下现成有的轮子.[easypermissions](https://github.com/googlesamples/easypermissions)相对来讲比较简便.不过有点不太符合自己的使用场景.所以自己再定制一下.然后就有了这个项目.

## Usage

## JitPack.io

我把项目放到了[jitpack.io](https://jitpack.io).如果要使用请按照如下对项目进行配置.

    repositories {
    	//...
    	maven { url "https://jitpack.io" }
	}

	dependencies {
		//...
    	compile 'com.github.chenzj-king:PermissionsHelper:1.0'
	}

## 例子
![](http://i.imgur.com/GagdaOT.gif)

在BaseAct中进行权限管理相关的逻辑.其他Act都继承他即可.

	//在实现的Act中返回需要的权限.
    protected String[] getPermissions() {
        return new String[]{};
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @AfterPermissionGranted(RC_ALL_PERM)
    protected void checkPermission() {

        String[] perms = getPermissions();

        if (EasyPermissions.hasPermissions(this, perms)) {
			//所有权限都通过的回调
            doPermissinosSuc();
        } else {
			//进行相关的不再提示之类的检测
            String[] denieds = EasyPermissions.getDeniedPermissions(this, perms);
            if (!isNeverAskAgain) {
                EasyPermissions.requestPermissions(this, getString(R.string.rationale_all), RC_ALL_PERM, denieds);
            } else {

                ArrayList<String> deniedList = new ArrayList<>();
                Collections.addAll(deniedList, denieds);
                isNeverAskAgain = EasyPermissions.checkDeniedPermissionsNeverAskAgain(this, getString(R.string.rationale_ask_again),
                        R.string.setting, android.R.string.cancel, deniedList);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms.size() == getPermissions().length) {
			//判断到所有权限都获取到了
            doPermissinosSuc();
        } else {
            Log.e("BaseAct", "onPermissionsGranted: 有部分权限没有允许");
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
		//标记一下有没有部分权限被设置成拒绝并且不再提示
        isNeverAskAgain = EasyPermissions.checkDeniedPermissionsNeverAskAgain(this, getString(R.string.rationale_ask_again),
                R.string.setting, android.R.string.cancel, perms);
    }

## 记得一定要在onDestory的时候dis一下dialog ##

    @Override
    protected void onDestroy() {
        EasyPermissions.hidePermissionsDialog();
        super.onDestroy();
    }

## License ##

    Copyright (c) 2016 chenzj-king

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    Come on, don't tell me you read that.

## About me ##

- **QQ:** 364972027
- **Weibo:** [http://weibo.com/u/1829515680](http://weibo.com/u/1829515680)
- **Email:** admin@chenzhongjin.cn
- **Github:** [https://github.com/chenzj-king](https://github.com/chenzj-king)
- **Blog:** [http://www.chenzhongjin.cn](http://www.chenzhongjin.cn)
