<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<tr>
	<td>
		<form:input cssClass="form-control" path="inquiryForm.attributes[${attributeNumber}].name"/>
	</td>
	<td>
		<form:input cssClass="form-control" path="inquiryForm.attributes[${attributeNumber}].value"/>
	</td>
</tr>