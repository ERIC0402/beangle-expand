[#ftl]
{
faculties : [[#list faculties! as faculty]{id : ${faculty.id}, name : "[${(faculty.name?js_string)!}]${(faculty.fullname?js_string)!}"}[#if faculty_has_next],[/#if][/#list]]
}