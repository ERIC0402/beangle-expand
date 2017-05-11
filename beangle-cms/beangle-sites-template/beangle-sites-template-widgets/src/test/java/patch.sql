insert into
cms_widget_configs(id, widget_id, config, no, position, group_id, site_id, template_id)
select id, widget_id, config, no, position, group_id, site_id, template_id
from widget_configs;

update CMS_WIDGETS 
set class_name = substr(class_name,0,22) || 'cms' || substr(class_name,22)
where class_name like 'org.beangle.website.template%'

commit;