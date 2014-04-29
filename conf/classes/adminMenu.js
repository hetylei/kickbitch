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
    AdminMenus:{
        menus:[
            {id:'manage', name:'管理', link:'/manage',
                groups:[
                    {id:'user', name:'账户管理',
                        menus:[
                            {id:'userlist', name:'用户管理', link:'/manage/userlist'},
                            {id:'addlog', name:'充值记录', link:'/manage/addLog'}
                        ]
                    },
                    {id:'platform', name:'平台管理',
                        menus:[
                            {id:'msg', name:'平台公告', link:'/manage/msgsend'},
                            {id:'gfwword', name:'屏蔽词设置', link:'/manage/gfwword'},
                            {id:'queue', name:'短信队列', link:'/manage/queuelist'},
                            {id:'log', name:'短信日志', link:'/manage/loglist'}
                        ]
                    }
                ]
            }/*,

            {id:'tutorial', name:'教程', link:'/tutorial',
                groups:[
                    {id:'computerShop', name:'computerShop',
                        menus:[
                            {id:'computerShop1', name:'computerShop', link:'/tutorial/computerShop'},
                            {id:'flexGrid', name:'flexGrid', link:'/tutorial/computerShop/flexGrid'}
                        ]
                    },
                    {id:'test', name:'test',
                        menus:[
                            {id:'nb001', name:'宁波和易菜单1', link:'/tutorial/nb/001'},
                            {id:'nb002', name:'宁波和易菜单2', link:'/tutorial/nb/002'}
                        ]
                    }
                ]
            }  */
        ]
    }
}