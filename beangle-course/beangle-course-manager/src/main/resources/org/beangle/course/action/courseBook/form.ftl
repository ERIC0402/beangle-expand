[#ftl]
[@b.head/]
[@b.toolbar title="课程" entityId=course.id!0]bar.addBack();[/@]
[#include "../course/nav.ftl"/]
[@b.form action="!save"  title="课程教材信息" theme="list"]
	[@b.textfield label="教材名称" name="courseBook.name" value="${(courseBook.name)!}"  style="width:200px;" required="true" maxlength="200"/]
	[@b.textfield label="作者" name="courseBook.author" value="${(courseBook.author)!}" style="width:200px;" required="true" maxlength="200" /]
	[@b.textfield label="出版社" name="courseBook.publishingHouse" value="${(courseBook.publishingHouse)!}" style="width:200px;" required="true"  maxlength="100" /]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="courseBook.id" value="${courseBook.id!}" />
		<input type="hidden" name="courseId" value="${course.id!}" />
	[/@]
[/@]
[@b.foot/]