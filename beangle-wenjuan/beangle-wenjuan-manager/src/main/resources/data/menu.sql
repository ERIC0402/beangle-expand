--scope：0：公开、1：公共、2：私有
--组卷管理
insert into se_menus (code, entry, name, title, parent_id, id, enabled, profile_id)
     values ('4.9', null, '组卷管理', '组卷管理', 
     (select max(id) from se_menus where code = '4' and profile_id = 3), seq_se_menus.nextval, 1, 3);
--试卷管理
insert into se_resources (name, title, id, enabled, scope, need_params)
     values ( '/wenjuan/sjgl', '试卷管理', seq_se_resources.nextval, 1, 2, 0);
insert into se_menus (code, entry, name, title, parent_id, id, enabled, profile_id)
     values ('4.9.1', '/wenjuan/sjgl', '试卷管理', '试卷管理', 
     (select max(id) from se_menus where code = '4.9' and profile_id = 3), seq_se_menus.nextval, 1, 3);
insert into se_menus_resources (menu_id, resource_id)
     values ((select max(id) from se_menus where code = '4.9.1' and profile_id = 3),
     (select max(id) from se_resources where name = '/wenjuan/sjgl'));
--评教管理
insert into se_resources (name, title, id, enabled, scope, need_params)
     values ( '/wenjuan/pjgl', '评教管理', seq_se_resources.nextval, 1, 2, 0);
insert into se_menus (code, entry, name, title, parent_id, id, enabled, profile_id)
     values ('4.9.2', '/wenjuan/pjgl', '评教管理', '评教管理', 
     (select max(id) from se_menus where code = '4.9' and profile_id = 3), seq_se_menus.nextval, 1, 3);
insert into se_menus_resources (menu_id, resource_id)
     values ((select max(id) from se_menus where code = '4.9.2' and profile_id = 3),
     (select max(id) from se_resources where name = '/wenjuan/pjgl'));
--选择XX
insert into se_resources (name, title, id, enabled, scope, need_params)
     values ( '/wenjuan/select', '资源选择', seq_se_resources.nextval, 1, 1, 0);