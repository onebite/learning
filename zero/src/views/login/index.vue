<template>
    <div class="login-view">
        <div class="layer bg" id="login"/>
        <div class="layer flex-center">
            <div class="form-group">
                <el-card>
                    <el-form ref="loginForm" label-position="top" :rules="rules" :model="formLogin">
                        <el-form-item prop="username">
                            <el-input type="text" v-model="formLogin.username" placeholder="用户名">
                                <i slot="prepend" class="fa fa-user-circle-o"></i>
                            </el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input type="text" v-model="formLogin.password" placeholder="用户名">
                                <i slot="prepend" class="fa fa-user-circle-o"></i>
                            </el-input>
                        </el-form-item>
                        <el-button @click="submit" type="primary" class="button-login">登录</el-button>
                    </el-form>
                </el-card>
            </div>
            <el-dialog title="快速选择用户" :visible.sync="dialogVisible" width="400px">
                <el-row :gutter="10" style="margin: -20px 0px -10px 0px;">
                    <el-col v-for="(user,index) in users" :key="index" :span="8">
                        <div class="user-btn" @click="handleUserBtnClick(user)">
                            <d2-icon name="user-circle-o"/>
                            <span>{{user.name}}</span>
                        </div>
                    </el-col>
                </el-row>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import {mapActions} from 'vuex'
export default {
    data () {
        return {
            dialogVisible: false,
            users: [
                {
            name: '管理员',
            username: 'admin',
            password: 'admin'
            },
            {
            name: '编辑',
            username: 'editor',
            password: 'editor'
            },
            {
            name: '用户1',
            username: 'user1',
            password: 'user1'
            }
        ],
        // 表单
        formLogin: {
            username: 'admin',
            password: 'admin',
            code: 'v9am'
        },
        // 校验
        rules: {
            username: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
            ],
            password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
            ],
            code: [
            { required: true, message: '请输入验证码', trigger: 'blur' }
            ]
        }
    }
        }
        
}
</script>
