/**
 * menu结构
 * 导航组 {
 * 导航组id,
 * 导航组名称,
 * 导航组链接,
 * 分组{分组id,分组名,菜单项{菜单id, 菜单名,菜单链接}}
 * }
 *
 */
var ioc = {
    UserMenus:{
        menus:[
            {id:'home', name:'首页', link:'/'},
            {id:'sms', name:'短信管理', link:'/sms',
                groups:[
                    {id:'smsmanage', name:'短信管理',
                        menus:[
                            {id:'sendsms', name:'短信发送', link:'/sms/send'},
                            {id:'queue', name:'查看队列', link:'/sms/queue'},
                            {id:'sendlog', name:'发送日志', link:'/sms/sendlog'}
                        ]
                    },
                    {id:'contactmanager', name:'通讯录管理',
                        menus:[
                            {id:'contacts', name:'查看通讯录', link:'/sms/contacts'}
                        ]
                    }

                ]
            },
            {id:'set', name:'查询余额', link:'/account',
                groups:[
                    {id:'account', name:'账户查询',
                        menus:[
                            {id:'accountsetting', name:'查询余额', link:'/account'},
                            {id:'password', name:'修改密码', link:'/password'}
                        ]
                    }
                ]
            }
        ]
    }
}