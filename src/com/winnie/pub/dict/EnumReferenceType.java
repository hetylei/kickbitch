package com.winnie.pub.dict;

public enum EnumReferenceType {
    /*按照引用表顺序列表tables
        0.独立表,没有别的外键引用,自己也不引用别的表
        1.纯引用表,引用了别的表格,自己没有被别的表引用
        2.混合引用表,引用了别的表格,自己也被别的表引用
        3.被引用表,没有引用别的表格,自己被别的表引用
    */
    Alone,
    Refer,
    Mixed,
    Host
}

