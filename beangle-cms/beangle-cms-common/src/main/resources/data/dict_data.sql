--广告位置类型
insert into SYS_DICT_TYPES (name, code, enabled, id)
     values ('广告位置', 'ADWZ', 1, seq_sys_dict_types.nextval);
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('首页中间', 'ADWZ_SYZJ', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'ADWZ'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('两边', 'ADWZ_LB', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'ADWZ'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('浮动', 'ADWZ_FD', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'ADWZ'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('顶部', 'ADWZ_DB', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'ADWZ'));