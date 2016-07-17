/*
* Copyright (c) 2016  DreamLiner Studio
* Licensed under the Apache License, Version 2.0 (the "License”);
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.dreamliner.easypermissions.sample.ui.activity;

import android.widget.Toast;

import com.dreamliner.easypermissions.sample.R;
import com.dreamliner.easypermissions.sample.common.PermissionConstant;
import com.dreamliner.easypermissions.sample.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected String[] getPermissions() {
        return PermissionConstant.PERMS;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void doPermissinosSuc() {
        Toast.makeText(MainActivity.this, "所有权限获取成功", Toast.LENGTH_SHORT).show();
    }
}
