--问卷题目类型 单选题、多选题、排序题、网格题、填空题、简答题。
insert into SYS_DICT_TYPES (name, code, enabled, id)
     values ('问卷题目类型', 'WJ_TI_MU_LX', 1, seq_sys_dict_types.nextval);
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('判断题', 'WJ_TI_MU_LX_PAN_DUAN', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WJ_TI_MU_LX'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('单选题', 'WJ_TI_MU_LX_DAN_XUAN', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WJ_TI_MU_LX'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('多选题', 'WJ_TI_MU_LX_DUO_XUAN', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WJ_TI_MU_LX'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('排序题', 'WJ_TI_MU_LX_PAI_XU', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WJ_TI_MU_LX'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('网格题', 'WJ_TI_MU_LX_WANG_GE', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WJ_TI_MU_LX'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('填空题', 'WJ_TI_MU_LX_TIAN_KON', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WJ_TI_MU_LX'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('简答题', 'WJ_TI_MU_LX_JIAN_DA', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WJ_TI_MU_LX'));
--问卷分类
insert into SYS_DICT_TYPES (name, code, enabled, id)
     values ('问卷分类', 'WEN_JUAN_FL', 1, seq_sys_dict_types.nextval);
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('考试问卷', 'WEN_JUAN_FL_KS', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WEN_JUAN_FL'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('评教问卷', 'WEN_JUAN_FL_PJ', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WEN_JUAN_FL'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('调查问卷', 'WEN_JUAN_FL_DC', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WEN_JUAN_FL'));
--问卷类型 固定问卷、随机问卷
insert into SYS_DICT_TYPES (name, code, enabled, id)
     values ('问卷类型', 'WEN_JUAN_LX', 1, seq_sys_dict_types.nextval);
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('固定问卷', 'WEN_JUAN_LX_GD', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WEN_JUAN_LX'));
insert into SYS_DICT_DATAS (name, code, enabled, id, Dict_Type_Id)
     values ('随机问卷', 'WEN_JUAN_LX_SJ', 1, seq_sys_dict_datas.nextval, 
     (select max(id) from sys_dict_types where code = 'WEN_JUAN_LX'));