--数据字典树
insert into se_resources (name, title, id, enabled, scope, need_params)
     values ( '/system/dict-tree', '数据字典树', seq_se_resources.nextval, 1, 2, 0);

insert into se_menus (code, entry, name, title, parent_id, id, enabled, profile_id)
     values ('5.01.3', '/system/dict-tree', '数据字典树', '数据字典树', 
     (select max(id) from se_menus where code = '5.01' and profile_id = 3), seq_se_menus.nextval, 1, 3);
insert into se_menus_resources (menu_id, resource_id)
     values ((select max(id) from se_menus where code = '5.01.3' and profile_id = 3),
     (select max(id) from se_resources where name = '/system/dict-tree'));

--站内通知
insert into se_resources (name, title, id, enabled, scope, need_params)
     values ( '/system/zntz', '站内通知', seq_se_resources.nextval, 1, 2, 0);

insert into se_menus (code, entry, name, title, parent_id, id, enabled, profile_id)
     values ('5.01.4', '/system/zntz', '站内通知', '站内通知', 
     (select max(id) from se_menus where code = '5.01' and profile_id = 3), seq_se_menus.nextval, 1, 3);
insert into se_menus_resources (menu_id, resource_id)
     values ((select max(id) from se_menus where code = '5.01.4' and profile_id = 3),
     (select max(id) from se_resources where name = '/system/zntz'));

--站内消息
insert into se_resources (name, title, id, enabled, scope, need_params)
     values ( '/system/znxx', '站内消息', seq_se_resources.nextval, 1, 1, 0);
insert into se_resources (name, title, id, enabled, scope, need_params)
     values ( '/system/znxxhf', '站内消息回复', seq_se_resources.nextval, 1, 1, 0);
insert into se_menus (code, entry, name, title, parent_id, id, enabled, profile_id)
     values ('5.01.5', '/system/znxx', '站内消息', '站内消息', 
     (select max(id) from se_menus where code = '5.01' and profile_id = 3), seq_se_menus.nextval, 1, 3);
insert into se_menus_resources (menu_id, resource_id)
     values ((select max(id) from se_menus where code = '5.01.5' and profile_id = 3),
     (select max(id) from se_resources where name = '/system/znxx'));